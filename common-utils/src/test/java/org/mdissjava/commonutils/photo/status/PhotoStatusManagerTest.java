package org.mdissjava.commonutils.photo.status;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	public void processedStatusTest() throws IOException
	{
		this.logger.info("[TEST] processedStatusTest");
		
		//creation first state to null ->  need to process
		photoStatusManager.createPhotoStatus(NAME);
		assertTrue(photoStatusManager.needsToBeProcessed(NAME));
		assertFalse(photoStatusManager.hasStartedProcessing(NAME));
		assertFalse(photoStatusManager.hasFinishedProcessing(NAME));
		
		//second state,  the process has been started (in the job queue) -> no need to process
		photoStatusManager.markAsProcessedStarted(NAME);
		assertFalse(photoStatusManager.needsToBeProcessed(NAME));
		assertTrue(photoStatusManager.hasStartedProcessing(NAME));
		assertFalse(photoStatusManager.hasFinishedProcessing(NAME));
		
		//third state, the process has finished -> no need to process
		photoStatusManager.markAsProcessedFinished(NAME);
		assertFalse(photoStatusManager.needsToBeProcessed(NAME));
		assertTrue(photoStatusManager.hasStartedProcessing(NAME));
		assertTrue(photoStatusManager.hasFinishedProcessing(NAME));

		
	}
	
	@Test
	public void detailedStatusTest() throws IOException
	{
		this.logger.info("[TEST] detailedStatusTest");
		
		photoStatusManager.createPhotoStatus(NAME);
		assertTrue(photoStatusManager.needsToBeDetailed(NAME));
		
		photoStatusManager.markAsDetailed(NAME);
		assertFalse(photoStatusManager.needsToBeDetailed(NAME));
		
	}
}
