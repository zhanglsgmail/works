package com.asiainfo.ai.framework.sys.mapper;

import com.asiainfo.ai.framework.sys.entity.SysRolePerm;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色和权限关联表 Mapper 接口
 *
 * @author zhangls
 */
@Mapper
public interface SysRolePermMapper extends BaseMapper<SysRolePerm> {

}
