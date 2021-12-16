package com.asiainfo.ai.common.constant;

import cn.hutool.crypto.digest.MD5;

/**
 * @author zhangls
 */
public class RedisKey {

	public static String getLoginCodeKey(String phone) {
		return "LOGIN:CODE:" + phone;
	}

	public static String getModifyPasswordCodeKey(String phone) {
		return "MODIFY:PWD:CODE:" + phone;
	}

	public static String getLoginUserKey(String phone) {
		return "LOGIN:USER:" + phone;
	}

	public static String getRequestLimitKey(String servletPath, String phone) {
		return "LIMIT:" + MD5.create().digestHex(servletPath + phone);
	}

}
