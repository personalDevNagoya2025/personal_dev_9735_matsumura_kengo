package com.example.demo.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tasks")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "user_id")
	private Integer userId;
	private String title;
	@Column(name = "closing_date")
	private Date closingdate;
	private String memo;
	private Boolean deleted;
	@Column(name = "categories_id")
	private Integer categoriesId;
	
	public Task() {
	}
	
	public Task(String title, Date closingdate, String memo, Integer categoriesId) {
		this.title = title;
		this.closingdate = closingdate;
		this.memo = memo;
		this.deleted = false;
		this.categoriesId = categoriesId;
	}
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getId() {
		return id;
	}

	public Date getClosingdate() {
		return closingdate;
	}

	public void setClosingdate(Date closingdate) {
		this.closingdate = closingdate;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Integer getCategoriesId() {
		return categoriesId;
	}

	public void setCategoriesId(Integer categoriesId) {
		this.categoriesId = categoriesId;
	}
	
	
}
