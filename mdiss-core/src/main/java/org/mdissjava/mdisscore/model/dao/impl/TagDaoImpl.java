package org.mdissjava.mdisscore.model.dao.impl;

import java.util.List;

import org.bson.types.ObjectId;
import org.mdissjava.mdisscore.model.dao.TagDao;
import org.mdissjava.mdisscore.model.pojo.Tag;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.mapping.Mapper;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

public class TagDaoImpl  extends BasicDAO<Tag, ObjectId> implements TagDao {

	public TagDaoImpl(Datastore ds) {
		super(ds);
	}
	
	private Query<Tag> queryToFindMe(ObjectId id) {
		return ds.createQuery(Tag.class).field(Mapper.ID_KEY).equal(id);
	}
	
	@Override
	public void insertTag(Tag tag) {
		this.save(tag);
	}

	@Override
	public List<Tag> findTag(Tag tag) {

		Query<Tag> query = ds.createQuery(Tag.class);
		if (tag.getId() != null) {
			query.field("id").equal(tag.getId());
		}
		if (tag.getDescription() != null) {
			query.field("description").equal(tag.getDescription());
		}
		if (tag.getPhotos() != null) {
			query.field("photos").equal(tag.getPhotos());
		}
		
		List<Tag> tags = query.asList();

		
		return tags;
	}

	@Override
	public void updateTag(Tag tag) {
		
		UpdateOperations<Tag> ops = ds.createUpdateOperations(Tag.class);
		
		if (tag.getDescription() != null) {
			ops.set("description", tag.getDescription());
		}
		if (tag.getPhotos() !=null){
			ops.set("photos", tag.getPhotos());
		}
		Query<Tag> query = this.queryToFindMe(tag.getId());
		ds.update(query, ops);
		
	}

	@Override
	public void deleteTag(Tag tag) {

		ds.delete(tag);
	}

}
