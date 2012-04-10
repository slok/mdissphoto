package org.mdissjava.mdisscore.model.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mdissjava.mdisscore.metadata.MetadataExtractor;
import org.mdissjava.mdisscore.metadata.impl.MetadataExtractorImpl;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.pojo.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;
import com.google.code.morphia.Datastore;

public class MetadataDaoImplTest {

	
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/*
	 * 	Metadata for JPG test photo
	 * 
	 * 	ShutterSpeed: 7
	 *	Aperture: 5.65
	 *	Focal Length: 6
	 *	ISO: 64
	 *	Y Resolution: 72
	 *	X Resolution: 72
	 *	Datetime: Sat Apr 23 18:26:27 CEST 2011
	 */
	
	@Test
	public void testExtractMetadataJPG() throws MetadataException, ImageProcessingException, IOException
	{
		try{
		
			this.logger.info("[TEST] testExtractMetadataJPG MetadataDaoImpl");
			MetadataExtractor metadataExtractor = new MetadataExtractorImpl();
			
			//TODO Improve file extension extraction
			File photo = new File("src/main/resources/test/metadata/photo/testPhotoJPG.jpg");
			String format = metadataExtractor.getExtension(photo);
			assertEquals(format, "jpg");
		
			FileInputStream filePhoto = new FileInputStream(photo);
			
			Metadata metadata = metadataExtractor.obtenerMetadata(filePhoto, format);
			
			//Check if the Aperture value for the photoTestJPG's metadata is 5.65 and file format is jpg
			assertEquals(metadata.getAperture(), "5.65");	
			assertEquals(metadata.getFormat(), "jpg");

		}catch (Exception e) {
			
			this.logger.error(e.getMessage());
			fail("Exception not expected");

		}
	
		
	}


	/*
	 * 	Metadata for TIF test photo
	 * 
	 * 	Size: 6242922
	 *	ShutterSpeed: null
	 *	Aperture: null
	 *	Focal Length: 15
	 *	ISO: 160	
	 *	Y Resolution: 180
	 *	X Resolution: 180
	 *	Datetime: Fri Mar 18 12:06:22 CET 2011
	 *
	 */
	
	@Test
	public void testExtractMetadataTIFF() throws MetadataException, ImageProcessingException, IOException
	{
		try{
			
			this.logger.info("[TEST] testExtractMetadataJPG MetadataDaoImpl");
			MetadataExtractor metadataExtractor = new MetadataExtractorImpl();
			
			//TODO Improve file extension extraction
			File photo = new File("src/main/resources/test/metadata/photo/testPhotoTIF.tif");
			String format = metadataExtractor.getExtension(photo);
			assertEquals(format, "tif");
		
			FileInputStream filePhoto = new FileInputStream(photo);
			
			Metadata metadata = metadataExtractor.obtenerMetadata(filePhoto, format);
			
			assertEquals(metadata.getSize(), 6242922, 0.0);
			assertEquals(metadata.getFormat(), "tif");
			//Check if the Aperture value for the photoTestJPG's metadata is 5.65
			//assertEquals(metadata.getAperture(), "5.65");	
			
		}catch (Exception e) {
			
			this.logger.error(e.getMessage());
			fail("Exception not expected");

		}
	
		
	}
}
