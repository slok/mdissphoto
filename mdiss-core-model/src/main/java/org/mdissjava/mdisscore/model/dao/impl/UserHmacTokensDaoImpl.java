package org.mdissjava.mdisscore.model.dao.impl;

import java.util.List;

import org.bson.types.ObjectId;
import org.mdissjava.mdisscore.model.dao.UserHmacTokensDao;
import org.mdissjava.mdisscore.model.pojo.UserHmacTokens;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.mapping.Mapper;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

public class UserHmacTokensDaoImpl extends BasicDAO<UserHmacTokens, ObjectId> implements UserHmacTokensDao {

	public UserHmacTokensDaoImpl(Datastore ds) {
		super(ds);
	}
	
	private Query<UserHmacTokens> queryToFindMe(ObjectId id) {
		return ds.createQuery(UserHmacTokens.class).field(Mapper.ID_KEY).equal(id);
	}
	
	@Override
	public void insertUserHmacTokens(UserHmacTokens userHmacTokens) {
		this.save(userHmacTokens);
	}

	@Override
	public List<UserHmacTokens> findUserHmacTokens(UserHmacTokens userHmacTokens) {
		Query<UserHmacTokens> query = ds.createQuery(UserHmacTokens.class);

		if (userHmacTokens.getId() != null) {
			query.field("id").equal(userHmacTokens.getId());
		}
		if (userHmacTokens.getUsername() != null) {
			query.field("username").equal(userHmacTokens.getUsername());
		}
		
		List<UserHmacTokens> userHmacTokensList = query.asList();

		return userHmacTokensList;
	}

	@Override
	public void updateUserHmacTokens(UserHmacTokens userHmacTokens) {
		UpdateOperations<UserHmacTokens> ops = ds.createUpdateOperations(UserHmacTokens.class);
		
		if (userHmacTokens.getUsername() != null) {
			ops.set("username", userHmacTokens.getUsername());
		}
		if (userHmacTokens.getTokens() != null) {
			ops.set("tokens", userHmacTokens.getTokens());
		}
		
		ds.update(this.queryToFindMe(userHmacTokens.getId()), ops);

	}

	@Override
	public void deleteUserHmacTokens(UserHmacTokens userHmacTokens) {
		ds.delete(userHmacTokens);
	}

}
