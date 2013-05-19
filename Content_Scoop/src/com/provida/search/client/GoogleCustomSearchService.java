package com.provida.search.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.provida.search.shared.SearchResult;

@RemoteServiceRelativePath("searchResults")
public interface GoogleCustomSearchService extends RemoteService{

	SearchResult search(String term, String imageType, String imageSize,
			int words, int startIndex);

}
