package com.provida.search.server;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.provida.search.client.StoreTextService;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class StoreTextServiceImpl extends RemoteServiceServlet implements StoreTextService {

	public String storeText(String text) throws IllegalArgumentException {

		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(
			        new FileOutputStream("stored_searches.txt", true), "UTF-8"));
			bufferedWriter.write(text);
			bufferedWriter.newLine();
			bufferedWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
	}

	
}
