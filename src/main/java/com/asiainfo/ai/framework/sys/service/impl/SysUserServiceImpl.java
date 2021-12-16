package com.asiainfo.ai.framework.sys.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.asiainfo.ai.common.enums.ErrorState;
import com.asiainfo.ai.common.enums.RoleEnums;
import com.asiainfo.ai.common.utils.CommonsUtils;
import com.asiainfo.ai.framework.shiro.service.PermissionsService;
import com.asiainfo.ai.framework.sys.entity.SysUser;
import com.asiainfo.ai.framework.sys.mapper.SysUserMapper;
import com.asiainfo.ai.framework.sys.service.ISysUserService;
import com.asiainfo.ai.framework.web.exception.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 用户信息 服务实现类
 *
 * @author zhangls
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

	private final PermissionsService permissionsService;

	private final TransactionTemplate transactionTemplate;

	@Override
	public SysUser selectUserByPhone(String phone) {
		LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.<SysUser>lambdaQuery()
				// 根据手机号过滤
				.eq(SysUser::getPhoneNum, phone).last("limit 1");
		return this.getOne(queryWrapper);
	}

	/**
	 * 用户注册,默认密码为手机号后六位
	 * @param phone phone
	 */
	@Override
	public Boolean register(String phone, String... args) {
		// 判断是否已存在该用户
		SysUser db = this.selectUserByPhone(phone);
		if (ObjectUtil.isNotNull(db)) {
			throw new ServiceException(ErrorState.USER_ALREADY_EXIST);
		}
		SysUser user = new SysUser();
		user.setPhoneNum(phone);
		// 如果有密码，则使用用户输入的密码
		String salt = IdUtil.simpleUUID();
		String encryptPassword;
		if (args.length > 0) {
			encryptPassword = CommonsUtils.encryptPassword(args[0], salt);
		}
		else {
			encryptPassword = CommonsUtils.encryptPassword(phone.substring(5, 11), salt);
		}
		user.setPassWord(encryptPassword);
		user.setPassSalt(salt);
		return transactionTemplate.execute(status -> {
			try {
				this.save(user);
				return permissionsService.addRole(user.getId(), RoleEnums.ADMIN.getCode(), RoleEnums.COMMON.getCode());
			}
			catch (Exception e) {
				// 回滚
				status.setRollbackOnly();
				return Boolean.FALSE;
			}
		});
	}

}
