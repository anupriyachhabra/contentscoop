package com.provida.search.client.view;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.search.client.ImageResult;
import com.google.gwt.search.client.Result;
import com.google.gwt.search.client.ResultClass;
import com.google.gwt.search.client.WebResult;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;
import com.provida.search.client.presenter.interfaces.IResultPanelView;
import com.provida.search.client.presenter.interfaces.IResultPanelView.IResultPanelPresenter;

@Singleton
public class ResultPanelView extends ReverseCompositeView<IResultPanelPresenter> implements IResultPanelView {

	private static ResultPanelViewUiBinder uiBinder = GWT.create( ResultPanelViewUiBinder.class );
    List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();
	interface ResultPanelViewUiBinder extends UiBinder<Widget, ResultPanelView> {
	}

	@UiField
	FlexTable textResults,imageResults;
	
	@UiField
	TabPanel mainTab;
	
	
	int textRow = 0;
	int imageRow = 0;
	public ResultPanelView() {
		initWidget( uiBinder.createAndBindUi( this ) );
		mainTab.selectTab(0);
	}

	
	@Override
	public void setResults(JsArray<? extends Result> results,int words) {
		textRow = 0;
		imageRow = 0 ;	
		removehandlers();
		for (int i = 0; i < results.length(); i++) {
			if (results.get(i).getResultClass().equals(
					ResultClass.WEB_SEARCH_RESULT)) {
				textRow++;
				WebResult result = (WebResult) results.get(i);
				textResults.setText(textRow, 0, "" + textRow);
				String content = result.getContent();
				if(words>0){
					int p=0;
					int spaceIdx=0;
					while(p<words && spaceIdx!=-1){
						spaceIdx = content.indexOf(" ",spaceIdx+1);
						p++;
					}
					content = content.substring(0, spaceIdx);
				}
				textResults.setHTML(textRow, 1,content);
				textResults.setWidget(textRow, 2, new Button("Save"));
			}else if(results.get(i).getResultClass().equals(
					ResultClass.IMAGE_SEARCH_RESULT)){
				imageRow++;
				ImageResult result = (ImageResult) results.get(i);
				imageResults.setText(imageRow, 0, "" + imageRow);
				imageResults.setHTML(imageRow, 1, "<a href=\""+result.getUrl()+ "\"><img src='"+result.getThumbnailUrl()+"'/></a>");
				imageResults.setWidget(imageRow, 2, new Button("Save Image"));
			}
		}
		addHandlers();
	}		
	private void addHandlers() {
		int textRows = textResults.getRowCount();
		int imageRows = imageResults.getRowCount();

		for(int i=0;i<textRows;i++){
			if (textResults.isCellPresent(i, 2) && textResults.getWidget(i, 2)!=null){
				Button button = (Button) textResults.getWidget(i, 2);
				registrations.add(button.addClickHandler(saveTextHandler));
			}
		}
		for(int i=0;i<imageRows;i++){
			if(imageResults.isCellPresent(i, 2) && imageResults.getWidget(i, 2)!=null){
				Button button = (Button) imageResults.getWidget(i, 2);
				registrations.add(button.addClickHandler(saveImageHandler));
			}
		}		
	}
	ClickHandler saveTextHandler = new ClickHandler() {      
        @Override
        public void onClick(ClickEvent event) {
            com.google.gwt.user.client.ui.HTMLTable.Cell cell = textResults.getCellForEvent(event);
            String textToStore = textResults.getText(cell.getRowIndex(), cell.getCellIndex()-1);            
            presenter.storeText(textToStore);
        }
    };
    ClickHandler saveImageHandler = new ClickHandler() {      
        @Override
        public void onClick(ClickEvent event) {
            com.google.gwt.user.client.ui.HTMLTable.Cell cell = imageResults.getCellForEvent(event);
            String imageHTML = imageResults.getHTML(cell.getRowIndex(), cell.getCellIndex()-1);
            int to = imageHTML.indexOf(">");
            int from = imageHTML.indexOf("\"");
            imageHTML = imageHTML.substring(from+1, to-1);
            presenter.storeImage(imageHTML);
        }
    };
	@Override
	public void clearAll() {		
		removehandlers();
		textResults.removeAllRows();
	    imageResults.removeAllRows();	
		textResults.setWidth("100%");
		textResults.getColumnFormatter().setWidth(0, "5%");
		textResults.getColumnFormatter().setWidth(1, "75%");
		textResults.getColumnFormatter().setWidth(2,"20%"); 
		mainTab.selectTab(0);
	}


	private void removehandlers() {
		for(HandlerRegistration handler:registrations){
			handler.removeHandler();
		}
		registrations = new ArrayList<HandlerRegistration>();
	}
}
