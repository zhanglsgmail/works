package com.asiainfo.ai.framework.web.domain;

import com.asiainfo.ai.framework.sys.entity.SysUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 用户信息
 *
 * @author zhangls
 */
@NoArgsConstructor
@Getter
@Setter
public class UserInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** id */
	private Integer id;

	/** 电话 */
	private String phone;

	/** 姓名 */
	private String name;

	public UserInfo(SysUser user) {
		this.id = user.getId();
		this.phone = user.getPhoneNum();
		this.name = user.getRealName();
	}

}
