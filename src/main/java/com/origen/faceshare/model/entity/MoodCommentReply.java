
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
 * 心情评论回复
 * 
 * @author: origen.wang
 * @date: 2017年8月18日
 */
@Entity
@Table(name = "t_mood_comment_reply")
public class MoodCommentReply implements Serializable {

	private static final long serialVersionUID = 2318057813030715402L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "content")
	private String content;

	@Column(name = "create_date")
	private String createDate;

	@ManyToOne
	@JoinColumn(name = "create_by")
	private User createBy;

	@ManyToOne
	@JoinColumn(name = "accept_by")
	private User acceptBy;

	@ManyToOne
	@JoinColumn(name = "mood_comment")
	private MoodComment moodComment;

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

	public String getCreateDate() {

		return createDate;
	}

	public void setCreateDate(String createDate) {

		this.createDate = createDate;
	}

	public User getCreateBy() {

		return createBy;
	}

	public void setCreateBy(User createBy) {

		this.createBy = createBy;
	}

	public User getAcceptBy() {
		return acceptBy;
	}

	public void setAcceptBy(User acceptBy) {
		this.acceptBy = acceptBy;
	}

	public MoodComment getMoodComment() {

		return moodComment;
	}

	public void setMoodComment(MoodComment moodComment) {

		this.moodComment = moodComment;
	}

}
