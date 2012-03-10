package org.mdissjava.mdisscore.model.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.mdissjava.mdisscore.model.dao.AlbumDao;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.AlbumDaoImpl;
import org.mdissjava.mdisscore.model.pojo.Album;
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

