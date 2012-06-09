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
import javax.ws.rs.core.Response;

import org.mdissjava.commonutils.properties.PropertiesFacade;
import org.mdissjava.mdisscore.model.dao.AlbumDao;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.AlbumDaoImpl;
import org.mdissjava.mdisscore.model.pojo.Album;
import org.mdissjava.mdisscore.model.pojo.Photo;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.annotations.AlsoLoad;

@Path("/albums")
public class Albums {
	
	//constants
	private final String GLOBAL_PROPS_KEY = "globals";
	private final String MORPHIA_DATABASE_KEY = "morphia.db";
	private Datastore datastore = null;
	private String usernick;
	
	public Albums() throws IllegalArgumentException, IOException {
		PropertiesFacade propertiesFacade = new PropertiesFacade();
		String database = propertiesFacade.getProperties(GLOBAL_PROPS_KEY).getProperty(MORPHIA_DATABASE_KEY);
		this.datastore = MorphiaDatastoreFactory.getDatastore(database);
		//TODO: Change this and fetch the usernick from the header of the request
		this.usernick = "cerealguy";
	}
	
	@GET
	@Path("/")
	@Produces("application/json")
	public Response getAlbums(){
		
		Album album = new Album();
		album.setUserNick(this.usernick);
		
		AlbumDao albumDao = new AlbumDaoImpl(this.datastore);
		List<Album> albums = albumDao.findAlbum(album);
		
		if (albums.size() != 0)
			return Response.status(200).entity(albums).build();
		else
			return Response.status(400).entity("Error retrieving albums").build();
	}
	
	@GET
	@Path("/{albumId}")
	@Produces("application/json")
	public Response getAlbum(@PathParam("albumId") String albumId){
		
		Album album = new Album();
		album.setAlbumId(albumId);
		
		AlbumDao albumDao = new AlbumDaoImpl(this.datastore);
		List<Album> albums = albumDao.findAlbum(album);
		
		//System.out.println("yeah");
		if (albums.size() == 1)
			return Response.status(200).entity(albums.get(0)).build();
		else
			return Response.status(400).entity("Error retrieving album").build();
	}
	
	@PUT
	@Path("/{albumId}")
	@Consumes("application/json")
	public Response updateAlbum(Album album){
		if (album !=null){
			
			AlbumDao albumDao = new AlbumDaoImpl(this.datastore);
			//Search if album exists
			Album a = new Album();
			a.setAlbumId(album.getAlbumId());
			a.setUserNick(album.getUserNick());
			if (albumDao.findAlbum(a).size() == 1)
			{
				albumDao.updateAlbum(album);
				return Response.status(200).entity(album).build();
			}
		}
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
			a.setUserNick(album.getUserNick());
			if (albumDao.findAlbum(a).size() > 0)
				return Response.status(400).entity("Album already exists").build();
			
			//save in database
			album.setAlbumId(UUID.randomUUID().toString());
			album.setCreationDate(new Date());
			albumDao.insertAlbum(album);
			return Response.status(200).entity(album).build();
			
		}else
			return Response.status(400).entity("Error creating album").build();
	}
	
	/*
	@DELETE
	@Path("/{albumId}")
	@Consumes("application/json")
	public Response deleteAlbum(@PathParam("albumId") String albumId){
	}
	*/
	
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

}
