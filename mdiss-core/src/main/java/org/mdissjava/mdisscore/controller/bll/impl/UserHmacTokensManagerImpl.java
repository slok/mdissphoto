package org.mdissjava.mdisscore.controller.bll.impl;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.mdissjava.commonutils.utils.Utils;
import org.mdissjava.mdisscore.controller.bll.UserHmacTokensManager;
import org.mdissjava.mdisscore.model.dao.UserHmacTokensDao;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.UserHmacTokensDaoImpl;
import org.mdissjava.mdisscore.model.pojo.UserHmacTokens;
import org.mdissjava.mdisscore.model.pojo.UserHmacTokens.HmacService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.Datastore;

public class UserHmacTokensManagerImpl implements UserHmacTokensManager {

	private final String DATABASE = "mdissphoto"; 
	private UserHmacTokensDao userHmacTokensDao;
	private Datastore datastore;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public UserHmacTokensManagerImpl() {
		this.datastore = MorphiaDatastoreFactory.getDatastore(DATABASE);
		this.userHmacTokensDao = new UserHmacTokensDaoImpl(this.datastore);
	}
	
	public UserHmacTokensManagerImpl(Datastore datastore) {
		this.datastore = datastore;
		this.userHmacTokensDao = new UserHmacTokensDaoImpl(this.datastore);
	}
	
	@Override
	public String getUserHmacToken(String username, HmacService service) {
		this.logger.debug("getting {} service for user {}",service, username);
		
		if (username.isEmpty() || service == null)	
		{
			this.logger.error("Some arguments is/are null, can't continue with the action");
			throw new IllegalArgumentException("Some arguments are null, can't continue with the action");
		}
		
		UserHmacTokens uht = new UserHmacTokens();
		uht.setUsername(username);
		List<UserHmacTokens> userTokens = this.userHmacTokensDao.findUserHmacTokens(uht);
		
		 if(userTokens.size() > 1) //There should be only one
			throw new IllegalStateException("There are more than one elements for the same id");
		 else if(userTokens.isEmpty()) //The user hasn't tokens yet
				throw new IllegalAccessError("There user hasn't been registered yet for any tokens");
		 else{
			 Map<HmacService, String> tokens = userTokens.get(0).getTokens();
			 //if there isn't nothing for that service exception
			 try{
				 
				 String token = tokens.get(service);
				 if (token == null)
					 throw new IllegalArgumentException("There are no AccessTokens for " + service + " service");
				 else
					 return token;
				 
			 }catch (NullPointerException npe){
				 throw new IllegalArgumentException("There are no AccessTokens for " + service + " service");
			 }
		}
	}

	@Override
	public void insertOrUpdateUserHmacToken(String username, HmacService service, String token) {
		
		this.logger.debug("Inserting new {} service for user {}",service, username);
		
		if (username.isEmpty() || service == null || token.isEmpty())	
		{
			this.logger.error("Some arguments is/are null, can't continue with the action");
			throw new IllegalArgumentException("Some arguments are null, can't continue with the action");
		}
		
		//get the users tokens
		UserHmacTokens uht = new UserHmacTokens();
		uht.setUsername(username);
		List<UserHmacTokens> userTokens = this.userHmacTokensDao.findUserHmacTokens(uht);
		
		//if there are no tokens then insert
		if (userTokens.isEmpty())
		{
			Map<HmacService, String> tokens = new HashMap<HmacService, String>();
			tokens.put(service, token);
			uht.setTokens(tokens);
			
			//save in database
			this.userHmacTokensDao.insertUserHmacTokens(uht);
			
		}else if(userTokens.size() > 1) //There should be only one
			throw new IllegalStateException("There are more than one elements for the same id");
		else{
			//insert or update
			uht = userTokens.get(0);
			Map<HmacService, String> tokens = uht.getTokens();
			//maybe is empty
			if (tokens == null)
				tokens = new HashMap<HmacService, String>();
			
			tokens.put(service, token);
			uht.setTokens(tokens);
			
			//save in database
			this.userHmacTokensDao.updateUserHmacTokens(uht);
		}
	}

	@Override
	public String insertOrUpdateUserHmacToken(String username, HmacService service) throws NoSuchAlgorithmException {
		String token = Utils.calculateHash(UUID.randomUUID().toString(), "SHA-256");
		this.insertOrUpdateUserHmacToken(username, service, token);
		return token;
	}

	@Override
	public void deleteUserHmacToken(String username, HmacService service) {
		this.logger.debug("deleting {} service token for user {}",service, username);
		if (username.isEmpty() || service == null)	
		{
			this.logger.error("Some arguments is/are null, can't continue with the action");
			throw new IllegalArgumentException("Some arguments are null, can't continue with the action");
		}
		
		//get the users tokens
		UserHmacTokens uht = new UserHmacTokens();
		uht.setUsername(username);
		List<UserHmacTokens> userTokens = this.userHmacTokensDao.findUserHmacTokens(uht);
		
		if(userTokens.size() > 1) //There should be only one
			throw new IllegalStateException("There are more than one elements for the same id");
		else if(userTokens.isEmpty()) //There should be only one
			throw new IllegalStateException("Nothing to delete");
		else{
			uht = userTokens.get(0);
			Map<HmacService, String> tokens = uht.getTokens();
			tokens.remove(service);
			uht.setTokens(tokens);
			this.userHmacTokensDao.updateUserHmacTokens(uht);
		}				
	}

}
