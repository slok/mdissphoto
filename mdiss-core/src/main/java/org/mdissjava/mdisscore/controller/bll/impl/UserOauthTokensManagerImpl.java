package org.mdissjava.mdisscore.controller.bll.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mdissjava.mdisscore.controller.bll.UserOauthTokensManager;
import org.mdissjava.mdisscore.model.dao.UserOauthTokensDao;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.UserOauthTokensDaoImpl;
import org.mdissjava.mdisscore.model.pojo.OauthAccessToken;
import org.mdissjava.mdisscore.model.pojo.UserOauthTokens;
import org.mdissjava.mdisscore.model.pojo.UserOauthTokens.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.Datastore;

public class UserOauthTokensManagerImpl implements UserOauthTokensManager{

	private final String DATABASE = "mdissphoto"; 
	private UserOauthTokensDao userOauthTokensDao;
	private Datastore datastore;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	
	public UserOauthTokensManagerImpl(Datastore ds) {
		this.datastore = ds;
		this.userOauthTokensDao = new UserOauthTokensDaoImpl(ds);
	}
	
	public UserOauthTokensManagerImpl() {
		this.datastore = MorphiaDatastoreFactory.getDatastore(DATABASE);
		this.userOauthTokensDao = new UserOauthTokensDaoImpl(datastore);
	}
	
	@Override
	public OauthAccessToken getUserOauthAccessToken(String username, Service service) {
		
		this.logger.debug("getting {} service for user {}",service, username);
		
		if (username.isEmpty() || service == null)	
		{
			this.logger.error("Some arguments is/are null, can't continue with the action");
			throw new IllegalArgumentException("Some arguments are null, can't continue with the action");
		}
		
		//get the users tokens
		UserOauthTokens uot = new UserOauthTokens();
		uot.setUsername(username);
		List<UserOauthTokens> userTokens = this.userOauthTokensDao.findUserOauthTokens(uot);
		
		 if(userTokens.size() > 1) //There should be only one
			throw new IllegalStateException("There are more than one elements for the same id");
		 else{
			 Map<Service, OauthAccessToken> tokens = userTokens.get(0).getTokens();
			 //if there isn't nothing for that service exception
			 try{
				 
				 OauthAccessToken oat = tokens.get(service);
				 if (oat == null)
					 throw new IllegalArgumentException("There are no AccessTokens for " + service + " service");
				 else
					 return oat;
				 
			 }catch (NullPointerException npe){
				 throw new IllegalArgumentException("There are no AccessTokens for " + service + " service");
			 }
		}
	}

	@Override
	public void insertOrUpdateUserOauthAccessToken(String username, Service service,
			String token, String tokenSecret) {

		this.logger.debug("Inserting new {} service for user {}",service, username);
		
		if (username.isEmpty() || service == null 
			|| token.isEmpty() || tokenSecret.isEmpty())	
		{
			this.logger.error("Some arguments is/are null, can't continue with the action");
			throw new IllegalArgumentException("Some arguments are null, can't continue with the action");
		}
		
		//get the users tokens
		UserOauthTokens uot = new UserOauthTokens();
		uot.setUsername(username);
		List<UserOauthTokens> userTokens = this.userOauthTokensDao.findUserOauthTokens(uot);
		
		//create the OauthAccessToken for inserting or updating
		OauthAccessToken accessToken = new OauthAccessToken(token, tokenSecret);
		
		//if there are no tokens then insert
		if (userTokens.isEmpty())
		{
			Map<Service, OauthAccessToken> tokens = new HashMap<Service, OauthAccessToken>();
			tokens.put(service, accessToken);
			uot.setTokens(tokens);
			
			//save in database
			this.userOauthTokensDao.insertUserOauthTokens(uot);
			
		}else if(userTokens.size() > 1) //There should be only one
			throw new IllegalStateException("There are more than one elements for the same id");
		else{
			//insert or update
			uot = userTokens.get(0);
			Map<Service, OauthAccessToken> tokens = uot.getTokens();
			//maybe is empty
			if (tokens == null)
				tokens = new HashMap<UserOauthTokens.Service, OauthAccessToken>();
			
			tokens.put(service, accessToken);
			uot.setTokens(tokens);
			
			//save in database
			this.userOauthTokensDao.updateUserOauthTokens(uot);
		}
		
		
	}

	@Override
	public void deleteUserOauthAccessToken(String username, Service service) {
		
		this.logger.debug("deleting {} service token for user {}",service, username);
		if (username.isEmpty() || service == null)	
		{
			this.logger.error("Some arguments is/are null, can't continue with the action");
			throw new IllegalArgumentException("Some arguments are null, can't continue with the action");
		}
		
		//get the users tokens
		UserOauthTokens uot = new UserOauthTokens();
		uot.setUsername(username);
		List<UserOauthTokens> userTokens = this.userOauthTokensDao.findUserOauthTokens(uot);
		
		if(userTokens.size() > 1) //There should be only one
			throw new IllegalStateException("There are more than one elements for the same id");
		else if(userTokens.isEmpty()) //There should be only one
			throw new IllegalStateException("Nothing to delete");
		else{
			uot = userTokens.get(0);
			Map<Service, OauthAccessToken> tokens = uot.getTokens();
			tokens.remove(service);
			uot.setTokens(tokens);
			this.userOauthTokensDao.updateUserOauthTokens(uot);
		}				
	}

}
