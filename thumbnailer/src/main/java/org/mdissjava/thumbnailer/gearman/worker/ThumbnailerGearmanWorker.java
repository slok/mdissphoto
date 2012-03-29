package org.mdissjava.thumbnailer.gearman.worker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.management.InvalidAttributeValueException;

import org.gearman.common.Constants;
import org.gearman.common.GearmanNIOJobServerConnection;
import org.gearman.worker.GearmanFunction;
import org.gearman.worker.GearmanWorker;
import org.gearman.worker.GearmanWorkerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThumbnailerGearmanWorker {
	
	private GearmanNIOJobServerConnection conn;
    private List<Class<GearmanFunction>> functions;
    private GearmanWorker worker = null;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    public ThumbnailerGearmanWorker(String host, int port, List<Class<GearmanFunction>> funs) {
		conn = new GearmanNIOJobServerConnection(host, port);
		functions = new ArrayList<Class<GearmanFunction>>();
		//add all the functions that this worker can do
		functions.addAll(funs); 
		this.logger.info("ThumbnailerGearmanWorker instance created");
	}
    
    public ThumbnailerGearmanWorker(String host, int port) {
    	conn = new GearmanNIOJobServerConnection(host, port);
    	functions = new ArrayList<Class<GearmanFunction>>();
    	this.logger.info("ThumbnailerGearmanWorker instance created");
    }
    
    public ThumbnailerGearmanWorker(List<Class<GearmanFunction>> funs) {
		this(Constants.GEARMAN_DEFAULT_TCP_HOST, Constants.GEARMAN_DEFAULT_TCP_PORT, funs);
	}
    
    public ThumbnailerGearmanWorker() {
		this(Constants.GEARMAN_DEFAULT_TCP_HOST, Constants.GEARMAN_DEFAULT_TCP_PORT);
	}
    
    public void start() throws InvalidAttributeValueException, IOException{
        //if there are no functions to attach to gearman then gearman doesn't work...
    	if (functions.isEmpty())
        	throw new InvalidAttributeValueException("There are no Gearman functions to attach to the workers. Set some ;)");
    	else
    	{
    		//If we reuse the instance then stop the attached worker before launching the new
    		this.stop();

	        //Create a new worker
    		worker = new GearmanWorkerImpl();
    		
    		//connect and check if the connection fails
			if(!worker.addServer(conn))
			{
				this.logger.error("Could not connect to Gearman");
				throw new IOException("Could not connect to Gearman");
			}
			
	        //attach the workers functions
	        for (Class<GearmanFunction> fun : functions) {
	            worker.registerFunction(fun);
	        }
	        
	        this.logger.info("Starting gearman worker");
	        worker.work();
    	}
    }
    

    public void stop(){
        if (this.worker != null)
        {
        	this.logger.info("Stopping gearman worker");
        	worker.stop();
        }
        
    }
    
    public void shutdown(){
    	if (this.worker!=null)
    	{
    		this.logger.info("Stopping gearman worker");
    		worker.shutdown();
    	}
    }

}
