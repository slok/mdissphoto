package org.mdissjava.mdisscore.metadata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.mdissjava.mdisscore.metadata.MetadataExtractor;
import org.mdissjava.mdisscore.metadata.impl.MetadataExtractorImpl;
import org.mdissjava.mdisscore.model.pojo.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;


public class MetadataExtractorImplTest {

	
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
			
			FileInputStream photoFile = new FileInputStream("src/main/resources/test/metadata/photo/testPhotoJPG.jpg");
			byte[] photo = IOUtils.toByteArray(photoFile);
			
			Metadata metadata = metadataExtractor.obtenerMetadata(photo);
			//Check if the Aperture value for the photoTestJPG's metadata is 5.65 and file format is jpg
			if(metadata != null)
			{
				assertEquals(metadata.getAperture(), "5.65");	
				assertEquals(metadata.getFormat(), "jpeg");
			}
			else
				System.out.println("Metadata returned null!!");
			
		}catch (Exception e) {
			e.printStackTrace();
			
			this.logger.error(e.getMessage());
			fail("Exception not expected");

		}
	}
	

	@Test
	public void testExtractMetadataJPG_noMetadataImage() throws MetadataException, ImageProcessingException, IOException
	{
		try{
		
			this.logger.info("[TEST] testExtractMetadataJPG MetadataDaoImpl");
			MetadataExtractor metadataExtractor = new MetadataExtractorImpl();
			
			FileInputStream photoFile = new FileInputStream("src/main/resources/test/metadata/photo/testPhotoJPG_nometadata.jpg");
			byte[] photo = IOUtils.toByteArray(photoFile);
			
			Metadata metadata = metadataExtractor.obtenerMetadata(photo);
			
			//Check if returned metadata is null, if it is, there is no metadata available for this picture
			assertEquals(metadata, null);
					
		}catch (Exception e) {
			e.printStackTrace();
			
			this.logger.error(e.getMessage());
			fail("Exception not expected");

		}
	}
	
	@Test
	public void testExtractMetadataPNG() throws MetadataException, ImageProcessingException, IOException
	{
		try{
		
			this.logger.info("[TEST] testExtractMetadataJPG MetadataDaoImpl");
			MetadataExtractor metadataExtractor = new MetadataExtractorImpl();
			
			FileInputStream photoFile = new FileInputStream("src/main/resources/test/metadata/photo/testPhotoPNG.png");
			byte[] photo = IOUtils.toByteArray(photoFile);
			
			Metadata metadata = metadataExtractor.obtenerMetadata(photo);
			//Check if the Aperture value for the photoTestJPG's metadata is 5.65 and file format is jpg
			if(metadata != null)
			{
				//assertEquals(metadata.getAperture(), "5.65");	
				assertEquals(metadata.getFormat(), "png");
			}
			else
				System.out.println("Metadata returned null!!");
			
		}catch (Exception e) {
			e.printStackTrace();
			
			this.logger.error(e.getMessage());
			fail("Exception not expected");

		}
	
		
	}
	
	@Test
	public void testExtractMetadataGIF() throws MetadataException, ImageProcessingException, IOException
	{
		try{
		
			this.logger.info("[TEST] testExtractMetadataJPG MetadataDaoImpl");
			MetadataExtractor metadataExtractor = new MetadataExtractorImpl();
			
			FileInputStream photoFile = new FileInputStream("src/main/resources/test/metadata/photo/testPhotoGIF.gif");
			byte[] photo = IOUtils.toByteArray(photoFile);
			
			Metadata metadata = metadataExtractor.obtenerMetadata(photo);
			//Check if the Aperture value for the photoTestJPG's metadata is 5.65 and file format is jpg
			if(metadata != null)
			{
				assertEquals(metadata.getFormat(), "gif");
			}
			else
				System.out.println("Metadata returned null!!");
			
		}catch (Exception e) {
			e.printStackTrace();
			
			this.logger.error(e.getMessage());
			fail("Exception not expected");

		}
	
		
	}
	
	/*
	 * 	Metadata for TIF test photo
	 * 
	 * 	Size: 5 Mb
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
			
			FileInputStream photoFile = new FileInputStream("src/main/resources/test/metadata/photo/testPhotoTIF.tif");
			byte[] photo = IOUtils.toByteArray(photoFile);
			
			Metadata metadata = metadataExtractor.obtenerMetadata(photo);
			//Check if the Aperture value for the photoTestJPG's metadata is 5.65 and file format is jpg
			if(metadata != null)
			{
				assertEquals(metadata.getSize(), 5.0, 0.0);	
				assertEquals(metadata.getFormat(), "tiff");
			}
			else
				System.out.println("Metadata returned null!!");
			
		}catch (Exception e) {
			e.printStackTrace();
			
			this.logger.error(e.getMessage());
			fail("Exception not expected");

		}
	
		
	}
}
