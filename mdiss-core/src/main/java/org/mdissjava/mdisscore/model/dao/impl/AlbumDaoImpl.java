package org.mdissjava.mdisscore.model.dao.impl;

import java.util.List;

import org.bson.types.ObjectId;
import org.mdissjava.mdisscore.model.dao.AlbumDao;
import org.mdissjava.mdisscore.model.pojo.Album;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.mapping.Mapper;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

public class AlbumDaoImpl extends BasicDAO<Album, ObjectId> implements
		AlbumDao {

	public AlbumDaoImpl(Datastore ds) {
		super(ds);
	}

	private Query<Album> queryToFindMe(ObjectId id) {
		return ds.createQuery(Album.class).field(Mapper.ID_KEY).equal(id);
	}

	@Override
	public void insertAlbum(Album album) {

		this.save(album);
	}

	@Override
	public List<Album> findAlbum(Album album) {

		Query<Album> query = ds.createQuery(Album.class);
		if (album.getId() != null) {
			query.field("id").equal(album.getId());
		}
		if (album.getTitle() != null) {
			query.field("title").equal(album.getTitle());
		}
		if (album.getCreationDate() != null) {
			query.field("creationDate").equal(album.getCreationDate());
		}
		if (album.getUserId() != 0) {
			query.field("user").equal(album.getUserId());
		}
		if (album.getPhotos() != null) {
			query.field("photos").equal(album.getPhotos());
		}
		
		List<Album> albums = query.asList();

		return albums;

	}

	@Override
	public void updateAlbum(Album album) {
		UpdateOperations<Album> ops = ds.createUpdateOperations(Album.class);
			if (album.getTitle() != null) {
				ops.set("title", album.getTitle());
			}
			if (album.getCreationDate() !=null){
				ops.set("creationDay", album.getCreationDate());
			}
			if (album.getUserId() != 0){
				ops.set("user", album.getUserId());
			}
			if (album.getPhotos() !=null){
				ops.set("photos", album.getPhotos());
			}
			
		ds.update(this.queryToFindMe(album.getId()), ops);
	}

	@Override
	public void deleteAlbum(Album album) {

		ds.delete(album);

	}
}
