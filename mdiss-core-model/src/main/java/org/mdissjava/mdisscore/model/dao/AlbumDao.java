package org.mdissjava.mdisscore.model.dao;

import java.util.List;

import org.mdissjava.mdisscore.model.pojo.Album;

public interface AlbumDao {
	void insertAlbum(Album album);

	List<Album> findAlbum(Album album);

	void updateAlbum(Album album);

	void deleteAlbum(Album album);

	int getTotalAlbums();

	List<Album> findUserAlbumsOffset(String userNick, int quantityNumberAlbums, int skipNumberAlbums);

	int getTotalAlbumsUser(String userNick);
}
