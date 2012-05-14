package org.mdissjava.mdisscore.view.twitter;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.mdissjava.mdisscore.controller.api.third.TwitterApiManager;
import org.mdissjava.mdisscore.controller.bll.impl.UserOauthTokensManagerImpl;
import org.mdissjava.mdisscore.model.pojo.UserOauthTokens.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import twitter4j.auth.AccessToken;

@RequestScoped
@ManagedBean
public class TwitterBean {
	
	private String token;
	private String tokenSecret;
	private boolean allOk;
	
	public TwitterBean(){
		
		try{
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, String> parameterMap = (Map<String, String>) externalContext.getRequestParameterMap();
			String oauthVerifier = parameterMap.get("oauth_verifier");
			
			//Create a requestToken
			TwitterApiManager twitterApi = new TwitterApiManager();

			//get the access token
			AccessToken at = twitterApi.verifyAndGetTwitterAccessToken(this.retrieveSessionUserNick(), oauthVerifier);
			this.token = at.getToken();
			this.tokenSecret = at.getTokenSecret();
			System.out.println(this.token + " : " + this.tokenSecret);
			
			new UserOauthTokensManagerImpl().insertOrUpdateUserOauthAccessToken(retrieveSessionUserNick(), 
																				Service.TWITTER, 
																				token, tokenSecret);
			allOk = true;
		}catch(Exception e){
			allOk = false;
			
		}
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
	
	public boolean isAllOk() {
		return allOk;
	}

	public void setAllOk(boolean allOk) {
		this.allOk = allOk;
	}

	private String retrieveSessionUserNick() {
		//Get the current logged user's username
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
		
	}

}
