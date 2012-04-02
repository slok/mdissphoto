package org.mdissjava.thumbnailer.gearman;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.junit.Test;
import org.mdissjava.thumbnailer.gearman.client.ThumbnailerGearmanClient;
import org.mdissjava.thumbnailer.gearman.client.ThumbnailerGearmanClientPool;

public class GearmanClientPoolTest {

	//@Test
	public void test() throws IllegalArgumentException, IOException, InterruptedException, ExecutionException {
	
		int max = 27;
		PoolThread thread =  new PoolThread();
		
		for(int i=0; i<max; i++)
		{
			Thread.sleep(200);
			System.out.println("Tread: " + i);
			new Thread (thread).start();
		}
		while(true);
	}
	
	class PoolThread implements Runnable{

		@Override
		public void run() {
			ThumbnailerGearmanClient tgc;
			try {
				tgc = (ThumbnailerGearmanClient)ThumbnailerGearmanClientPool.getInstance().getClient();
				tgc.ThumbnailizeImageSynchronous("hola");
				//tgc.shutdown();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}

}
