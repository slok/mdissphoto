package org.mdissjava.mdisscore.model.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.PhotoDaoImpl;
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
		try {

			this.logger.info("[TEST] testInsertField");

			Photo photo = new Photo();
			photo.setPlus18(true);
			photo.setPublicPhoto(false);
			photo.setTitle("MiFoto");

			// Insertion in the Mongo db
			photodao.insertPhoto(photo);
			// The inserted photo is find in the Mongo db
			List<Photo> photoList = photodao.findPhoto(photo);
			// If the returned photo's title is "MiFoto" the insert went good
			assertEquals(photoList.get(0).getTitle(), "MiFoto");
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			fail("Exception not expected");
		}
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
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			fail("Exception not expected");
		}
	}

}