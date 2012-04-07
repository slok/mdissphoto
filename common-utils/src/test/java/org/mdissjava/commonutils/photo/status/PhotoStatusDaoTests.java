package org.mdissjava.commonutils.photo.status;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mdissjava.commonutils.mongo.morphia.MorphiaDatastoreConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.Datastore;

public class PhotoStatusDaoTests {

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	private PhotoStatusDao photoStatusDao;

	@SuppressWarnings("rawtypes")
	@Before
	public void init() {
		List<Class> clazzez = new ArrayList<Class>();
		clazzez.add(PhotoStatus.class);
		MorphiaDatastoreConnection mdc = MorphiaDatastoreConnection.getInstance();
		mdc.connect("127.0.0.1", 27017, "test", clazzez);
		photoStatusDao = new PhotoStatusDaoImpl(mdc.getDatastore());
	}

	@After
	public void destroy() {
		photoStatusDao = null;
	}
	
	@Test
	public void insertAndFindTest() {

		this.logger.info("[TEST] insertAndFindTest");

		PhotoStatus photoStatus = new PhotoStatus();
		photoStatus.setName("horl");
		photoStatus.setProcessed(false);
		photoStatus.setDetailed(true);
		photoStatus.setUpdateDate(new Date());
		
		// Insertion in the Mongo db
		photoStatusDao.insertPhotoStatus(photoStatus);
		
		// Find in the Mongo db
		List<PhotoStatus> photoStatusList = photoStatusDao.findPhotoStatus(photoStatus);
		
		assertEquals(photoStatusList.get(0).getName(), "horl");
	}
	
	@Test
	public void deleteTest() {

		this.logger.info("[TEST] deleteTest");

		PhotoStatus photoStatus = new PhotoStatus();
		photoStatus.setName("horl2");
		photoStatus.setProcessed(false);
		photoStatus.setDetailed(true);
		photoStatus.setUpdateDate(new Date());
		
		// Insertion in the Mongo db
		photoStatusDao.insertPhotoStatus(photoStatus);
		
		//Delete
		photoStatusDao.deletePhotoStatus(photoStatus);
		
		//check
		List<PhotoStatus> photoStatusList = photoStatusDao.findPhotoStatus(photoStatus);
		
		assertTrue(photoStatusList.isEmpty());
	}

	
	@Test
	public void updateTest() {

		this.logger.info("[TEST] deleteTest");

		PhotoStatus photoStatus = new PhotoStatus();
		photoStatus.setName("horl3");
		photoStatus.setProcessed(false);
		photoStatus.setDetailed(true);
		photoStatus.setUpdateDate(new Date());
		
		// Insertion in the Mongo db
		photoStatusDao.insertPhotoStatus(photoStatus);
		
		//check
		List<PhotoStatus> photoStatusList = photoStatusDao.findPhotoStatus(photoStatus);
		assertEquals(photoStatusList.get(0).getName(), "horl3");
		
		//update
		photoStatus.setName("horl4");
		photoStatusDao.updatePhotoStatus(photoStatus);
		
		//check again
		photoStatusList = photoStatusDao.findPhotoStatus(photoStatus);
		assertEquals(photoStatusList.get(0).getName(), "horl4");
		
	}
}
