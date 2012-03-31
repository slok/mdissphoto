package org.mdissjava.commonutils.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

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
	

}
