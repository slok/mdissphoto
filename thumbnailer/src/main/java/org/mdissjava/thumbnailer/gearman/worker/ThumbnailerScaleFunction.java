package org.mdissjava.thumbnailer.gearman.worker;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.gearman.client.GearmanJobResult;
import org.gearman.client.GearmanJobResultImpl;
import org.gearman.util.ByteUtils;
import org.gearman.worker.AbstractGearmanFunction;
import org.mdissjava.commonutils.mongo.gridfs.GridfsDataStorer;
import org.mdissjava.commonutils.mongo.morphia.MorphiaDatastoreConnection;
import org.mdissjava.commonutils.photo.status.PhotoStatus;
import org.mdissjava.commonutils.photo.status.PhotoStatusManager;
import org.mdissjava.commonutils.properties.PropertiesFacade;
import org.mdissjava.thumbnailer.thumbnails.Thumbnailer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.Datastore;

public class ThumbnailerScaleFunction extends AbstractGearmanFunction{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private String imageId = null; 
	private BufferedImage imageData = null;
	private static final String RESOLUTIONS_PROPERTIES_KEY = "resolutions";
	private static final String THUMBNAILS_PROPERTIES_KEY = "thumbnails";
	
	private static String MORPHIA_DB;
	private static String THUMBNAILS_DB;
	private static String ORIGINAL_BUCKET;
	private static final String GLOBALS_KEY = "globals";
	
	private PropertiesFacade propertiesFacade = null;
	private String imageFormat = "png";
	private Thumbnailer thumnailer = null;
	
	/**
	 *  The method that gearman will execute. This method will call the needed the 
	 *  methods to create all the thumbnails
	 */
	@SuppressWarnings("finally")
	@Override
	public GearmanJobResult executeFunction() {
		this.imageId = new String(ByteUtils.fromUTF8Bytes((byte[]) this.data));
		this.logger.info("Executing Gearman Thumbnail worker function with '{}' data", this.imageId);
		byte[] exception = new byte[0];
		boolean result = true;
		
		//FIXME: Doesn't return COMPLETE package to gearman in async!!!!
		try {
			//Load the needed properties
			this.propertiesFacade = new PropertiesFacade();
			Properties globals = this.propertiesFacade.getProperties(GLOBALS_KEY);
			MORPHIA_DB = globals.getProperty("morphia.db");
			THUMBNAILS_DB = globals.getProperty("images.db");
			ORIGINAL_BUCKET = globals.getProperty("images.bucket");
			
			//Create the thumbnails
			createThumbnails();
			
			//Set the new status for the photo process (true) -> finished
			Datastore ds = this.connectToMorphia();
			PhotoStatusManager photoManager = new PhotoStatusManager(ds);
			photoManager.markAsProcessedFinished(this.imageId);
			
		} catch (Exception e) {
			e.printStackTrace();//check witch is the exception (we catch many)
			this.logger.error("Something went worng with '{}' Image thumbnail creation", this.imageId);
			exception = e.toString().getBytes();
			result = false;
		} finally{
			//return the result to the client (always)
			final GearmanJobResult gjr = new GearmanJobResultImpl(this.jobHandle, 
															result, this.imageId.getBytes(), 
															new byte[0], exception,
															0, 0);
			
			this.logger.info("Returning worker result of '{}' to gearman", this.imageId);
			return gjr;
			
		}
		
	}

	/**
	 * This method creates thumbnails retrieving the resolutions from the properties
	 * and the image from Mongo
	 * 
	 * @throws IOException
	 */
	private void createThumbnails() throws IOException {
		
		//get the original image and convert to to buffered image
		GridfsDataStorer gds = new GridfsDataStorer(THUMBNAILS_DB, ORIGINAL_BUCKET);
		ByteArrayOutputStream baos = (ByteArrayOutputStream)gds.getData(this.imageId);
		byte[] data = baos.toByteArray();
		this.imageData = ImageIO.read(new ByteArrayInputStream(data));
		
		//Get all the resolutions
		Properties resolutionProps = propertiesFacade.getProperties(RESOLUTIONS_PROPERTIES_KEY);
		
		//get the bucket props
		Properties thumbnailsProps = propertiesFacade.getProperties(THUMBNAILS_PROPERTIES_KEY);
		
		//create the instance of thumbnailer
		this.thumnailer = new Thumbnailer(this.imageData);
		 
		@SuppressWarnings("rawtypes")
		Enumeration resolutions = resolutionProps.keys();
		
		String key = null;
		String bucket = null;
		
		//call for each resolution the scale method (with the resolution and the bucket to store name
		this.logger.info("Start creating resolutions for image: {}", this.imageId);
		while(resolutions.hasMoreElements())
		{
			key = (String)resolutions.nextElement();
			bucket = thumbnailsProps.getProperty(resolutionProps.getProperty(key));
			
			this.scaleImageAndSave(Integer.valueOf(key), bucket);
		}

	}
	
	
	/**
	 * Creates the scaled image adn saves the image in Mongo
	 * 
	 * @param resolution
	 * @param bucketName
	 * @throws IOException
	 */
	private void scaleImageAndSave(int resolution, String bucketName) throws IOException {
		
		BufferedImage scaledImage = null;
		//if the scale is too big the scale method will tell us with an exception
		try
		{
			//To crop or not to crop thats the question...
			if(bucketName.contains("square")){
				scaledImage = this.thumnailer.thumbnailizeImageCropping(resolution);
			}
			else{
				scaledImage = this.thumnailer.autoThumbnailizeImage(resolution);
			}
			
			//save process
			GridfsDataStorer gds = new GridfsDataStorer(THUMBNAILS_DB, bucketName);
			
			//convert to buffered image to inputStream
			
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(scaledImage, imageFormat, os);
			InputStream is = new ByteArrayInputStream(os.toByteArray());
			
			gds.saveData(is,this.imageId);
			//ImageIO.write(scaledImage, "jpeg", new File("/home/slok/gearman_thumbnails/"+this.imageId+"_"+resolution+"px"));

			
		}catch(IllegalArgumentException iae)
		{
			this.logger.error("Resolution to resize is higher than the image");
		}
		
	}
	
	private Datastore connectToMorphia()
	{
			
			@SuppressWarnings("rawtypes")
			ArrayList<Class> classes = new ArrayList<Class>();
			classes.add(PhotoStatus.class);
			
			MorphiaDatastoreConnection mdc = MorphiaDatastoreConnection.getInstance();
			mdc.connect("127.0.0.1", 27017, MORPHIA_DB, classes);
			return mdc.getDatastore();
			
	}

}
