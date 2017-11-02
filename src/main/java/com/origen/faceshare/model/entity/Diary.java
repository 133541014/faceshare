
package com.origen.faceshare.model.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 日记表
 * 
 * @author: origen.wang
 * @date: 2017年8月30日
 */
@Entity
@Table(name = "t_diary")
public class Diary implements Serializable {

	private static final long serialVersionUID = -1711884105801500236L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "title")
	private String title;

	@Column(name = "content")
	@Lob // 因内容较多,故采用long text类型存储数据
	@Basic(fetch = FetchType.LAZY) // 采用延迟加载
	private String content;

	// 创建人
	@ManyToOne
	@JoinColumn(name = "create_by")
	private User createBy;

	@Column(name = "create_date")
	private String createDate;

	@Column(name = "update_date")
	private String updateDate;

	public Integer getId() {

		return id;
	}

	public void setId(Integer id) {

		this.id = id;
	}

	public String getTitle() {

		return title;
	}

	public void setTitle(String title) {

		this.title = title;
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

	public String getCreateDate() {

		return createDate;
	}

	public void setCreateDate(String createDate) {

		this.createDate = createDate;
	}

	public String getUpdateDate() {

		return updateDate;
	}

	public void setUpdateDate(String updateDate) {

		this.updateDate = updateDate;
	}

}
