package com.provida.search.client;
import java.util.List;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.search.client.Result;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Start;
import com.mvp4g.client.event.EventBus;
import com.provida.search.client.presenter.ResultPanelPresenter;
import com.provida.search.client.presenter.RootPresenter;
import com.provida.search.client.presenter.SearchPanelPresenter;
import com.provida.search.shared.SearchResult;

@Events(startPresenter = RootPresenter.class )
public interface ContentScoopEventBus extends EventBus{

	@Start
	@Event(bind = { SearchPanelPresenter.class, ResultPanelPresenter.class},handlers = RootPresenter.class )
	void start();

//	@Event(handlers = ResultPanelPresenter.class )
//	void setResults(JsArray<? extends Result> results,int words);

	@Event(handlers = ResultPanelPresenter.class )
	void clearResults();

	@Event(handlers = ResultPanelPresenter.class )
	void setResults(SearchResult result,String term, String imageType,
			String imageSize, int words);

}
