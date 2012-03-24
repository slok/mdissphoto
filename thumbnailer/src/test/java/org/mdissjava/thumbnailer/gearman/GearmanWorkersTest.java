package org.mdissjava.thumbnailer.gearman;

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
	public void testConnetion() throws InvalidAttributeValueException, InterruptedException {

		//add functions
		List<Class<GearmanFunction>> functions = new ArrayList<Class<GearmanFunction>>();
		Class gf = ThumbnailerScaleFunction.class;
		functions.add((Class<GearmanFunction>)gf);
		
		ThumbnailerGearmanWorker worker = new ThumbnailerGearmanWorker(functions);
		
		//start worker (creating a thread brecause we want to finish the test!)
		workerThread workerThread =  new workerThread(worker);
		//Legen...
		new Thread (workerThread).start();
		//Wait for it...
		Thread.sleep(500); //for the connection
		//Dary!
		workerThread.stop();
	}

	//helper class for threading...
	class workerThread implements Runnable{
	
		private ThumbnailerGearmanWorker worker;
		
		public workerThread(ThumbnailerGearmanWorker worker) {
			this.worker = worker;
		}
		
		@Override
		public void run() {
			try {
				this.worker.start();
			} catch (InvalidAttributeValueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void stop() {
			this.worker.stop();
			this.worker = null;
		}

	}

}


