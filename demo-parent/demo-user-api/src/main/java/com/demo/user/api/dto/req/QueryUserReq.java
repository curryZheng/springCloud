package com.demo.user.api.dto.req;

import com.demo.common.structure.RequestPageHead;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 查询用户信息请求参数
 * @author leiZheng
 *
 *2019年1月15日
 */
@ApiModel("查询用户信息请求参数")
public class QueryUserReq extends RequestPageHead {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("姓名")
	private String name;
	
	@ApiModelProperty("城市")
	private String city;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	
	
}
