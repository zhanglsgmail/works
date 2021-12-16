package com.asiainfo.ai.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhangls
 */
@AllArgsConstructor
@Getter
public enum RoleEnums {

	/**
	 * 管理员
	 */
	ADMIN(1000, "admin", "管理员"),
	/**
	 * 普通角色
	 */
	COMMON(1001, "common", "普通角色");

	private Integer code;

	private String name;

	private String msg;

}
