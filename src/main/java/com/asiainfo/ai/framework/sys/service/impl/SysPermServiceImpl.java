package com.asiainfo.ai.framework.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.asiainfo.ai.framework.sys.entity.SysPerm;
import com.asiainfo.ai.framework.sys.mapper.SysPermMapper;
import com.asiainfo.ai.framework.sys.service.ISysPermService;
import com.asiainfo.ai.framework.sys.service.ISysRolePermService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 菜单权限表 服务实现类
 *
 * @author zhangls
 */
@Service
@RequiredArgsConstructor
public class SysPermServiceImpl extends ServiceImpl<SysPermMapper, SysPerm> implements ISysPermService {

    /**
     * 角色权限服务
     */
    private final ISysRolePermService rolePermService;

    /**
     * 通过角色id查询权限列表
     *
     * @param roleIds 角色id
     * @return
     */
    @Override
    public Set<String> selectPermSetByRoleIds(Set<Integer> roleIds) {
        Set<Integer> permIds = rolePermService.selectPermIdsByRoleIds(roleIds);
        if (CollUtil.isEmpty(permIds)) {
            return Collections.emptySet();
        }
        LambdaQueryWrapper<SysPerm> queryWrapper = Wrappers.<SysPerm>lambdaQuery()
                // 查询权限值
                .select(SysPerm::getPermKey)
                // 根据权限id列表
                .in(SysPerm::getId, permIds);

        return this.list(queryWrapper).stream().map(SysPerm::getPermKey).collect(Collectors.toSet());
    }

}
