package com.provida.search.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.provida.search.shared.SearchResult;

public interface GoogleCustomSearchServiceAsync {

	void search(String term, String imageType, String imageSize, int words,
			int startIndex, AsyncCallback<SearchResult> callback);

	
}
