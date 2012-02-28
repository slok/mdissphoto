package org.mdissjava.mdisscore.model;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.mdissjava.commonutils.mongo.db.MongoDBConnection;
import org.mdissjava.mdisscore.model.dao.CameraDao;
import org.mdissjava.mdisscore.model.dao.CameraDaoImpl;
import org.mdissjava.mdisscore.model.pojo.Camera;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;

public class CameraDaoImplTest {

	@Test
	public void testInsertFind() {
		MongoDBConnection mongodb = MongoDBConnection.getInstance();
		Datastore db = null;
		try {
			mongodb.connect();
			Mongo mongo = mongodb.getConnection();
			db = new Morphia().map(Camera.class).createDatastore(mongo, "MyMongoDb");
			db.ensureIndexes();
		} catch (Exception e) {
			fail("Failed connecting to the MongoDB");
		} 
		
		CameraDao cameraDao = new CameraDaoImpl(db);
		Camera camera = new Camera();
		camera.setBrand("Brand1");
		camera.setId("id1");
		camera.setModel("Canon 35D");
		
		cameraDao.insertCamera(camera);
		List<Camera> cameraList = cameraDao.findCamera(new Camera());
		assertEquals(cameraList.get(0).getId(), "id1");
	}
	
	

}
