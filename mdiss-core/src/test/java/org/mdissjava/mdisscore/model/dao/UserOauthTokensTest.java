package org.mdissjava.mdisscore.model.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.UserOauthTokensDaoImpl;
import org.mdissjava.mdisscore.model.pojo.UserOauthTokens;
import org.mdissjava.mdisscore.model.pojo.UserOauthTokens.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.Datastore;

public class UserOauthTokensTest {

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	private Datastore db;
	private UserOauthTokensDao userOauthTokenDao;
	
	@Before
	public void init() {
		this.db = MorphiaDatastoreFactory.getDatastore("test");
		this.userOauthTokenDao = new UserOauthTokensDaoImpl(db);
	}

	@After
	public void destroy() {
		this.db = null;
		this.userOauthTokenDao = null;
	}
	
	@Test
	public void insertOauthTokensTest() {
		
		this.logger.info("TEST insertOauthTokensTest");
		//insert
		UserOauthTokens uat = new UserOauthTokens();
		uat.setUsername("slok");
		Map<Service, String> tokens = new HashMap<UserOauthTokens.Service, String>();
		String token = "123454234dfdsfdad3223t5435rt432rt43erte2432wsadfsce3343wqew43232we";
		tokens.put(Service.TWITTER, token);
		uat.setTokens(tokens);
		
		this.userOauthTokenDao.insertUserOauthTokens(uat);
		
		//check
		UserOauthTokens uatHelper = new UserOauthTokens();
		uatHelper.setUsername("slok");
		uatHelper = this.userOauthTokenDao.findUserOauthTokens(uatHelper).get(0);
		assertEquals(token, uatHelper.getTokens().get(Service.TWITTER));
		
		//delete the garbage...
		this.userOauthTokenDao.deleteUserOauthTokens(uatHelper);
		
	}
	
	@Test
	public void updateOauthTokensTest() {
		
		this.logger.info("TEST updateOauthTokensTest");
		//insert
		UserOauthTokens uat = new UserOauthTokens();
		uat.setUsername("slok");
		Map<Service, String> tokens = new HashMap<UserOauthTokens.Service, String>();
		String token = "123454234dfdsfdad3223t5435rt432rt43erte2432wsadfsce3343wqew43232we";
		tokens.put(Service.TWITTER, token);
		uat.setTokens(tokens);
		
		this.userOauthTokenDao.insertUserOauthTokens(uat);
		
		//check
		UserOauthTokens uatHelper = new UserOauthTokens();
		uatHelper.setUsername("slok");
		uatHelper = this.userOauthTokenDao.findUserOauthTokens(uatHelper).get(0);
		assertEquals(token, uatHelper.getTokens().get(Service.TWITTER));
		
		//update
		token = "ABCDEFGHIJKLMNÑOPQRSTUXVWYZ";
		tokens = uatHelper.getTokens();
		tokens.put(Service.TWITTER, token);
		uatHelper.setTokens(tokens);
		this.userOauthTokenDao.updateUserOauthTokens(uatHelper);
		
		//check again
		uatHelper = new UserOauthTokens();
		uatHelper.setUsername("slok");
		uatHelper = this.userOauthTokenDao.findUserOauthTokens(uatHelper).get(0);
		assertEquals(token, uatHelper.getTokens().get(Service.TWITTER));
		
		//delete the garbage...
		this.userOauthTokenDao.deleteUserOauthTokens(uatHelper);
		
	}
	
	@Test
	public void deleteOauthTokensTest() {
		
		this.logger.info("TEST deleteOauthTokensTest");
		
		//insert
		UserOauthTokens uat = new UserOauthTokens();
		uat.setUsername("slok");
		Map<Service, String> tokens = new HashMap<UserOauthTokens.Service, String>();
		String token = "123454234dfdsfdad3223t5435rt432rt43erte2432wsadfsce3343wqew43232we";
		tokens.put(Service.TWITTER, token);
		uat.setTokens(tokens);
		
		this.userOauthTokenDao.insertUserOauthTokens(uat);
		
		//delete
		this.userOauthTokenDao.deleteUserOauthTokens(uat);
		
		//check
		UserOauthTokens uatHelper = new UserOauthTokens();
		uatHelper.setUsername("slok");
		assertTrue(this.userOauthTokenDao.findUserOauthTokens(uatHelper).isEmpty());
	}

}
