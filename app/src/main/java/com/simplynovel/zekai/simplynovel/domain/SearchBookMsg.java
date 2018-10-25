package com.simplynovel.zekai.simplynovel.domain;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class SearchBookMsg extends LitePalSupport implements Serializable {

	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getUpdatedes() {
		return updatedes;
	}
	public void setUpdatedes(String updatedes) {
		this.updatedes = updatedes;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	String img;
	String href;
	String bookName;
	String author;
	String type;
	String state;
	String des;
	String updatedes;
	String updatetime;
}
