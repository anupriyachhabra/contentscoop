package com.provida.search.client;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.search.client.Result;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Start;
import com.mvp4g.client.event.EventBus;
import com.provida.search.client.presenter.ResultPanelPresenter;
import com.provida.search.client.presenter.RootPresenter;
import com.provida.search.client.presenter.SearchPanelPresenter;

@Events(startPresenter = RootPresenter.class )
public interface ContentScoopEventBus extends EventBus{

	@Start
	@Event(bind = { SearchPanelPresenter.class, ResultPanelPresenter.class},handlers = RootPresenter.class )
	void start();

	@Event(handlers = ResultPanelPresenter.class )
	void setResults(JsArray<? extends Result> results,int words);
}
