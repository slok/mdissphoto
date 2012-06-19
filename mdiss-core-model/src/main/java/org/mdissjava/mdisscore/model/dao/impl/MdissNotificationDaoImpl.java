package org.mdissjava.mdisscore.model.dao.impl;

import java.util.List;

import org.bson.types.ObjectId;
import org.mdissjava.mdisscore.model.dao.MdissNotificationDao;
import org.mdissjava.mdisscore.model.pojo.Album;
import org.mdissjava.mdisscore.model.pojo.Photo;
import org.mdissjava.mdisscore.model.pojo.notifications.MdissNotification;
import org.mdissjava.mdisscore.model.pojo.notifications.PhotoUploadedNotification;
import org.mdissjava.mdisscore.model.pojo.notifications.ReportPhotoNotification;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.mapping.Mapper;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

public class MdissNotificationDaoImpl extends BasicDAO<MdissNotification, ObjectId> implements MdissNotificationDao {

	
	public MdissNotificationDaoImpl(Datastore ds) {
		super(ds);
	}

	private Query<MdissNotification> queryToFindMe(ObjectId id) {
		return ds.createQuery(MdissNotification.class).field(Mapper.ID_KEY).equal(id);
	}

	
	@Override
	public void insertMdissNotification(MdissNotification mdissNotification) {
		this.save(mdissNotification);

	}

	@Override
	public List<MdissNotification> findMdissNotification(
			MdissNotification mdissNotification) {
		
		Query<MdissNotification> query = ds.createQuery(MdissNotification.class);

		if (mdissNotification.getId() != null) {
			query.field("id").equal(mdissNotification.getId());
		}if (mdissNotification.getSelfUserName() != null) {
			query.field("selfUserName").equal(mdissNotification.getSelfUserName());
		}if (mdissNotification.getNotificationType() != null) {
			query.field("notificationType").equal(mdissNotification.getNotificationType());
		}if (mdissNotification.getDate() != null) {
			query.field("date").equal(mdissNotification.getDate());
		}if (mdissNotification.isRead() != null) {
			query.field("read").equal(mdissNotification.isRead());
		}
		
		List<MdissNotification> mdissNotifications = query.asList();

		return mdissNotifications;
		
	}

	@Override
	public void updateMdissNotification(MdissNotification mdissNotification) {

		UpdateOperations<MdissNotification> ops = ds.createUpdateOperations(MdissNotification.class);
		if (mdissNotification.getSelfUserName() != null) {
			ops.set("selfUserName", mdissNotification.getSelfUserName());
		}if (mdissNotification.getNotificationType() != null) {
			ops.set("notificationType", mdissNotification.getNotificationType());
		}if (mdissNotification.getDate() != null) {
			ops.set("date", mdissNotification.getDate());
		}if (mdissNotification.isRead() != null) {
			ops.set("read", mdissNotification.isRead());
		}
		
		ds.update(this.queryToFindMe(mdissNotification.getId()), ops);
	}

	
	public List<MdissNotification> findUsersMdissNotifications(String userName, int quantityNumberPhotos, int skipNumberPhotos) throws IllegalArgumentException{
		
		Query<MdissNotification> query = ds.createQuery(MdissNotification.class);
		
		if (userName != null) {
			query.field("selfUserName").equal(userName);
		}else
			throw new IllegalArgumentException("Need username to search users notifications");
		
		query.offset(skipNumberPhotos);
		//sort by date
		query.order("-date");
		if (quantityNumberPhotos > 0) 
			query.limit(quantityNumberPhotos); 
		
		List<MdissNotification> mdissNotifications = query.asList();
	
		return mdissNotifications;				
	}
	
	@Override
	public int findTotalNotificationsUser(String userName) {		
		return ((Long)ds.createQuery(MdissNotification.class).filter("selfUserName", userName).countAll()).intValue();
	}
	
	
	public PhotoUploadedNotification findPhotoUploadedNotifications(String userName, String photoId) throws IllegalStateException, IllegalArgumentException{
		
		Query<PhotoUploadedNotification> query = ds.createQuery(PhotoUploadedNotification.class);
		
		if (userName != null && photoId != null) {
			query.field("selfUserName").equal(userName);
			query.field("photoId").equal(photoId);
		}else
			throw new IllegalArgumentException("Need username to search users notifications");
		
		List<PhotoUploadedNotification> mdissNotifications = query.asList();
		
		if (mdissNotifications.size() > 1)
			throw new IllegalStateException("Too many results");
		else if (mdissNotifications.size() == 0 )
			throw new IllegalStateException("No notification match");

		return (PhotoUploadedNotification)mdissNotifications.get(0);
	}
	
	
	@Override
	public void deleteMdissNotification(MdissNotification mdissNotification) {		
		ds.delete(mdissNotification);
	}

	@Override
	public List<MdissNotification> findAllNotifications(int limit) {
		Query<MdissNotification> query = ds.createQuery(MdissNotification.class).limit(limit).order("-date");				
		List<MdissNotification> mdissNotifications = query.asList();
		return mdissNotifications;
	}
	
	@Override
	public void deleteSameMdissReportNotifications(MdissNotification mdissNotification){
		ReportPhotoNotification n = (ReportPhotoNotification) mdissNotification;
		Query<MdissNotification> query = ds.createQuery(MdissNotification.class).disableValidation().filter("className","org.mdissjava.mdisscore.model.pojo.notifications.ReportPhotoNotification").filter("photoId", n.getPhotoId());				
		ds.delete(query);
	}
	

}
