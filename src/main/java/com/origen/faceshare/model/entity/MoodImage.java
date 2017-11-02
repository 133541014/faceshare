
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
 * 心情图片
 */
@Entity
@Table(name = "t_mood_image")
public class MoodImage implements Serializable {

	private static final long serialVersionUID = 5479687168694764373L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "image_url", length = 50)
	private String imageUrl;

	@ManyToOne
	@JoinColumn(name = "mood_id")
	private Mood mood;

	@Column(name = "create_date", length = 50)
	private String createDate;

	public Integer getId() {

		return id;
	}

	public void setId(Integer id) {

		this.id = id;
	}

	public String getImageUrl() {

		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {

		this.imageUrl = imageUrl;
	}

	public Mood getMood() {

		return mood;
	}

	public void setMood(Mood mood) {

		this.mood = mood;
	}

	public String getCreateDate() {

		return createDate;
	}

	public void setCreateDate(String createDate) {

		this.createDate = createDate;
	}

}
