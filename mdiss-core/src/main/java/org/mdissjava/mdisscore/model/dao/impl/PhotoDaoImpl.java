package org.mdissjava.mdisscore.model.dao.impl;

import java.util.List;

import org.bson.types.ObjectId;
import org.mdissjava.mdisscore.model.dao.PhotoDao;
import org.mdissjava.mdisscore.model.pojo.Photo;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.mapping.Mapper;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

public class PhotoDaoImpl extends BasicDAO<Photo, ObjectId> implements PhotoDao {
	
	public PhotoDaoImpl(Datastore ds) {
		super(ds);
	}

	private Query<Photo> queryToFindMe(ObjectId id) {
		return ds.createQuery(Photo.class).field(Mapper.ID_KEY).equal(id);
	}

	@Override
	public void insertPhoto(Photo photo) {
		this.save(photo);
	}

	@Override
	public List<Photo> findPhoto(Photo photo) {
		Query<Photo> query = ds.createQuery(Photo.class);

		if (photo.getId() != null) {
			query.field("id").equal(photo.getId());
		}
		if (photo.getPhotoId() != null) {
			query.field("photoId").equal(photo.getPhotoId());
		}
		if (photo.getTitle() != null) {
			query.field("title").equal(photo.getTitle());
		}
		if (photo.getPublicPhoto() != null) {
			query.field("publicPhoto").equal(photo.getPublicPhoto());
		}
		if (!photo.getVotes().isEmpty()) {
			query.field("votes").equal(photo.getVotes());
		}
		if (photo.getUploadDate() != null) {
			query.field("uploadDate").equal(photo.getUploadDate());
		}
		if (photo.getNextPhoto() != null) {
			query.field("nextPhoto").equal(photo.getNextPhoto());
		}
		if (photo.getBackwardPhoto() != null) {
			query.field("backwardPhoto").equal(photo.getBackwardPhoto());
		}
		if (photo.getMetadata() != null) {
			query.field("metadata").equal(photo.getMetadata());
		}
		if (!photo.getTags().isEmpty()) {
			query.field("tags").equal(photo.getTags());
		}
		if (photo.getDataId() != null) {
			query.field("dataId").equal(photo.getDataId());
		}
		if (photo.getPlus18() != null) {
			query.field("plus18").equal(photo.getPlus18());
		}
		List<Photo> photos = query.asList();

		return photos;
	}

	@Override
	public void updatePhoto(Photo photo) {
		UpdateOperations<Photo> ops = ds.createUpdateOperations(Photo.class);
		if (photo.getTitle() != null) {
			ops.set("title", photo.getTitle());
		}
		if (photo.getPhotoId() != null) {
			ops.set("photoId", photo.getPhotoId());
		}
		if (photo.getPublicPhoto() != null) {
			ops.set("publicPhoto", photo.getPublicPhoto());
		}
		if (photo.getVotes() != null) {
			ops.set("votes", photo.getVotes());
		}
		if (photo.getUploadDate() != null) {
			ops.set("uploadDate", photo.getUploadDate());
		}
		if (photo.getNextPhoto() != null) {
			ops.set("nextPhoto", photo.getNextPhoto());
		}
		if (photo.getBackwardPhoto() != null) {
			ops.set("backwardPhoto", photo.getBackwardPhoto());
		}
		if (photo.getMetadata() != null) {
			ops.set("metadata", photo.getMetadata());
		}
		if (photo.getTags() != null) {
			ops.set("tags", photo.getTags());
		}
		if (photo.getDataId() != null) {
			ops.set("dataId", photo.getDataId());
		}
		if (photo.getPlus18() != null) {
			ops.set("plus18", photo.getPlus18());
		}

		ds.update(this.queryToFindMe(photo.getId()), ops);

	}

	@Override
	public void deletePhoto(Photo photo) {

		ds.delete(photo);

	}
}