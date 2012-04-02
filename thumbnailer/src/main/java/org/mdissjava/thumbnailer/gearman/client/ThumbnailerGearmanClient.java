package org.mdissjava.thumbnailer.gearman.client;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.gearman.client.GearmanClient;
import org.gearman.client.GearmanClientImpl;
import org.gearman.client.GearmanIOEventListener;
import org.gearman.client.GearmanJob;
import org.gearman.client.GearmanJobImpl;
import org.gearman.client.GearmanJobResult;
import org.gearman.common.Constants;
import org.gearman.common.GearmanJobServerConnection;
import org.gearman.common.GearmanNIOJobServerConnection;
import org.gearman.util.ByteUtils;
import org.mdissjava.thumbnailer.gearman.worker.ThumbnailerScaleFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThumbnailerGearmanClient extends AbstractGearmanClient{
	
	private GearmanClient client = null;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private GearmanJob job = null;
	private GearmanJobServerConnection connection = null;
	
	public ThumbnailerGearmanClient(GearmanJobServerConnection connection) {
		this.connection = connection;
		this.stablishClientConnection();
		
	}
	 
	public ThumbnailerGearmanClient(String host, int port)
	{
		this(new GearmanNIOJobServerConnection(host, port));
	}
	
	public ThumbnailerGearmanClient()
	{   
        this(new GearmanNIOJobServerConnection(Constants.GEARMAN_DEFAULT_TCP_HOST,
        										Constants.GEARMAN_DEFAULT_TCP_PORT));
	}
	
	
	/**
	 * Automatically managed by the pool. Don't use it!!!
	 * 
	 * @throws IllegalStateException
	 */
	public void shutdown() throws IllegalStateException {
		if (client == null)
		{
			this.logger.error("No gearman thumbnail client to shutdown");
			throw new IllegalStateException("No gearman thumbnail client to shutdown");
		}
		this.client.shutdown();
		this.client = null;
		this.logger.info("Gearman thumbnail client instance closed");
		
	}
	
	public void stablishClientConnection()
	{
		if (client != null)
		{
			this.logger.error("Can't restablish, client active connection, first shutdown the client!");
			throw new IllegalStateException("Can't restablish, client active connection, first shutdown the client!");
		}
		client = new GearmanClientImpl();
		client.addJobServer(connection);
		this.logger.info("Gearman thumbnail client instance created");
	}
	
	
	public void ThumbnailizeImageAsynchronous(String imageId, AtomicBoolean finishedFlag) throws InterruptedException, ExecutionException
	{

		//Get the worker
		String function = ThumbnailerScaleFunction.class.getCanonicalName();
		//Get the image identifier 
		String uniqueId = null;
		byte[] data = ByteUtils.toUTF8Bytes(imageId);
		
		//Create a job to submit to Gearman server (background)
		this.job = GearmanJobImpl.createBackgroundJob(function, data, uniqueId);

		//Register in the Job our listener
		//Java doesn't use reference params so... for setting the result in the clients variable (asynchronous method) we need
		//a reference boolean, so we use atomicBoolean (these are exceptional reference variables)
		GearmanIOEventListener giol = new ThumbnailerScaleListener(finishedFlag);
		job.registerEventListener(giol);
		
		//set the job in the Gearman server
		client.submit(job);

	}
	
	public String ThumbnailizeImageSynchronous(String imageId) throws InterruptedException, ExecutionException
	{
		//Maybe we are waiting from other job submission
		/*if (this.job != null)
		{
			if(!this.job.isBackgroundJob())
			{
				
			}
				
		}*/
		//Get the worker
		String function = ThumbnailerScaleFunction.class.getCanonicalName();
		//Get the image identifier 
		String uniqueId = null;
		byte[] data = ByteUtils.toUTF8Bytes(imageId);
		
		//Create a job to submit to Gearman server
		this.job = GearmanJobImpl.createJob(function, data, uniqueId);
		String value = "";
		
		//set the job in the Gearman server
		client.submit(job);
		
		GearmanJobResult res = job.get();
		value = ByteUtils.fromUTF8Bytes(res.getResults());
		
		return value;
	}

	public GearmanClient getClient() {
		return client;
	}

	public GearmanJob getJob() {
		return job;
	}
	
}
