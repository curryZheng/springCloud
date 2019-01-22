package com.demo.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import com.demo.framework.config.DataSourceConfig;
import com.demo.framework.config.swagger.SwaggerConfig;
import com.demo.framework.utils.ApplicationContextUtils;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages= {"com.demo.shop.*"})
//@EnableFeignClients注解用于开启Feign远程服务调用功能
@Import(value= {DataSourceConfig.class,SwaggerConfig.class})
public class UserApplication {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(UserApplication.class, args);
	    //保存spring 上下文
		ApplicationContextUtils.setContext(context);
	}

}
