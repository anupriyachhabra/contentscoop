package com.provida.search.server;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.provida.search.client.GoogleCustomSearchService;
import com.provida.search.shared.ImageResult;
import com.provida.search.shared.SearchResult;
import com.provida.search.shared.WebResult;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class GoogleCustomSearchServiceImpl extends RemoteServiceServlet implements GoogleCustomSearchService{

	@Override
	public SearchResult search(String term, String imageType,
			String imageSize, int words,int startIndex) {
		String url = "https://www.googleapis.com/customsearch/v1?key=AIzaSyDC3S4hwVZUUcoFnXlZHB1NDyoLJANhj18&cx=014881207863242567761:e0f9pxx2z4k&q="+term+"&alt=json&start="+startIndex;
		SearchResult searchResult = new SearchResult();
		List<WebResult> webResult = getWebResult(url);
		List<ImageResult> imageResult = getImageResult(url+"&searchType=image");

		searchResult.setWebResult(webResult);
		searchResult.setImageResult(imageResult);
		return searchResult;
	}

	private List<ImageResult> getImageResult(String url) {
		List<ImageResult> imageResults = new ArrayList<ImageResult>();
		Client client = Client.create();
		WebResource webResource = client.resource(url);

		ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}

		String output = response.getEntity(String.class);
		org.json.JSONObject json=null;
		try{
			json = new JSONObject(output);
			org.json.JSONArray itemsArray=json.getJSONArray("items");
			System.out.println("Curr Imagses length "+itemsArray.length());
			for(int i=0;i<itemsArray.length();i++)
			{
				ImageResult result = new ImageResult();
				JSONObject newJSONObj=itemsArray.getJSONObject(i);
				result.setUrl(newJSONObj.getString("link"));
				imageResults.add(result);
			}
		}catch(Exception exe){
			exe.printStackTrace();
		}
		client.destroy();
		return imageResults;
	}

	private List<WebResult> getWebResult(String url) {
		List<WebResult> results = new ArrayList<WebResult>();
		Client client = Client.create();
		WebResource webResource = client.resource(url);

		ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}

		String output = response.getEntity(String.class);
		org.json.JSONObject json=null;
		try{
			json = new JSONObject(output);
			org.json.JSONObject queryArray=json.getJSONObject("queries");
			org.json.JSONArray itemsArray=json.getJSONArray("items");
			System.out.println("Curr length "+itemsArray.length());
			for(int i=0;i<itemsArray.length();i++)
			{
				WebResult result = new WebResult();
				JSONObject newJSONObj=itemsArray.getJSONObject(i);
				result.setSnippet(newJSONObj.getString("snippet"));

				results.add(result);
			}
		}catch(Exception exe){
			exe.printStackTrace();
		}
		client.destroy();
		return results;
	}

}
