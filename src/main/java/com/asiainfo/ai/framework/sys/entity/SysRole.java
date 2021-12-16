package com.asiainfo.ai.framework.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.asiainfo.ai.framework.web.domain.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 角色信息
 *
 * @author zhangls
 */
@Getter
@Setter
@TableName("sys_role")
@ApiModel(value = "SysRole对象", description = "角色信息")
public class SysRole extends BaseBean {

	@ApiModelProperty("角色id")
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	@ApiModelProperty("角色名称")
	private String roleName;

	@ApiModelProperty("角色权限字符串")
	private String roleKey;

	@ApiModelProperty("备注")
	private String roleRemark;

	@ApiModelProperty("状态 1:可用状态; 2:锁定状态; 3:弃用状态")
	private Boolean state;

}
