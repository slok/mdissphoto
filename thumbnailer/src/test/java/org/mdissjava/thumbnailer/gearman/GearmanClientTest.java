package org.mdissjava.thumbnailer.gearman;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.management.InvalidAttributeValueException;

import org.gearman.worker.GearmanFunction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mdissjava.thumbnailer.gearman.GearmanWorkersTest.workerThread;
import org.mdissjava.thumbnailer.gearman.client.ThumbnailerGearmanClient;
import org.mdissjava.thumbnailer.gearman.worker.GearmanDaemonWorker;
import org.mdissjava.thumbnailer.gearman.worker.ThumbnailerGearmanWorker;
import org.mdissjava.thumbnailer.gearman.worker.ThumbnailerScaleFunction;

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
	public void asyncGearmanClientCall() throws InterruptedException, ExecutionException
	{
		Thread.sleep(100); //for the connection thread
		
		ThumbnailerGearmanClient tgc = new ThumbnailerGearmanClient();
		AtomicBoolean exit = new AtomicBoolean();
		exit.set(false);
		tgc.ThumbnailizeImageAsynchronous("iris1", exit);
		
	}
	
	@Test
	public void syncGearmanClientCall() throws IOException, InterruptedException, ExecutionException
	{
		Thread.sleep(100); //for the connection thread
		
		ThumbnailerGearmanClient tgc = new ThumbnailerGearmanClient();
		String result = tgc.ThumbnailizeImageSynchronous("iris1");
		assertEquals("iris1", result);
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
	
}
