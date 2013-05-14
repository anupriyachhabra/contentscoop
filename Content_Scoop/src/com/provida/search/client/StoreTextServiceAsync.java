package com.provida.search.client;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface StoreTextServiceAsync {
	void storeText(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;
}
