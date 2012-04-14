package org.mdissjava.commonutils.photo.status;

import java.util.List;

public interface PhotoStatusDao {
	
	void insertPhotoStatus(PhotoStatus photoStatus);
	List<PhotoStatus> findPhotoStatus(PhotoStatus photoStatus);
	void updatePhotoStatus(PhotoStatus photoStatus);
	void deletePhotoStatus(PhotoStatus photoStatus);

}
