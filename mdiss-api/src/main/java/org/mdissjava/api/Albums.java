package org.mdissjava.api;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.spi.HttpRequest;
import org.mdissjava.api.helpers.ApiHelper;
import org.mdissjava.commonutils.properties.PropertiesFacade;
import org.mdissjava.mdisscore.model.dao.AlbumDao;
import org.mdissjava.mdisscore.model.dao.PhotoDao;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.AlbumDaoImpl;
import org.mdissjava.mdisscore.model.dao.impl.PhotoDaoImpl;
import org.mdissjava.mdisscore.model.pojo.Album;
import org.mdissjava.mdisscore.model.pojo.Photo;

import com.google.code.morphia.Datastore;

@Path("/albums")
public class Albums {
	
	//constants
	private final String GLOBAL_PROPS_KEY = "globals";
	private final String MORPHIA_DATABASE_KEY = "morphia.db";
	private final String DEFAULT_ALBUM_TITLE = "Master";
	private Datastore datastore = null;
	private String userName = null;
	
	public Albums(@Context HttpRequest request) throws IllegalArgumentException, IOException {
		this.userName = ApiHelper.getUserFromHttpRequest(request);
		PropertiesFacade propertiesFacade = new PropertiesFacade();
		String database = propertiesFacade.getProperties(GLOBAL_PROPS_KEY).getProperty(MORPHIA_DATABASE_KEY);
		this.datastore = MorphiaDatastoreFactory.getDatastore(database);
	}
	
	@GET
	@Path("/")
	@Produces("application/json")
	public Response getAlbums(){
		
		Album album = new Album();
		album.setUserNick(this.userName);
		
		AlbumDao albumDao = new AlbumDaoImpl(this.datastore);
		List<Album> albums = albumDao.findAlbum(album);
		
		if (albums.size() > 0)
			return Response.status(200).entity(albums).build();
		else
			return Response.status(400).entity("Error user has no albums").build();
	}
	
	@GET
	@Path("/{albumId}")
	@Produces("application/json")
	public Response getAlbum(@PathParam("albumId") String albumId){
		
		Album album = new Album();
		album.setAlbumId(albumId);
		
		AlbumDao albumDao = new AlbumDaoImpl(this.datastore);
		List<Album> albums = albumDao.findAlbum(album);
		
		if (albums.size() == 1)
			return Response.status(200).entity(albums.get(0)).build();
		else
			return Response.status(400).entity("Error retrieving album").build();
	}
	
	@PUT
	@Path("/{albumId}")
	@Consumes("application/json")
	public Response updateAlbum(@PathParam("albumId") String albumId, Album album){
	
			AlbumDao albumDao = new AlbumDaoImpl(this.datastore);
			//Search if album exists
			Album a = new Album();
			a.setAlbumId(albumId);
			
			List<Album> albums = albumDao.findAlbum(a);
			
			if (albums.size() == 1)
			{
				album.setId(albums.get(0).getId());
				album.setAlbumId(albums.get(0).getAlbumId());
				
				albumDao.updateAlbum(album);
				
				//Return the updated album's values
				return Response.status(200).entity(albumDao.findAlbum(a).get(0)).build();
			}
			else
				return Response.status(400).entity("Error updating album").build();
	}
	
	@POST
	@Consumes("application/json")
	public Response createAlbum(Album album){
		
		//Test: curl -i -X POST -H Accept:application/json -H Content-Type:application/json  -d '{"title":"Me","userNick":"horl"}' '127.0.0.1:8080/mdissapi/api/1.0/albums/'
		if (album != null){
			
			AlbumDao albumDao = new AlbumDaoImpl(this.datastore);
			
			//search if the title exists
			Album a = new Album();
			a.setTitle(album.getTitle());
			a.setUserNick(this.userName);
			if (albumDao.findAlbum(a).size() > 0)
				return Response.status(400).entity("Album already exists").build();
			
			//save in database
			album.setAlbumId(UUID.randomUUID().toString());
			album.setCreationDate(new Date());
			album.setUserNick(this.userName);
			albumDao.insertAlbum(album);
			return Response.status(200).entity("Album successfully created: "+ album).build();
			
		}else
			return Response.status(400).entity("Error creating album").build();
	}
	
