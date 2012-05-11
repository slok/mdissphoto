package org.mdissjava.mdisscore.controller.bll;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

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
import org.mdissjava.mdisscore.controller.bll.impl.AlbumManagerImpl;
import org.mdissjava.mdisscore.controller.bll.impl.PhotoManagerImpl;
import org.mdissjava.mdisscore.model.dao.TagDao;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.TagDaoImpl;
import org.mdissjava.mdisscore.model.pojo.Album;
import org.mdissjava.mdisscore.model.pojo.Photo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.Datastore;

public class PhotoManagerTest {

	
	
	private PhotoManager photoManager;
	private AlbumManager albumManager;
	private final String NAME = "715_15_4_7357";
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final Album album = new Album();
	
	@Before
	public void init()
	{
		Datastore ds = MorphiaDatastoreFactory.getDatastore("test");
		this.photoManager = new PhotoManagerImpl(ds);
		this.albumManager = new AlbumManagerImpl(ds);
	}
	
	@After
	public void destroy() throws IOException {
		albumManager.deleteAlbum(album);
		photoManager.deletePhoto(NAME);

		this.photoManager = null;
	}
	//TODO Problems with album deletion
	
	/*@Test
	public void creationTest() throws IllegalStateException, IOException {
		
		this.logger.info("[TEST] testUpdate TagDaoImpl");
		Datastore db = MorphiaDatastoreFactory.getDatastore("test");
		
		PhotoManagerImpl pManager = new PhotoManagerImpl(db);
				
		String tags = "animals, cats, dogs, police, dogs" ;
		
		String albumTitle = "Jaiak 2012";
		String userNickname = "slok";
		
		album.setTitle(albumTitle);
		album.setUserNick(userNickname);
		albumManager.insertAlbum(album);
		
		this.photoManager.insertPhoto(NAME, userNickname, 
				"yeaaaaaaaaaaaah", albumTitle, true, false, 
				"CC", tags);
		 

		Photo photo = pManager.searchPhotoUniqueUtil(NAME);
		
		if(photo!=null)
		{
			assertTrue(!(photo.getTags().isEmpty()));
		}
		
	}*/

}
