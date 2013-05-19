package com.provida.search.client.view;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.ImageCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.inject.Singleton;
import com.provida.search.client.GoogleCustomSearchService;
import com.provida.search.client.GoogleCustomSearchServiceAsync;
import com.provida.search.client.presenter.interfaces.IResultPanelView;
import com.provida.search.client.presenter.interfaces.IResultPanelView.IResultPanelPresenter;
import com.provida.search.shared.ImageResult;
import com.provida.search.shared.SearchResult;
import com.provida.search.shared.WebResult;

@Singleton
public class ResultPanelView extends ReverseCompositeView<IResultPanelPresenter> implements IResultPanelView {

	private static ResultPanelViewUiBinder uiBinder = GWT.create( ResultPanelViewUiBinder.class );
    List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();
	interface ResultPanelViewUiBinder extends UiBinder<Widget, ResultPanelView> {
	}
	@UiField
	HTMLPanel resultPanel;
	
	@UiField
	CellTable textResults,imageResults;
	
	SimplePager textPager;
    
	@UiField
	TabPanel mainTab;
	
	AsyncDataProvider<WebResult> provider =null;
	private String term;
	private String imageType;
	private String imageSize;
	private int words;
	public ResultPanelView() {
		initWidget( uiBinder.createAndBindUi( this ) );
		textPager= new SimplePager();
	//	textPager.setPageSize(20);
		textPager.setRangeLimited(false);
		mainTab.selectTab(0);
		resultPanel.add(textPager);
		setUpTable(textResults,imageResults);
	}
	private void setUpTable(CellTable textResults, final CellTable imageResults) {
		// Add a text column to show the name.
		TextColumn<WebResult> nameColumn = new TextColumn<WebResult>() {
			@Override
			public String getValue(WebResult object) {
				return object.getSnippet();
			}
		};
		textResults.addColumn(nameColumn);
		ButtonCell reListCell = new ButtonCell();
		Column<WebResult, String> reListColumn = new Column<WebResult, String>(reListCell) {
			@Override
			public String getValue(WebResult object) {
				return "Save";
			}
		};
		reListColumn.setFieldUpdater(new FieldUpdater<WebResult, String>() {
			@Override
			public void update(int index, WebResult object, String value) {
				presenter.storeText(object.getSnippet());
			}
		});

		textResults.addColumn(reListColumn);
		ImageCell btn= new ImageCell();
		Column<ImageResult,String> imgColumn = new Column<ImageResult,String>(btn) 
		{
		    @Override
		    public String getValue(ImageResult c) 
		    {
		        return c.getUrl();
		    }
		};
		imageResults.addColumn(imgColumn);
		ButtonCell imageSaveCell = new ButtonCell();
		Column<ImageResult, String> imageSaveColumn = new Column<ImageResult, String>(imageSaveCell) {
			@Override
			public String getValue(ImageResult object) {
				return "Save";
			}
		};
		imageSaveColumn.setFieldUpdater(new FieldUpdater<ImageResult, String>() {
			@Override
			public void update(int index, ImageResult object, String value) {
				presenter.storeImage(object.getUrl());
			}
		});

		imageResults.addColumn(imageSaveColumn);
		provider = new AsyncDataProvider<WebResult>() {
			@Override
			protected void onRangeChanged(HasData<WebResult> display) {
				final int start = display.getVisibleRange().getStart();
				//  int length = display.getVisibleRange().getLength();
				GoogleCustomSearchServiceAsync searchService = GWT.create(GoogleCustomSearchService.class);
				AsyncCallback<SearchResult> callback = new AsyncCallback<SearchResult>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(SearchResult result) {
						updateRowData(start, result.getWebResult());
						imageResults.setRowData(result.getImageResult());
					}
				};
				searchService.search(getTerm(),getImageType(),getImageSize(),getWords(),start+1,callback);
			}		
		};
		textResults.setRowData(new ArrayList<WebResult>());
		imageResults.setRowData(new ArrayList<ImageResult>());
		provider.addDataDisplay(textResults);
	}
	
	
	@Override
	public void setResults(SearchResult result,String term,String imageType,String imageSize,int words) {
		setTerm(term);
		setImageType(imageType);
		setImageSize(imageSize);
		setWords(words);
		textResults.setRowData(result.getWebResult());
		imageResults.setRowData(result.getImageResult());
		textPager.setDisplay(textResults);
	}		
	@Override
	public void clearAll() {
		textResults.setRowData(new ArrayList<WebResult>());
		imageResults.setRowData(new ArrayList<ImageResult>());
		provider.addDataDisplay(textResults);		
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getImageType() {
		return imageType;
	}
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
	public String getImageSize() {
		return imageSize;
	}
	public void setImageSize(String imageSize) {
		this.imageSize = imageSize;
	}
	public int getWords() {
		return words;
	}
	public void setWords(int words) {
		this.words = words;
	}
}
