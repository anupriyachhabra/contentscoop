package com.provida.search.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;
import com.provida.search.client.presenter.interfaces.ISearchPanelView;
import com.provida.search.client.presenter.interfaces.ISearchPanelView.ISearchPanelPresenter;

@Singleton
public class SearchPanelView extends ReverseCompositeView<ISearchPanelPresenter> implements ISearchPanelView {

	private static SearchPanelViewUiBinder uiBinder = GWT.create( SearchPanelViewUiBinder.class );

	interface SearchPanelViewUiBinder extends UiBinder<Widget, SearchPanelView> {
	}

	@UiField
	TextBox term;
	
	@UiField
	Button searchButton;
	
	@UiField
	ListBox imageType,imageSize;

	public SearchPanelView() {
		initWidget(uiBinder.createAndBindUi( this ) );
		imageType.addItem("JPG");
		imageType.addItem("PNG");
		imageType.addItem("GIF");
		imageType.addItem("BMP");
		imageSize.addItem("SMALL");
		imageSize.addItem("MEDIUM");
		imageSize.addItem("LARGE");
		imageSize.addItem("EXTRA_LARGE");
	}	
	
	@UiHandler("searchButton")
	public void onClickSearchButton(ClickEvent e){
		String imageTypeString = imageType.getValue(imageType.getSelectedIndex());
		String imageSizeString = imageSize.getValue(imageSize.getSelectedIndex());
		System.out.println("User searched for " +term.getValue()+"  "+imageSizeString+"  "+imageTypeString);
		presenter.startSearch(term.getValue(),imageTypeString,imageSizeString);
	}
	

}
