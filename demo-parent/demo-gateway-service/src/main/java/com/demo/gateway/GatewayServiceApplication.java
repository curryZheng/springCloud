package com.demo.gateway;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Import;

import com.demo.framework.config.swagger.SwaggerConfig;

@SpringCloudApplication
@EnableZuulProxy
@Import(value={SwaggerConfig.class})
@EnableDiscoveryClient
public class GatewayServiceApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}

//	@Bean
//	public FilterTest filterTest() {
//		
//		return new FilterTest();
//	}
	
}
