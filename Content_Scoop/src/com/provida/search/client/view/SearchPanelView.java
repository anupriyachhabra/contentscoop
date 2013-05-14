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
import com.google.gwt.util.tools.shared.StringUtils;
import com.google.inject.Singleton;
import com.provida.search.client.presenter.interfaces.ISearchPanelView;
import com.provida.search.client.presenter.interfaces.ISearchPanelView.ISearchPanelPresenter;

@Singleton
public class SearchPanelView extends ReverseCompositeView<ISearchPanelPresenter> implements ISearchPanelView {

	private static SearchPanelViewUiBinder uiBinder = GWT.create( SearchPanelViewUiBinder.class );

	interface SearchPanelViewUiBinder extends UiBinder<Widget, SearchPanelView> {
	}

	@UiField
	TextBox term,numberOfWords,imageWidth,imageHeight;
	
	@UiField
	Button searchButton;
	
	@UiField
	ListBox imageType;

	public SearchPanelView() {
		initWidget(uiBinder.createAndBindUi( this ) );
		imageType.addItem("ALL");
		imageType.addItem("JPG");
		imageType.addItem("PNG");
		imageType.addItem("GIF");
		imageType.addItem("BMP");
		
	}	
	
	@UiHandler("searchButton")
	public void onClickSearchButton(ClickEvent e){
		String imageTypeString = imageType.getValue(imageType.getSelectedIndex());
		String imageSize = null;
		String width = imageWidth.getValue();
		String height = imageHeight.getValue();
		if(!"".equals(width) && !"".equals(height)){
			imageSize = width+"x"+height;
		}
		String words = numberOfWords.getValue();
		if(words== null || "".equals(words)){
			words="0";
		}
		System.out.println("User searched for " +term.getValue()+"  "+imageSize+"  "+imageTypeString);
		presenter.startSearch(term.getValue(),imageTypeString,imageSize,Integer.parseInt(words));
	}
	

}
