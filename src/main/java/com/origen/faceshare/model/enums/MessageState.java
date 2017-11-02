
package com.origen.faceshare.model.enums;

public enum MessageState {

	NOT_READ("0"), ALREADY_READ("1");

	private String state;

	private MessageState(String state) {
		this.state = state;
	}

	public String getState() {

		return state;
	}

}
