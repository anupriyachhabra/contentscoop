package com.provida.search.client.presenter.interfaces;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.search.client.Result;
import com.google.gwt.user.client.ui.IsWidget;

public interface IResultPanelView extends IsWidget {

	public interface IResultPanelPresenter {
		public void onSetResults(JsArray<? extends Result> results);

		public void storeText(String textToStore);

		public void storeImage(String imageUrl);

	}

	void setResults(JsArray<? extends Result> results);

	void clearAll();

}

