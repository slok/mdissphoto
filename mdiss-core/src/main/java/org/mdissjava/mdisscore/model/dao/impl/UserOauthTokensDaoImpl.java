package org.mdissjava.mdisscore.model.dao.impl;

import java.util.List;

import org.bson.types.ObjectId;
import org.mdissjava.mdisscore.model.dao.UserOauthTokensDao;
import org.mdissjava.mdisscore.model.pojo.UserOauthTokens;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.mapping.Mapper;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

public class UserOauthTokensDaoImpl extends BasicDAO<UserOauthTokens, ObjectId> implements UserOauthTokensDao{

	public UserOauthTokensDaoImpl(Datastore ds) {
		super(ds);
	}

	private Query<UserOauthTokens> queryToFindMe(ObjectId id) {
		return ds.createQuery(UserOauthTokens.class).field(Mapper.ID_KEY).equal(id);
	}
	
	@Override
	public void insertUserOauthTokens(UserOauthTokens userOauthTokens) {
		this.save(userOauthTokens);
	}

	@Override
	public List<UserOauthTokens> findUserOauthTokens(
			UserOauthTokens userOauthTokens) {
		
		Query<UserOauthTokens> query = ds.createQuery(UserOauthTokens.class);

		if (userOauthTokens.getId() != null) {
			query.field("id").equal(userOauthTokens.getId());
		}
		if (userOauthTokens.getUsername() != null) {
			query.field("username").equal(userOauthTokens.getUsername());
		}
		
		List<UserOauthTokens> userOauthTokensList = query.asList();

		return userOauthTokensList;
	}

	@Override
	public void updateUserOauthTokens(UserOauthTokens userOauthTokens) {
		UpdateOperations<UserOauthTokens> ops = ds.createUpdateOperations(UserOauthTokens.class);
		
		if (userOauthTokens.getUsername() != null) {
			ops.set("username", userOauthTokens.getUsername());
		}
		if (userOauthTokens.getTokens() != null) {
			ops.set("tokens", userOauthTokens.getTokens());
		}
		
		ds.update(this.queryToFindMe(userOauthTokens.getId()), ops);
		
	}

	@Override
	public void deleteUserOauthTokens(UserOauthTokens userOauthTokens) {
		ds.delete(userOauthTokens);
	}

}
