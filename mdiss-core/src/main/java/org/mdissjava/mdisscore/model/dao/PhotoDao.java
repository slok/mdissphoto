package org.mdissjava.mdisscore.model.dao;

import java.util.List;

import org.mdissjava.mdisscore.model.pojo.Photo;

public interface PhotoDao {
	
	void insertPhoto(Photo photo);
	List<Photo> findPhoto(Photo photo);
	void updatePhoto(Photo photo);
	void deletePhoto(Photo photo);

}
