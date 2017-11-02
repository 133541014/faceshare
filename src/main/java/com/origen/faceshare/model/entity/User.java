
package com.origen.faceshare.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * 用户表
 * 
 * @author: origen.wang
 * @date: 2017年8月7日
 */
@Entity
@Table(name = "t_user")
public class User implements Serializable {

	private static final long serialVersionUID = 2828146895450909300L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "username", length = 50)
	private String username;

	@Column(name = "password", length = 50)
	private String password;

	@Column(name = "email", length = 50)
	private String email;

	@Column(name = "level", length = 10)
	private String level;

	@Column(name = "nickname", length = 50)
	private String nickname;

	@Column(name = "description", length = 500)
	private String description;

	@Column(name = "head_image", length = 50)
	private String headImage;// 头像

	@Column(name = "create_date", length = 20)
	private String createDate;

	// 心情(mappedBy = "user" 定义在关系被拥有方,它指向关系拥有方的user属性)
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
	private Set<Mood> moods = new HashSet<Mood>();

	// 用户拥有的好友列表
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "t_user_friend", joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "friend_id", referencedColumnName = "id") })
	private List<User> friends;

	// 好友拥有的用户列表
	@ManyToMany(mappedBy = "friends")
	private List<User> users;

	// 发送信息列表
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "createBy")
	private List<UserMessage> sendMessages;

	// 接收信息列表
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "acceptBy")
	@OrderBy(value = "state ASC") // 信息排序
	private List<UserMessage> accpetMessages;

	// 心情评论
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "createBy")
	@OrderBy(value = "createDate DESC")
	private List<MoodComment> comments;

	// 创建的心情回复
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "createBy")
	@OrderBy(value = "createDate DESC")
	private List<MoodCommentReply> createReplys;

	// 接收的心情回复
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "acceptBy")
	@OrderBy(value = "createDate DESC")
	private List<MoodCommentReply> acceptReplys;

	// 日记
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "createBy")
	@OrderBy(value = "createDate DESC")
	private List<Diary> diarys = new ArrayList<Diary>();

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

	public Set<Mood> getMoods() {

		return moods;
	}

	public void setMoods(Set<Mood> moods) {

		this.moods = moods;
	}

	public List<UserMessage> getSendMessages() {

		return sendMessages;
	}

	public void setSendMessages(List<UserMessage> sendMessages) {

		this.sendMessages = sendMessages;
	}

	public List<UserMessage> getAccpetMessages() {

		return accpetMessages;
	}

	public void setAccpetMessages(List<UserMessage> accpetMessages) {

		this.accpetMessages = accpetMessages;
	}

	public List<User> getFriends() {

		return friends;
	}

	public void setFriends(List<User> friends) {

		this.friends = friends;
	}

	public List<User> getUsers() {

		return users;
	}

	public void setUsers(List<User> users) {

		this.users = users;
	}

	public List<MoodComment> getComments() {

		return comments;
	}

	public void setComments(List<MoodComment> comments) {

		this.comments = comments;
	}

	public List<MoodCommentReply> getCreateReplys() {

		return createReplys;
	}

	public void setCreateReplys(List<MoodCommentReply> createReplys) {

		this.createReplys = createReplys;
	}

	public List<MoodCommentReply> getAcceptReplys() {

		return acceptReplys;
	}

	public void setAcceptReplys(List<MoodCommentReply> acceptReplys) {

		this.acceptReplys = acceptReplys;
	}

	public List<Diary> getDiarys() {

		return diarys;
	}

	public void setDiarys(List<Diary> diarys) {

		this.diarys = diarys;
	}

}
