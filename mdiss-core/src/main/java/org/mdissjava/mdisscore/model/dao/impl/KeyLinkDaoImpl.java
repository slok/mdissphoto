package org.mdissjava.mdisscore.model.dao.impl;

import java.util.List;

import org.bson.types.ObjectId;
import org.mdissjava.mdisscore.model.dao.KeyLinkDao;
import org.mdissjava.mdisscore.model.pojo.KeyLink;
import org.mdissjava.mdisscore.model.pojo.Photo;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.mapping.Mapper;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

public class KeyLinkDaoImpl extends BasicDAO<Photo, ObjectId> implements
		KeyLinkDao {

	public KeyLinkDaoImpl(Datastore ds) {
		super(ds);
	}

	private Query<KeyLink> queryToFindMe(String id) {
		return ds.createQuery(KeyLink.class).field(Mapper.ID_KEY).equal(id);
	}

	@Override
	public void insertKeyLink(KeyLink keyLink) {
		ds.save(keyLink);
	}

	@Override
	public KeyLink findKeyLink(String key) {
		Query<KeyLink> query = ds.createQuery(KeyLink.class);
		query.field("id").equal(key);
		List<KeyLink> keylink = query.asList();
		KeyLink kl = null;
		if (!keylink.isEmpty()) {
			kl = keylink.get(0);
		}
		return kl;
	}

	@Override
	public void updateKeyLink(KeyLink keyLink) {
		UpdateOperations<KeyLink> ops = ds
				.createUpdateOperations(KeyLink.class);
		if (keyLink.getUserId() != 0) {
			ops.set("userId", keyLink.getUserId());
		}
		if (keyLink.getExpireDate() != null) {
			ops.set("expireDate", keyLink.getExpireDate());
		}
		if (keyLink.getUniqueType() != 0) {
			ops.set("uniqueType", keyLink.getUniqueType());
		}
		ds.update(this.queryToFindMe(keyLink.getId()), ops);

	}

	@Override
	public void deleteKeyLink(KeyLink keyLink) {
		ds.delete(keyLink);

	}

}
