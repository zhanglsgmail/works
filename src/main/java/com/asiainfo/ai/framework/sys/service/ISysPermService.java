package com.asiainfo.ai.framework.sys.service;

import com.asiainfo.ai.framework.sys.entity.SysPerm;
import com.baomidou.mybatisplus.extension.service.IService;


import java.util.Set;

/**
 * 菜单权限表 服务类
 *
 * @author zhangls
 */
public interface ISysPermService extends IService<SysPerm> {

	/**
	 * 通过角色id查询权限列表
	 * @param roleIds 角色id
	 * @return {@link Set<String>}
	 */
	Set<String> selectPermSetByRoleIds(Set<Integer> roleIds);

}
