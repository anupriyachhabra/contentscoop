package com.provida.search.shared;

import java.io.Serializable;

public class ImageResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
