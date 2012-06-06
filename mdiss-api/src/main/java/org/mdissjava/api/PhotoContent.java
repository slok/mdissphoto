package org.mdissjava.api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.mdissjava.commonutils.mongo.gridfs.GridfsDataStorer;
import org.mdissjava.commonutils.properties.PropertiesFacade;

@Path("/photo-content")
public class PhotoContent {
	
	//constants
	private final String GLOBAL_PROPS_KEY = "globals";
	private final String RESOLUTIONS_PROPS_KEY = "resolutions";
	private final String THUMBNAILS_PROPS_KEY = "thumbnails";
	private final String IMAGES_DATABASE_KEY = "images.db";
	
	
	//private GridfsDataStorer imageDao = null;
	private String database = null;
	
	public PhotoContent() throws IOException {
		//get datastore
		PropertiesFacade propertiesFacade = new PropertiesFacade();
		this.database = propertiesFacade.getProperties(GLOBAL_PROPS_KEY).getProperty(IMAGES_DATABASE_KEY);
		
	}
	
	@GET
	@Path("/{imageId}/size/{size}")
	@Produces("image/*")
	public Response getThumbnailContent(@PathParam("imageId") String imageId, 
										@PathParam("size") String size){
		try {
			String bucketKey;
			bucketKey = new PropertiesFacade().getProperties(RESOLUTIONS_PROPS_KEY).getProperty(size);
			String bucket = new PropertiesFacade().getProperties(THUMBNAILS_PROPS_KEY).getProperty(bucketKey);
			
			byte[] byteImage = this.getImagefromGridFS(bucket, imageId);
			
			return Response.status(200).entity(byteImage).build();
			
			
		} catch (Exception e) {
			return Response.status(400).entity(e.toString()).build();
		}
	}
	
	@GET
	@Path("/{imageId}")
	@Produces("image/*")
	public Response getOriginalImageContent(@PathParam("imageId") String imageId){
		try {
			final String ORIGINAL_BUCKET = "images.bucket";
			String bucket = new PropertiesFacade().getProperties(GLOBAL_PROPS_KEY).getProperty(ORIGINAL_BUCKET);
			
			byte[] byteImage = this.getImagefromGridFS(bucket, imageId);
			
			if (byteImage == null)
				throw new IOException("Item " + imageId + " not found");
			
			return Response.status(200).entity(byteImage).build();
			
		} catch (IOException e) {
			return Response.status(400).entity(e.toString()).build();
		}
	}
	
	private byte[] getImagefromGridFS(String bucket, String imageId) throws IOException{
		
		GridfsDataStorer gds = new GridfsDataStorer(this.database, bucket);
		ByteArrayOutputStream data;
		data = (ByteArrayOutputStream)gds.getData(imageId);
		return data.toByteArray();
	}
	
	

}
