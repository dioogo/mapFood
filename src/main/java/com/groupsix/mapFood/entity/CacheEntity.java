package com.groupsix.mapFood.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "cache")
public class CacheEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(columnDefinition = "LONGTEXT")
	private String data;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
