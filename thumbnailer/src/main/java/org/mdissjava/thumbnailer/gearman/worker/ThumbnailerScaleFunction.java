package org.mdissjava.thumbnailer.gearman.worker;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.gearman.client.GearmanJobResult;
import org.gearman.client.GearmanJobResultImpl;
import org.gearman.util.ByteUtils;
import org.gearman.worker.AbstractGearmanFunction;
import org.mdissjava.commonutils.mongo.gridfs.GridfsDataStorer;
import org.mdissjava.commonutils.properties.PropertiesFacade;
import org.mdissjava.thumbnailer.thumbnails.Thumbnailer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThumbnailerScaleFunction extends AbstractGearmanFunction{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String imageId = null; 
	private BufferedImage imageData = null;
	private static final String RESOLUTIONS_PROPERTIES_KEY = "resolutions";
	private static final String THUMBNAILS_PROPERTIES_KEY = "thumbnails";
	private static final String THUMBNAILS_DB = "thumbnails";
	private static final String ORIGINAL_BUCKET = "original";
	private Thumbnailer thumnailer = null;
	
	/**
	 *  The method that gearman will execute. This method will call the needed the 
	 *  methods to create all the thumbnails
	 */
	@Override
	public GearmanJobResult executeFunction() {
		this.imageId = new String(ByteUtils.fromUTF8Bytes((byte[]) this.data));
		this.logger.info("Executing Gearman Thumbnail worker function with '{}' data", this.imageId);
		
		try {
			createThumbnails();
		} catch (IOException e) {
			this.logger.error("Something went worng with '{}' Image thumbnail creation", this.imageId);
			e.printStackTrace();
		}
		
		//return the result to the client
		GearmanJobResult gjr = new GearmanJobResultImpl(this.jobHandle, 
														true, this.imageId.getBytes(), 
														new byte[0], new byte[0],
														0, 0);
		
		return gjr;
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
		
		//get all the resolutions
		PropertiesFacade propertiesFacade = new PropertiesFacade();
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
			ImageIO.write(scaledImage, "jpg", os);
			InputStream is = new ByteArrayInputStream(os.toByteArray());
			
			gds.saveData(is,this.imageId);
			//ImageIO.write(scaledImage, "jpeg", new File("/home/slok/gearman_thumbnails/"+this.imageId+"_"+resolution+"px"));

			
		}catch(IllegalArgumentException iae)
		{
			this.logger.error("Resolution to resize is higher than the image");
		}
		
	}

}
