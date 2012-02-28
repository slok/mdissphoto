package org.mdissjava.mdisscore.model.dao;

import java.util.List;

import org.mdissjava.mdisscore.model.pojo.Camera;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.mapping.Mapper;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.sun.corba.se.spi.ior.ObjectId;

public class CameraDaoImpl extends BasicDAO<Camera, ObjectId> implements
		CameraDao {

	public CameraDaoImpl(Datastore ds) {
		super(ds);
		// TODO Auto-generated constructor stub
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
		if (camera.getVotes() != null) {
			query.field("votes").equal(camera.getVotes());
		}
		List<Camera> cameras = query.asList();

		return cameras;

	}

	@Override
	public void updateCamera(Camera camera) {
		if (camera.getId() != null) {
			Query<Camera> updateQuery = ds
					.createQuery(Camera.class).field(Mapper.ID_KEY)
					.equal(camera.getId());
			UpdateOperations<Camera> ops = ds
					.createUpdateOperations(Camera.class);

			if (camera.getBrand() != null) {
				ops.set("brand", camera.getBrand());
			}
			if (camera.getModel() != null) {
				ops.set("photos", camera.getModel());
			}
			if (camera.getVotes() != null) {
				ops.set("title", camera.getVotes());
			}
			ds.update(updateQuery, ops);
		}
	}

	@Override
	public void deleteCamera(Camera camera) {
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
		if (camera.getVotes() != null) {
			query.field("votes").equal(camera.getVotes());
		}
		ds.delete(query);

	}
}
