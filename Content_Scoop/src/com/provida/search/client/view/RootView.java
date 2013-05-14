package com.provida.search.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import com.provida.search.client.presenter.interfaces.IRootView;
import com.provida.search.client.presenter.interfaces.IRootView.IRootPresenter;

public class RootView extends ReverseCompositeView<IRootPresenter> implements IRootView {

	private static RootViewUiBinder uiBinder = GWT.create( RootViewUiBinder.class );
	interface RootViewUiBinder extends UiBinder<Widget, RootView> {
	}

	@UiField( provided = true )
	Widget searchPanel,resultPanel;

	@Inject
	public RootView(SearchPanelView searchPanel, ResultPanelView resultPanel) {
		this.searchPanel = searchPanel;
		this.resultPanel = resultPanel;
		initWidget( uiBinder.createAndBindUi( this ) );
	}

}
