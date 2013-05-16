package com.provida.search.client.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.search.client.Result;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.provida.search.client.ContentScoopEventBus;
import com.provida.search.client.StoreTextService;
import com.provida.search.client.StoreTextServiceAsync;
import com.provida.search.client.UploadImageService;
import com.provida.search.client.UploadImageServiceAsync;
import com.provida.search.client.presenter.interfaces.IResultPanelView;
import com.provida.search.client.presenter.interfaces.IResultPanelView.IResultPanelPresenter;
import com.provida.search.client.view.ResultPanelView;

@Presenter( view = ResultPanelView.class )
public class ResultPanelPresenter extends BasePresenter<IResultPanelView, ContentScoopEventBus> implements IResultPanelPresenter {

	
	public void onSetResults(JsArray<? extends Result> results,int words){
		view.setResults(results,words);
		
	}
	public void onClearResults(){
		view.clearAll();
	}

	@Override
	public void storeText(String textToStore) {
		System.out.println("Storing Text in DB "+textToStore);
		StoreTextServiceAsync storeTextService = GWT.create(StoreTextService.class);

		AsyncCallback<String> callback = new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				Window.alert("failed to save text");
			}

			public void onSuccess(String result) {
				Window.alert("saved text to file");
			}
		};
		storeTextService.storeText(textToStore, callback);

	}

	@Override
	public void storeImage(String imageUrl) {
		System.out.println("Storing image in filesystem "+imageUrl);
	    UploadImageServiceAsync imageUploadService = GWT.create(UploadImageService.class);
	    
        AsyncCallback<String> callback = new AsyncCallback<String>() {
	      public void onFailure(Throwable caught) {
	        Window.alert("Saving image on server failed");
	      }

	      public void onSuccess(String result) {
	         Window.alert("An image with name "+result+" has been created on server with 1000wx1500h");
	      }
	    };
	    imageUploadService.uploadImage(imageUrl, callback);
	   
	}
}
