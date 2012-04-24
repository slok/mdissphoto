package org.mdissjava.mdisscore.metadata.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.bson.types.ObjectId;
import org.mdissjava.mdisscore.metadata.MetadataExtractor;
import org.mdissjava.mdisscore.model.pojo.Metadata;
import org.mdissjava.mdisscore.model.pojo.Photo;
import org.mdissjava.mdisscore.model.pojo.Resolution;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.mapping.Mapper;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

import eu.medsea.mimeutil.MimeUtil;

public class MetadataExtractorImpl implements MetadataExtractor {
	
	@Override
	public Metadata obtenerMetadata(byte[] photo) throws MetadataException, ImageProcessingException, IOException {
		
		MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
        Collection<?> mimeTypes = MimeUtil.getMimeTypes(photo);
        String format = getOnlyExtension(mimeTypes.toString());
		
		if((format.equals("jpeg"))||(format.equals("tiff")))
		{
			Metadata metadata = new Metadata();
			Resolution resolution = new Resolution();
						
			ByteArrayInputStream photoInputStream = new ByteArrayInputStream(photo);
			BufferedInputStream bufferedPhoto = new BufferedInputStream(photoInputStream);
			
			com.drew.metadata.Metadata metadataFoto = ImageMetadataReader.readMetadata(bufferedPhoto, true);
		       
			ExifIFD0Directory exifIFD0Directory = metadataFoto.getDirectory(ExifIFD0Directory.class);
			ExifSubIFDDirectory exifSubIFDirectory = metadataFoto.getDirectory(ExifSubIFDDirectory.class);
			
			metadata.setShutterSpeed(exifSubIFDirectory.getString(ExifSubIFDDirectory.TAG_SHUTTER_SPEED));
			metadata.setAperture(exifSubIFDirectory.getString(ExifSubIFDDirectory.TAG_APERTURE));
			metadata.setFocalLength(exifSubIFDirectory.getInt(ExifSubIFDDirectory.TAG_FOCAL_LENGTH));
			metadata.setISOSpeed(exifSubIFDirectory.getInt(ExifSubIFDDirectory.TAG_ISO_EQUIVALENT));
			resolution.setHeight(exifIFD0Directory.getInt(ExifIFD0Directory.TAG_Y_RESOLUTION));
			resolution.setWidth(exifIFD0Directory.getInt(ExifIFD0Directory.TAG_X_RESOLUTION));
			metadata.setResolution(resolution);
			metadata.setDateTaken(exifIFD0Directory.getDate(exifIFD0Directory.TAG_DATETIME));
			metadata.setFormat(format);
			metadata.setSize((float)bytesToMb(photo.length));
			
			return metadata;
			
		}
		else
		{
			System.out.println("Metadata not supported for the format: "+format);
			return null;
		}
		
			
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
	 
	 @Override
	 public long bytesToMb(int bytes) {
	  return bytes / MEGABYTE ;
	 }
}

