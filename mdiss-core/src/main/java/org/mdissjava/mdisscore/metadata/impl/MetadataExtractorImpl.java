package org.mdissjava.mdisscore.metadata.impl;


import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.mdissjava.mdisscore.metadata.MetadataExtractor;
import org.mdissjava.mdisscore.metadata.SimpleImageInfo;
import org.mdissjava.mdisscore.model.pojo.Camera;
import org.mdissjava.mdisscore.model.pojo.Metadata;
import org.mdissjava.mdisscore.model.pojo.Resolution;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;

public class MetadataExtractorImpl implements MetadataExtractor {
	
	private byte[] photo = null;
	private SimpleImageInfo simpleImage = null;
	
	@Override
	public Metadata obtenerMetadata(byte[] photo) throws MetadataException, ImageProcessingException, IOException {
		
		this.photo = photo;
		this.simpleImage = new SimpleImageInfo(photo);
        String format = getOnlyExtension(simpleImage.getMimeType());
        
		if((format.equals("jpeg"))||(format.equals("tiff")))
		{
			return getEXIFMetadata(format);
			
		}
		else if((format.equals("png"))||(format.equals("gif")))
		{
			return getBasicMetadata(format);
			
		}
		else
		{
			System.out.println("EXIF metadata not supported for the format: "+format);
			return getBasicMetadata(format);
		}
		
			
	}
	
	@Override
	public Metadata getEXIFMetadata(String format) {

		Metadata metadata = new Metadata();
		Resolution resolutionPPI = new Resolution();
					
		ByteArrayInputStream photoInputStream = new ByteArrayInputStream(photo);
		BufferedInputStream bufferedPhoto = new BufferedInputStream(photoInputStream);
		
		try{
		
			
			com.drew.metadata.Metadata metadataFoto = ImageMetadataReader.readMetadata(bufferedPhoto, true);
			
			Resolution resolutionREAL = new Resolution();
			resolutionREAL.setHeight(this.simpleImage.getHeight());
			resolutionREAL.setWidth(this.simpleImage.getWidth());	

			ExifIFD0Directory exifIFD0Directory = metadataFoto.getDirectory(ExifIFD0Directory.class);
			ExifSubIFDDirectory exifSubIFDirectory = metadataFoto.getDirectory(ExifSubIFDDirectory.class);
					
			metadata.setShutterSpeed(exifSubIFDirectory.getString(ExifSubIFDDirectory.TAG_SHUTTER_SPEED));
			metadata.setAperture(exifSubIFDirectory.getString(ExifSubIFDDirectory.TAG_APERTURE));
			metadata.setFocalLength(exifSubIFDirectory.getInt(ExifSubIFDDirectory.TAG_FOCAL_LENGTH));
			metadata.setISOSpeed(exifSubIFDirectory.getInt(ExifSubIFDDirectory.TAG_ISO_EQUIVALENT));
			resolutionPPI.setHeight(exifIFD0Directory.getInt(ExifIFD0Directory.TAG_Y_RESOLUTION));
			resolutionPPI.setWidth(exifIFD0Directory.getInt(ExifIFD0Directory.TAG_X_RESOLUTION));
			metadata.setResolutionPPI(resolutionPPI);
			metadata.setResolutionREAL(resolutionREAL);
			metadata.setDateTaken(exifIFD0Directory.getDate(ExifIFD0Directory.TAG_DATETIME));
			metadata.setFormat(format);
			metadata.setSize((float)bytesToMb(photo.length));
			
			return metadata;
		}
		catch(NullPointerException npe)
		{
			//npe.printStackTrace();
			return getBasicMetadata(format);
		}
		catch(ImageProcessingException ipe){
			//ipe.printStackTrace();
			return getBasicMetadata(format);
		}	
		catch(IOException io){
			//io.getStackTrace();
			return getBasicMetadata(format);
		}
	    catch(MetadataException me)
	    {
	    	System.out.println("No metadata available");
	    	me.getStackTrace();
	    	return getBasicMetadata(format);
	    }
		
	}


	//TODO How to store if photo size is in kb or mb. New String field or convert size to string.
	
	@Override
	public Metadata getBasicMetadata(String format) {
		
		Metadata metadata = new Metadata();
		Resolution resolution = new Resolution();
		
		resolution.setWidth(this.simpleImage.getWidth());
		resolution.setHeight(this.simpleImage.getHeight());
				
		metadata.setResolutionREAL(resolution);
		metadata.setFormat(format);
		metadata.setSize((float)bytesToMb(photo.length));
		
		System.out.println(metadata.getSize());
		return metadata;
	}

	@Override
	public String getOnlyExtension(String type) {
		String ext = null;
		
		int i = type.lastIndexOf('/');

		if (i > 0 && i < type.length() - 1)
			
			ext = type.substring(i+1).toLowerCase();

		if(ext == null)
			return "";
		
		return ext;
	}
	
	 private static final long  MEGABYTE = 1024L * 1024L;
	 private static final long KBYTE = 1024L;
	 
	 @Override
	 public long bytesToMb(int bytes) {
	  return bytes / MEGABYTE ;
	 }
	 
	 @Override
	 public long bytesToKb(int bytes) {
	  return bytes / KBYTE ;
	 }

	@Override
	public Map<String, String> getMetadataFormatted(Metadata metadata) {

		Map<String, String> metadataMap = new HashMap<String, String>();
		
		String aperture = metadata.getAperture();
		Camera cam = metadata.getCamera();
		Date date = metadata.getDateTaken();
		int focal = metadata.getFocalLength();
		String format = metadata.getFormat();
		int isoSpeed = metadata.getISOSpeed();
		Resolution resolutionPPI = metadata.getResolutionPPI();
		Resolution resolutionReal = metadata.getResolutionREAL();
		//int sensorSize = metadata.getSensorSize();
		String shutterSpeed = metadata.getShutterSpeed();
		Float size = metadata.getSize();
		
		if(aperture != null)
			metadataMap.put("Aperture", aperture);
		if(cam != null)
			metadataMap.put("map", cam.getBrand() +"-"+ cam.getId());
		if(date != null)
			metadataMap.put("Date taken", date.toString());
		if(focal != 0)
			metadataMap.put("Focal length", String.valueOf(focal));
		if(format != null)
			metadataMap.put("Original format", format);
		if(isoSpeed != 0)
			metadataMap.put("ISO speed", String.valueOf(isoSpeed));
		if(resolutionPPI != null)
			metadataMap.put("Resolution PPI", resolutionPPI.getWidth()+"x"+resolutionPPI.getWidth());
		if(resolutionReal != null)
			metadataMap.put("Resolution", resolutionReal.getWidth()+"x"+resolutionReal.getWidth());
		//if(sensorSize != 0)
		//	metadataMap.put("Sensor size", String.valueOf(size));
		if(shutterSpeed != null)
			metadataMap.put("Shutter speed", shutterSpeed);
		if(size != 0)
			metadataMap.put("Original size", String.valueOf(size)+" MB");
		
		return metadataMap;

	}
}

