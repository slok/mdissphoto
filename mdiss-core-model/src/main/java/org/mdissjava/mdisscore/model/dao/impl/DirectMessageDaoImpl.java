package org.mdissjava.mdisscore.model.dao.impl;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.mdissjava.mdisscore.model.dao.DirectMessageDao;
import org.mdissjava.mdisscore.model.pojo.DirectMessage;
import org.mdissjava.mdisscore.model.pojo.Photo;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.mapping.Mapper;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

public class DirectMessageDaoImpl  extends BasicDAO<Photo, ObjectId> implements DirectMessageDao{

	public DirectMessageDaoImpl(Datastore ds) {
		super(ds);
	} 
	
	private Query<DirectMessage> queryToFindMe(String id) {
		return ds.createQuery(DirectMessage.class).field(Mapper.ID_KEY).equal(id);
	}
	
	@Override
	public void insertDirectMessage(DirectMessage directMessage) {
		ds.save(directMessage);
	}

	@Override
	public void markAsRead(String messageId) {
		UpdateOperations<DirectMessage> ops = ds
				.createUpdateOperations(DirectMessage.class);
		ops.set("read", true);
		ds.update(this.queryToFindMe(messageId), ops);
		
	}

	@Override
	public void deleteDirectMessage(DirectMessage directMessage) {
		ds.delete(directMessage);
		
	}

	@Override
	public List<DirectMessage> findDirectMessage(DirectMessage directMessage, boolean readAndNotRead) {
		Query<DirectMessage> query = ds.createQuery(DirectMessage.class);
		if(directMessage.getFromUserId() != 0) {
			query.field("fromUserId").equal(directMessage.getFromUserId());
		}
		if(directMessage.getToUserId() != 0) {
			query.field("toUserId").equal(directMessage.getToUserId());
		}
		if(!readAndNotRead) {
			query.field("read").equal(directMessage.isRead());
		}
		if(directMessage.getId() != null) {
			query.field("id").equal(directMessage.getId());
		}
		query.order("-sentDate");
		List<DirectMessage> directMessageList = query.asList();
		return directMessageList;
	}

	@Override
	public List<DirectMessage> findDirectMessageBetween(Date from, Date to) {
		if(from == null && to == null) {
			throw new NullPointerException("The two dates can not be null");
		}
		if(from.after(to)) {
			throw new IllegalArgumentException("from Date must be before to Date");
		}
		Query<DirectMessage> query = ds.createQuery(DirectMessage.class);
		query.field("sentDate").greaterThan(from);
		query.field("sentDate").lessThan(to);
		
		
		return null;
	}

}
