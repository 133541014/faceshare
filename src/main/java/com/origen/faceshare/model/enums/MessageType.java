
package com.origen.faceshare.model.enums;

public enum MessageType {

	SYSTEM_MESSAGE("0"), USER_MESSAGE("1"), ADD_FRIEND_MESSAGE("2"), SYSTEM_MESSAGE_STR("系统消息"), USER_MESSAGE_STR("普通用户消息"), ADD_FRIEND_MESSAGE_STR("好友申请");

	private String value;

	private MessageType(String value) {
		this.value = value;
	}

	public String getValue() {

		return value;
	}

}
