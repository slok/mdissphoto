package org.mdissjava.commonutils.mongo.gridfs;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.util.UUID;

import org.mdissjava.commonutils.data.DataStorer;
import org.mdissjava.commonutils.mongo.db.MongoDBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

/**
 * Implements the DataStorer interface for storing data in GRidFS (MongoDB) based
 * NoSQL databases
 * 
 * @author MDISS Java team 2011-2012 University of Deusto
 *
 */
public class GridfsDataStorer implements DataStorer {

	String collection;
	String database;
	MongoDBConnection mongoConn;
	String url = null;
	int port = 0;
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * The GridFS constructor that receives the database of mongo and the collection
	 * where the file will be stored
	 * 
	 * @param database The name of the database of mongo used for the project
	 * @param collection The name of the "bucket" where files will be stored
	 */
	public GridfsDataStorer(String database, String collection)
	{
		this.database = database;
		this.collection = collection;
		
		this.mongoConn = MongoDBConnection.getInstance();
	}
	
	/**
	 * The GridFS constructor that receives the database of mongo, the collection
	 * where the file will be stored and a specific direction where the Mongo is 
	 * 
	 * @param database The name of the database of mongo used for the project
	 * @param collection The name of the "bucket" where files will be stored
	 * @param url URL of mongo location (IP, domain...)
	 * @param port Port where mongo is listening
	 */
	public GridfsDataStorer( String database, String collection, String url, int port)
	{
		this.database = database;
		this.collection = collection;
		this.mongoConn = MongoDBConnection.getInstance();
		this.url = url;
		this.port = port;
	}
	
	/**
	 * Private method to manage the mongo connection used in some methods
	 * 
	 * @throws UnknownHostException If the host isn't reachable this exception will be thrown
	 * @throws MongoException If during the connection time something goes wrong with mongo this exception will be thrown 
	 */
	private void connectMongo() throws UnknownHostException, MongoException
	{

		if ( (this.port == 0) && (this.url == null) ) 
			this.mongoConn.connect();
		
		else
			this.mongoConn.connect(this.url, this.port);
			
	}
	
	
	@Override
	public String saveData(InputStream data) throws IOException 
	{
		//get uuid (unique and random)
		String uuid = UUID.randomUUID().toString();

		this.saveData(data, uuid);
		
		return uuid;
	}

	@Override
	public void saveData(InputStream data, String id) throws IOException{
		
		if (data == null)
			throw new IllegalStateException("No data/file. Nothing to store.");
		
		//Connect and create/get database &  Create namespace
		try {
			this.connectMongo();
			Mongo mdb = this.mongoConn.getConnection();
			//get a "file" of gridFS with the database + bucket(collection) 
			GridFS gfsFile = new GridFS(mdb.getDB(this.database), this.collection);
			
			// Load in a GridFS file the Inptustream
			GridFSInputFile gfsInputFile = gfsFile.createFile(data);
			
			// set a filename for identification purposes. We use the previously created UUID
			gfsInputFile.setFilename(id);
			
			// save the file file into mongoDB and disconnect
			gfsInputFile.save();
			
			this.logger.info("[File][GridFS] File with {} id data Stored", id);
			

		}catch (UnknownHostException e) {
			this.logger.error("[File][GridFS] File with {} id not Stored", id);
			throw new IOException("Unknown host exception, Data not stored");
		} catch (MongoException e) {
			this.logger.error("[File][GridFS] File with {} id not Stored", id);
			throw new IOException("Mongo exception, Data not stored");
		}
	}

	@Override
	public OutputStream getData(String id) throws IOException{
		if (id == null)
			throw new IllegalStateException("No id! Nothing to retrieve.");
		
		try {
			this.connectMongo();
			Mongo mdb = this.mongoConn.getConnection(); 
			GridFS gfs = new GridFS(mdb.getDB(this.database), this.collection);
			GridFSDBFile fileForOutput = gfs.findOne(id);
			
			//If the file is null then we know that there isn't in the database
			if (fileForOutput == null)
			{
				this.logger.error("[File][GridFS] Can not retrieve item: {}", id);
				throw new IOException(id + " Item not found in Mongo database");
			}
			
			//kind of clone (we need to clone and change format)
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			fileForOutput.writeTo(baos);
			
			this.logger.info("[File][GridFS] File with {} id data retrieved", id );
			
			return baos;
		}catch (UnknownHostException e) {
			this.logger.error("[File][GridFS] File with {} id not retrieved", id);
			throw new IOException("Unknown host exception, Data not retrieved");
		} catch (MongoException e) {
			this.logger.error("[File][GridFS] File with {} id not retrieved", id);
			throw new IOException("Mongo exception, Data not retrieved");
		}

	}

	@Override
	public void deleteData(String id) throws IOException
	{
	
		try {
			this.connectMongo();
			Mongo mdb = this.mongoConn.getConnection(); 
			GridFS gfs = new GridFS(mdb.getDB(this.database), this.collection);
			gfs.remove(id);
		}catch (UnknownHostException e) {
			this.logger.error("[File][GridFS] File with {} id not deleted", id);
			throw new IOException("Unknown host exception, Data not deleted");
		} catch (MongoException e) {
			this.logger.error("[File][GridFS] File with {} id not deleted", id);
			throw new IOException("Mongo exception, Data not deleted");
		}
		
	}

}
