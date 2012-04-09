package org.mdissjava.commonutils.properties;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Properties;

import org.junit.Test;

public class PropertiesFacadeTest {

	@Test
	public void Propertiestest() throws IllegalArgumentException, IOException {
		PropertiesFacade pf = new PropertiesFacade();
		Properties p = pf.getProperties("thumbnails");
		assertEquals("square.30", p.getProperty("thumbnail.square.30px.bucket.name"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void PropertiesWrongtest() throws IllegalArgumentException, IOException {
		PropertiesFacade pf = new PropertiesFacade();
		pf.getProperties("noProperty");
	}

}
