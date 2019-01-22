package com.demo.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Import;

import com.demo.framework.aspectj.ControllerInterceptorConfig;
import com.demo.framework.aspectj.ServiceInterceptorConfig;
import com.demo.framework.config.DataSourceConfig;
import com.demo.framework.config.swagger.SwaggerConfig;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages= {"com.demo.user.*"}) //如果调用Feign接口此注解必须加 不然会报找不到bean
@Import(value= {DataSourceConfig.class,SwaggerConfig.class,ControllerInterceptorConfig.class,ServiceInterceptorConfig.class})
public class ShopApplication {

	public static void main(String[] args) {

		SpringApplication.run(ShopApplication.class, args);

	}

}
