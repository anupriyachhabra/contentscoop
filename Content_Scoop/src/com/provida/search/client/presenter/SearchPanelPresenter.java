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
	public void startSearch(String term,String imageType,String imageSize) {
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

	   // imageSearch.setQueryAddition("as_filetype="+imageType);
	    imageSearch.setResultSetSize(ResultSetSize.LARGE);
	    if(imageType!=null && !"".equals(imageType)){
	    	imageSearch.setFileType(FileTypeValue.valueOf(imageType));
	    }
	    if(imageSize!=null && !"".equals(imageSize)){
	    	imageSearch.setImageSize(ImageSizeValue.valueOf(imageSize));
	    }
	    options.add(imageSearch);
	    final SearchControl control = new SearchControl(options);
	    control.addSearchResultsHandler(new SearchResultsHandler() {

	    	public void onSearchResults(SearchResultsEvent event) {
	    	    results = event.getResults();
	    	    eventBus.setResults(results);
	    	}
	    });
        control.execute(term);
}
}
