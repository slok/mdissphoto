package org.mdissjava.mdisscore.model.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mdissjava.mdisscore.model.dao.AlbumDao;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.AlbumDaoImpl;
import org.mdissjava.mdisscore.model.dao.impl.PhotoDaoImpl;
import org.mdissjava.mdisscore.model.pojo.Album;
import org.mdissjava.mdisscore.model.pojo.Photo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.Datastore;

public class AlbumDaoImplTest {

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Test
	public void testInsertFind() {		
		Datastore db = MorphiaDatastoreFactory.getDatastore("test");
		this.logger.info("[TEST] testInsertField");

		AlbumDao albumdao = new AlbumDaoImpl(db);
		Album album = new Album();
		album.setTitle("Fiestas de Bilbao 2012");

		//insert some photos
		Photo p = new Photo();
		p.setTitle("Me and my cat");
		p.setPlus18(false);
		p.setDataId("23456784567");
		
		Photo p2 = new Photo();
		p2.setTitle("Me and my dog");
		p2.setPlus18(false);
		p2.setDataId("43265123124");
		
		PhotoDao pDao = new PhotoDaoImpl(db);
		pDao.insertPhoto(p);
		pDao.insertPhoto(p2);
		
		ArrayList<Photo> photos = new ArrayList<Photo>();
		photos.add(p);
		photos.add(p2);
		
		album.setPhotos(photos);
		
		// Insertion in the Mongo db
		albumdao.insertAlbum(album);

		// The inserted photo is find in the Mongo db
		List<Album> albumList = albumdao.findAlbum(album);
		
		// If the returned photo's title is "MiFoto" the insert went good
		assertEquals(albumList.get(0).getTitle(), "Fiestas de Bilbao 2012");
	}

	@Test
	public void testDelete() {
		this.logger.info("[TEST] testDelete PhotoDaoImpl");
		Datastore db = MorphiaDatastoreFactory.getDatastore("test");

		AlbumDao albumdao = new AlbumDaoImpl(db);
		Album album = new Album();
		album.setTitle("Fiestas de Bilbao 2012");
		
		// Insertion of the photo in the Mongo db
		albumdao.insertAlbum(album);
		
		// Deletion of the same Photo in the Mongo db
		albumdao.deleteAlbum(album);
		
		List<Album> albumList = albumdao.findAlbum(album);
		// If finding the photo returns an empty list means that it didn't find
		// the photo because it was deleted
		assertTrue(albumList.isEmpty());

	}

	@Test
	public void testUpdate() {
		this.logger.info("[TEST] testUpdate PhotoDaoImpl");
		Datastore db = MorphiaDatastoreFactory.getDatastore("test");

		AlbumDao albumdao = new AlbumDaoImpl(db);
		Album album = new Album();
		album.setTitle("Fiestas de Bilbao 2012");
		
		// Insertion of the photo in the Mongo db
		albumdao.insertAlbum(album);
		
		// The camera's model is changed
		album.setTitle("Fiestas de Bilbao 2012");
		
		// The photo is updated in the Mongo db
		albumdao.updateAlbum(album);
		List<Album> albumList = albumdao.findAlbum(album);
		assertFalse(albumList.isEmpty());
		
		// If the returned photo have the second title it went correct the
		// update in the Mongo db
		assertEquals(albumList.get(0).getTitle(), "Fiestas de Bilbao 2012");
	}
}

