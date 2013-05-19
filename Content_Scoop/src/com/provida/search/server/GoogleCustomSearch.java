package com.provida.search.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class GoogleCustomSearch {

	public static void main(String args[]) throws JSONException{

		
			Client client = Client.create();
			WebResource webResource = client
			   .resource("https://www.googleapis.com/customsearch/v1?key=AIzaSyDC3S4hwVZUUcoFnXlZHB1NDyoLJANhj18&cx=014881207863242567761:e0f9pxx2z4k&q=flower&alt=json&start=1&searchType=image");
	 
			ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
	 
			if (response.getStatus() != 200) {
			   throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatus());
			}
	 
			String output = response.getEntity(String.class);
	 
			System.out.println("Output from Server .... \n");
			System.out.println(output);
	 
			org.json.JSONObject json=null;
			json = new JSONObject(output);
			org.json.JSONObject queryArray=json.getJSONObject("queries");
			org.json.JSONArray itemsArray=json.getJSONArray("items");
			System.out.println("Curr length "+itemsArray.length());
			for(int i=0;i<itemsArray.length()-1;i++)
			{
				JSONObject newJSONObj=itemsArray.getJSONObject(i);
				System.out.println(newJSONObj.getString("link"));
			}
			client.destroy();
			
		
	 
	}

	private static void getResultsPage(String link) throws IOException {
		URL url = new URL(link);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();    
		conn.setRequestMethod("GET");     
		conn.setRequestProperty("Accept", "text/html");     
		BufferedReader br = new BufferedReader(new InputStreamReader( (conn.getInputStream())));      
		StringBuffer output = new StringBuffer();  
		String line;
		System.out.println("Output from Server .... \n");     
		while ((line = br.readLine()) != null) 
		 {  
			output.append(line);
		 }
		conn.disconnect();
		String outputString = output.toString();
		int start = outputString.indexOf("<body");
		start = outputString.indexOf(">", start+5);
		int end = outputString.indexOf("</body>");
		outputString = outputString.substring(start+5, end);
		System.out.println(outputString);
		
	}
}
