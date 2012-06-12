package org.mdissjava.mdisscore.controller.bll.impl;

import java.io.IOException;
import java.util.List;

import org.mdissjava.mdisscore.controller.bll.TagManager;
import org.mdissjava.mdisscore.model.dao.PhotoDao;
import org.mdissjava.mdisscore.model.dao.TagDao;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.PhotoDaoImpl;
import org.mdissjava.mdisscore.model.dao.impl.TagDaoImpl;
import org.mdissjava.mdisscore.model.pojo.Photo;
import org.mdissjava.mdisscore.model.pojo.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.Datastore;

/**
 * Manager for tag
 * 
 * 
 * @author david
 *
 */
public class TagManagerImpl implements TagManager {

	//TODO: Load from properties
	private final String DATABASE = "mdissphoto";
	private TagDao tagDao;
	private PhotoDao photoDao;
	private Datastore datastore;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	public TagManagerImpl(Datastore datastore) {
		this.datastore = datastore;
		this.tagDao = new TagDaoImpl(this.datastore);
		this.photoDao = new PhotoDaoImpl(this.datastore);
	}

	public TagManagerImpl() {
		this.datastore = MorphiaDatastoreFactory.getDatastore(DATABASE);
		this.tagDao = new TagDaoImpl(this.datastore);
		this.photoDao = new PhotoDaoImpl(this.datastore);
	}
	
	/**
	 * get all the photos from the description of the tag
	 * @param tagDescription
	 * @return List<Photo>
	 */
	@Override
	public List<Photo> getPhotosFromTag(String tagDescription)
			throws IllegalArgumentException, IOException {
		List<Photo> photos = null;
		if(!tagDescription.equals("")) {
			Tag tag = new Tag();
			tag.setDescription(tagDescription);
			List<Tag> tags = this.tagDao.findTag(tag);
			if(tags.isEmpty()){
				this.logger.error("There are not any tags called such as "+ tagDescription +" stored in database");
				throw new IOException("There are not any tags called such as "+ tagDescription +" stored in database");
			}
			photos = this.photoDao.getPhotosFromTag(tags.get(0).getDescription());
			if(photos.isEmpty()){
				this.logger.error("There are not any photos from tag "+ tagDescription +" stored in database");
				throw new IOException("There are not any photos from tag "+ tagDescription +" stored in database");
			}
			
		}	
		return photos;
		
	}

	
}
