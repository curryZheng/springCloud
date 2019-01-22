package com.demo.common.structure;

public class ResultPageBean<T> extends ResultBean<T> {

	/**
	 */
	private static final long serialVersionUID = -9054356524503419184L;

	private long total;
	private int pages;
	private int pageNum;
	private int pageSize;
	
	/**
	 * 创建一个无分页数据的分页结果数据
	 * @return
	 */
	public static <T> ResultPageBean<T> createNotPageResult() {
		ResultPageBean<T> result = new ResultPageBean<>();
		result.setTotal(0);
		result.setPages(0);
		result.setPageNum(1);
		return result;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
