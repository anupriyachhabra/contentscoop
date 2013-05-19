package com.provida.search.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.provida.search.client.GoogleCustomSearchService;
import com.provida.search.shared.ImageResult;
import com.provida.search.shared.SearchResult;
import com.provida.search.shared.WebResult;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class GoogleCustomSearchServiceImpl extends RemoteServiceServlet implements GoogleCustomSearchService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public SearchResult search(String term, String imageType,
			String imageSize, int words,int startIndex) {
		SearchResult searchResult = new SearchResult();
		if(StringUtils.isNotEmpty(term)){
			try {
				term = URLEncoder.encode(term, "UTF-8");
			} catch (UnsupportedEncodingException e) {
			   System.out.println("URL encoding failed");
			}
			String url = "https://www.googleapis.com/customsearch/v1?key=AIzaSyB0Mz6DEAV6qZ8vk-RAOvTiMDL7v7V36aI&cx=014881207863242567761:e0f9pxx2z4k&q="+term+"&alt=json&start="+startIndex;
			String customImageUrlAdd ="";
			if(imageType!=null && !("".equals(imageType) || "ALL".equalsIgnoreCase(imageType))){
				customImageUrlAdd = "&fileType="+imageType;
			}
			if(imageSize!=null && !("".equals(imageSize) || "ALL".equalsIgnoreCase(imageSize))){
				customImageUrlAdd = customImageUrlAdd+ "&imgSize="+imageSize;
			}
			List<WebResult> webResult = getWebResult(url,words);
			List<ImageResult> imageResult = getImageResult(url+"&searchType=image"+customImageUrlAdd);

			searchResult.setWebResult(webResult);
			searchResult.setImageResult(imageResult);
}
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
			JSONArray itemsArray=json.getJSONArray("items");
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

	private List<WebResult> getWebResult(String url,int words) {
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
			org.json.JSONArray itemsArray=json.getJSONArray("items");
			System.out.println("Curr length "+itemsArray.length());
			for(int i=0;i<itemsArray.length();i++)
			{
				WebResult result = new WebResult();
				JSONObject newJSONObj=itemsArray.getJSONObject(i);
				String text = getText(newJSONObj.getString("link"),words);
				if(text!=null &&  words!=0)
					result.setSnippet(text);
				else
					result.setSnippet(newJSONObj.getString("snippet"));
				results.add(result);
			}
		}catch(Exception exe){
			exe.printStackTrace();
		}
		client.destroy();
		return results;
	}
    private String getText(String link, int words){
    	Document doc = null;
		try {
			doc = Jsoup.connect(link).get();
		} catch (IOException e) {
			return null;
		}
        Element body = doc.body();
        String text = Jsoup.parse(body.html()).text();
        int index = StringUtils.ordinalIndexOf(text, " ", words);
        if(index!=0 || index!=-1)
        	text = text.substring(0, index+1);
        return text;
    }
}
