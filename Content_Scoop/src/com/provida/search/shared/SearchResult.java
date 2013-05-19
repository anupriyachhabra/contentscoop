package com.provida.search.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<WebResult> webResult = new ArrayList<WebResult>();
	List<ImageResult> imageResult = new ArrayList<ImageResult>();
	public List<WebResult> getWebResult() {
		return webResult;
	}
	public void setWebResult(List<WebResult> webResult) {
		this.webResult = webResult;
	}
	public List<ImageResult> getImageResult() {
		return imageResult;
	}
	public void setImageResult(List<ImageResult> imageResult) {
		this.imageResult = imageResult;
	}

}
