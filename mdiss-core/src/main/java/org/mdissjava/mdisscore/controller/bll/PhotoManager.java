package org.mdissjava.mdisscore.controller.bll;

import java.io.IOException;
import java.util.List;

import org.mdissjava.mdisscore.model.pojo.Photo;

public interface PhotoManager {
	
	void insertPhoto(String albumTitle, String userNickname, Photo photo) throws IllegalStateException, IOException;
	void insertPhoto(String imageId, String userNickname, String title, 
			String albumTitle, boolean publicPhoto, boolean plus18, 
			String license, String tags) throws IllegalArgumentException, IOException;
	List<Photo> findPhoto(Photo photo) throws IllegalArgumentException;
	void updatePhoto(Photo photo)throws IllegalArgumentException;
	void deletePhoto(Photo photo)throws IllegalArgumentException;
	void deletePhoto(String photoId) throws IOException;

}
