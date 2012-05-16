package org.mdissjava.mdisscore.model.dao;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.KeyLinkDaoImpl;
import org.mdissjava.mdisscore.model.pojo.KeyLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.Datastore;

public class KeyLinkDaoImplTest {

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	Datastore db;
	KeyLinkDao keylinkdao;

	@Before
	public void init() {
		db = MorphiaDatastoreFactory.getDatastore("test");
		keylinkdao = new KeyLinkDaoImpl(db);
	}

	@After
	public void destroy() {
		db = null;
		keylinkdao = null;
	}

	@Test
	public void test() {
		logger.info("KeyLinkDaoImplTest Test initiated");
		KeyLink keylink = new KeyLink(123456789, KeyLink.EMAIL_VALIDATION);
		keylinkdao.insertKeyLink(keylink);
		KeyLink kl = keylinkdao.findKeyLink(keylink.getId());
		assertEquals(kl.getUserId(), 123456789);
	}

}
