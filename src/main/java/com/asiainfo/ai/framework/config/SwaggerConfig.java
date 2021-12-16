package com.asiainfo.ai.framework.config;

import com.asiainfo.ai.common.annotction.PhoneNumber;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author zhangls
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				// 创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
				.apiInfo(apiInfo())
				// 设置哪些接口暴露给Swagger展示
				.select()
				// 指定扫描有Api注解的包
				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				// 扫描指定包中的swagger注解
				// 扫描所有 .apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build()
				/* 设置安全模式，swagger可以设置访问token */
				// .securitySchemes(securitySchemes()).securityContexts(securityContexts())
				.ignoredParameterTypes(PhoneNumber.class);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("工作量上报系统").description("接口文档").termsOfServiceUrl("").version("1.0.0").build();
	}

}
