package org.mdissjava.thumbnailer.gearman;

import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import javax.management.InvalidAttributeValueException;

import org.gearman.worker.GearmanFunction;
import org.junit.Test;
import org.mdissjava.thumbnailer.gearman.worker.ThumbnailerGearmanWorker;
import org.mdissjava.thumbnailer.gearman.worker.ThumbnailerScaleFunction;

public class GearmanWorkersTest {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testConnetion() throws InvalidAttributeValueException, InterruptedException, ConnectException {

		//add functions
		List<Class<GearmanFunction>> functions = new ArrayList<Class<GearmanFunction>>();
		Class gf = ThumbnailerScaleFunction.class;
		functions.add((Class<GearmanFunction>)gf);
		
		//TODO: Exception
		ThumbnailerGearmanWorker worker = new ThumbnailerGearmanWorker(functions);
		
		//start worker (creating a thread brecause we want to finish the test!)
		workerThread workerThread =  new workerThread(worker);
		//Legen...
		new Thread (workerThread).start();
		//Wait for it...
		Thread.sleep(500); //for the connection
		//Dary!
		workerThread.stop();
		assertFalse(workerThread.isException());
	}

	//helper class for threading...
	class workerThread implements Runnable{
	
		private ThumbnailerGearmanWorker worker;
		private boolean exception = false;
		
		public workerThread(ThumbnailerGearmanWorker worker) {
			this.worker = worker;
		}
		
		@Override
		public void run() {
			try {
				this.worker.start();
			} catch (InvalidAttributeValueException iae) {
				this.exception = true;
			} catch(IOException ioe)
			{
				this.exception = true;
			}
		}
		public void stop() {
			this.worker.stop();
			this.worker = null;
		}
		
		public boolean isException()
		{
			return this.exception;
		}

	}

	/*@Test
	public void test() throws InvalidAttributeValueException
	{
		//add functions
		List<Class<GearmanFunction>> functions = new ArrayList<Class<GearmanFunction>>();
		Class gf = ThumbnailerScaleFunction.class;
		functions.add((Class<GearmanFunction>)gf);
		ThumbnailerGearmanWorker worker = new ThumbnailerGearmanWorker(functions);
		worker.start();
	}*/
}


