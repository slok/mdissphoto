package org.mdissjava.notifier.notifications.dao.impl;

import java.util.List;

import org.bson.types.ObjectId;
import org.mdissjava.notifier.notifications.dao.MdissNotificationDao;
import org.mdissjava.notifier.notifications.pojo.MdissNotification;

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

	
	public List<MdissNotification> findUsersMdissNotifications(String userName, int limit) throws IllegalStateException{
		
		Query<MdissNotification> query = ds.createQuery(MdissNotification.class).limit(limit);
		
		if (userName != null) {
			query.field("selfUserName").equal(userName);
		}else
			throw new IllegalStateException("Need username to search users notifications");
		
		List<MdissNotification> mdissNotifications = query.asList();

		return mdissNotifications;
	}
	
	@Override
	public void deleteMdissNotification(MdissNotification mdissNotification) {
		
		ds.delete(mdissNotification);

	}

}
