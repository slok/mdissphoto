package org.mdissjava.mdisscore.view.photo;

import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mdissjava.commonutils.properties.PropertiesFacade;



public class DisqusJsonReader {

	final private String OAUTH_PROPERTIES = "oauth";
	final private String DISQUS_CONSUMER_KEY = "disqus.consumer.key";
	final private String FORUM = "disqus.forum.id";
	
	
	private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }

	  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
	    InputStream is = new URL(url).openStream();
	    try {
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
	      JSONObject json = new JSONObject(jsonText);
	      return json;
	    } finally {
	      is.close();
	    }
	  }

	  public ArrayList<Integer> readLikesAndDislikes(String url) throws IOException, JSONException {
		 
		Properties oauthProperties = new PropertiesFacade().getProperties(OAUTH_PROPERTIES);
		String consumerKey = oauthProperties.getProperty(DISQUS_CONSUMER_KEY);
		String forum = oauthProperties.getProperty(FORUM);
	
		
		ArrayList<Integer> array=new ArrayList<Integer>();
		System.out.println("******Disqus url called: http://disqus.com/api/3.0/threads/list.json?api_key="+consumerKey+"&forum="+forum+"&thread:link=http://jboss.mdiss.info/mdissphoto/u/cerealguy/photo/76ed905b-7c8d-4c7a-b16a-bada8833955a/");
		JSONObject json = readJsonFromUrl("http://disqus.com/api/3.0/threads/list.json?api_key="+consumerKey+"&forum="+forum+"&thread:link=http://jboss.mdiss.info/mdissphoto/u/cerealguy/photo/76ed905b-7c8d-4c7a-b16a-bada8833955a/");
	    System.out.println(json.toString());
	    JSONArray rec= json.getJSONArray("response");
	    array.add(rec.getJSONObject(0).getInt("likes")); 
	    array.add(rec.getJSONObject(0).getInt("dislikes"));
		return array; 
	    
	    
	  }

	
	
}
