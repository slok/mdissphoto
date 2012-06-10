package org.mdissjava.commonutils.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

public class Utils {
	
	@SuppressWarnings("rawtypes")
	public static BufferedImage loadImageFromResources(Class c, String fileName) throws FileNotFoundException, IOException 
	{
		//InputStream is = c.getResourceAsStream("/" + fileName);
		InputStream is = c.getResourceAsStream(fileName);
		if (is == null)
			throw new FileNotFoundException("Can't access to '/"+ fileName +"' file. Missing file or permission problems?");
		
		 return ImageIO.read(is);
	}
	
	@SuppressWarnings("rawtypes")
	public static InputStream loadFileFromResources(Class c,  String fileName) throws FileNotFoundException, IOException 
	{
		 //InputStream is = c.getResourceAsStream("/" + fileName);
		 InputStream is = c.getResourceAsStream(fileName);
		 if (is == null)
			 throw new FileNotFoundException("Can't access to '/"+ fileName +"' file. Missing file or permission problems?");
		
		 return is;
	}
	
	public static void byteArrayOutputStreamToFile(ByteArrayOutputStream baos, String path) throws IOException
	{
		OutputStream outputStream = new FileOutputStream (path);
		baos.writeTo(outputStream);
	}
	
	public static void inputStreamToFile(InputStream is, String path) throws IOException
	{
		byte[] buf =new byte[1024];
		int len;
		File f = new File(path);
		
		OutputStream output = new FileOutputStream(f);
		
		while( (len = is.read(buf)) > 0){
			
			output.write(buf,0,len);
			
		}
		
		output.close();
		is.close();
	}
	
	public static ByteArrayInputStream byteArrayOutputStreamToByteArrayInputStream(ByteArrayOutputStream baos)
	{
		return new ByteArrayInputStream(baos.toByteArray());	
	}
	
	/**
	 * Splits the tags from a regular expression, if the regex is null then by default will split comma separated 
	 * 
	 * @param tags
	 * @return
	 */
	public static ArrayList<String> splitTags(String tags, String regex) 
	{

//		this.logger.debug("Splitting tags");
		if (regex == null)
			regex = "\\,";
		
		String[] splittedTags = tags.split(regex);

		ArrayList<String> tagList = new ArrayList<String>();
		for (String i:splittedTags)
			tagList.add(i);

		return tagList;
	}
	
	public static int getRandomNumInRange(int min, int max)
	{
		Random rand = new Random();

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt(max - min + 1) + min;
		return randomNum;
	}
	/**
	 * 
	 * 
	 * @param contentToEncode
	 * @param algorithm: 	MD2: 	RFC 1319
							MD5: 	RFC 1321
							SHA-1:	NIST FIPS 180-1.
							SHA-256:NIST FIPS 180-1. 
							SHA-384:NIST FIPS 180-1. 
							SHA-512:NIST FIPS 180-1.
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String calculateHash(String contentToEncode, String algorithm) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance(algorithm);
		digest.update(contentToEncode.getBytes());
		String result = new String(Base64.encodeBase64(digest.digest()));
		return result;
	}
	
}
