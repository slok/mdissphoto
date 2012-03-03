package org.mdissjava.commonutils.mongo.morphia;

import java.util.ArrayList;

import org.junit.Test;

import com.google.code.morphia.Datastore;

public class MorphiaDatastoreConnectionTest {

	@Test
	public void GetConnectionDefaultTest() {
		
		// Create the mapping classes array
		@SuppressWarnings("rawtypes")
		ArrayList<Class> classes = new ArrayList<Class>();
		classes.add(MyEntity.class);
		
		// Create a test object
		MyEntity me = new MyEntity();
		me.setName("test MyEntity");
		
		// Test Morphia connection with the mdc
		MorphiaDatastoreConnection mdc = MorphiaDatastoreConnection.getInstance();
		mdc.connect("test", classes);
		Datastore ds = mdc.getDatastore();
		
		ds.save(me);
		
	}
	
	@Test
	public void GetConnectionCustomTest() {
		
		// Create the mapping classes array
		@SuppressWarnings("rawtypes")
		ArrayList<Class> classes = new ArrayList<Class>();
		classes.add(MyEntity.class);
		
		// Create a test object
		MyEntity me = new MyEntity();
		me.setName("prueba2");
		
		// Test Morphia connection with the mdc
		MorphiaDatastoreConnection mdc = MorphiaDatastoreConnection.getInstance();
		mdc.connect("127.0.0.1", 27017, "test", classes);
		Datastore ds = mdc.getDatastore();
		
		ds.save(me);
		
	}
	
	@Test(expected=IllegalStateException.class)
	public void GetWrongConnectionTest() {
		
		// Create the mapping classes array
		@SuppressWarnings("rawtypes")
		ArrayList<Class> classes = new ArrayList<Class>();
		classes.add(MyEntity.class);
		
		// Create a test object
		MyEntity me = new MyEntity();
		me.setName("prueba3");
		
		// Test Morphia connection with the mdc
		MorphiaDatastoreConnection mdc = MorphiaDatastoreConnection.getInstance();
		mdc.releaseInstance();
		mdc = MorphiaDatastoreConnection.getInstance();
		Datastore ds = mdc.getDatastore();
		
		ds.save(me);
		
	}

}
