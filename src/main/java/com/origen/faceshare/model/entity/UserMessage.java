
package com.origen.faceshare.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 用户消息表
 * 
 * @author: origen.wang
 * @date: 2017年8月7日
 */
@Entity
@Table(name = "t_user_message")
public class UserMessage implements Serializable {

	private static final long serialVersionUID = 188544579611245593L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	// 消息内容
	@Column(name = "content")
	private String content;

	// 消息发送人
	@ManyToOne
	@JoinColumn(name = "create_by")
	private User createBy;

	// 消息接收人
	@ManyToOne
	@JoinColumn(name = "accept_by")
	private User acceptBy;

	// 消息类型 (0:系统消息 ，1:普通用户消息 ，2:好友申请)
	@Column(name = "type")
	private String messageType;

	// 消息状态(0:未读，1:已读)
	@Column(name = "state")
	private String state;

	// 消息创建时间
	@Column(name = "createDate")
	private String createDate;

	public Integer getId() {

		return id;
	}

	public void setId(Integer id) {

		this.id = id;
	}

	public String getContent() {

		return content;
	}

	public void setContent(String content) {

		this.content = content;
	}

	public User getCreateBy() {

		return createBy;
	}

	public void setCreateBy(User createBy) {

		this.createBy = createBy;
	}

	public String getMessageType() {

		return messageType;
	}

	public void setMessageType(String messageType) {

		this.messageType = messageType;
	}

	public String getState() {

		return state;
	}

	public void setState(String state) {

		this.state = state;
	}

	public String getCreateDate() {

		return createDate;
	}

	public void setCreateDate(String createDate) {

		this.createDate = createDate;
	}

	public User getAcceptBy() {

		return acceptBy;
	}

	public void setAcceptBy(User acceptBy) {

		this.acceptBy = acceptBy;
	}

}
