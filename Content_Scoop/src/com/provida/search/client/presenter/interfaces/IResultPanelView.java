package com.provida.search.client.presenter.interfaces;

import java.util.List;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.search.client.Result;
import com.google.gwt.user.client.ui.IsWidget;
import com.provida.search.shared.SearchResult;

public interface IResultPanelView extends IsWidget {

	public interface IResultPanelPresenter {
		public void onSetResults(SearchResult results,String term, String imageType,
				String imageSize, int words);

		public void storeText(String textToStore);

		public void storeImage(String imageUrl);

		public void getMoreResults(int start);

	}

	void clearAll();

	void setResults(SearchResult results, String term, String imageType,
			String imageSize, int words);

}

