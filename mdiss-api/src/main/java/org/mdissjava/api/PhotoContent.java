package org.mdissjava.api;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.mdissjava.api.helpers.FileUploadForm;
import org.mdissjava.commonutils.mongo.gridfs.GridfsDataStorer;
import org.mdissjava.commonutils.photo.status.PhotoStatusManager;
import org.mdissjava.commonutils.properties.PropertiesFacade;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.thumbnailer.gearman.client.ThumbnailerGearmanClient;
import org.mdissjava.thumbnailer.gearman.client.ThumbnailerGearmanClientPool;

import com.google.code.morphia.Datastore;

@Path("/photo-content")
public class PhotoContent {
	
	//constants
	private final String GLOBAL_PROPS_KEY = "globals";
	private final String RESOLUTIONS_PROPS_KEY = "resolutions";
	private final String THUMBNAILS_PROPS_KEY = "thumbnails";
	private final String IMAGES_DATABASE_KEY = "images.db";
	
	private final String MORPHIA_DATABASE_KEY = "morphia.db";
	private final String ORIGINAL_IMG_BUCKET_KEY = "images.bucket";
	private final String UPLOAD_MIN_SIZE = "image.min.resolution";
	
	
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
	
	
	@POST
	@Path("/")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response UploadPhotoContent(@MultipartForm FileUploadForm form){

		try
		{
			//get the properties
			Properties globals = new PropertiesFacade().getProperties(GLOBAL_PROPS_KEY);
			//String morphiaDB = globals.getProperty(MORPHIA_DATABASE_KEY);
			String imageDB = globals.getProperty(IMAGES_DATABASE_KEY);
			String originalImageBucket  = globals.getProperty(ORIGINAL_IMG_BUCKET_KEY);
			//int minSize = Integer.valueOf(globals.getProperty(UPLOAD_MIN_SIZE));
			
			//store in database
			GridfsDataStorer gfs = new GridfsDataStorer(imageDB, originalImageBucket);
			String imageId = gfs.saveData(new ByteArrayInputStream(form.getFileData()));
			
			//set the new status to the photo
			Datastore ds = MorphiaDatastoreFactory.getDatastore(database);
			PhotoStatusManager photoManager = new PhotoStatusManager(ds);
			photoManager.createPhotoStatus(imageId);
			
			//call thumbnailer
			if (photoManager.needsToBeProcessed(imageId))
			{	
				ThumbnailerGearmanClient thumbClient = (ThumbnailerGearmanClient) ThumbnailerGearmanClientPool.getInstance().getClient();
				
				//set process to second state (false) -> start
				photoManager.markAsProcessedStarted(imageId);
				
				//don't wait
				AtomicBoolean finishedFlag = null;
				thumbClient.ThumbnailizeImageAsynchronous(imageId, finishedFlag);
				return Response.status(200).entity(imageId).build();
			}else
				return Response.status(400).entity("Error uploading").build();
		}catch(Exception e){
			return Response.status(400).entity("Error uploading").build();
		}
		
	}
	
	private byte[] getImagefromGridFS(String bucket, String imageId) throws IOException{
		
		GridfsDataStorer gds = new GridfsDataStorer(this.database, bucket);
		ByteArrayOutputStream data;
		data = (ByteArrayOutputStream)gds.getData(imageId);
		return data.toByteArray();
	}
	
	

}
