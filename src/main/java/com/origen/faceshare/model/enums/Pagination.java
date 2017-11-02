
package com.origen.faceshare.model.enums;

public enum Pagination {

	PAGE_SIZE_DEFAULT(10), // 每页显示信息条数

	PAGE_SIZE_MOOD(3);// 每页显示信息条数

	private int size;

	/**
	 * @param size
	 */
	private Pagination(int size) {
		this.size = size;
	}

	public int getSize() {

		return size;
	}

}
