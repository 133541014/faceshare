
package com.origen.faceshare.model.vo;

import java.io.Serializable;

public class UserVO implements Serializable {

	private static final long serialVersionUID = -1717351337237736515L;

	private Integer id;

	private String username;

	private String password;

	private String email;

	private String level;

	private String nickname;

	private String description;

	private String headImage;

	private String createDate;

	private Boolean isYourSelf;// 是否是登录用户自己,用于添加好友时检测

	public Integer getId() {

		return id;
	}

	public void setId(Integer id) {

		this.id = id;
	}

	public String getUsername() {

		return username;
	}

	public void setUsername(String username) {

		this.username = username;
	}

	public String getPassword() {

		return password;
	}

	public void setPassword(String password) {

		this.password = password;
	}

	public String getEmail() {

		return email;
	}

	public void setEmail(String email) {

		this.email = email;
	}

	public String getLevel() {

		return level;
	}

	public void setLevel(String level) {

		this.level = level;
	}

	public String getNickname() {

		return nickname;
	}

	public void setNickname(String nickname) {

		this.nickname = nickname;
	}

	public String getDescription() {

		return description;
	}

	public void setDescription(String description) {

		this.description = description;
	}

	public String getHeadImage() {

		return headImage;
	}

	public void setHeadImage(String headImage) {

		this.headImage = headImage;
	}

	public String getCreateDate() {

		return createDate;
	}

	public void setCreateDate(String createDate) {

		this.createDate = createDate;
	}

	public Boolean getIsYourSelf() {

		return isYourSelf;
	}

	public void setIsYourSelf(Boolean isYourSelf) {

		this.isYourSelf = isYourSelf;
	}

}
