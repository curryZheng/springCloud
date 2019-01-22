package com.demo.framework.config.swagger;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * swagger配置类
 * 
 * @author Qiaoxin.Hong
 *
 */
public class SwaggerFactoryBean implements FactoryBean<Docket>
	, InitializingBean {
	
	/** swagger配置属性 */
	private SwaggerProperties properties;
	
	/** swagger api */
	private Docket docket;
	
	public SwaggerFactoryBean() {
		super();
	}

	public SwaggerFactoryBean(SwaggerProperties properties) {
		super();
		this.properties = properties;
	}

	/**
	 * 创建swagger api
	 * @return
	 */
	protected Docket createRestApi() {
		Docket docket = new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(createApiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage(properties.getBasePackage()))
				.paths(PathSelectors.any())
				.build();
		return docket;
	}
	
	/**
	 * 创建API的基本信息
	 * @return
	 */
	protected ApiInfo createApiInfo() {
		ApiInfo apiInfo = new ApiInfoBuilder()
				.title(properties.getTitle())
				.description(properties.getDescription())
				.version(properties.getVersion())
				.contact(new Contact(properties.getName(), properties.getUrl(), properties.getEmail()))
				.build();
		return apiInfo;
	}

	@Override
	public Docket getObject() throws Exception {
		return docket;
	}

	@Override
	public Class<?> getObjectType() {
		return docket == null ? Docket.class : docket.getClass();
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.docket = createRestApi();
	}
	
	
	public SwaggerProperties getProperties() {
		return properties;
	}
	
	public void setProperties(SwaggerProperties properties) {
		this.properties = properties;
	}
}
