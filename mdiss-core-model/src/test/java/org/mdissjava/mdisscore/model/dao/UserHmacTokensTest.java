package org.mdissjava.mdisscore.model.dao;

import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mdissjava.commonutils.utils.Utils;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.UserHmacTokensDaoImpl;
import org.mdissjava.mdisscore.model.dao.impl.UserOauthTokensDaoImpl;
import org.mdissjava.mdisscore.model.pojo.UserHmacTokens;
import org.mdissjava.mdisscore.model.pojo.UserHmacTokens.HmacService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.Datastore;

public class UserHmacTokensTest {
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	private Datastore db;
	private UserHmacTokensDao userHmacTokensDao;
	
	@Before
	public void init() {
		this.db = MorphiaDatastoreFactory.getDatastore("test");
		this.userHmacTokensDao = new UserHmacTokensDaoImpl(db);
	}

	@After
	public void destroy() {
		this.db = null;
		this.userHmacTokensDao = null;
	}
	
	@Test
	public void insertHmacTokenTest() throws NoSuchAlgorithmException{
		this.logger.info("[TEST] insertHmacTokenTest");
		String user = "slok";
		UserHmacTokens uht = new UserHmacTokens();
		uht.setUsername(user);
		HashMap<HmacService, String> tokens = new HashMap<UserHmacTokens.HmacService, String>();
		String secret = Utils.calculateHash(UUID.randomUUID().toString(), "SHA-256");
		this.logger.info("Inserting token {} for user {}", secret, user);
		tokens.put(HmacService.MDISSPHOTO, secret);
		uht.setTokens(tokens);
		
		this.userHmacTokensDao.insertUserHmacTokens(uht);
		
		//Check
		UserHmacTokens uhtHelper = new UserHmacTokens();
		uhtHelper.setUsername(user);
		uhtHelper = this.userHmacTokensDao.findUserHmacTokens(uhtHelper).get(0);
		assertEquals(secret, uhtHelper.getTokens().get(HmacService.MDISSPHOTO));
		
		//delete...
		this.userHmacTokensDao.deleteUserHmacTokens(uhtHelper);
	}

	@Test
	public void updateHmacTokenTest() throws NoSuchAlgorithmException{
		//insert
		this.logger.info("[TEST] updateHmacTokenTest");
		String user = "slok";
		UserHmacTokens uht = new UserHmacTokens();
		uht.setUsername(user);
		HashMap<HmacService, String> tokens = new HashMap<UserHmacTokens.HmacService, String>();
		String secret = Utils.calculateHash(UUID.randomUUID().toString(), "SHA-256");
		this.logger.info("Inserting token {} for user {}", secret, user);
		tokens.put(HmacService.MDISSPHOTO, secret);
		uht.setTokens(tokens);
		
		this.userHmacTokensDao.insertUserHmacTokens(uht);
		
		//Check
		UserHmacTokens uhtHelper = new UserHmacTokens();
		uhtHelper.setUsername(user);
		uhtHelper = this.userHmacTokensDao.findUserHmacTokens(uhtHelper).get(0);
		assertEquals(secret, uhtHelper.getTokens().get(HmacService.MDISSPHOTO));
		
		//update
		uht = new UserHmacTokens();
		uht.setUsername(user);
		uht = this.userHmacTokensDao.findUserHmacTokens(uht).get(0);
		String secret2 = Utils.calculateHash(UUID.randomUUID().toString(), "SHA-256");
		tokens.put(HmacService.MDISSPHOTO, secret2);
		uht.setTokens(tokens);
		this.userHmacTokensDao.updateUserHmacTokens(uht);
		
		//check
		uhtHelper = new UserHmacTokens();
		uhtHelper.setUsername(user);
		uhtHelper = this.userHmacTokensDao.findUserHmacTokens(uhtHelper).get(0);
		assertEquals(secret2, uhtHelper.getTokens().get(HmacService.MDISSPHOTO));
		
		//delete
		this.userHmacTokensDao.deleteUserHmacTokens(uhtHelper);
	}
	
	@Test
	public void deleteHmacTokenTest() throws NoSuchAlgorithmException{
		//insert
		this.logger.info("[TEST] updateHmacTokenTest");
		String user = "slok";
		UserHmacTokens uht = new UserHmacTokens();
		uht.setUsername(user);
		HashMap<HmacService, String> tokens = new HashMap<UserHmacTokens.HmacService, String>();
		String secret = Utils.calculateHash(UUID.randomUUID().toString(), "SHA-256");
		this.logger.info("Inserting token {} for user {}", secret, user);
		tokens.put(HmacService.MDISSPHOTO, secret);
		uht.setTokens(tokens);
		
		this.userHmacTokensDao.insertUserHmacTokens(uht);
		
		//delete
		this.userHmacTokensDao.deleteUserHmacTokens(uht);
		
		//check
		uht = new UserHmacTokens();
		uht.setUsername(user);
		assertTrue(this.userHmacTokensDao.findUserHmacTokens(uht).isEmpty());
	}
}
