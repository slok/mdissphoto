package org.mdissjava.mdisscore.model.dao.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.bson.types.ObjectId;
import org.mdissjava.mdisscore.model.dao.MetadataDao;
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

public class MetadataDaoImpl implements MetadataDao {
	
	@Override
	public Metadata obtenerMetadata(FileInputStream photo, String format) throws MetadataException, ImageProcessingException, IOException {
		
		if((format.equals("jpg"))||(format.equals("tif")))
		{
			Metadata metadata = new Metadata();
			Resolution resolution = new Resolution();
			
			//TODO Improve size extraction
			metadata.setSize((float)photo.available());
			
			BufferedInputStream bufferedPhoto = new BufferedInputStream(photo);
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
			
			return metadata;
			
		}
		else
		{
			System.out.println("Metadata not supported for the format: "+format);
			return null;
		}
		
			
	}

	//TODO Improve getExtension (Using metadata)
	@Override
	public String getExtension(File f)
	{
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1)
			
			ext = s.substring(i+1).toLowerCase();

		if(ext == null)
			return "";
		
		return ext;
	}
}
