package org.mdissjava.thumbnailer.gearman.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

import org.mdissjava.commonutils.properties.PropertiesFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a connection/instance pool. Can act like both closing connections and controlling the instances or can reuse the
 * connections and control the instances too. Maybe someone wants to close connections and not reuse them
 * This is because sometimes clients are waiting to the response and we want to go ahead 
 * (is a background process and we don't need to wait for it, we can do also...) If we don't close the connection the other thread
 * gets stuck waiting until the job is finished or the instance is deleted by the server.
 * 
 * So this class is to provide a max connection controlling and reusing the same instances or connection 
 * every time, when there are no connections available, the pool checks zombie connections, this is a 
 * stablished connection but with the job done.
 * 
 * So in other words we control that the RAM isn't going to do a stack overflow with more and more instances adn connections
 * This class is singleton and thread safe (I guess ;))
 * 
 * @author slok
 *
 */
public class ThumbnailerGearmanClientPool {
	
	private final int minConn;
	private final int maxConn;
	private final int maxTries;
	private final boolean closeConnections;
	
	private static ThumbnailerGearmanClientPool instance = null;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private List<AbstractGearmanClient> busyConnections;
	private List<AbstractGearmanClient> idleConnections;
	
	private ThumbnailerGearmanClientPool() throws IllegalArgumentException, IOException {
		
		busyConnections = new ArrayList<AbstractGearmanClient>();
		idleConnections = new ArrayList<AbstractGearmanClient>();
		logger.info("Initializing pool with {} connections", this.minConn);
		Properties p = new PropertiesFacade().getProperties("globals");
		
		String conns = p.getProperty("gearman.pool.max.connections");
		maxConn = Integer.valueOf(conns);
		
		conns = p.getProperty("gearman.pool.min.connections");
		minConn = Integer.valueOf(conns);
		
		conns = p.getProperty("gearman.pool.connection.tries");
		maxTries = Integer.valueOf(conns);
		
		conns = p.getProperty("gearman.pool.close.connections");
		closeConnections = conns.equals("true")?true:false;
		
		
		for(int i = 0; i < this.minConn; i++)
		{
			idleConnections.add(this.createClient());
		}
		
	}
	
	public static ThumbnailerGearmanClientPool getInstance() throws IllegalArgumentException, IOException
	{
		if (instance == null)
			instance = new ThumbnailerGearmanClientPool();
		
		return instance;
	}
	
	
	public synchronized AbstractGearmanClient getClient() throws TimeoutException
	{
		if (idleConnections.isEmpty())
		{
			if (busyConnections.size() < maxConn)
			{
				idleConnections.add(createClient());
			}
			else
			{
				// max capacity reached
				logger.warn("Maximum capacity reached, waiting for clients to become available");
				int tries = 0;
				
				while(busyConnections.size() == maxConn && tries < maxTries)
				{
					tries++;
					try
					{
						//search for zombie connections (connected but with job finished)
						for(Iterator<AbstractGearmanClient> it = busyConnections.iterator(); it.hasNext();)
						{
							ThumbnailerGearmanClient i = (ThumbnailerGearmanClient)it.next();
							//check if some has the connection open and has finished
							if (i.getJob().isDone())
							{
								//shutdown and stablish new connection again -> NOT NEEDED IF WE ARE REUSING CONNECTIONS
								if (closeConnections)
								{
									i.shutdown();
									i.stablishClientConnection();
								}
								
								it.remove();
								logger.info("Closing a zombie connection... remaining: {}", this.maxConn - busyConnections.size());
								idleConnections.add(i);
								break;
							}
						}
						
						wait(5);
					}
					catch (InterruptedException e){}
				}
				
				//we have reached the timeout limit
				if(tries++ == maxTries)
					throw new TimeoutException("Timeout for connection waiting... No connections avaiable");
			}
		}
		
		//we have new connections avaiable :)
		AbstractGearmanClient client = idleConnections.remove(0);
		busyConnections.add(client);
		logger.info("Using iddle client, remaining: {}", this.maxConn - busyConnections.size());
		return client;
	}

	private AbstractGearmanClient createClientInstance() {
		return new ThumbnailerGearmanClient();
	}
	

	private AbstractGearmanClient createClient() {
		
		AbstractGearmanClient gc = createClientInstance();
		//this.actualConn++;
	
		logger.info("Created Gearman client connection");
		return gc;
	}


	public synchronized void releaseClient(AbstractGearmanClient client)
	{
		boolean removed = busyConnections.remove(client);
		if (removed)
		{
			logger.info("Releasing a client...");
			
			//close the previous connection -> NOT NEEDED IF WE ARE REUSING CONNECTIONS
			if (closeConnections)
				((ThumbnailerGearmanClient)client).shutdown();
			
			idleConnections.add(client);
			notifyAll();
		}
		else
			logger.warn("releaseClient called on a client which was not in the busy list");
	}

}
