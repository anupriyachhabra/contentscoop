package com.provida.search.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface UploadImageServiceAsync {
	void uploadImage(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;
}
