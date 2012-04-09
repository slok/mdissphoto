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
	
	@Test (expected = IllegalArgumentException.class)
	public void ThumbnailScaleWrongTest() throws FileNotFoundException, IOException {
		final float SCALE = 1.5f;
		
		this.logger.info("[Test]Start ThumbnailScaleWrongTest()");
		this.imageData = this.loadFromResources("Iris.jpg");
		
		//create the thumbnail
		Thumbnailer thumbnailer = new Thumbnailer(this.imageData);
		thumbnailer.thumbnailizeImage(SCALE);

	}
	
	@Test
	public void ThumbnailWithWidthTest() throws FileNotFoundException, IOException {
		//2592x1944 -> 400x299
		final int WIDTH_MAX_PIXELS = 400;
		final int COMPARE = 299; 
		
		this.logger.info("[Test]Start ThumbnailWitWidthTest()");
		this.imageData = this.loadFromResources("Iris2.jpg");
		
		//create the thumbnail
		Thumbnailer thumbnailer = new Thumbnailer(this.imageData);
		BufferedImage thumbnailImg = thumbnailer.thumbnailizeImageWithWidth(WIDTH_MAX_PIXELS);

		Assert.assertEquals(COMPARE, thumbnailImg.getHeight());
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void ThumbnailWithWidthWrongTest() throws FileNotFoundException, IOException {
		//2592x1944 -> 400x299
		final int WIDTH_MAX_PIXELS = 4400;
		
		this.logger.info("[Test]Start ThumbnailWitWidthWrongTest()");
		this.imageData = this.loadFromResources("Iris2.jpg");
		
		//create the thumbnail
		Thumbnailer thumbnailer = new Thumbnailer(this.imageData);
		thumbnailer.thumbnailizeImageWithWidth(WIDTH_MAX_PIXELS);
	}
	
	@Test
	public void ThumbnailWithHeightTest() throws FileNotFoundException, IOException {
		//600x800 -> 300x400
		final int HEIGHT_MAX_PIXELS = 400;
		final int COMPARE = 300; 
		
		this.logger.info("[Test]Start ThumbnailWithHeightTest()");
		this.imageData = this.loadFromResources("Iris.jpg");
		
		//create the thumbnail
		Thumbnailer thumbnailer = new Thumbnailer(this.imageData);
		BufferedImage thumbnailImg = thumbnailer.thumbnailizeImageWithHeight(HEIGHT_MAX_PIXELS);

		Assert.assertEquals(COMPARE, thumbnailImg.getWidth());
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void ThumbnailWithHeightWrongTest() throws FileNotFoundException, IOException {
		//600x800 -> 300x400
		final int HEIGHT_MAX_PIXELS = 4400;
		
		this.logger.info("[Test]Start ThumbnailWithHeightWrongTest()");
		this.imageData = this.loadFromResources("Iris.jpg");
		
		//create the thumbnail
		Thumbnailer thumbnailer = new Thumbnailer(this.imageData);
		thumbnailer.thumbnailizeImageWithHeight(HEIGHT_MAX_PIXELS);
	}
	
	@Test
	public void ThumbnailWithCropTest() throws FileNotFoundException, IOException {
		//600x800 -> 400x400
		//2592x1944 -> 400x400
		final int SQUARE_MAX_PIXELS = 400;
	
		this.logger.info("[Test]Start ThumbnailWithCropTest()");
		
		//width image
		this.imageData = this.loadFromResources("Iris2.jpg");
		
		//create the thumbnail
		Thumbnailer thumbnailer = new Thumbnailer(this.imageData);
		BufferedImage thumbnailImg = thumbnailer.thumbnailizeImageCropping(SQUARE_MAX_PIXELS);

		Assert.assertEquals(SQUARE_MAX_PIXELS, thumbnailImg.getWidth());
		Assert.assertEquals(SQUARE_MAX_PIXELS, thumbnailImg.getHeight());
		
		//height image
		this.imageData = this.loadFromResources("Iris.jpg");
		
		//create the thumbnail
		thumbnailer = new Thumbnailer(this.imageData);
		thumbnailImg = thumbnailer.thumbnailizeImageCropping(SQUARE_MAX_PIXELS);

		Assert.assertEquals(SQUARE_MAX_PIXELS, thumbnailImg.getWidth());
		Assert.assertEquals(SQUARE_MAX_PIXELS, thumbnailImg.getHeight());
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void ThumbnailWithCropWrongTest() throws FileNotFoundException, IOException {
		//2592x1944 -> 400x400
		final int SQUARE_MAX_PIXELS = 4400;
	
		this.logger.info("[Test]Start ThumbnailWithCropWrongTest()");
		
		//width image
		this.imageData = this.loadFromResources("Iris2.jpg");
		
		//create the thumbnail
		Thumbnailer thumbnailer = new Thumbnailer(this.imageData);
		thumbnailer.thumbnailizeImageCropping(SQUARE_MAX_PIXELS);
	}
	
	@Test
	public void ThumbnailAutomaticTest() throws FileNotFoundException, IOException {

		
		final int SCALE_MAX_PIXELS = 400;
		final int COMPARE_W = 299; //Accurance of automatic scale with float?
		final int COMPARE_H = 300;
	
		this.logger.info("[Test]Start ThumbnailWithCropTest()");
		
		//width image 2592x1944 -> 400x299
		this.imageData = this.loadFromResources("Iris2.jpg");
		
		//create the thumbnail
		Thumbnailer thumbnailer = new Thumbnailer(this.imageData);
		BufferedImage thumbnailImg = thumbnailer.autoThumbnailizeImage(SCALE_MAX_PIXELS);

		Assert.assertEquals(COMPARE_W, thumbnailImg.getHeight());
		
		
		//height image 600x800 -> 300x400
		this.imageData = this.loadFromResources("Iris.jpg");
		
		//create the thumbnail
		thumbnailer = new Thumbnailer(this.imageData);
		thumbnailImg = thumbnailer.autoThumbnailizeImage(SCALE_MAX_PIXELS);

		Assert.assertEquals(COMPARE_H, thumbnailImg.getWidth());
	}
	
	
	private BufferedImage loadFromResources(String fileName) throws FileNotFoundException, IOException 
	{
		 InputStream is = getClass().getResourceAsStream("/" + fileName);
		 if (is == null)
			 throw new FileNotFoundException("Can't access to '/"+ fileName +"' file. Missing file or permission problems?");
		
		 return ImageIO.read(is);
	}

}
