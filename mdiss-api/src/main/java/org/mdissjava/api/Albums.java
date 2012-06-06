package org.mdissjava.api;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.mdissjava.commonutils.properties.PropertiesFacade;
import org.mdissjava.mdisscore.model.dao.AlbumDao;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.AlbumDaoImpl;
import org.mdissjava.mdisscore.model.pojo.Album;

import com.google.code.morphia.Datastore;

@Path("/albums")
public class Albums {
	
	//constants
	private final String GLOBAL_PROPS_KEY = "globals";
	private final String MORPHIA_DATABASE_KEY = "morphia.db";
	private Datastore datastore = null;
	
	public Albums() throws IllegalArgumentException, IOException {
		PropertiesFacade propertiesFacade = new PropertiesFacade();
		String database = propertiesFacade.getProperties(GLOBAL_PROPS_KEY).getProperty(MORPHIA_DATABASE_KEY);
		this.datastore = MorphiaDatastoreFactory.getDatastore(database);
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

}
