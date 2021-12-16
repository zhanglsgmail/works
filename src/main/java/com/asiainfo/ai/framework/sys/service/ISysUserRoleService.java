package com.asiainfo.ai.framework.sys.service;

import com.asiainfo.ai.framework.sys.entity.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
 * 用户和角色关联表 服务类
 *
 * @author zhangls
 */
public interface ISysUserRoleService extends IService<SysUserRole> {

	/**
	 * 通过用户id查询角色id列表
	 * @param userId 用户id
	 * @return {@link Set<String>}
	 */
	Set<Integer> selectRoleIdByUserId(Integer userId);

}
