
package com.origen.faceshare.model.enums;

/**
 * 文件路径枚举
 * 
 * @author: origen
 * @date: 2017年8月3日
 */
public enum FilePath {

	COMPANYIMAGE("E:\\workspace\\FaceShare\\src\\main\\resources\\static\\upload"),
	HOUSEIMAGE("F:\\workspacenew\\FaceShare\\src\\main\\resources\\static\\upload");

	private String path;

	private FilePath(String path) {
		this.path = path;
	}

	public String getPath() {

		return path;
	}

	public void setPath(String path) {

		this.path = path;
	}

}
