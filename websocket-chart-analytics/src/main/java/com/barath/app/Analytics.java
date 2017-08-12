package com.barath.app;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ANALYTICS")
public class Analytics {
	
	@Id
	@Column(name="ANALYTICS_ID")
	@GeneratedValue
	private Long id;
	
	@Column(name="NAME",unique=true)
	private String name;
	
	@Column(name="LIKES")
	private Long likes;
	
	@Column(name="ANALYTICS_LOVES")
	private Long loves;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getLikes() {
		return likes;
	}

	public void setLikes(Long likes) {
		this.likes = likes;
	}

	public Long getLoves() {
		return loves;
	}

	public void setLoves(Long loves) {
		this.loves = loves;
	}

	@Override
	public String toString() {
		return "Analytics [id=" + id + ", name=" + name + ", likes=" + likes + ", loves=" + loves + "]";
	}

	public Analytics(Long id, String name, Long likes, Long loves) {
		super();
		this.id = id;
		this.name = name;
		this.likes = likes;
		this.loves = loves;
	}

	public Analytics(String name, Long likes, Long loves) {
		super();
		this.name = name;
		this.likes = likes;
		this.loves = loves;
	}

	public Analytics() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	

}
