package org.mdissjava.mdisscore.model.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.AlbumDaoImpl;
import org.mdissjava.mdisscore.model.dao.impl.PhotoDaoImpl;
import org.mdissjava.mdisscore.model.pojo.Album;
import org.mdissjava.mdisscore.model.pojo.Photo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.Datastore;

public class PhotoDaoImplTest {

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	Datastore db;
	PhotoDao photodao;

	@Before
	public void init() {
		db = MorphiaDatastoreFactory.getDatastore("test");
		photodao = new PhotoDaoImpl(db);
	}

	@After
	public void destroy() {
		db = null;
		photodao = null;
	}

	@Test
	public void testInsertFind() {

		this.logger.info("[TEST] testInsertField");

		Photo photo = new Photo();
		photo.setPlus18(true);
		photo.setPublicPhoto(false);
		photo.setTitle("MiFoto");
		photo.setPhotoId("this is a mighty ID");
		photo.setDataId(photo.getPhotoId());
		photo.setPublicToken(UUID.randomUUID().toString());
		photo.setLicense("GPLv3");
		
		//insert album BEFORE adding to the photo
		Album a = new Album();
		a.setTitle("the mighty album");
		new AlbumDaoImpl(db).insertAlbum(a);
		
		photo.setAlbum(a);

		// Insertion in the Mongo db
		photodao.insertPhoto(photo);
		// The inserted photo is searched in the Mongo db
		Photo searchPhoto = new Photo();
		searchPhoto.setDataId("this is a mighty ID");
		
		Photo p = photodao.findPhoto(searchPhoto).get(0);
		// Check the photo title and the album title (check if the references are fine)
		assertEquals(p.getTitle(), "MiFoto");
		assertEquals(p.getAlbum().getTitle(), "the mighty album");
		
		this.photodao.deletePhoto(p);
	}

	@Test
	public void testDelete() {
		this.logger.info("[TEST] testDelete PhotoDaoImpl");
		try {
			Photo photo = new Photo();
			photo.setPlus18(true);
			photo.setPublicPhoto(false);
			photo.setTitle("MiFoto2");
			// Insertion of the photo in the Mongo db
			photodao.insertPhoto(photo);
			// Deletion of the same Photo in the Mongo db
			photodao.deletePhoto(photo);
			List<Photo> photoList = photodao.findPhoto(photo);
			// If finding the photo returns an empty list means that it didn't
			// find
			// the photo because it was deleted
			assertTrue(photoList.isEmpty());
			this.photodao.deletePhoto(photo);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			fail("Exception not expected");
		}

	}

	@Test
	public void testUpdate() {
		this.logger.info("[TEST] testUpdate PhotoDaoImpl");
		try {
			Photo photo = new Photo();
			photo.setPlus18(false);
			photo.setPublicPhoto(true);
			photo.setTitle("MiFoto3");
			// Insertion of the photo in the Mongo db
			photodao.insertPhoto(photo);
			// The camera's model is changed
			photo.setTitle("Me in the Beach");
			photo.setPlus18(true);
			// The photo is updated in the Mongo db
			photodao.updatePhoto(photo);
			List<Photo> photoList = photodao.findPhoto(photo);
			assertFalse(photoList.isEmpty());
			// If the returned photo have the second title it went correct the
			// update in the Mongo db
			assertEquals(photoList.get(0).getTitle(), "Me in the Beach");
			this.photodao.deletePhoto(photo);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			fail("Exception not expected");
		}
	}
	
	//Tested manually, not tested automatically with a random deduction algorithm
	@Test
	public void testRandom(){
		
		final String PREFIX = "photo_";
		final int MAX = 100;
		
		List<Photo> photos = new ArrayList<Photo>();
		
		for (int i=0; i < MAX; i++){
			Photo photo = new Photo();
			photo.setTitle(PREFIX+String.valueOf(i));
			photo.setRandom(Math.random());
			
			this.photodao.insertPhoto(photo);
			photos.add(photo);
			
		}
		for (Photo photo:this.photodao.getRandomPhotos(10)){
			System.out.println(photo.getTitle() + ": " + photo.getRandom());
		}
		
		for (Photo photo: photos)
			this.photodao.deletePhoto(photo);
		
	}

	@Test
	public void testgetPhotos(){
		String prefix = "photo_";
		this.logger.info("[TEST] testUpdate PhotoDaoImpl");
		
		//create the album
		Album album = new Album();
		album.setAlbumId(UUID.randomUUID().toString());
		AlbumDao albumDao = new AlbumDaoImpl(db);
		albumDao.insertAlbum(album);
		
		//create 10 photos
		List<Photo> photos = new ArrayList<Photo>();
		for (int i=0; i < 10; i++){
			Photo p = new Photo();
			p.setAlbum(album);
			p.setPhotoId(UUID.randomUUID().toString());
			p.setTitle(prefix+String.valueOf(i));
			this.photodao.insertPhoto(p);
			photos.add(p);
		}
		
		//check by getting 4 photos starting in the second photo (photo_2, photo_3, photo_4, photo_5)
		int start = 2;
		int quantity = 4;
		List<Photo> photosHelper = photodao.getPhotos(album, quantity, start);
		
		assertEquals(photosHelper.size(), quantity);
		int cont = start;
		for (Photo p: photosHelper){
			assertEquals(prefix+cont, p.getTitle());
			cont++;
		}
		
		//check getting without limit and skip
		photosHelper = photodao.getPhotos(album, 0, 0);
		assertEquals(photos.size(), photosHelper.size());
		
		//delete album
		albumDao.deleteAlbum(album);
		
		//delete photos
		for (Photo p: photos){
			photodao.deletePhoto(p);
		}
		
		
	}
	
}