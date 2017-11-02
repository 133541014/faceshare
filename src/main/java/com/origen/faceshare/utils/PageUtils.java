
package com.origen.faceshare.utils;

import java.util.List;

/**
 * 分页工具类
 * 
 * @author: origen.wang
 * @date: 2017年8月14日
 */
public class PageUtils<T> {

	private Integer pageNo;// 当前页码

	private Integer totalPages;// 总页码

	private List<T> content; // 页面信息

	private Integer count;// 总记录数

	private Integer pageSize;// 每页显示记录数

	public Integer getPageNo() {

		return pageNo;
	}

	public void setPageNo(Integer pageNo) {

		this.pageNo = pageNo;
	}

	public Integer getTotalPages() {

		if ( count == null )
		{

			return totalPages;
		}

		totalPages = count % pageSize == 0 ? count % pageSize : count / pageSize + 1;

		return totalPages;

	}

	public void setTotalPages(Integer totalPages) {

		this.totalPages = totalPages;
	}

	public List<T> getContent() {

		return content;
	}

	public void setContent(List<T> content) {

		this.content = content;
	}

	public Integer getCount() {

		return count;
	}

	public void setCount(Integer count) {

		this.count = count;
	}

	public Integer getPageSize() {

		return pageSize;
	}

	public void setPageSize(Integer pageSize) {

		this.pageSize = pageSize;
	}

}
