package org.mdissjava.api;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;
import org.mdissjava.api.helpers.ApiHelper;

public class UsersTest {
	
	@Test
	public void HttpGetUserFollowingsTest() throws ClientProtocolException, IOException {
		String user = "cerealguy";
		String secret = "0f49b4dca61952cbafb1da0e433963c5424bb0301701611a23c58075e65f6878";
		String url = "http://127.0.0.1:8080/mdissapi/api/1.0/users/cerealguy/following";
		
		HttpGet get = ApiHelper.assembleHttpGet(user, secret, url);
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(get);
		
		String message = ApiHelper.inputStreamToOutputStream(response.getEntity().getContent()).toString();
		
		System.out.println(message);
	}
	
	@Test
	public void HttpGetUserFollowersTest() throws ClientProtocolException, IOException {
		String user = "cerealguy";
		String secret = "0f49b4dca61952cbafb1da0e433963c5424bb0301701611a23c58075e65f6878";
		String url = "http://127.0.0.1:8080/mdissapi/api/1.0/users/cerealguy/followers";
		
		HttpGet get = ApiHelper.assembleHttpGet(user, secret, url);
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(get);
		
		String message = ApiHelper.inputStreamToOutputStream(response.getEntity().getContent()).toString();
		
		System.out.println(message);
	}
	
	
	@Test
	public void HttpGetUserTest() throws ClientProtocolException, IOException {
		String user = "cerealguy";
		String secret = "0f49b4dca61952cbafb1da0e433963c5424bb0301701611a23c58075e65f6878";
		String url = "http://127.0.0.1:8080/mdissapi/api/1.0/users/cerealguy/";
		
		HttpGet get = ApiHelper.assembleHttpGet(user, secret, url);
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(get);
		
		String message = ApiHelper.inputStreamToOutputStream(response.getEntity().getContent()).toString();
		
		System.out.println(message);
	}
	
	/*
	@Test
	public void HttpUpdateUserTest() throws ClientProtocolException, IOException {
		String user = "cerealguy";
		String secret = "0f49b4dca61952cbafb1da0e433963c5424bb0301701611a23c58075e65f6878";
		String data = "{\"title\":\"Catuset\",\"userNick\":\"cerealguy\"}";
		String url = "http://127.0.0.1:8080/mdissapi/api/1.0/users/cerealguy/";
		
		HttpPut put = ApiHelper.assembleHttpPut(user, secret, data, url);
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(put);
		
		String message = ApiHelper.inputStreamToOutputStream(response.getEntity().getContent()).toString();
		
		System.out.println(message);
	}
	*/
	/*
	@Test
	public void HttpCreateUserTest() throws ClientProtocolException, IOException {
		String user = "cerealguy";
		String secret = "0f49b4dca61952cbafb1da0e433963c5424bb0301701611a23c58075e65f6878";
		String data = "{\"title\":\"Me\",\"userNick\":\"horl\"}";
		String url = "http://127.0.0.1:8080/mdissapi/api/1.0/users/";
		
		HttpPost post = ApiHelper.assembleHttpPost(user, secret, data, url);
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(post);
		
		String message = ApiHelper.inputStreamToOutputStream(response.getEntity().getContent()).toString();
		
		System.out.println(message);
	}	
	*/

	@Test
	public void HttpDeleteUserTest() throws ClientProtocolException, IOException {
		String user = "cerealguy";
		String secret = "0f49b4dca61952cbafb1da0e433963c5424bb0301701611a23c58075e65f6878";
		String url = "http://127.0.0.1:8080/mdissapi/api/1.0/users/maifrup/";
		
		HttpDelete delete = ApiHelper.assembleHttpDelete(user, secret, url);
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(delete);
		
		String message = ApiHelper.inputStreamToOutputStream(response.getEntity().getContent()).toString();
		
		System.out.println(message);
	} 
	
	@Test
	public void HttpGetUserAlbumsTest() throws ClientProtocolException, IOException {
		String user = "cerealguy";
		String secret = "0f49b4dca61952cbafb1da0e433963c5424bb0301701611a23c58075e65f6878";
		String url = "http://127.0.0.1:8080/mdissapi/api/1.0/users/cerealguy/albums";
		
		HttpGet get = ApiHelper.assembleHttpGet(user, secret, url);
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(get);
		
		String message = ApiHelper.inputStreamToOutputStream(response.getEntity().getContent()).toString();
		
		System.out.println(message);
	}

}
