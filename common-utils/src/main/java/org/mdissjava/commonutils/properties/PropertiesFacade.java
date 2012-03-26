package org.mdissjava.commonutils.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesFacade {
	
	private Map<String, String> PROPERTIES = null;
	
	public PropertiesFacade() {
		if(PROPERTIES == null)
		{
			PROPERTIES = new HashMap<String, String>();
			//add all properties
			PROPERTIES.put("thumbnails", "/thumbnail.properties");
		}
	}
	
	public Properties getProperties(String propertyKey) throws IllegalArgumentException, IOException
	{
		String propertiesFile = PROPERTIES.get(propertyKey);
		
		if(propertiesFile == null)
			throw new IllegalArgumentException("There isn't a property file assigned to that key"); 

		Properties properties = new Properties();
		InputStream is = getClass().getResourceAsStream(propertiesFile);
		properties.load(is);
		is.close();
		return properties;
	}
	

}
