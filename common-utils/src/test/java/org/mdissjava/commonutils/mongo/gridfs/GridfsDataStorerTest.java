package org.mdissjava.commonutils.mongo.gridfs;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;



public class GridfsDataStorerTest {

	@Test
	public void saveDataTest() throws IOException
	{
		GridfsDataStorer gds = new GridfsDataStorer("mdissphoto", "test");
		//load the file to store
		InputStream is = getClass().getResourceAsStream("/slackware.png");
		gds.saveData(is);	
	}
	
	@Test(expected=IOException.class)
	public void saveDataTestWrongConnection() throws IOException
	{
		GridfsDataStorer gds = new GridfsDataStorer("mdissphoto", "test","127.0.0.2", 80 );
		//load the file to store
		InputStream is = getClass().getResourceAsStream("/slackware.png");
		gds.saveData(is);	
	}

}
