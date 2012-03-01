package org.mdissjava.mdisscore.model.dao;

import java.net.UnknownHostException;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class DBManager {

	private static Mongo mongo;
	private static Datastore ds;	
	
	private static Datastore connect(){
		Datastore datastore = null;
		try {
			mongo = new Mongo( "localhost" , 27017 );
			datastore = new Morphia().createDatastore(mongo, "myDB");
			datastore.ensureIndexes();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}
		return datastore;
	}
	
	public static Datastore getInstance(){
		if (ds == null){
			ds = connect();
		}
		return ds;
	}
	

}
