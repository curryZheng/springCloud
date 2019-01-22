package com.demo.common.structure;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "RequestPageHead", description = "分页请求头")
public class RequestPageHead implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7119771909417406137L;

	@ApiModelProperty(value = "当前显示页下标")
	private int pageIndex = 1;

	@ApiModelProperty(value = "每页显示行数大小")
	private int pageSize = 1000;

//	@ApiModelProperty(value = "需要排序的字段[{\"field\":\"field1\",\"order\":\"desc\"},{\"field\":\"field2\"}]")
//	private String orderBy;
	
	/**
	 * <pre>
	 * 当在一次请求中需要分页多次查询同一数据集，此时除第一次需要查询总数量，来确定总页数，其余查询则可以不查询总数量来提高性能，
	 * 此时可将第一次查询出的总数量填充进此字段，来让PageHelper规避掉二次查询总数量的情况，
	 * </pre>
	 */
	private Long totalForExcludeQueryCount = null;
	
	@ApiModelProperty(value = "sql排序规则")
	private List<RequestPageOrderByReq> orderByList;

	public RequestPageHead() {
		super();
	}

	public RequestPageHead(int pageIndex, int pageSize) {
		super();
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
	}
	
	/**
	 * 规避掉二次查询总数量
	 * @param resultPageBean
	 */
	public void excludeQueryCount(Long total) {
		this.totalForExcludeQueryCount = total;
	}
	
	/**
	 * 是否启用了规避二次查询总数量
	 * @return
	 */
	public boolean isExcludeQueryCount() {
		return totalForExcludeQueryCount != null && totalForExcludeQueryCount >= 0;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

//	public String getOrderBy() {
//		return orderBy;
//	}
//
//	public void setOrderBy(String orderBy) {
//		this.orderBy = orderBy;
//	}
	
	public void setOrderByList(List<RequestPageOrderByReq> orderByList) {
		this.orderByList = orderByList;
	}
	
	public List<RequestPageOrderByReq> getOrderByList() {
		return orderByList;
	}
	
	public Long getTotalForExcludeQueryCount() {
		return totalForExcludeQueryCount;
	}
}
