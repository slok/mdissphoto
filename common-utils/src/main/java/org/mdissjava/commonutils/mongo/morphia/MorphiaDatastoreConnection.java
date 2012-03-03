package org.mdissjava.commonutils.mongo.morphia;

import java.net.UnknownHostException;
import java.util.List;

import org.mdissjava.commonutils.mongo.db.MongoDBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.MongoException;

/**
 * This class is Singleton so only one instance will be in the execution time
 * MorphiaDatastoreConnection mnages the mongo connection and datastore instances
 * for the morphia objects manipulation
 * 
 * @author slok
 *
 */
public class MorphiaDatastoreConnection {
	
	private static MorphiaDatastoreConnection instance = null;
	private String db = null;
	private String url = null;
	private int port = 0;
	private Datastore dataStore = null;
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	 /**
	  * Private contructor because of the singleton class
	  */
	private MorphiaDatastoreConnection(){
	 }

	/**
	 * Connects with Mongo database and creates the datastore. This connect to method
	 * Manages the localhost to connection with Mongo (127.0.0.1:27017)
	 * 
	 * @param db is the database to connect
	 * @param classes are the classes to register in the Morphia instance
	 */
	@SuppressWarnings("rawtypes")
	public void connect(String db, List<Class> classes){
		this.connect(null, 0, db, classes);
	}
	
	/**
	 * Connects with Mongo database and creates the datastore. This connect to method
	 * 
	 * @param url is the URL where the Mongo instance is listening
	 * @param port is the port where Mongo database is listening
	 * @param db is the database to connect
	 * @param classes are the classes to register in the Morphia instance
	 */
	@SuppressWarnings("rawtypes")
	public void connect(String url, int port, String db, List<Class> classes){
		
		if (classes.isEmpty()){
			this.logger.error("[Morphia] [Datastore] Morphia needs to map some classes before creating datastore");
			throw new IllegalStateException("Morphia needs to map some classes before creating datastore");
		}
		
		//if nothing has changed then don't create a new connection
		if(this.db != db || this.url != url || this.port != port){
			
			this.logger.info("[Morphia] [Datastore] Creating new connection of Morphia datastore");
			
			MongoDBConnection mongo = null;
			
			try {
				//get mongo connection 
				mongo = MongoDBConnection.getInstance();
				if (url == null  && port == 0)
					mongo.connect();
				else
					mongo.connect(url, port);
				
				// create morphia and register classes
				Morphia morphia = new Morphia();
				this.registerClassInDatastore(morphia, classes);
				
				//create datastore
				this.dataStore = morphia.createDatastore(mongo.getConnection(), db);
				this.dataStore.ensureIndexes();
				
				//set data in instance
				this.db = db;
				this.url = url;
				this.port = port;
				
			} catch (UnknownHostException e) {
				this.logger.error("[File][Morphia Datastore] Failed connection. Unkown host: {}", e.toString());
				
			} catch (MongoException e) {
				this.logger.error("[File][Morphia Datastore] Failed connection. Mongo exception: {}", e.toString());
			}
		}
		else
			this.logger.info("[Morphia] [Datastore] Using previous connection of Morphia datastore");
	}
	
	
	/**
	 * This method is static, and is the form that we have to obtain the singleton instance
	 * 
	 * @return the MorphiaDatastoreConnection singleton instance
	 */
	public static MorphiaDatastoreConnection getInstance(){
		
		if (instance == null)
			MorphiaDatastoreConnection.instance = new MorphiaDatastoreConnection();
		
		return instance;
	}
	
	public void releaseInstance()
	{
		MorphiaDatastoreConnection.instance = null;
	}
	
	/**
	 * Gets the datastore that we created previously with the "connect"
	 * 
	 * @return the datastore previously created
	 */
	public Datastore getDatastore()
	{
		if(this.dataStore == null)
			throw new IllegalStateException("Not initialized. Connect first");
		
		return this.dataStore;
	}
	
	/**
	 * Maps the classes that we want to map in the morphia instance
	 * 
	 * @param morphia is the Morphia instance where willl be mapped the classes
	 * @param classes are the ones that will be mapped in the Moprhia instance
	 */
	@SuppressWarnings("rawtypes") 
	private void registerClassInDatastore(Morphia morphia, List<Class> classes){
		
		this.logger.info("[Morphia] [Datastore] Mapping clases " + classes.size() +" in morphia obejct");
		
		for(Class i: classes){
			morphia.map(i);
		}	
	}
}
