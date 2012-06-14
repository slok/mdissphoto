package org.mdissjava.api;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;
import org.mdissjava.api.helpers.ApiHelper;

public class PhotoContentTest {
	
	private String user = "cerealguy";
	private final String secret = "iiW35PK+9APga+Ci9f1Bq9BTA25TDNf4MavLwLhG1xs=";
	
	@Test
	public void HttpGetPhotoContentTest() throws ClientProtocolException, IOException {
		String url = "http://127.0.0.1:8080/mdissapi/api/1.0/photo-content/fcf6a3a3-77e8-4a0f-8ce5-cd9aaf9b5e0d";
		
		HttpGet get = ApiHelper.assembleHttpGetData(user, secret, url);
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(get);
		
		String message = ApiHelper.inputStreamToOutputStream(response.getEntity().getContent()).toString();
		
		System.out.println(message);
	}
	
	@Test
	public void HttpGetPhotoContentSizedTest() throws ClientProtocolException, IOException {
		String url = "http://127.0.0.1:8080/mdissapi/api/1.0/photo-content/fcf6a3a3-77e8-4a0f-8ce5-cd9aaf9b5e0d/size/100";
		
		HttpGet get = ApiHelper.assembleHttpGetData(user, secret, url);
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(get);
		
		String message = ApiHelper.inputStreamToOutputStream(response.getEntity().getContent()).toString();
		
		System.out.println(message);
	}
	
	@Test
	public void HttpPostContentTest() throws ClientProtocolException, IOException {
		File f = new File("/home/sharem/desktop.png");
		byte[] data = FileUtils.readFileToByteArray(f);
		String url = "http://localhost:8080/mdissapi/api/1.0/photo-content/";
		
		HttpPost post = ApiHelper.assembleHttpPostData(user, secret, data, url);
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(post);
		
		String message = ApiHelper.inputStreamToOutputStream(response.getEntity().getContent()).toString();
		
		System.out.println(message);
	}

}
