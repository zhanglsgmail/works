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
 * 菜单权限表
 *
 * @author zhangls
 */
@Getter
@Setter
@TableName("sys_perm")
@ApiModel(value = "权限对象", description = "权限表")
public class SysPerm extends BaseBean {

    @ApiModelProperty("权限id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 权限名称
     */
    @ApiModelProperty("权限名称")
    private String permName;

    /**
     * 权限标识
     */
    @ApiModelProperty("权限标识")
    private String permKey;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String permRemark;

    /**
     * 状态 1:可用状态; 2:锁定状态; 3:弃用状态
     */
    @ApiModelProperty("状态 1:可用状态; 2:锁定状态; 3:弃用状态")
    private Integer state;

}
