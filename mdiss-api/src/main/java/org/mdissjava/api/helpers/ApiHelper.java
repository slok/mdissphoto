package org.mdissjava.api.helpers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.jboss.resteasy.spi.HttpRequest;

public class ApiHelper {
	
	static private String HMAC_SHA1_ALGORITHM = "HmacSHA1";
	static public final String HEADER_KEY_USER = "user"; //public key
	static public final String HEADER_KEY_HMAC = "hmac";
	static public final String HEADER_KEY_DATE = "timeStamp";
	
	static public String calculateHMAC(String secret, String data, String timeStamp, String method, String url) {
		try {
			
			data = method + "\n" + timeStamp + "\n" + url + "\n" + data;
			
			SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes(),	HMAC_SHA1_ALGORITHM);
			Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
			mac.init(signingKey);
			byte[] rawHmac = mac.doFinal(data.getBytes());
			String result = new String(Base64.encodeBase64(rawHmac));
			return result;
		} catch (GeneralSecurityException e) {
			throw new IllegalArgumentException();
		}
	}

	static public ByteArrayOutputStream inputStreamToOutputStream(InputStream is) throws IOException{
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    byte[] buffer = new byte[1024];
	    int len;
	    while ((len = is.read(buffer)) > -1 ) {
	        baos.write(buffer, 0, len);
	    }
	    baos.flush();
	    
	    return baos;
		
	}

	
	static public HttpPost assembleHttpPost(String user, String key, String data, String url) throws UnsupportedEncodingException{
		//necessary data
		String jsonMime = "application/json";
		String date =  new Date().toString();
		String hmac =  ApiHelper.calculateHMAC(key, data, date, "POST", url);
		
		//create the client
		HttpPost httpPost = new HttpPost(url);

		//add headers
		httpPost.addHeader(ApiHelper.HEADER_KEY_HMAC, hmac);
		httpPost.addHeader(ApiHelper.HEADER_KEY_DATE, date);
		httpPost.addHeader(ApiHelper.HEADER_KEY_USER, user);
		httpPost.addHeader("Accept", jsonMime);
		httpPost.addHeader("Content-Type", jsonMime);
		
		//add body
		StringEntity dataHelper = new StringEntity(data, "UTF-8");
		httpPost.setEntity(dataHelper);
		
		return httpPost;
	}
	
	static public HttpGet assembleHttpGet(String user, String key, String url) throws UnsupportedEncodingException{
		//necessary data
		String jsonMime = "application/json";
		String date =  new Date().toString();
		String hmac =  ApiHelper.calculateHMAC(key, "", date, "GET", url);
		
		//create the client
		HttpGet httpGet = new HttpGet(url);

		//add headers
		httpGet.addHeader(ApiHelper.HEADER_KEY_HMAC, hmac);
		httpGet.addHeader(ApiHelper.HEADER_KEY_DATE, date);
		httpGet.addHeader(ApiHelper.HEADER_KEY_USER, user);
		httpGet.addHeader("Accept", jsonMime);
		httpGet.addHeader("Content-Type", jsonMime);
		
		return httpGet;
	}
	
	static public HttpPut assembleHttpPut(String user, String key, String data, String url) throws UnsupportedEncodingException{
		//necessary data
		String jsonMime = "application/json";
		String date =  new Date().toString();
		String hmac =  ApiHelper.calculateHMAC(key, data, date, "PUT", url);
		
		//create the client
		HttpPut httpPut = new HttpPut(url);

		//add headers
		httpPut.addHeader(ApiHelper.HEADER_KEY_HMAC, hmac);
		httpPut.addHeader(ApiHelper.HEADER_KEY_DATE, date);
		httpPut.addHeader(ApiHelper.HEADER_KEY_USER, user);
		httpPut.addHeader("Accept", jsonMime);
		httpPut.addHeader("Content-Type", jsonMime);
		
		//add body
		StringEntity dataHelper = new StringEntity(data, "UTF-8");
		httpPut.setEntity(dataHelper);
		
		return httpPut;
	}
	
	static public HttpDelete assembleHttpDelete(String user, String key, String url) throws UnsupportedEncodingException{
		//necessary data
		String jsonMime = "application/json";
		String date =  new Date().toString();
		String hmac =  ApiHelper.calculateHMAC(key, "", date, "DELETE", url);
		
		//create the client
		HttpDelete httpDelete = new HttpDelete(url);

		//add headers
		httpDelete.addHeader(ApiHelper.HEADER_KEY_HMAC, hmac);
		httpDelete.addHeader(ApiHelper.HEADER_KEY_DATE, date);
		httpDelete.addHeader(ApiHelper.HEADER_KEY_USER, user);
		httpDelete.addHeader("Accept", jsonMime);
		httpDelete.addHeader("Content-Type", jsonMime);
		
		return httpDelete;
	}
	
	static public String getUserFromHttpRequest(HttpRequest request){
		return request.getHttpHeaders().getRequestHeaders().get(ApiHelper.HEADER_KEY_USER).get(0);
	}
	
}
