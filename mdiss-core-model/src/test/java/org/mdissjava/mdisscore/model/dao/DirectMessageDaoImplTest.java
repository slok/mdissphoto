package org.mdissjava.mdisscore.model.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.DirectMessageDaoImpl;
import org.mdissjava.mdisscore.model.dao.impl.KeyLinkDaoImpl;
import org.mdissjava.mdisscore.model.pojo.DirectMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.Datastore;

public class DirectMessageDaoImplTest {

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	Datastore db;
	DirectMessageDao directMessageDao;

	@Before
	public void init() {
		db = MorphiaDatastoreFactory.getDatastore("test");
		directMessageDao = new DirectMessageDaoImpl(db);
	}

	@After
	public void destroy() {
		db = null;
		directMessageDao = null;
	}
	
	@Test
	public void testInsert()
	{
		DirectMessage directMessage = new DirectMessage(1,2, "Hola 2");
		directMessageDao.insertDirectMessage(directMessage);
		
		DirectMessage filter = new DirectMessage();
		filter.setId(directMessage.getId());
		
		List<DirectMessage> directMessageDaoList = directMessageDao.findDirectMessage(filter, true);
		assertTrue("There is one object", directMessageDaoList.size() == 1);
	
		directMessageDao.deleteDirectMessage(directMessageDaoList.get(0));
		directMessageDaoList = directMessageDao.findDirectMessage(filter, true);
		assertTrue("The db is empty", directMessageDaoList.size() == 0);
	}
	
	@Test
	public void testUpdate() {
		DirectMessage directMessage = new DirectMessage(1,2, "Hello");
		System.out.println("1 " + directMessage.isRead());
		directMessageDao.insertDirectMessage(directMessage);
		DirectMessage filter = new DirectMessage();
		filter.setId(directMessage.getId());
		
		List<DirectMessage> directMessageDaoList = directMessageDao.findDirectMessage(filter, true);
		if(!directMessageDaoList.isEmpty()) {
			directMessage = directMessageDaoList.get(0);
			System.out.println("2 " + directMessage.isRead());
			assertTrue(!directMessage.isRead());
		}
		else{
			fail("There must be a object in the database");
		}
		directMessageDao.markAsRead(directMessage.getId());
		directMessageDaoList = directMessageDao.findDirectMessage(filter, true);
		if(!directMessageDaoList.isEmpty()) {
			assertTrue(directMessageDaoList.get(0).isRead());
		}
		else{
			fail("There must be a object in the database");
		}
	}
	
}
