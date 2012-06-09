package org.mdissjava.api;


import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;
import org.mdissjava.api.helpers.ApiHelper;

public class ApiHelperTest {

	@Test
	public void HttpPostTest() throws ClientProtocolException, IOException {
		String user = "slok";
		String secret = "32d9737523ec594fc9b007643b162011863c1024";
		String data = "{\"title\":\"Me\",\"userNick\":\"horl\"}";
		String url = "http://127.0.0.1:8080/mdissapi/api/1.0/albums/";
		
		HttpPost post = ApiHelper.assembleHttpPost(user, secret, data, url);
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(post);
		
		String message = ApiHelper.inputStreamToOutputStream(response.getEntity().getContent()).toString();
		
		System.out.println(message);
	}

	@Test
	public void HttpGetTest() throws ClientProtocolException, IOException {
		String user = "slok";
		String secret = "32d9737523ec594fc9b007643b162011863c1024";
		String url = "http://127.0.0.1:8080/mdissapi/api/1.0/albums/bb305a10-8960-48dc-aba2-e176dd718c65/";
		
		HttpGet get = ApiHelper.assembleHttpGet(user, secret, url);
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(get);
		
		String message = ApiHelper.inputStreamToOutputStream(response.getEntity().getContent()).toString();
		
		System.out.println(message);
	}
	
}
