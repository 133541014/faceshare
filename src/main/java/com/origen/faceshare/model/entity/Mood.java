
package com.origen.faceshare.model.entity;

import java.io.Serializable;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * 心情表
 * 
 * @author: origen.wang
 * @date: 2017年8月18日
 */
@Entity
@Table(name = "t_mood")
public class Mood implements Serializable {

	private static final long serialVersionUID = -7431116121111572199L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "content", length = 500)
	private String content;

	// 创建人
	@ManyToOne
	@JoinColumn(name = "create_by")
	private User user;

	// 心情图片
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "mood")
	@OrderBy(value = "createDate ASC")
	private Set<MoodImage> images = new HashSet<MoodImage>();

	@Column(name = "create_date", length = 50)
	private String createDate;

	// 心情评论
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "moodId")
	private List<MoodComment> comments;

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

	public User getUser() {

		return user;
	}

	public void setUser(User user) {

		this.user = user;
	}

	public Set<MoodImage> getImages() {

		return images;
	}

	public void setImages(Set<MoodImage> images) {

		this.images = images;
	}

	public String getCreateDate() {

		return createDate;
	}

	public void setCreateDate(String createDate) {

		this.createDate = createDate;
	}

	public List<MoodComment> getComments() {

		return comments;
	}

	public void setComments(List<MoodComment> comments) {

		this.comments = comments;
	}

}
