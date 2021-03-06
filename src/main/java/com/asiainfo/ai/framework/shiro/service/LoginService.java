package com.asiainfo.ai.framework.shiro.service;

import cn.hutool.core.util.IdUtil;
import com.asiainfo.ai.common.constant.Constant;
import com.asiainfo.ai.common.constant.RedisKey;
import com.asiainfo.ai.common.enums.ErrorState;
import com.asiainfo.ai.common.utils.CommonsUtils;
import com.asiainfo.ai.framework.shiro.token.PhoneCodeToken;
import com.asiainfo.ai.framework.sys.entity.SysUser;
import com.asiainfo.ai.framework.sys.service.ISysUserService;
import com.asiainfo.ai.framework.web.domain.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 登陆服务
 *
 * @author zhangls
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    /**
     * 用户服务
     */
    private final ISysUserService userService;

    /**
     * redis模板
     */
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 令牌服务
     */
    private final TokenService tokenService;

    /**
     * 根据用户名查询是否已登陆
     *
     * @param phone
     * @return
     */
    public Boolean isLogin(String phone) {
        // 判断用户登陆信息Key是否存在 用户登陆KEY 7天失效
        return stringRedisTemplate.hasKey(RedisKey.getLoginCodeKey(phone));
    }

    /**
     * 发送登录验证码
     *
     * @param phone 电话
     * @return boolean
     */
    public boolean sendLoginCode(String phone) {
        if (isLogin(phone)) {
            log.info("用户名【{}】 - 用户已登陆，无需发送登陆验证码", phone);
            return false;
        }

        // 这里使用默认值
        int code = CommonsUtils.getCode();
        // todo 此处为发送验证码代码

        log.info("【{}】发送登录验证码:{}", phone, code);

        // 将验证码加密后存储到redis中
        String encryptCode = CommonsUtils.encryptPassword(String.valueOf(code), phone);
        stringRedisTemplate.opsForValue().set(RedisKey.getLoginCodeKey(phone), encryptCode, Constant.CODE_EXPIRE_TIME, TimeUnit.MINUTES);
        return true;
    }

    public boolean sendModifyPasswordCode(String phone) {
        int code = CommonsUtils.getCode();
        // todo 此处为发送验证码代码
        log.info("【{}】发送修改密码验证码:{}", phone, code);
        // 将验证码加密后存储到redis中
        String encryptCode = CommonsUtils.encryptPassword(String.valueOf(code), phone);
        stringRedisTemplate.opsForValue().set(RedisKey.getModifyPasswordCodeKey(phone), encryptCode, Constant.CODE_EXPIRE_TIME, TimeUnit.MINUTES);
        return true;
    }

    /**
     * 手机号密码登陆
     *
     * @param phone    电话
     * @param password 密码
     * @return {@link Result}
     */
    public Result loginByPassword(String phone, String password) {
        if (isLogin(phone)) {
            log.info("用户名【{}】 - 用户已登陆", phone);
            return Result.error(ErrorState.USER_HAS_LOGIN);
        }

        // 1.获取Subject
        Subject subject = SecurityUtils.getSubject();
        // 2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(phone, password);
        // 3.执行登录方法
        try {
            subject.login(token);
            return Result.success(returnLoginInitParam(phone));
        } catch (UnknownAccountException e) {
            return Result.error(ErrorState.USERNAME_NOT_EXIST);
        } catch (IncorrectCredentialsException e) {
            return Result.error(ErrorState.PASSWORD_ERROR);
        }
    }

    /**
     * 手机号验证码登录
     *
     * @param phone 电话
     * @param code  代码
     * @return {@link Result}
     */
    public Result loginByCode(String phone, String code) {
        if (isLogin(phone)) {
            log.info("用户名【{}】 - 用户已登陆", phone);
            return Result.error(ErrorState.USER_HAS_LOGIN);
        }

        // 1.获取Subject
        Subject subject = SecurityUtils.getSubject();
        SysUser sysUser = userService.selectUserByPhone(phone);
        // 2.验证码登录，如果该用户不存在则创建该用户
        if (Objects.isNull(sysUser)) {
            // 2.1 注册
            userService.register(phone);
        }
        // 3.封装用户数据
        PhoneCodeToken token = new PhoneCodeToken(phone, code);
        // 4.执行登录方法
        try {
            subject.login(token);
            return Result.success(returnLoginInitParam(phone));
        } catch (UnknownAccountException e) {
            return Result.error(ErrorState.USERNAME_NOT_EXIST);
        } catch (ExpiredCredentialsException e) {
            return Result.error(ErrorState.CODE_EXPIRE);
        } catch (IncorrectCredentialsException e) {
            return Result.error(ErrorState.CODE_ERROR);
        }
    }

    /**
     * 修改密码
     *
     * @param phone    手机号
     * @param code     验证码
     * @param password 密码
     * @return {@link Result}
     */
    public Result modifyPassword(String phone, String code, String password) {
        Object modifyCode = stringRedisTemplate.opsForValue().get(RedisKey.getModifyPasswordCodeKey(phone));
        // 判断redis中是否存在验证码
        if (Objects.isNull(modifyCode)) {
            return Result.error(ErrorState.CODE_EXPIRE);
        }
        String encryptCode = CommonsUtils.encryptPassword(code, phone);
        // 判断redis中code与传递过来的code 是否相等
        if (!Objects.equals(encryptCode, modifyCode.toString())) {
            return Result.error(ErrorState.CODE_ERROR);
        }
        SysUser user = userService.selectUserByPhone(phone);
        // 如果用户不存在，执行注册
        if (Objects.isNull(user)) {
            Boolean flag = userService.register(phone, password);
            if (flag) {
                return Result.success(this.returnLoginInitParam(phone));
            } else {
                return Result.error();
            }
        }
        // 加密所需盐值
        String salt = IdUtil.simpleUUID();
        // 加密后的密码
        String encryptPassword = CommonsUtils.encryptPassword(password, salt);
        user.setPassSalt(salt);
        user.setPassWord(encryptPassword);
        // 删除缓存
        stringRedisTemplate.delete(RedisKey.getLoginUserKey(phone));
        boolean flag = userService.updateById(user);
        if (flag) {
            return Result.success(this.returnLoginInitParam(phone));
        } else {
            return Result.error();
        }
    }

    /**
     * 返回登录后初始化参数
     *
     * @param phone phone
     * @return Map<String, Object>
     */
    private Map<String, Object> returnLoginInitParam(String phone) {
        Map<String, Object> data = new HashMap<>(4);
        // 根据手机号查询用户
        SysUser user = userService.selectUserByPhone(phone);
        // 生成jwtToken
        String token = tokenService.createToken(phone, String.valueOf(user.getId()), user.getPassWord(), Constant.TOKEN_EXPIRE_TIME);
        // 生成刷新token
        String refreshToken = tokenService.createToken(phone, String.valueOf(user.getId()), user.getPassWord(), Constant.TOKEN_REFRESH_TIME);
        // token
        data.put("token", token);
        // 刷新时所需token
        data.put("refreshToken", refreshToken);
        return data;
    }

    /**
     * token刷新
     *
     * @return Result
     */
    public Result tokenRefresh(String refreshToken) {
        String phone = tokenService.getPhone(refreshToken);
        SysUser user = userService.selectUserByPhone(phone);
        String userId = String.valueOf(user.getId());
        String password = user.getPassWord();
        boolean verify = tokenService.verify(refreshToken, password);
        if (!verify) {
            return Result.error(ErrorState.REFRESH_TOKEN_INVALID);
        }
        Map<String, Object> data = new HashMap<>(4);
        // 生成jwtToken
        String newToken = tokenService.createToken(phone, userId, password, Constant.TOKEN_EXPIRE_TIME);
        // 生成刷新token
        String newRefreshToken = tokenService.createToken(phone, userId, password, Constant.TOKEN_REFRESH_TIME);
        // toke
        data.put("token", newToken);
        // 刷新时所需token
        data.put("refreshToken", newRefreshToken);
        return Result.success(data);
    }

}
