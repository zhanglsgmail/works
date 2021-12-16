package com.asiainfo.ai.framework.sys.service;

import com.asiainfo.ai.framework.sys.entity.SysRolePerm;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
 * 角色和菜单关联表 服务类
 *
 * @author zhangls
 */
public interface ISysRolePermService extends IService<SysRolePerm> {

	/**
	 * 通过角色id查询权限id列表
	 * @param roleIds 角色id
	 * @return {@link Set<String>}
	 */
	Set<Integer> selectPermIdsByRoleIds(Set<Integer> roleIds);

}
