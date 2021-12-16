package com.asiainfo.ai.framework.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.asiainfo.ai.framework.sys.entity.SysRole;
import com.asiainfo.ai.framework.sys.entity.SysUserRole;
import com.asiainfo.ai.framework.sys.mapper.SysRoleMapper;
import com.asiainfo.ai.framework.sys.service.ISysRoleService;
import com.asiainfo.ai.framework.sys.service.ISysUserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色信息 服务实现类
 *
 * @author zhangls
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    /**
     * 用户角色服务
     */
    private final ISysUserRoleService userRoleService;

    /**
     * 查询角色列表
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public List<SysRole> selectRoleList(Integer userId) {
        // 角色id列表
        Set<Integer> roleIds = userRoleService.selectRoleIdByUserId(userId);
        if (CollUtil.isEmpty(roleIds)) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<SysRole> queryWrapper = Wrappers.<SysRole>lambdaQuery()
                // 查询角色id,name,key
                .select(SysRole::getId, SysRole::getRoleName, SysRole::getRoleKey)
                // 根据角色id列表过滤
                .in(SysRole::getId, roleIds);
        return this.list(queryWrapper);
    }

    /**
     * 添加角色
     *
     * @param userId  用户id
     * @param roleIds 角色id列表
     * @return
     */
    @Override
    public boolean addRole(Integer userId, Integer... roleIds) {
        List<SysUserRole> userRoles = Arrays.stream(roleIds).map(roleId -> {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            return userRole;
        }).collect(Collectors.toList());
        return userRoleService.saveBatch(userRoles);
    }

}
