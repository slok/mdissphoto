package org.mdissjava.mdisscore.controller.bll;

import java.io.IOException;
import java.util.List;

import org.mdissjava.mdisscore.model.pojo.Album;
import org.mdissjava.mdisscore.model.pojo.Photo;

public interface AlbumManager {
	
	void insertAlbum(String albumTitle, String userNickname) throws IllegalArgumentException, IOException;
	void insertAlbum(Album album) throws IllegalArgumentException, IOException;
	void addPhotoListToAlbum(String userNickname, String albumTitle, List<Photo> photos) throws IllegalArgumentException;
	void addNewPhotoToAlbum(String userNickname, String albumTitle, Photo photo) throws IllegalArgumentException, IOException;
	void movePhotoToAlbum(String userNickname, String albumTitle, String photoId) throws IllegalArgumentException, IOException;
	void movePhotoToAlbum(String userNickname, String albumTitle, Photo photo) throws IllegalArgumentException, IOException;
	void updateAlbum(Album album) throws IllegalArgumentException;
	void deleteAlbum(Album album) throws IllegalArgumentException, IOException;
	void deleteAlbum(String albumId, String userNickname) throws IllegalArgumentException, IOException;
	void deleteUserAllAlbumsAndPhotos(String userNickname) throws IllegalArgumentException, IOException;
	List<Photo> getPhotosFromAlbum(String albumTitle, String userNickname) throws IllegalArgumentException, IOException;
	List<Album> findAlbum(Album album) throws IllegalArgumentException;
	List<Album> findUserAlbums(String userNick);

}
