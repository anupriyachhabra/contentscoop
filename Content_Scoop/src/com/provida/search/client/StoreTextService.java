package com.provida.search.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("saveText")
public interface StoreTextService extends RemoteService {
	String storeText(String url) throws IllegalArgumentException;
}
