package org.mdissjava.mdisscore.model.dao;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mdissjava.mdisscore.controller.bll.UserOauthTokensManager;
import org.mdissjava.mdisscore.controller.bll.impl.UserOauthTokensManagerImpl;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.UserOauthTokensDaoImpl;
import org.mdissjava.mdisscore.model.pojo.OauthAccessToken;
import org.mdissjava.mdisscore.model.pojo.UserOauthTokens;
import org.mdissjava.mdisscore.model.pojo.UserOauthTokens.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.Datastore;

public class UserOauthTokensManagerTests {

	final Logger logger = LoggerFactory.getLogger(this.getClass());
	Datastore db;
	private UserOauthTokensDao userOauthTokensDao;
	
	private final String USERNAME = "slok";
	private final String token = "a0hORbvyFyWTuByeXN5tPwkJiTHWzswYaSSv";
	private final String tokenSecret = "4l6fZwinZErlFjz24zilzotA8BYl7SgKwioEfuiL34c";
	private final Service service = Service.TWITTER;
	@Before
	public void init() {
		this.db = MorphiaDatastoreFactory.getDatastore("test");
		this.userOauthTokensDao = new UserOauthTokensDaoImpl(db);
	}
	
	
	@After
	public void destroy() {		
		userOauthTokensDao = null;
	}
	
	
	@Test
	public void insertAndGetOauthTokenTest() {
		
		//insert
		UserOauthTokensManager uotm =  new UserOauthTokensManagerImpl(db);
		uotm.insertOrUpdateUserOauthAccessToken(USERNAME, service, token, tokenSecret);
		
		OauthAccessToken oat = uotm.getUserOauthAccessToken(USERNAME, service);
		
		//check
		assertEquals(token, oat.getToken());
		assertEquals(tokenSecret, oat.getTokenSecret());
		
		//delete
		UserOauthTokens userOauthTokens = new UserOauthTokens();
		userOauthTokens.setUsername(USERNAME);
		userOauthTokens = this.userOauthTokensDao.findUserOauthTokens(userOauthTokens).get(0);
		this.userOauthTokensDao.deleteUserOauthTokens(userOauthTokens);
		
		
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void deleteAndGetOauthTokenTest(){
		
		//insert
		UserOauthTokensManager uotm =  new UserOauthTokensManagerImpl(db);
		uotm.insertOrUpdateUserOauthAccessToken(USERNAME, service, token, tokenSecret);
		
		//check
		OauthAccessToken oat = uotm.getUserOauthAccessToken(USERNAME, service);
		assertEquals(token, oat.getToken());
		assertEquals(tokenSecret, oat.getTokenSecret());
		
		//delete
		uotm.deleteUserOauthAccessToken(USERNAME, service);
		
		//check again
		oat = uotm.getUserOauthAccessToken(USERNAME, service);
	}
}
