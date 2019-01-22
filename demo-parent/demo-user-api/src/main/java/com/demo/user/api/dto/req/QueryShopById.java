package com.demo.user.api.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户编号
 * 
 * @author leiZheng
 *
 *         2019年1月17日
 */
@ApiModel(value = "根据用户ID查询用户请求参数")
public class QueryShopById {

	public QueryShopById() {

	}

	public QueryShopById(String id) {
		super();
		this.id = id;

	}

	@ApiModelProperty("id")
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
