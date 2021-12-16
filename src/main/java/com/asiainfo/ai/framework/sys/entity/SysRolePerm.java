package com.asiainfo.ai.framework.sys.entity;

import com.asiainfo.ai.framework.web.domain.BaseBean;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 角色和菜单关联表
 *
 * @author zhangls
 */
@Getter
@Setter
@TableName("sys_role_perm")
@ApiModel(value = "角色权限关系对象", description = "角色和菜单关联表")
public class SysRolePerm {

    @ApiModelProperty("角色权限关系id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("角色ID")
    private Integer roleId;

    @ApiModelProperty("权限ID")
    private Integer permId;

}
