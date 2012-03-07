package org.mdissjava.mdisscore.model.dao;

import java.util.List;

import org.mdissjava.mdisscore.model.pojo.Album;

public interface AlbumDao {
	void insertAlbum(Album album);

	List<Album> findAlbum(Album album);

	void updateAlbum(Album album);

	void deleteAlbum(Album album);
}
