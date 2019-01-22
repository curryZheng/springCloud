package com.demo.framework.config.swagger;

/**
 * swagger配置属性
 * 
 * @author Qiaoxin.Hong
 *
 */
public class SwaggerProperties {

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 版本
	 */
	private String version;

	/**
	 * 联系人名称
	 */
	private String name;

	/**
	 * 联系人url
	 */
	private String url;

	/**
	 * 联系人email
	 */
	private String email;
	
	/**
	 * 包路径
	 */
	private String basePackage;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}
	
	public String getBasePackage() {
		return basePackage;
	}
}
