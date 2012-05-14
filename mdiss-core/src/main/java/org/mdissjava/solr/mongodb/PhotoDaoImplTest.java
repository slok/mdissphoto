package org.mdissjava.solr.mongodb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mdissjava.mdisscore.model.dao.AlbumDao;
import org.mdissjava.mdisscore.model.dao.PhotoDao;
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
	AlbumDao albumdao;

	@Before
	public void init() {
		db = MorphiaDatastoreFactory.getDatastore("mongoDBTest");
		photodao = new PhotoDaoImpl(db);
		albumdao = new AlbumDaoImpl(db);
	}

	@After
	public void destroy() {
		db = null;
		photodao = null;
	}

	@Test
	public void testInsertFind() {

		this.logger.info("[TEST] testInsertField");

		//insert album BEFORE adding to the photo
		Album a = new Album();
		a.setTitle("Fiestas de Bilbo 2011");
		a.setCreationDate(new Date());
		new AlbumDaoImpl(db).insertAlbum(a);
		
		Photo photo = new Photo();
		photo.setPlus18(true);
		photo.setPublicPhoto(false);
		photo.setTitle("Los colegas del salamanca y yo en el arenal");
		photo.setPhotoId("Este es un ID inventado");
		photo.setDataId(photo.getPhotoId());
		List<String> tags = new ArrayList<String>();
		tags.add("colegas");
		tags.add("salamanca");
		tags.add("arenal");
		photo.setTags(tags);
		photo.setUploadDate(new Date());
		new PhotoDaoImpl(db).insertPhoto(photo);
		photo.setAlbum(a);
		//añadir a la lista de fotos
		a.addPhotoToAlbum(photo);
		// Insertion in the Mongo db
		photodao.insertPhoto(photo);
		
		photo = new Photo();
		photo.setPlus18(true);
		photo.setPublicPhoto(false);
		photo.setTitle("Maria y yo en la txosna de Gogorregi");
		photo.setPhotoId("Este es un ID inventado");
		photo.setDataId(photo.getPhotoId());
		tags = new ArrayList<String>();
		tags.add("maria");
		tags.add("txosna");
		tags.add("gogorregi");
		photo.setTags(tags);
		photo.setUploadDate(new Date());
		new PhotoDaoImpl(db).insertPhoto(photo);
		photo.setAlbum(a);
		//añadir a la lista de fotos
		a.addPhotoToAlbum(photo);
		// Insertion in the Mongo db
		photodao.insertPhoto(photo);
		
		photo = new Photo();
		photo.setPlus18(true);
		photo.setPublicPhoto(false);
		photo.setTitle("En el concierto de heroes del silencio en zorrozaurre");
		photo.setPhotoId("Este es un ID inventado");
		photo.setDataId(photo.getPhotoId());
		tags = new ArrayList<String>();
		tags.add("concierto");
		tags.add("heroes");
		tags.add("zorrozaurre");
		photo.setTags(tags);
		photo.setUploadDate(new Date());
		new PhotoDaoImpl(db).insertPhoto(photo);
		photo.setAlbum(a);
		//añadir a la lista de fotos
		a.addPhotoToAlbum(photo);
		// Insertion in the Mongo db
		photodao.insertPhoto(photo);
		
		photo = new Photo();
		photo.setPlus18(true);
		photo.setPublicPhoto(false);
		photo.setTitle("En el teatro arriaga durante el pregon de fiestas");
		photo.setPhotoId("Este es un ID inventado");
		photo.setDataId(photo.getPhotoId());
		tags = new ArrayList<String>();
		tags.add("teatro");
		tags.add("arriaga");
		tags.add("pregon");
		tags.add("fiestas");
		photo.setTags(tags);
		photo.setUploadDate(new Date());
		new PhotoDaoImpl(db).insertPhoto(photo);
		photo.setAlbum(a);
		//añadir a la lista de fotos
		a.addPhotoToAlbum(photo);
		// Insertion in the Mongo db
		photodao.insertPhoto(photo);
		
		photo = new Photo();
		photo.setPlus18(true);
		photo.setPublicPhoto(false);
		photo.setTitle("Los fuegos artificiales desde el zubi zuri");
		photo.setPhotoId("Este es un ID inventado");
		photo.setDataId(photo.getPhotoId());
		tags = new ArrayList<String>();
		tags.add("fuegos");
		tags.add("zubi");
		tags.add("zuri");
		photo.setTags(tags);
		photo.setUploadDate(new Date());
		new PhotoDaoImpl(db).insertPhoto(photo);
		photo.setAlbum(a);
		//añadir a la lista de fotos
		a.addPhotoToAlbum(photo);
		// Insertion in the Mongo db
		photodao.insertPhoto(photo);
		
		// The inserted photo is searched in the Mongo db
		Photo searchPhoto = new Photo();
		searchPhoto.setDataId(photo.getPhotoId());
		
		Photo p = photodao.findPhoto(searchPhoto).get(0);
		// Check the photo title and the album title (check if the references are fine)
		assertEquals(p.getTitle(), "Los colegas del salamanca y yo en el arenal");
		assertEquals(p.getAlbum().getTitle(), "Fiestas de Bilbo 2011");
	}

//	@Test
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

//	@Test
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