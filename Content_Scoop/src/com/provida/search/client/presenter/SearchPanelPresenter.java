package com.provida.search.client.presenter;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.search.client.FileTypeValue;
import com.google.gwt.search.client.ImageSearch;
import com.google.gwt.search.client.ImageSizeValue;
import com.google.gwt.search.client.Result;
import com.google.gwt.search.client.ResultSetSize;
import com.google.gwt.search.client.SearchControl;
import com.google.gwt.search.client.SearchControlOptions;
import com.google.gwt.search.client.SearchResultsHandler;
import com.google.gwt.search.client.WebSearch;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.provida.search.client.ContentScoopEventBus;
import com.provida.search.client.presenter.interfaces.ISearchPanelView;
import com.provida.search.client.presenter.interfaces.ISearchPanelView.ISearchPanelPresenter;
import com.provida.search.client.view.SearchPanelView;

@Presenter( view = SearchPanelView.class )
public class SearchPanelPresenter extends BasePresenter<ISearchPanelView, ContentScoopEventBus> implements ISearchPanelPresenter {
	
	JsArray<? extends Result> results;
	@Override
	public void startSearch(String term,String imageType,String imageSize,final int words) {
		results = null;
		//System.out.println("User searched for " +term+"  "+imageSize+"  "+imageType);

	    SearchControlOptions options = new SearchControlOptions();        
	    WebSearch webSearch = new WebSearch();
	    webSearch.setResultSetSize(ResultSetSize.LARGE);
	   //webSearch.setQueryAddition(addition)
	    options.add(webSearch);
	    ImageSearch imageSearch = new ImageSearch();
	    imageSearch.clearColorization();
	    imageSearch.clearFileType();
	    imageSearch.clearImageSize();

	    imageSearch.setResultSetSize(ResultSetSize.LARGE);
	    String searchParam = "";
	    if(imageType!=null && !"".equals(imageType)){
	    	if(!imageType.equalsIgnoreCase("all")) {
	    		searchParam = "filetype:"+imageType.toLowerCase();
	    	}
	    }
	    if(imageSize!=null && !"".equals(imageSize)){
	    	if(searchParam.isEmpty()) {
	    		searchParam = "imagesize:" + imageSize;
	    	}	    	
	    }
	    if(!searchParam.isEmpty()) {
	    	imageSearch.setQueryAddition(searchParam);
	    }
	    options.add(imageSearch);
	    final SearchControl control = new SearchControl(options);
	    control.addSearchResultsHandler(new SearchResultsHandler() {

	    	public void onSearchResults(SearchResultsEvent event) {
	    	    results = event.getResults();
	    	    eventBus.setResults(results,words);
	    	}
	    });
        control.execute(term);
}
}
