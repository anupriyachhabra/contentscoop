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
	TextBox term,numberOfWords;
	
	@UiField
	Button searchButton,clearButton;
	
	@UiField
	ListBox imageType,imageSize;

	public SearchPanelView() {
		initWidget(uiBinder.createAndBindUi( this ) );
		imageType.addItem("ALL");
		imageType.addItem("jpg");
		imageType.addItem("png");
		imageType.addItem("gif");
		imageType.addItem("bmp");
		imageSize.addItem("ALL");
		imageSize.addItem("small");
		imageSize.addItem("medium");
		imageSize.addItem("large");
		imageSize.addItem("xlarge");
		imageSize.addItem("xxlarge");
		imageSize.addItem("huge");

	}	
	
	@UiHandler("searchButton")
	public void onClickSearchButton(ClickEvent e){
		String imageTypeString = imageType.getValue(imageType.getSelectedIndex());
		String imageSizeString = imageSize.getValue(imageSize.getSelectedIndex());;
		String words = numberOfWords.getValue();
		if(words== null || "".equals(words)){
			words="0";
		}
		System.out.println("User searched for " +term.getValue()+"  "+imageSize+"  "+imageTypeString);
		//presenter.startSearch(term.getValue(),imageTypeString,imageSize,Integer.parseInt(words));
		presenter.startGoogleSearch(term.getValue(),imageTypeString,imageSizeString,Integer.parseInt(words));
	}
	
	@UiHandler("clearButton")
	public void onClearButton(ClickEvent e){
		term.setText("");
		numberOfWords.setText("");
		imageType.setSelectedIndex(0);
		imageSize.setSelectedIndex(0);
		presenter.clearResults();
		
	}

}
