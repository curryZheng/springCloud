package com.demo.shop.api.dto.req;

import com.demo.common.structure.RequestPageHead;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("查询商品信息req")
public class queryShopReq  extends RequestPageHead {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value="姓名")
	private String  name;
	
	@ApiModelProperty(value="商品类型")
	private Integer type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	
	
}
