package com.asiainfo.ai.framework.web.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础bean
 *
 * @author zhangls
 */
@Getter
@Setter
public class BaseBean implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("创建时间")
	@TableField(fill = FieldFill.INSERT)
	private Date createDate;

	@ApiModelProperty("创建人ID")
	@TableField(fill = FieldFill.INSERT)
	private Integer createBy;

	@ApiModelProperty("更新时间")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateDate;

	@ApiModelProperty("修改人ID")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Integer updateBy;
}
