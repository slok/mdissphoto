package org.mdissjava.api;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.mdissjava.mdisscore.model.dao.AlbumDao;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.AlbumDaoImpl;
import org.mdissjava.mdisscore.model.pojo.Album;

import com.google.code.morphia.Datastore;

@Path("/helloworld")
public class HelloWorld {
	
	@GET
	@Path("/{param}")
	public Response saySomething(@PathParam("param") String msg){
		
		msg = "Hello world from server!!: " + msg;
		return Response.status(200).entity(msg).build();
	}
	
	@GET
	@Path("/albums/{albumId}")
	@Produces("application/json")
	public Response getAlbum(@PathParam("albumId") String albumId){
		
		Album album = new Album();
		album.setAlbumId(albumId);
		Datastore ds = MorphiaDatastoreFactory.getDatastore("mdissphoto");
		AlbumDao albumDao = new AlbumDaoImpl(ds);
		List<Album> albums = albumDao.findAlbum(album);
		
		
		return Response.status(200).entity(albums).build();
	}

}