	@DELETE
	@Path("/{albumId}")
	public Response deleteAlbum(@PathParam("albumId") String albumId){
		
		Album album = new Album();
		album.setAlbumId(albumId);
		
		// To delete an album first we must find it in the DB
		AlbumDao albumDao = new AlbumDaoImpl(this.datastore);
		List<Album> albums = albumDao.findAlbum(album);
		
		if (albums.size() == 1)
		{
			Album searchMasterAlbum = new Album();
			Album masterAlbum = new Album();
			
			searchMasterAlbum.setTitle(DEFAULT_ALBUM_TITLE);
			searchMasterAlbum.setUserNick(albums.get(0).getUserNick());
			masterAlbum = albumDao.findAlbum(searchMasterAlbum).get(0);

			List<Photo> photoList = albums.get(0).getPhotos();
			
			PhotoDao photoDao = new PhotoDaoImpl(this.datastore);
			
			//if there aren't any photos, then we don't need to move nothing
			if (photoList != null)
			{
				for(Photo i: photoList)
				{
					System.out.println("moving to master album: "+ i.getPhotoId());
					
					i.setAlbum(masterAlbum);
					photoDao.updatePhoto(i);
					
					masterAlbum.addPhotoToAlbum(i);
					albumDao.updateAlbum(masterAlbum);
					albumDao.updateAlbum(albums.get(0));
				}
			}
			
			albumDao.deleteAlbum(albums.get(0));
			return Response.status(200).entity("Album successfuly deleted.").build();
		}
		else
			return Response.status(400).entity("Error album doesn't exist").build();		
	}
	
	@GET
	@Path("/{albumId}/photos")
	@Produces("application/json")
	public Response getAlbumPhotos(@PathParam("albumId") String albumId){
		
		Album album = new Album();
		album.setAlbumId(albumId);
		
		AlbumDao albumDao = new AlbumDaoImpl(this.datastore);
		List<Album> albums = albumDao.findAlbum(album);
		
		if (albums.size() == 1)
		{
			List<Photo> albumPhotos = albums.get(0).getPhotos();
			return Response.status(200).entity(albumPhotos).build();
		}
		else
			return Response.status(400).entity("Error retrieving album's photos").build();
	}
	
	/*
	@GET
	@Path("/{albumId}/photos/url")
	@Produces("application/json")
	public Response getAlbumPhotosUrl(@PathParam("albumId") String albumId){
	
	}
	*/
	
	@PUT
	@Path("/{albumId}/addPhoto/{photoId}")
	public Response movePhotoToAlbum(@PathParam("albumId") String albumId, @PathParam("photoId") String photoId){
		
		Album searchAlbum = new Album();
		AlbumDao albumDao = new AlbumDaoImpl(this.datastore);
		
		searchAlbum.setAlbumId(albumId);
		searchAlbum.setUserNick(this.userName);
		List<Album> albums = albumDao.findAlbum(searchAlbum);
		
		Photo searchPhoto = new Photo();
		PhotoDao photoDao = new PhotoDaoImpl(this.datastore);
		
		searchPhoto.setPhotoId(photoId);
		List<Photo> photos = photoDao.findPhoto(searchPhoto);
		
		if(albums.size() == 1 && photos.size() == 1)
		{	
			Photo photo = new Photo();
			photo = photos.get(0);
			
			Album album = new Album();
			album = albums.get(0);
			
			if(album.getId().equals(photo.getAlbum().getId()))
				return Response.status(400).entity("Photo already in album. No need to move.").build();
			else
			{
				Album oldAlbum = new Album();
				oldAlbum = photo.getAlbum();
			
				photo.setAlbum(album);
				photoDao.updatePhoto(photo);
					
				album.addPhotoToAlbum(photo);
				albumDao.updateAlbum(album);
				
				oldAlbum.getPhotos().remove(photo);
				albumDao.updateAlbum(oldAlbum);
			
				return Response.status(200).entity("Photo successfully moved to album.").build();
			}
		}
		else
			return Response.status(400).entity("Error moving photo to album").build();
	}

}
