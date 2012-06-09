package org.mdissjava.api.interceptors;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.interception.SecurityPrecedence;
import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;
import org.mdissjava.api.helpers.ApiHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Provider
@SecurityPrecedence
@ServerInterceptor
public class HmacSecurityInterceptor implements PreProcessInterceptor {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public ServerResponse preProcess(HttpRequest request, ResourceMethod resourceMethod)
			throws Failure, WebApplicationException {
		
		
		//get neccesary data
		Map<String, List<String>> headers = request.getHttpHeaders().getRequestHeaders();
		
		try {
			
			//get the needed data to calculate hmac hash in the server
			String method = request.getHttpMethod();
			String user = headers.get(ApiHelper.HEADER_KEY_USER).get(0);
			String key = getUserPrivateKey(user);
			String timeStamp = headers.get(ApiHelper.HEADER_KEY_DATE).get(0);
			String url = request.getUri().getAbsolutePath().toString();
			String data = this.getRequestBody(request);
			
			//calculate hmac hash in server
			String serverHmacHash = ApiHelper.calculateHMAC(key, data, timeStamp, method, url);
			String clientHmacHash = headers.get(ApiHelper.HEADER_KEY_HMAC).get(0); 
			
			//the user is the real user? compare hmac from server and hmac from client
			if (clientHmacHash.equals(serverHmacHash))
			{
				logger.info("Access granted with server hmac: {}", serverHmacHash);
				return null;
			}else
			{
				logger.info("Access denied with server hmac: {}\nclient hmac: {}", serverHmacHash, clientHmacHash);
				return (ServerResponse) Response.status(400).entity("Autentication Error").build();
			}
		} catch (IOException e) {
			return (ServerResponse) Response.status(400).entity("Security process Error").build();
		}
	}
	
	private String getUserPrivateKey(String user){
		
		HashMap<String, String> keys = new HashMap<String, String>();
		keys.put("slok", "32d9737523ec594fc9b007643b162011863c1024");
		keys.put("test", "a94a8fe5ccb19ba61c4c0873d391e987982fbbd3");
		
		return keys.get(user);
	}
	
	private String getRequestBody(HttpRequest request) throws IOException{
		//we need the body, but is an inputStream so we have to clone
		InputStream is = request.getInputStream();
		ByteArrayOutputStream baos = ApiHelper.inputStreamToOutputStream(is);
		request.setInputStream(new ByteArrayInputStream(baos.toByteArray()));
		
		return baos.toString();
	}

}
