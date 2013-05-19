package com.provida.search.shared;

import java.io.Serializable;

public class WebResult implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String url;
	private String snippet;
	private String title;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSnippet() {
		return snippet;
	}
	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
