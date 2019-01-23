package com.demo.gateway;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.demo.framework.config.swagger.SwaggerConfig;
import com.demo.gateway.filter.DemoFilterPost;
import com.demo.gateway.filter.DemoFilterPre;

@SpringCloudApplication
@EnableZuulProxy
@Import(value={SwaggerConfig.class})
@EnableFeignClients(basePackages = { "com.demo.*" })
@EnableDiscoveryClient
public class GatewayServiceApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}

	@Bean
	public DemoFilterPost demoFilterPost() {
		
		return new DemoFilterPost();
	}
	
	@Bean
	public DemoFilterPre  demoFilterPre() {
		
		return new DemoFilterPre();
	}
	
}
