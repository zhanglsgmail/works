package com.asiainfo.ai.common.constant;

import java.util.concurrent.TimeUnit;

/**
 * 常量类
 *
 * @author zhangls
 */
public class Constant {

	/**
	 * 验证码过期时间 此处为五分钟
	 */
	public static Integer CODE_EXPIRE_TIME = 5;

	/**
	 * jwtToken过期时间 20分钟
	 */
	public static Long TOKEN_EXPIRE_TIME = TimeUnit.MINUTES.toMillis(20);

	/**
	 * jwtToken刷新时间 7天
	 */
	public static Long TOKEN_REFRESH_TIME = TimeUnit.DAYS.toMillis(7);

	/**
	 * token请求头名称
	 */
	public static String TOKEN_HEADER_NAME = "X-Token";

}
