package com.simplynovel.zekai.simplynovel.domain;

public class ForumData {
	int id;
	String img;
	String name;
	String content;
	String contentHref;
	String updateTime ;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContentHref() {
		return contentHref;
	}
	public void setContentHref(String contentHref) {
		this.contentHref = contentHref;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}
