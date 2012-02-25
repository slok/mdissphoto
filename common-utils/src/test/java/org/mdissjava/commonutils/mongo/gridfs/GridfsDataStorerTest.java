package org.mdissjava.commonutils.mongo.gridfs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

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
	
	@Test(expected=IllegalStateException.class)
	public void saveDataTestNull() throws IOException
	{
		GridfsDataStorer gds = new GridfsDataStorer("mdissphoto", "test");
		gds.saveData(null);	
	}
	
	@Test
	public void saveDataTestWithCustomName() throws IOException
	{
		GridfsDataStorer gds = new GridfsDataStorer("mdissphoto", "test");
		//load the file to store
		InputStream is = getClass().getResourceAsStream("/slackware.png");
		gds.saveData(is, "myCustomName");	
	}

	@Test
	public void retrieveData() throws IOException
	{
		GridfsDataStorer gds = new GridfsDataStorer("mdissphoto", "test");
		OutputStream baos = gds.getData("myCustomName");
		
		assert(((ByteArrayOutputStream)baos).size() != 0);
	}
	
	@Test(expected=IOException.class)
	public void retrieveMissingData() throws IOException
	{
		GridfsDataStorer gds = new GridfsDataStorer("mdissphoto", "test");
		OutputStream baos = gds.getData("D03sn'7 3x15t");
	}
	
	@Test
	public void sameInSameOut() throws IOException, NoSuchAlgorithmException
	{
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
