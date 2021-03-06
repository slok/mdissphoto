package org.mdissjava.api;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;
import org.mdissjava.api.helpers.ApiHelper;

public class UsersTest {
	
	private final String user = "cerealguy";
	private final String secret = "iiW35PK+9APga+Ci9f1Bq9BTA25TDNf4MavLwLhG1xs=";

	@Test
	public void HttpGetUserFollowingsTest() throws ClientProtocolException, IOException {
		
		String url = "http://127.0.0.1:8080/mdissapi/api/1.0/users/cerealguy/following";
		
		HttpGet get = ApiHelper.assembleHttpGet(this.user, this.secret, url);
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(get);
		
		String message = ApiHelper.inputStreamToOutputStream(response.getEntity().getContent()).toString();
		
		System.out.println(message);
	}
	
	@Test
	public void HttpGetUserFollowersTest() throws ClientProtocolException, IOException {
		
		String url = "http://127.0.0.1:8080/mdissapi/api/1.0/users/cerealguy/followers";
		
		HttpGet get = ApiHelper.assembleHttpGet(this.user, this.secret, url);
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(get);
		
		String message = ApiHelper.inputStreamToOutputStream(response.getEntity().getContent()).toString();
		
		System.out.println(message);
	}
	
	
	@Test
	public void HttpGetUserTest() throws ClientProtocolException, IOException {
		
		String url = "http://127.0.0.1:8080/mdissapi/api/1.0/users/cerealguy/";
		
		HttpGet get = ApiHelper.assembleHttpGet(this.user, this.secret, url);
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(get);
		
		String message = ApiHelper.inputStreamToOutputStream(response.getEntity().getContent()).toString();
		
		System.out.println(message);
	}
	
	@Test
	public void HttpGetUserAlbumsTest() throws ClientProtocolException, IOException {
		
		String url = "http://127.0.0.1:8080/mdissapi/api/1.0/users/cerealguy/albums";
		
		HttpGet get = ApiHelper.assembleHttpGet(this.user, this.secret, url);
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(get);
		
		String message = ApiHelper.inputStreamToOutputStream(response.getEntity().getContent()).toString();
		
		System.out.println(message);
	}

}
