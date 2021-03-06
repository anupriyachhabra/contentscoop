package com.provida.search.client.presenter.interfaces;

import com.google.gwt.user.client.ui.IsWidget;

public interface ISearchPanelView extends IsWidget {

	public interface ISearchPanelPresenter {

		void startSearch(String term,String imageType,String imageSize, int words);

		void clearResults();

		void startGoogleSearch(String term,String imageType,String imageSize, int words);

	}
}

