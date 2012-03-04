package org.mdissjava.mdisscore.model.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.mdissjava.commonutils.mongo.db.MongoDBConnection;
import org.mdissjava.mdisscore.model.dao.PhotoDao;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.PhotoDaoImpl;
import org.mdissjava.mdisscore.model.pojo.Photo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;

public class PhotoDaoImplTest {

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Test
	public void testInsertFind() {		
		Datastore db = MorphiaDatastoreFactory.getDatastore("test");
		this.logger.info("[TEST] testInsertField");

		PhotoDao photodao = new PhotoDaoImpl(db);
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
	}

	@Test
	public void testDelete() {
		this.logger.info("[TEST] testDelete PhotoDaoImpl");
		Datastore db = MorphiaDatastoreFactory.getDatastore("test");

		PhotoDao photodao = new PhotoDaoImpl(db);
		Photo photo = new Photo();
		photo.setPlus18(true);
		photo.setPublicPhoto(false);
		photo.setTitle("MiFoto2");
		// Insertion of the photo in the Mongo db
		photodao.insertPhoto(photo);
		// Deletion of the same Photo in the Mongo db
		photodao.deletePhoto(photo);
		List<Photo> photoList = photodao.findPhoto(photo);
		// If finding the photo returns an empty list means that it didn't find
		// the photo because it was deleted
		assertTrue(photoList.isEmpty());

	}

	@Test
	public void testUpdate() {
		this.logger.info("[TEST] testUpdate PhotoDaoImpl");
		Datastore db = MorphiaDatastoreFactory.getDatastore("test");

		PhotoDao photodao = new PhotoDaoImpl(db);
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
	}

}