package org.mdissjava.commonutils.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesFacade {
	
	private static Map<String, String> PROPERTIES = null;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public PropertiesFacade() throws IOException {
		if(PROPERTIES == null)
		{
			PROPERTIES = new HashMap<String, String>();
			String pathOfPaths = "/paths.properties";
			
			//get the paths
			Properties properties = new Properties();
			InputStream is = getClass().getResourceAsStream(pathOfPaths);
			properties.load(is);
			is.close();
			
			//set all the paths to the map 
			@SuppressWarnings("rawtypes")
			Enumeration em = properties.keys();
			String key;
			while(em.hasMoreElements())
			{
				key = (String)em.nextElement();
				PROPERTIES.put(key, properties.getProperty(key));
			}
			
			this.logger.info("Loaded properties paths: {}", PROPERTIES);
		}
	}
	
	public Properties getProperties(String propertyKey) throws IllegalArgumentException, IOException
	{
		String propertiesFile = PROPERTIES.get(propertyKey);
		
		if(propertiesFile == null)
		{
			this.logger.error("There isn't a property file assigned to that key");
			throw new IllegalArgumentException("There isn't a property file assigned to that key"); 
		}
		Properties properties = new Properties();
		InputStream is = getClass().getResourceAsStream(propertiesFile);
		properties.load(is);
		is.close();
		this.logger.info("Loaded properties of '{}' with: {}", propertyKey, properties);
		return properties;
	}
	

}
