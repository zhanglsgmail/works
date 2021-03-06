package com.asiainfo.ai.framework.shiro.realms;

import com.asiainfo.ai.framework.shiro.service.TokenService;
import com.asiainfo.ai.framework.sys.entity.SysUser;
import com.asiainfo.ai.framework.sys.service.ISysUserService;
import com.asiainfo.ai.framework.web.domain.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;

/**
 * @author zhangls
 */
@Slf4j
public class PasswordRealm extends AuthorizingRealm {

	@Resource
	private ISysUserService userService;

	/** 令牌服务 */
	@Resource
	private TokenService tokenService;

	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof UsernamePasswordToken;
	}

	/**
	 * 获取授权信息
	 * @param principals principals
	 * @return AuthorizationInfo
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		LoginUser loginUser = tokenService.getLoginUser();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		// 添加角色
		authorizationInfo.addRoles(loginUser.getRoleSet());
		// 添加权限
		authorizationInfo.addStringPermissions(loginUser.getPermissionsSet());
		return authorizationInfo;
	}

	/**
	 * 获取身份认证信息
	 * @param authenticationToken 身份认证凭据
	 * @return AuthenticationInfo 身份认证信息
	 * @throws AuthenticationException 身份认证期间抛出的异常
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		log.info(token.getUsername() + " - password auth start...");
		// 根据手机号查询用户
		SysUser user = userService.selectUserByPhone(token.getUsername());
		if (user == null) {
			// 抛出账号不存在异常
			throw new UnknownAccountException();
		}
		// 1.principal：认证的实体信息，可以是手机号，也可以是数据表对应的用户的实体类对象
		// 2.credentials：密码
		Object credentials = user.getPassWord();
		// 3.realmName：当前realm对象的name，调用父类的getName()方法即可
		String realmName = super.getName();
		// 4.盐,取用户信息中唯一的字段来生成盐值，避免由于两个用户原始密码相同，加密后的密码也相同
		ByteSource credentialsSalt = ByteSource.Util.bytes(user.getPassSalt());
		return new SimpleAuthenticationInfo(user, credentials, credentialsSalt, realmName);
	}

}
