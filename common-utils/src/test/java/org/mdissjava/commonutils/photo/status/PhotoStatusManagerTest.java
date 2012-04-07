package org.mdissjava.commonutils.photo.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mdissjava.commonutils.mongo.morphia.MorphiaDatastoreConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PhotoStatusManagerTest {

	
	private PhotoStatusManager photoStatusManager;
	private String NAME = "715_15_4_7357";
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@SuppressWarnings("rawtypes")
	@Before
	public void init() {
		List<Class> clazzez = new ArrayList<Class>();
		clazzez.add(PhotoStatus.class);
		MorphiaDatastoreConnection mdc = MorphiaDatastoreConnection.getInstance();
		mdc.connect("127.0.0.1", 27017, "test", clazzez);
		this.photoStatusManager = new PhotoStatusManager(mdc.getDatastore());
	}

	@After
	public void destroy() throws IOException {
		photoStatusManager.deletePhotoStatus(NAME);
		this.photoStatusManager = null;
	}
	
	@Test
	public void creationTest() throws IOException {
		this.logger.info("[TEST] creationTest");
		photoStatusManager.createPhotoStatus(NAME);
		
		
	}

	@Test(expected=IOException.class)
	public void creationduplicateTest() throws IOException{
		this.logger.info("[TEST] creationduplicateTest");
		photoStatusManager.createPhotoStatus(NAME);
		photoStatusManager.createPhotoStatus(NAME);
			
	}
	
	@Test
	public void markProcessedTest() throws IOException
	{
		this.logger.info("[TEST] markProcessedTest");
		photoStatusManager.createPhotoStatus(NAME);
		
		assertFalse(photoStatusManager.isProcessed(NAME));
		photoStatusManager.markAsProcessed(NAME);
		assertTrue(photoStatusManager.isProcessed(NAME));
		photoStatusManager.unmarkAsProcessed(NAME);
		assertFalse(photoStatusManager.isProcessed(NAME));
		
	}
	
	@Test
	public void markDetailedTest() throws IOException
	{
		this.logger.info("[TEST] markDetailedTest");
		photoStatusManager.createPhotoStatus(NAME);
		
		assertFalse(photoStatusManager.isDetailed(NAME));
		photoStatusManager.markAsDetailed(NAME);
		assertTrue(photoStatusManager.isDetailed(NAME));
		photoStatusManager.unmarkAsDetailed(NAME);
		assertFalse(photoStatusManager.isDetailed(NAME));
		
	}
}
