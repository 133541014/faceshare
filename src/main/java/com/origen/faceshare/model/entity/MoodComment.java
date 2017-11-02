
package com.origen.faceshare.model.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * 心情评论
 * 
 * @author: origen.wang
 * @date: 2017年8月18日
 */
@Entity
@Table(name = "t_mood_comment")
public class MoodComment implements Serializable {

	private static final long serialVersionUID = -6662539597068256923L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "content")
	private String content;

	@ManyToOne
	@JoinColumn(name = "create_by")
	private User createBy;

	@ManyToOne
	@JoinColumn(name = "moode_id")
	private Mood moodId;

	@Column(name = "create_date")
	private String createDate;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "moodComment")
	@OrderBy(value = "createDate ASC")
	private List<MoodCommentReply> replys;

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

	public Mood getMoodId() {

		return moodId;
	}

	public void setMoodId(Mood moodId) {

		this.moodId = moodId;
	}

	public String getCreateDate() {

		return createDate;
	}

	public void setCreateDate(String createDate) {

		this.createDate = createDate;
	}

	public List<MoodCommentReply> getReplys() {

		return replys;
	}

	public void setReplys(List<MoodCommentReply> replys) {

		this.replys = replys;
	}

}
