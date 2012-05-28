package org.mdissjava.mdisscore.model.dao.impl;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.mdissjava.mdisscore.model.dao.KeyLinkDao;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
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
	public KeyLink findKeyByUserId(int id) {
		Query<KeyLink> query = ds.createQuery(KeyLink.class);
		query.field("userId").equal(id);
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

	/**
	 * Return the userId of the link if the link doesn't expired yet. if the
	 * link expired or the link doesn't exist will return -1.
	 * 
	 * @param link
	 *            the link
	 * @return the users id
	 */
	@Override
	public int retrieveUserFromValidationLink(String link) {
		// TODO: KeyLinkDaoImpl how to create it?
		Datastore db = MorphiaDatastoreFactory.getDatastore("test");
		KeyLinkDao keyLinkDao = new KeyLinkDaoImpl(db);
		KeyLink keyLink = keyLinkDao.findKeyLink(link);

		if (keyLink == null) {
			throw new IllegalArgumentException();
		} else {
			Date now = new Date();
			if (now.before(keyLink.getExpireDate())) {
				return keyLink.getUserId();
			} else {
				return -1;
			}
		}
	}
}
