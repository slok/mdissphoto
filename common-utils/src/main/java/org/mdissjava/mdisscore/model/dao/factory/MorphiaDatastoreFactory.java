package org.mdissjava.mdisscore.model.dao.factory;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.mdissjava.commonutils.mongo.morphia.MorphiaDatastoreConnection;
import org.mdissjava.commonutils.properties.PropertiesFacade;

import com.google.code.morphia.Datastore;

/**
 * This Factory automates the Morphia Datastore process 
 * 
 * @author MDISS Java team 2011-2012 University of Deusto
 *
 */
public class MorphiaDatastoreFactory {
	
	private static final String MORPHIA_CLASSES_PROPERTIES = "morphia-classes";

	/**
	 * Gets the dataStore from a given database
	 * 
	 * @param database is the database where the database will be set
	 * @return the datastore from Morphia
	 */
	@SuppressWarnings("rawtypes")
	public static Datastore getDatastore(String database) {
		
		ArrayList<Class> classes = (ArrayList<Class>)getClasses();
		
		MorphiaDatastoreConnection mdc = MorphiaDatastoreConnection.getInstance();
		mdc.connect("127.0.0.1", 27017, database, classes);
		return mdc.getDatastore();
		
	}
	
	/**
	 * Gets the dataStore from a given database
	 * 
	 * @param database is the database where the database will be set
	 * @param classes is a list of the classes that morphia will map
	 * @return the datastore from Morphia
	 */
	@SuppressWarnings("rawtypes")
	public static Datastore getDatastore(String database, List<Class> classes) {
		
		MorphiaDatastoreConnection mdc = MorphiaDatastoreConnection.getInstance();
		mdc.connect("127.0.0.1", 27017, database, classes);
		return mdc.getDatastore();
		
	}
	
	/**
	 * Sets the classes to map in the Morphia instance (loading from morphia-classes.properties)
	 * 
	 * @return the list of classes to map in Morphia
	 */
	@SuppressWarnings("rawtypes")
	private static List<Class> getClasses() {
	
		ArrayList<Class> classes = new ArrayList<Class>();
		try {
			Properties properties;
			String key;
			properties = new PropertiesFacade().getProperties(MORPHIA_CLASSES_PROPERTIES);
			Enumeration em = properties.keys();
			
			while(em.hasMoreElements())
			{
				key = (String)em.nextElement();
				
				//use reflection to load the clazzes
				String clazz = properties.getProperty(key);
				classes.add(Class.forName(clazz));
			}
		} catch (Exception e) {
			throw new IllegalStateException("Error loading morphia classes: "+ e.toString());
		}

		
        return classes;
		
	}

}
