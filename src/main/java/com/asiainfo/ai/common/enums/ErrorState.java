package com.asiainfo.ai.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lixiao
 * @date 2019/8/5 17:16 本项目异常使用四位数 第三方接口异常使用五位数
 */
@AllArgsConstructor
@Getter
public enum ErrorState {

	/** 短信发送失败 */
	SEND_SMS_ERROR(10001, "短信发送失败"),

	/** 用户名已存在 */
	USER_ALREADY_EXIST(1001, "用户名已存在"),

	/** 验证码无效 */
	CODE_EXPIRE(1002, "验证码无效"),

	/** 验证码不正确 */
	CODE_ERROR(1003, "验证码不正确"),

	/** 用户名不存在 */
	USERNAME_NOT_EXIST(1004, "用户名不存在"),

	/** 密码错误 *///
	PASSWORD_ERROR(1005, "密码不正确"),

	/** 没有相关权限 */
	NOT_AUTH(1006, "没有相关权限"),

	/** 令牌无效 */
	TOKEN_INVALID(1007, "token failure!"),

	/** 缺少相应参数 */
	MISSING_PARAMETER(1008, "参数绑定失败:缺少参数"),

	/** 接口请求限制 */
	REQUEST_LIMIT(1009, "请求频繁,请稍后重试"),

	/** 刷新令牌无效 */
	REFRESH_TOKEN_INVALID(1010, "认证过期,请重新登录"),

	/** 用户已登陆，请不要重复登陆 */
	USER_HAS_LOGIN(1011, "用户已登陆，请不要重复登陆"),

	;

	private final Integer code;

	private final String msg;

}
