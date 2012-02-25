package org.mdissjava.commonutils.data;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 
 * This Interface has the common methods to store files. Could be of various types
 * Database storer, file storer, cache storer...
 * 
 * @author @author MDISS Java team 2011-2012 University of Deusto
 *
 */
public interface DataStorer 
{
	/**
	 * Saves some data that gets in a InputStream and returns a ID that has been generated
	 * for the stored file/data
	 * 
	 * @param data InputStream with the data to store
	 * @return Returns the id that has been auto generated by the method when the data/file has been stored 
	 */
	String saveData(InputStream data);
	
	/**
	 * Saves some data that gets in a InputStream and assigns the ID that has been passed through the
	 * id param
	 * 
	 * @param data InputStream with the data to store
	 * @param id The key that will be assigned to stored the data
	 */
	void saveData(InputStream data, String id);
	
	/**
	 * Get the data previously stored with the id that is passed in the param
	 * 
	 * @param id The identifier that identifies the stored data
	 * @return Returns the retrieved data in an outputStream
	 */
	OutputStream getData(String id);
	
	/**
	 * Deletes the previously stored data with the id that is passed in the param
	 * 
	 * @param id The identifier that identifies the stored data
	 */
	void deleteData(String id);

}
