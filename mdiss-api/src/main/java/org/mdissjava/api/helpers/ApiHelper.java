package org.mdissjava.api.helpers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class ApiHelper {
	
	static private String HMAC_SHA1_ALGORITHM = "HmacSHA1";
	
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

}
