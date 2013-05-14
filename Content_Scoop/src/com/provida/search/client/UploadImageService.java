package com.provida.search.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("saveImage")
public interface UploadImageService extends RemoteService {
	String uploadImage(String url) throws IllegalArgumentException;
}
