package org.mdissjava.mdisscore.model.dao.impl;

import java.util.List;

import org.bson.types.ObjectId;
import org.mdissjava.mdisscore.model.dao.CameraDao;
import org.mdissjava.mdisscore.model.pojo.Camera;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.mapping.Mapper;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

public class CameraDaoImpl extends BasicDAO<Camera, ObjectId> implements
		CameraDao {

	public CameraDaoImpl(Datastore ds) {
		super(ds);
		// TODO Auto-generated constructor stub
	}

	private Query<Camera> queryToFindMe(ObjectId id) {
		return ds.createQuery(Camera.class).field(Mapper.ID_KEY).equal(id);
	}

	@Override
	public void insertCamera(Camera camera) {

		this.save(camera);
	}

	@Override
	public List<Camera> findCamera(Camera camera) {

		Query<Camera> query = ds.createQuery(Camera.class);
		if (camera.getBrand() != null) {
			query.field("brand").equal(camera.getBrand());
		}
		if (camera.getId() != null) {
			query.field("id").equal(camera.getId());
		}
		if (camera.getModel() != null) {
			query.field("model").equal(camera.getModel());
		}
		if (!camera.getVotes().isEmpty()) {
			query.field("votes").equal(camera.getVotes());
		}
		List<Camera> cameras = query.asList();

		return cameras;

	}

	@Override
	public void updateCamera(Camera camera) {
		UpdateOperations<Camera> ops = ds.createUpdateOperations(Camera.class)
				.set("brand", camera.getBrand())
				.set("model", camera.getModel())
				.set("votes", camera.getVotes());
		ds.update(this.queryToFindMe(camera.getId()), ops);
	}

	@Override
	public void deleteCamera(Camera camera) {

		ds.delete(camera);

	}
}
