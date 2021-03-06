package com.asiainfo.ai.framework.shiro.service;

import cn.hutool.core.util.StrUtil;
import com.asiainfo.ai.common.constant.Constant;
import com.asiainfo.ai.common.constant.RedisKey;
import com.asiainfo.ai.common.utils.ServletUtils;
import com.asiainfo.ai.framework.sys.entity.SysUser;
import com.asiainfo.ai.framework.sys.service.ISysUserService;
import com.asiainfo.ai.framework.web.domain.LoginUser;
import com.asiainfo.ai.framework.web.domain.UserInfo;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangls
 */
@Service
@RequiredArgsConstructor
public class TokenService {

    /**
     * 用户服务
     */
    private final ISysUserService userService;

    /**
     * 权限服务
     */
    private final PermissionsService permissionsService;

    @Resource
    private RedisTemplate<String, LoginUser> redisTemplate;

    /**
     * 获取当前登录的User对象
     *
     * @return User
     */
    public LoginUser getLoginUser() {
        // 获取token
        String token = getToken(ServletUtils.getRequest());
        // 获取手机号
        String phone = getPhone(token);
        // 获取缓存loginUserKey
        String loginUserKey = RedisKey.getLoginUserKey(phone);
        // 获取缓存loginUser
        LoginUser cacheObject = redisTemplate.opsForValue().get(loginUserKey);
        if (cacheObject == null) {
            LoginUser loginUser = new LoginUser();
            // 获取当前登录用户
            SysUser user = userService.selectUserByPhone(phone);
            loginUser.setUser(new UserInfo(user));
            // 填充角色与权限
            permissionsService.addRoleAndPerms(loginUser);
            // 缓存当前登录用户
            redisTemplate.opsForValue().set(loginUserKey, loginUser, 7, TimeUnit.DAYS);
            return loginUser;
        }
        return cacheObject;
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @param token token
     * @return token中包含的用户手机号
     */
    public String getPhone(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("phone").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @param token token
     * @return token中包含的用户id
     */
    public String getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userId").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获取当前登录用户的token,如果token为null则获取refreshToken
     *
     * @param request HttpServletRequest
     * @return token
     */
    public String getToken(HttpServletRequest request) {
        String token = request.getHeader(Constant.TOKEN_HEADER_NAME);
        if (StrUtil.isBlank(token)) {
            return request.getParameter("refreshToken");
        } else {
            return token;
        }
    }

    /**
     * @param phone  用户名/手机号
     * @param userId 用户id
     * @param secret 用户的密码
     * @param time   token的有效时间 单位:毫秒
     * @return 加密的token
     */
    public String createToken(String phone, String userId, String secret, Long time) {
        Date date = new Date(System.currentTimeMillis() + time);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create().withClaim("phone", phone).withClaim("userId", userId).withExpiresAt(date).sign(algorithm);
    }

    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public boolean verify(String token, String secret) {
        try {
            // 根据密码生成JWT效验器
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).withClaim("phone", getPhone(token)).withClaim("userId", getUserId(token)).build();
            // 效验TOKEN
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }
    }

}
