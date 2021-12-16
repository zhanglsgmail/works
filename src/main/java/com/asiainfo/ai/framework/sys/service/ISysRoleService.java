package com.asiainfo.ai.framework.sys.service;

import com.asiainfo.ai.framework.sys.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 角色信息 服务类
 *
 * @author zhangls
 */
public interface ISysRoleService extends IService<SysRole> {

	/**
	 * 查询角色列表
	 * @param userId 用户id
	 * @return {@link List<SysRole>}
	 */
	List<SysRole> selectRoleList(Integer userId);

	/**
	 * 添加角色
	 * @param userId 用户id
	 * @param roleIds 角色id列表
	 * @return boolean
	 */
	boolean addRole(Integer userId, Integer... roleIds);

}
