package org.mdissjava.commonutils.photo.status;

import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.mapping.Mapper;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

public class PhotoStatusDaoImpl extends BasicDAO<PhotoStatus, ObjectId> implements PhotoStatusDao {

	protected PhotoStatusDaoImpl(Datastore ds) {
		super(ds);
	}

	
	private Query<PhotoStatus> queryToFindMe(ObjectId id) {
		return ds.createQuery(PhotoStatus.class).field(Mapper.ID_KEY).equal(id);
	}
	
	@Override
	public void insertPhotoStatus(PhotoStatus photoStatus) {
		this.save(photoStatus);
	}

	@Override
	public List<PhotoStatus> findPhotoStatus(PhotoStatus photoStatus) {
		Query<PhotoStatus> query = ds.createQuery(PhotoStatus.class);

		if (photoStatus.getId() != null) {
			query.field("id").equal(photoStatus.getId());
		}
		if (photoStatus.getName() != null) {
			query.field("name").equal(photoStatus.getName());
		}if (photoStatus.isProcessed() != null) {
			query.field("processed").equal(photoStatus.isProcessed());
		}if (photoStatus.isDetailed() != null) {
			query.field("detailed").equal(photoStatus.isDetailed());
		}if (photoStatus.getUpdateDate() != null) {
			query.field("updateDate").equal(photoStatus.getUpdateDate());
		}
		
		List<PhotoStatus> photoStatuses = query.asList();

		return photoStatuses;
	}

	@Override
	public void updatePhotoStatus(PhotoStatus photoStatus) {
		UpdateOperations<PhotoStatus> ops = ds.createUpdateOperations(PhotoStatus.class);
		if (photoStatus.getName() != null) {
			ops.set("name", photoStatus.getName());
		}if (photoStatus.isProcessed() != null) {
			ops.set("processed", photoStatus.isProcessed());
		}if (photoStatus.isDetailed() != null) {
			ops.set("detailed", photoStatus.isDetailed());
		}if (photoStatus.getUpdateDate() != null) {
			ops.set("updateDate", photoStatus.getUpdateDate());
		}
		
		ds.update(this.queryToFindMe(photoStatus.getId()), ops);

	}

	@Override
	public void deletePhotoStatus(PhotoStatus photoStatus) {
		ds.delete(photoStatus);

	}

}
