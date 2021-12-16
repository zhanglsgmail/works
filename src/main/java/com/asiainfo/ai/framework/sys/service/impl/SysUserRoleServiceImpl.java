package com.asiainfo.ai.framework.sys.service.impl;

import com.asiainfo.ai.framework.sys.entity.SysUserRole;
import com.asiainfo.ai.framework.sys.mapper.SysUserRoleMapper;
import com.asiainfo.ai.framework.sys.service.ISysUserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户和角色关联表 服务实现类
 * </p>
 *
 * @author zhangls
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

	@Override
	public Set<Integer> selectRoleIdByUserId(Integer userId) {
		LambdaQueryWrapper<SysUserRole> queryWrapper = Wrappers.<SysUserRole>lambdaQuery()
				// 查询角色id
				.select(SysUserRole::getRoleId)
				// 根据用户id
				.eq(SysUserRole::getUserId, userId);
		List<SysUserRole> userRoles = this.list(queryWrapper);
		return userRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toSet());
	}

}
