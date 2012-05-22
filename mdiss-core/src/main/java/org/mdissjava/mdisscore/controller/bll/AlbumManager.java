package org.mdissjava.mdisscore.controller.bll;

import java.io.IOException;
import java.util.List;

import org.mdissjava.mdisscore.model.pojo.Album;
import org.mdissjava.mdisscore.model.pojo.Photo;

public interface AlbumManager {
	
	String insertAlbum(String albumTitle, String userNickname) throws IllegalArgumentException, IOException;
	String insertAlbum(Album album) throws IllegalArgumentException, IOException;
	void addPhotoListToAlbum(String userNickname, String albumId, List<Photo> photos) throws IllegalArgumentException;
	void addNewPhotoToAlbum(String userNickname, String albumId, Photo photo) throws IllegalArgumentException, IOException;
	void movePhotoToAlbum(String userNickname, String albumId, String photoId) throws IllegalArgumentException, IOException;
	void movePhotoToAlbum(String userNickname, String albumId, Photo photo) throws IllegalArgumentException, IOException;
	void updateAlbum(Album album) throws IllegalArgumentException;
	void deleteAlbum(Album album) throws IllegalArgumentException, IOException;
	void deleteAlbum(String albumId, String userNickname) throws IllegalArgumentException, IOException;
	void deleteUserAllAlbumsAndPhotos(String userNickname) throws IllegalArgumentException, IOException;
	List<Photo> getPhotosFromAlbum(String albumId, String userNickname) throws IllegalArgumentException, IOException;
	List<Album> findAlbum(Album album) throws IllegalArgumentException;
	List<Album> findUserAlbums(String userNick);
	void forceDeleteAlbum(Album albumMaster) throws IOException;
	Album searchAlbumUniqueUtil(String albumId, String userNickname) throws IOException;

}
