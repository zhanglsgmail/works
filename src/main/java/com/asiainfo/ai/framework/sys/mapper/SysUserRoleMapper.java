package com.asiainfo.ai.framework.sys.mapper;

import com.asiainfo.ai.framework.sys.entity.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户和角色关联表 Mapper 接口
 *
 * @author zhangls
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

}
