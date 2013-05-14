package com.provida.search.client.presenter;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.provida.search.client.ContentScoopEventBus;
import com.provida.search.client.presenter.interfaces.IRootView;
import com.provida.search.client.presenter.interfaces.IRootView.IRootPresenter;
import com.provida.search.client.view.RootView;

@Presenter( view = RootView.class )
public class RootPresenter extends BasePresenter<IRootView, ContentScoopEventBus> implements IRootPresenter {

	public void onStart() {
		System.out.println("The Application started");
		//eventBus.goToPage1( "The application started." );
	}

}
