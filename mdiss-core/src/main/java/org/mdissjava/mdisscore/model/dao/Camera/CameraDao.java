package org.mdissjava.mdisscore.model.dao.Camera;

import java.util.List;

import org.mdissjava.mdisscore.model.pojo.Camera;

public interface CameraDao {
	void insertCamera(Camera camera);

	List<Camera> findCamera(Camera camera);

	void updateCamera(Camera camera);

	void deleteCamera(Camera camera);
}
