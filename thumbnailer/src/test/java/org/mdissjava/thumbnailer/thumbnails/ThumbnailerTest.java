package org.mdissjava.thumbnailer.thumbnails;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import junit.framework.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThumbnailerTest {

	private BufferedImage imageData = null;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Test
	public void ThumbnailScaleTest() throws FileNotFoundException, IOException {
		//600x800 -> 300x400
		final float SCALE = 0.5f;
		final int WIDTH = 300;
		final int HEIGHT = 400;
		
		this.logger.info("[Test]Start ThumbnailScaleTest()");
		this.imageData = this.loadFromResources("Iris.jpg");
		
		//create the thumbnail
		Thumbnailer thumbnailer = new Thumbnailer(this.imageData);
		BufferedImage thumbnailImg = thumbnailer.thumbnailizeImage(SCALE);

		Assert.assertEquals(WIDTH, thumbnailImg.getWidth());
		Assert.assertEquals(HEIGHT, thumbnailImg.getHeight());
	}
	
	private BufferedImage loadFromResources(String fileName) throws FileNotFoundException, IOException 
	{
		 InputStream is = getClass().getResourceAsStream("/" + fileName);
		 if (is == null)
			 throw new FileNotFoundException("Can't access to '/"+ fileName +"' file. Missing file or permission problems?");
		
		 return ImageIO.read(is);
	}

}
