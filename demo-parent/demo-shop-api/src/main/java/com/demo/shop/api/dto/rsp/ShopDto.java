package com.demo.shop.api.dto.rsp;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("商品信息DTO")
public class ShopDto  {

	@ApiModelProperty("编号")
	private Integer id;
	
	@ApiModelProperty("商品名称")
	private String name;
	
	@ApiModelProperty("类型 1电脑2手机3耳机")
	private Integer type;
	
	@ApiModelProperty("价格")
	private BigDecimal price;
	
	@ApiModelProperty("用户姓名")
	private String  userName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
