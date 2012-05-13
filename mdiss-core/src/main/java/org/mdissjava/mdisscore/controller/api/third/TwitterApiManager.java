package org.mdissjava.mdisscore.controller.api.third;

import java.util.HashMap;
import java.util.Map;

import org.mdissjava.mdisscore.controller.bll.UserOauthTokensManager;
import org.mdissjava.mdisscore.controller.bll.impl.UserOauthTokensManagerImpl;
import org.mdissjava.mdisscore.model.dao.UserOauthTokensDao;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.UserOauthTokensDaoImpl;
import org.mdissjava.mdisscore.model.pojo.OauthAccessToken;
import org.mdissjava.mdisscore.model.pojo.UserOauthTokens.Service;

import com.google.code.morphia.Datastore;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterApiManager {
	
	private ConfigurationBuilder cb = new ConfigurationBuilder();
	private Twitter twitter = null;
	static private Map<String, RequestToken> requestTokens = null;
	final private String CALLBACK_URL = "http://127.0.0.1:8080/mdissphoto/s/twitter/oauth";
	final private Datastore datastore;
	
	public TwitterApiManager() throws TwitterException {
		
		//TODO: load from properties
		this.datastore = MorphiaDatastoreFactory.getDatastore("mdissphoto");
		this.cb.setOAuthConsumerKey("uJOTDXfM7THTu4IIlAu6Xw");
		this.cb.setOAuthConsumerSecret("SZONdaQ2lAipEhnPh8hScHy4MQxr7omL9bjuin8I5I");
		this.twitter = new TwitterFactory(cb.build()).getInstance();
		
		//we need one requestToken for each OAUTH process.
		//we need to use the same request token (it's because of the time stamp)
		//So we store the items in a Map
		if (requestTokens == null)
			requestTokens = new HashMap<String, RequestToken>();
	}
	
	public String getTwitterTokenUrl(String username) throws TwitterException{
		
		//Add the requestToken for each user :)
		RequestToken rt = twitter.getOAuthRequestToken(this.CALLBACK_URL);
		requestTokens.put(username, rt);
		//return the url where the user accepts our app
		return rt.getAuthorizationURL();
		
	}
	
	public AccessToken verifyAndGetTwitterAccessToken(String username, String oauthVerifier) throws TwitterException{
		
		//retrieve the access token and delete the requestToken from the map. We don't need any more
		AccessToken accessToken = twitter.getOAuthAccessToken(requestTokens.remove(username), oauthVerifier);
		
		return accessToken;
	}
	
	public AccessToken createTwitterAccessToken(String token, String tokenSecret){
		return new AccessToken(token, tokenSecret);
	}
	
	
	public void updatestatus(OauthAccessToken oauthAccessToken, String message) throws TwitterException{
		
		AccessToken accessToken = new AccessToken(oauthAccessToken.getToken(), oauthAccessToken.getTokenSecret());
		twitter.setOAuthAccessToken(accessToken);
		twitter.updateStatus(message);
	}
	
	public OauthAccessToken getUserOauthCredentials(String username) throws IllegalAccessError
	{
		UserOauthTokensManager manager = new UserOauthTokensManagerImpl(datastore);
		return manager.getUserOauthAccessToken(username, Service.TWITTER);
	}
}
