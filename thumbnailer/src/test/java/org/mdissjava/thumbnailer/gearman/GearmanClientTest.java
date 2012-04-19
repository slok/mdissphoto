package org.mdissjava.thumbnailer.gearman;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.imageio.ImageIO;
import javax.management.InvalidAttributeValueException;

import org.gearman.worker.GearmanFunction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mdissjava.commonutils.mongo.gridfs.GridfsDataStorer;
import org.mdissjava.commonutils.mongo.morphia.MorphiaDatastoreConnection;
import org.mdissjava.commonutils.photo.status.PhotoStatus;
import org.mdissjava.commonutils.photo.status.PhotoStatusManager;
import org.mdissjava.commonutils.properties.PropertiesFacade;
import org.mdissjava.thumbnailer.gearman.GearmanWorkersTest.workerThread;
import org.mdissjava.thumbnailer.gearman.client.ThumbnailerGearmanClient;
import org.mdissjava.thumbnailer.gearman.worker.GearmanDaemonWorker;
import org.mdissjava.thumbnailer.gearman.worker.ThumbnailerGearmanWorker;
import org.mdissjava.thumbnailer.gearman.worker.ThumbnailerScaleFunction;

import com.google.code.morphia.Datastore;

public class GearmanClientTest {

	
	private static WorkerThread workerThread;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		workerThread = (new GearmanClientTest()).new WorkerThread();
		new Thread (workerThread).start();
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
	}

	
	
	@Test
	public void asyncGearmanClientCall() throws InterruptedException, ExecutionException, IOException
	{
		Thread.sleep(100); //for the connection thread
		
		String imageId = "iris1";
		
		//insert in database  testing image
		PropertiesFacade propertiesFacade = new PropertiesFacade();
		Properties globals = propertiesFacade.getProperties("globals");
		final String THUMBNAILS_DB = globals.getProperty("images.db");
		final String ORIGINAL_BUCKET = globals.getProperty("images.bucket");
		GridfsDataStorer gds = new GridfsDataStorer(THUMBNAILS_DB,  ORIGINAL_BUCKET);
		InputStream is = getClass().getResourceAsStream("/Iris.jpg");		
		gds.saveData(is, imageId);
		
		//set the status for the photo
		Datastore ds = this.connectToMorphia();
		PhotoStatusManager photoManager = new PhotoStatusManager(ds);
		photoManager.createPhotoStatus(imageId);

		ThumbnailerGearmanClient tgc = new ThumbnailerGearmanClient();
		AtomicBoolean exit = new AtomicBoolean();
		exit.set(false);
		tgc.ThumbnailizeImageAsynchronous("iris1", exit);
		
		//delete data
		gds.deleteData(imageId);
		photoManager.deletePhotoStatus(imageId);
		
	}
	
	@Test
	public void syncGearmanClientCall() throws IOException, InterruptedException, ExecutionException
	{
		Thread.sleep(100); //for the connection thread

		String imageId = "iris1";
		
		//insert in database  testing image
		PropertiesFacade propertiesFacade = new PropertiesFacade();
		Properties globals = propertiesFacade.getProperties("globals");
		final String THUMBNAILS_DB = globals.getProperty("images.db");
		final String ORIGINAL_BUCKET = globals.getProperty("images.bucket");
		GridfsDataStorer gds = new GridfsDataStorer(THUMBNAILS_DB,  ORIGINAL_BUCKET);
		InputStream is = getClass().getResourceAsStream("/Iris.jpg");		
		gds.saveData(is, imageId);
		
		//set the status for the photo
		Datastore ds = this.connectToMorphia();
		PhotoStatusManager photoManager = new PhotoStatusManager(ds);
		photoManager.createPhotoStatus(imageId);
		
		//call gearman
		ThumbnailerGearmanClient tgc = new ThumbnailerGearmanClient();
		String result = tgc.ThumbnailizeImageSynchronous("iris1");
		assertEquals(imageId, result);
		
		//delete data
		gds.deleteData(imageId);
		photoManager.deletePhotoStatus(imageId);
	}
	
	//helper class for threading...
	class WorkerThread implements Runnable{
	
		
		@Override
		public void run() {
			try {
				//run the daemon
				GearmanDaemonWorker.main(null);
			} catch (InvalidAttributeValueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}
	
	private Datastore connectToMorphia()
	{
			
			@SuppressWarnings("rawtypes")
			ArrayList<Class> classes = new ArrayList<Class>();
			classes.add(PhotoStatus.class);
			
			MorphiaDatastoreConnection mdc = MorphiaDatastoreConnection.getInstance();
			mdc.connect("127.0.0.1", 27017, "mdissphoto", classes);
			return mdc.getDatastore();
			
	}
	
}
