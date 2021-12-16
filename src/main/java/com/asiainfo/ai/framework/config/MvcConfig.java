package com.asiainfo.ai.framework.config;

import com.asiainfo.ai.framework.web.interceptor.RequestLimitInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zhangls
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

	/**
	 * 设置允许跨域请求的域名
	 * @param registry
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("*").allowCredentials(false).allowedMethods("*").maxAge(3600);
	}

	/**
	 * 请求限制拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(requestLimitInterceptor())
				.excludePathPatterns("/webjars/**", "/swagger-ui.html", "/swagger-resources/**", "/v2/**", "/images/**")
				.addPathPatterns("/**").order(Integer.MAX_VALUE - 1);
	}

	@Bean
	public RequestLimitInterceptor requestLimitInterceptor() {
		return new RequestLimitInterceptor();
	}

}
