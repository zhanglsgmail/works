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
 * <p>
 * 用户和角色关联表
 * </p>
 *
 * @author zhangls
 */
@Getter
@Setter
@TableName("sys_user_role")
@ApiModel(value = "用户角色关系对象", description = "用户和角色关联表")
public class SysUserRole {

	@ApiModelProperty("用户角色关系Id")
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	@ApiModelProperty("用户ID")
	private Integer userId;

	@ApiModelProperty("角色ID")
	private Integer roleId;

}
