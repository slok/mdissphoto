package org.mdissjava.commonutils.mongo.gridfs;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class GridfsDataStorerTest {

	final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Test
	public void saveDataTest() throws IOException
	{
		this.logger.info("[TEST][GridFS] saveDataTest()");
		GridfsDataStorer gds = new GridfsDataStorer("mdissphoto", "test");
		//load the file to store
		InputStream is = getClass().getResourceAsStream("/slackware.png");
		gds.saveData(is);	
	}
	
	@Test(expected=IOException.class)
	public void saveDataTestWrongConnection() throws IOException
	{
		this.logger.info("[TEST][GridFS] saveDataTestWrongConnection");
		GridfsDataStorer gds = new GridfsDataStorer("mdissphoto", "test","127.0.0.2", 80 );
		//load the file to store
		InputStream is = getClass().getResourceAsStream("/slackware.png");
		gds.saveData(is);	
	}
	
	@Test(expected=IllegalStateException.class)
	public void saveDataTestNull() throws IOException
	{
		this.logger.info("[TEST][GridFS] saveDataTestNull");
		
		GridfsDataStorer gds = new GridfsDataStorer("mdissphoto", "test");
		gds.saveData(null);	
	}
	
	@Test
	public void saveDataTestWithCustomName() throws IOException
	{
		this.logger.info("[TEST][GridFS] saveDataTestWithCustomName");
		
		GridfsDataStorer gds = new GridfsDataStorer("mdissphoto", "test");
		//load the file to store
		InputStream is = getClass().getResourceAsStream("/slackware.png");
		gds.saveData(is, "myCustomName");	
	}

	@Test
	public void retrieveData() throws IOException
	{
		this.logger.info("[TEST][GridFS] retrieveData");
		
		GridfsDataStorer gds = new GridfsDataStorer("mdissphoto", "test");
		OutputStream baos = gds.getData("myCustomName");
		
		assert(((ByteArrayOutputStream)baos).size() != 0);
	}
	
	@Test(expected=IOException.class)
	public void retrieveMissingData() throws IOException
	{
		this.logger.info("[TEST][GridFS] retrieveMissingData");
		
		GridfsDataStorer gds = new GridfsDataStorer("mdissphoto", "test");
		gds.getData("D03sn'7 3x15t");
	}
	
	@Test
	public void sameInSameOut() throws IOException, NoSuchAlgorithmException
	{
		this.logger.info("[TEST][GridFS] sameInSameOut");
		
		GridfsDataStorer gds = new GridfsDataStorer("mdissphoto", "test");

		InputStream is = getClass().getResourceAsStream("/slackware.png");
		gds.saveData(is, "myCustomName");	
		ByteArrayOutputStream baos = (ByteArrayOutputStream)gds.getData("myCustomName");
		
		//check
		//out image
		byte[] outBytes = baos.toByteArray();
		
		//in image
		InputStream isCheck = getClass().getResourceAsStream("/slackware.png");
		byte[] inBytes = this.InputStreamToBytes(isCheck);
		
		assertTrue(Arrays.equals(outBytes,inBytes));
	}
	
	@Test
	public void notSameInSameOut() throws IOException, NoSuchAlgorithmException
	{
		this.logger.info("[TEST][GridFS] notSameInSameOut");
		
		GridfsDataStorer gds = new GridfsDataStorer("mdissphoto", "test");

		InputStream is = getClass().getResourceAsStream("/slackware.png");
		gds.saveData(is, "myCustomName");	
		ByteArrayOutputStream baos = (ByteArrayOutputStream)gds.getData("myCustomName");
		
		//check
		//out image
		byte[] outBytes = baos.toByteArray();
		
		//in image
		byte[] inBytes = "A fake image".getBytes();
		
		assertFalse(Arrays.equals(outBytes,inBytes));
	}
	
	@Test(expected=IOException.class)
	public void deleteData() throws IOException
	{
		this.logger.info("[TEST][GridFS] deleteData");
		
		String id = "7h15 w1ll n07 574y much 71m3 1n d474b453";
		GridfsDataStorer gds = new GridfsDataStorer("mdissphoto", "test");
		//load the file to store
		InputStream is = getClass().getResourceAsStream("/slackware.png");
		
		gds.saveData(is, id);
		gds.getData(id);
		//delete and retrieve to test
		gds.deleteData(id);
		gds.getData(id);
		
	}
	/**
	 * Method taken from: http://stackoverflow.com/questions/1264709/convert-inputstream-to-byte-in-java
	 */
	private byte[] InputStreamToBytes(InputStream is) throws IOException
	{
		int nRead;
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		byte[] data = new byte[16384];

		while ((nRead = is.read(data, 0, data.length)) != -1) {
		  buffer.write(data, 0, nRead);
		}

		buffer.flush();
		return  buffer.toByteArray();
	}
	
}
