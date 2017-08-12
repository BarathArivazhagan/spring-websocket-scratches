package com.barath.app;

public class AnalyticsDTO {
	
	private String name;
	
	private Long likes;
	
	private Long loves;

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

	public AnalyticsDTO(String name, Long likes, Long loves) {
		super();
		this.name = name;
		this.likes = likes;
		this.loves = loves;
	}

	public AnalyticsDTO() {
		super();
		
	}

	@Override
	public String toString() {
		return "AnalyticsDTO [name=" + name + ", likes=" + likes + ", loves=" + loves + "]";
	}
	
	
	
}
