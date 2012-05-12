package org.mdissjava.mdisscore.model.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.PhotoDaoImpl;
import org.mdissjava.mdisscore.model.dao.impl.TagDaoImpl;
import org.mdissjava.mdisscore.model.pojo.Photo;
import org.mdissjava.mdisscore.model.pojo.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.Datastore;

public class TagDaoImplTest {
	
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Test
	public void testInsertFind() {
		
		Datastore db = MorphiaDatastoreFactory.getDatastore("test");
		this.logger.info("[TEST] testInsertField");
		
		TagDao tagDaoImpl = new TagDaoImpl(db);
		Tag tag = new Tag();
		tag.setDescription("Dogs");
		
		//tag some photos
		Photo p1 = new Photo();
		p1.setTitle("Me and my dog in the beach");
		p1.setPlus18(false);
		p1.setDataId("23456784567");
		
		Photo p2 = new Photo();
		p2.setTitle("Me and my dog at home");
		p2.setPlus18(false);
		p2.setDataId("43265123124");
		
		PhotoDao pDao = new PhotoDaoImpl(db);
		pDao.insertPhoto(p1);
		pDao.insertPhoto(p2);
		
		ArrayList<Photo> photos = new ArrayList<Photo>();
		photos.add(p1);
		photos.add(p2);
		
		//Set photos to the tag
		tag.setPhotos(photos);
		
		tagDaoImpl.insertTag(tag);
		
		List<Tag> tagList = tagDaoImpl.findTag(tag);
		List<String> tagStringList = new ArrayList<String>();
		//Set tag to the photos
		Iterator<Tag> iterator = tagList.listIterator();
		while(iterator.hasNext())
		{
			tagStringList.add(iterator.next().getDescription());
		}
		
		p1.setTags(tagStringList);
		p2.setTags(tagStringList);
		
		pDao.updatePhoto(p2);
		pDao.updatePhoto(p1);
		
		assertEquals(tagList.get(0).getDescription(), "Dogs");
		assertEquals(p2.getTags().get(0), "Dogs");
		assertEquals(p1.getTags().get(0), "Dogs");

	}
	
	@Test
	public void testDelete() { 
		
		this.logger.info("[TEST] testDelete TagDaoImpl");
		Datastore db = MorphiaDatastoreFactory.getDatastore("test");
		
		TagDao tagDaoImpl = new TagDaoImpl(db);
		Tag tag = new Tag();
		tag.setDescription("Dogs");
		
		// Insertion of the tag in the Mongo db
		tagDaoImpl.insertTag(tag);
		
		Photo p1 = new Photo();
		p1.setTitle("Me and my dog at home");
		p1.setPlus18(false);
		p1.setDataId("43265123124");
		
		List<Tag> tagList = tagDaoImpl.findTag(tag);
		List<String> tagStringList = new ArrayList<String>();
		//Set tag to the photos
		Iterator<Tag> iterator = tagList.listIterator();
		while(iterator.hasNext())
		{
			tagStringList.add(iterator.next().getDescription());
		}
		p1.setTags(tagStringList);
		
		PhotoDao pDao = new PhotoDaoImpl(db);
		pDao.insertPhoto(p1);
		
		// Deletion of the same Tag in the Mongo db
		tagDaoImpl.deleteTag(tag);
				
		tagList = tagDaoImpl.findTag(tag);
		
		tagStringList = p1.getTags();
		
		tagStringList.remove(tag.getDescription());
		
		p1.setTags(tagStringList);
		pDao.updatePhoto(p1);
		// If finding the tag returns an empty list means that it didn't find
		// the tag because it was deleted
		assertTrue(tagList.isEmpty());
		assertTrue(p1.getTags().isEmpty());
	}
	
	@Test
	public void testUpdate() { 
		
		this.logger.info("[TEST] testUpdate TagDaoImpl");
		Datastore db = MorphiaDatastoreFactory.getDatastore("test");
		
		TagDao tagDaoImpl = new TagDaoImpl(db);
		Tag tag = new Tag();
		tag.setDescription("Dogs");
		
		// Insertion of the tag in the Mongo db
		tagDaoImpl.insertTag(tag);
		
		Photo p1 = new Photo();
		p1.setTitle("Me and my dog at home");
		p1.setPlus18(false);
		p1.setDataId("43265123124");
		
		List<Tag> tagList = tagDaoImpl.findTag(tag);
		List<String> tagStringList = new ArrayList<String>();
		//Set tag to the photos
		Iterator<Tag> iterator = tagList.listIterator();
		while(iterator.hasNext())
		{
			tagStringList.add(iterator.next().getDescription());
		}
		p1.setTags(tagStringList);
		
		PhotoDao pDao = new PhotoDaoImpl(db);
		pDao.insertPhoto(p1);
	
		assertEquals(p1.getTags().get(0), "Dogs");
		
		// Change tag description
		tag.setDescription("English Cocker Spaniel");
		
		// The tag is updated in the Mongo db
		tagDaoImpl.updateTag(tag);
		tagList = tagDaoImpl.findTag(tag);
		//Set tag to the photos
		iterator = tagList.listIterator();
		tagStringList.clear();
		while(iterator.hasNext())
		{
			tagStringList.add(iterator.next().getDescription());
		}
		p1.setTags(tagStringList);
		pDao.updatePhoto(p1);
		
		assertFalse(tagList.isEmpty());
		assertFalse(p1.getTags().isEmpty());
		

		// If the returned photo have the second title it went correct the
		// update in the Mongo db
		assertEquals(tagList.get(0).getDescription(), "English Cocker Spaniel");
		assertEquals(p1.getTags().get(0), "English Cocker Spaniel");

	}
	
}
