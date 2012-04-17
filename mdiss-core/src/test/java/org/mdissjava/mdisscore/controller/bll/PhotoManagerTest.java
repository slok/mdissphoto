package org.mdissjava.mdisscore.controller.bll;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mdissjava.commonutils.mongo.morphia.MorphiaDatastoreConnection;
import org.mdissjava.commonutils.photo.status.PhotoStatus;
import org.mdissjava.commonutils.photo.status.PhotoStatusManager;
import org.mdissjava.mdisscore.controller.bll.impl.PhotoManagerImpl;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.pojo.Photo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.Datastore;

public class PhotoManagerTest {

	
	
	private PhotoManager photoManager;
	private final String NAME = "715_15_4_7357";
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Before
	public void init()
	{
		Datastore ds = MorphiaDatastoreFactory.getDatastore("test");
		this.photoManager = new PhotoManagerImpl(ds);
	}
	
	@After
	public void destroy() throws IOException {
		photoManager.deletePhoto(NAME);
		this.photoManager = null;
	}
	
	/*@Test
	public void creationTest() throws IllegalStateException, IOException {
		
		String tags = "animals, cats, dogs, police" ;
		
		String albumTitle = "Jaiak 2012";
		String userNickname = "slok";
		
		this.photoManager.insertPhoto(NAME, userNickname, 
				"yeaaaaaaaaaaaah", albumTitle, true, false, 
				"CC", tags);
		
	}*/

}
