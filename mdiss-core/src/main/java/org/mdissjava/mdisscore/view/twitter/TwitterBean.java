package org.mdissjava.mdisscore.view.twitter;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.mdissjava.mdisscore.controller.api.third.TwitterApiManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;

@RequestScoped
@ManagedBean
public class TwitterBean {
	
	private String token;
	private String tokenSecret;
	
	public TwitterBean() throws TwitterException {
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, String> parameterMap = (Map<String, String>) externalContext.getRequestParameterMap();
		String oauthToken = parameterMap.get("oauth_token");
		String oauthVerifier = parameterMap.get("oauth_verifier");
		
		//Create a requestToken
		TwitterApiManager twitterApi = new TwitterApiManager();
		
		//TODO: Don't hardcode things!!
		//get the access token
		AccessToken at = twitterApi.getTwitterAccessToken(this.retrieveSessionUserNick(), oauthVerifier);
		this.token = at.getToken();
		this.tokenSecret = at.getTokenSecret();
		
		//Now our 
		
		//were done, save in database :)
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
	
	private String retrieveSessionUserNick() {
		//Get the current logged user's username
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
		
	}

}
