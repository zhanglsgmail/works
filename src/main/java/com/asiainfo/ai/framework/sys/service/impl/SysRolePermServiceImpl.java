package com.asiainfo.ai.framework.sys.service.impl;

import com.asiainfo.ai.framework.sys.entity.SysRolePerm;
import com.asiainfo.ai.framework.sys.mapper.SysRolePermMapper;
import com.asiainfo.ai.framework.sys.service.ISysRolePermService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色和菜单关联表 服务实现类
 *
 * @author zhangls
 */
@Service
public class SysRolePermServiceImpl extends ServiceImpl<SysRolePermMapper, SysRolePerm> implements ISysRolePermService {

    /**
     * 通过角色id查询权限id列表
     *
     * @param roleIds 角色id
     * @return
     */
    @Override
    public Set<Integer> selectPermIdsByRoleIds(Set<Integer> roleIds) {
        LambdaQueryWrapper<SysRolePerm> queryWrapper = Wrappers.<SysRolePerm>lambdaQuery()
                // 查询权限id
                .select(SysRolePerm::getPermId)
                // 根据角色id过滤
                .in(SysRolePerm::getRoleId, roleIds);
        return this.list(queryWrapper).stream().map(SysRolePerm::getPermId).collect(Collectors.toSet());
    }

}
