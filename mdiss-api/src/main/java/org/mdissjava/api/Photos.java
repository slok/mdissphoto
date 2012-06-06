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
import org.mdissjava.mdisscore.model.dao.PhotoDao;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.AlbumDaoImpl;
import org.mdissjava.mdisscore.model.dao.impl.PhotoDaoImpl;
import org.mdissjava.mdisscore.model.pojo.Album;
import org.mdissjava.mdisscore.model.pojo.Photo;

import com.google.code.morphia.Datastore;

@Path("/photos")
public class Photos {

	//constants
	private final String GLOBAL_PROPS_KEY = "globals";
	private final String MORPHIA_DATABASE_KEY = "morphia.db";
	private Datastore datastore = null;
	
	public Photos() throws IllegalArgumentException, IOException {
		PropertiesFacade propertiesFacade = new PropertiesFacade();
		String database = propertiesFacade.getProperties(GLOBAL_PROPS_KEY).getProperty(MORPHIA_DATABASE_KEY);
		this.datastore = MorphiaDatastoreFactory.getDatastore(database);
	}
	
	
	@GET
	@Path("/{photoId}")
	@Produces("application/json")
	public Response getPhoto(@PathParam("photoId") String photoId){
		
		Photo photo = new Photo();
		photo.setPhotoId(photoId);
		
		
		PhotoDao photoDao = new PhotoDaoImpl(this.datastore);
		List<Photo> photos = photoDao.findPhoto(photo);
		
		if (photos.size() == 1)
			return Response.status(200).entity(photos.get(0)).build();
		else
			return Response.status(400).entity("Error retrieving photo").build();
		
	}

}
