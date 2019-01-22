package com.demo.common.structure;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * sql排序规则
 * 
 * @author Qiaoxin.Hong
 *
 */
public class RequestPageOrderByReq implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 字段名
	 */
	@ApiModelProperty(value = "字段名")
	private String field;
	
	/**
	 * 排序顺序，asc：升序；desc：降序。默认asc
	 */
	@ApiModelProperty(value = "asc：升序；desc：降序。默认asc")
	private String order = "asc";

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
}
