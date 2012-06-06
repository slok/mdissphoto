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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class DisqusJsonReader {

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
	    
		ArrayList<Integer> array=new ArrayList<Integer>();
		JSONObject json = readJsonFromUrl("http://disqus.com/api/3.0/threads/list.json?api_key=hCYB59qaUgKv4BrD6CF7Qyqk46qFinidYAcUEDP10YAgOJQz7W2u0wJRXIGgTNnB&forum=mdissphoto&thread:link="+url);//http://jboss.mdiss.info/mdissphoto/u/cerealguy/photo/3e0af8cc-331c-430d-83f8-ccd23c4f6395/");
	    System.out.println(json.toString());
	    JSONArray rec= json.getJSONArray("response");
	    array.add(rec.getJSONObject(0).getInt("likes")); 
	    array.add(rec.getJSONObject(0).getInt("dislikes"));
		return array; 
	    
	    
	  }

	
	
}
