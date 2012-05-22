package org.mdissjava.mdisscore.model.pojo;

public class OauthAccessToken {
	private String token;
	private String tokenSecret;
	
	public OauthAccessToken() {
	}
	
	public OauthAccessToken(String token, String tokenSecret) {
		this.token = token;
		this.tokenSecret = tokenSecret;
	}
	
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getTokenSecret() {
		return tokenSecret;
	}
	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}
	
	
}
