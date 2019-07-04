package com.biwork.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
	 * 类描述：配置swagger2信息 
	 * 创建人：XiaChengwei 
	 * 创建时间：2017年7月28日 上午10:03:29
	 * @version 1.0
	 */
	@Configuration      //让Spring来加载该类配置
	@EnableWebMvc       //启用Mvc，非springboot框架需要引入注解@EnableWebMvc
	@EnableSwagger2     //启用Swagger2
	public class Swagger2Config {
	    @Bean
	    public Docket createRestApi() {
	    	  ParameterBuilder parameterBuilder = new ParameterBuilder();
	    	  
	          parameterBuilder
	                  .parameterType("header") //参数类型支持header, cookie, body, query etc
	                  .name("Authorization") //参数名
	                  .defaultValue("") //默认值
	                  .description("除了登录相关接口都需要提供Authorization,即登录返回token")
	                  .modelRef(new ModelRef("String"))//指定参数值的类型
	                  .required(false)
	                  .build(); //非必需，这里是全局配置，然而在登陆的时候是不用验证的
	          List<Parameter> parameterList = new ArrayList<>();
	          parameterList.add(parameterBuilder.build());
	   

	        return new Docket(DocumentationType.SWAGGER_2)
	                .apiInfo(apiInfo())
	                .globalOperationParameters(parameterList)
	                .select()
	                //扫描指定包中的swagger注解
	                //.apis(RequestHandlerSelectors.basePackage("com.xia.controller"))
	                //扫描所有有注解的api，用这种方式更灵活
	                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
	                .paths(PathSelectors.any())
	                .build();
	    }

	    private ApiInfo apiInfo() {
	        return new ApiInfoBuilder()
	                .title("biwork  APIs")
	                .description("biwork 接口文档")
	                .termsOfServiceUrl("")
	                .contact("Cyx")
	                .version("1.0.0")
	                .build();
	    }
	}

	

