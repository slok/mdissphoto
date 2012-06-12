package org.mdissjava.mdisscore.controller.bll;

import static org.junit.Assert.assertEquals;

import java.security.NoSuchAlgorithmException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mdissjava.mdisscore.controller.bll.impl.UserHmacTokensManagerImpl;
import org.mdissjava.mdisscore.model.dao.UserHmacTokensDao;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.UserHmacTokensDaoImpl;
import org.mdissjava.mdisscore.model.pojo.UserHmacTokens;
import org.mdissjava.mdisscore.model.pojo.UserHmacTokens.HmacService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.Datastore;

public class UserHmacTokensManagerTest {

	final Logger logger = LoggerFactory.getLogger(this.getClass());
	private Datastore db;
	private UserHmacTokensDao userHmacTokensDao;

	private final String USERNAME = "slok";
	private final String TOKEN = "32d9737523ec594fc9b007643b162011863c1024";
	private final HmacService SERVICE = HmacService.MDISSPHOTO;

	@Before
	public void init() {
		this.db = MorphiaDatastoreFactory.getDatastore("test");
		this.userHmacTokensDao = new UserHmacTokensDaoImpl(db);
	}

	@After
	public void destroy() {
		this.userHmacTokensDao = null;
	}

	@Test
	public void insertAndGetHmacToken() throws NoSuchAlgorithmException {
		// insert
		UserHmacTokensManager uhtm = new UserHmacTokensManagerImpl(db);
		uhtm.insertOrUpdateUserHmacToken(USERNAME, SERVICE, TOKEN);
		String token = uhtm.getUserHmacToken(USERNAME, SERVICE);

		// check
		assertEquals(TOKEN, token);

		// delete
		UserHmacTokens hmacTokens = new UserHmacTokens();
		hmacTokens.setUsername(USERNAME);
		hmacTokens = this.userHmacTokensDao.findUserHmacTokens(hmacTokens).get(
				0);
		this.userHmacTokensDao.deleteUserHmacTokens(hmacTokens);
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteAndGetHmacTokenTest() {

		// insert
		UserHmacTokensManager uhtm = new UserHmacTokensManagerImpl(db);
		uhtm.insertOrUpdateUserHmacToken(USERNAME, SERVICE, TOKEN);
		String token = uhtm.getUserHmacToken(USERNAME, SERVICE);

		// check
		assertEquals(TOKEN, token);

		// delete
		uhtm.deleteUserHmacToken(USERNAME, SERVICE);

		// check again
		uhtm.getUserHmacToken(USERNAME, SERVICE);
	}

}
