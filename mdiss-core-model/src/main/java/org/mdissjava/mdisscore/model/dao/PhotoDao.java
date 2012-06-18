package org.mdissjava.mdisscore.model.dao;

import java.util.List;

import org.mdissjava.mdisscore.model.pojo.Album;
import org.mdissjava.mdisscore.model.pojo.Photo;

public interface PhotoDao {
	
	void insertPhoto(Photo photo);
	List<Photo> findPhoto(Photo photo);
	void updatePhoto(Photo photo);
	void deletePhoto(Photo photo);
	public List<Photo> getRandomPhotos(int quantity) throws IllegalStateException;
	public List<Photo> getPhotos(Album album, int quantityNumberPhotos, int skipNumberPhotos);
	public List<Photo> getPhotosFromTag(String tag);
	int getTotalPhotos();
	int getTotalPhotosAlbum(Album album);

}
