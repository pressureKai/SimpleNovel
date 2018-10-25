package com.simplynovel.zekai.simplynovel.domain;

import java.io.Serializable;

public class BookBodyUri implements Serializable {
	 private int id;
	 private String title;
     private String href;
     private String baseUrl;
     

     public String getBaseUrl() {
 		return baseUrl;
 	}
 	public void setBaseUrl(String baseUrl) {
 		this.baseUrl = baseUrl;
 	}
     public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}
}