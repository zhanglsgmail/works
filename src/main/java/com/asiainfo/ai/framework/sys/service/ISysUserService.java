package com.asiainfo.ai.framework.sys.service;

import com.asiainfo.ai.framework.sys.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户信息 服务类
 *
 * @author zhangls
 */
public interface ISysUserService extends IService<SysUser> {

	/**
	 * 根据手机号查询用户
	 * @param phone phone
	 * @return User
	 */
	SysUser selectUserByPhone(String phone);

	/**
	 * 用户注册,默认密码为手机号后六位
	 * @param phone phone
	 * @param args 密码
	 * @return boolean
	 */
	Boolean register(String phone, String... args);

}
