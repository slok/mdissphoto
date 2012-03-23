package org.mdissjava.thumbnailer.thumbnails;

import java.awt.image.BufferedImage;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Thumbnailer implements IThumbnailer {

	private int width = 0;
	private int height = 0;
	private BufferedImage img = null;
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public Thumbnailer(BufferedImage img) 
	{
		this.img = img;
		this.width = img.getWidth();
		this.height = img.getHeight();
		this.logger.info("Thumbnailer instance created");
	}
	
	@Override
	public BufferedImage thumbnailizeImageWithWidth(int width) throws IllegalArgumentException {
		return null;
	}

	@Override
	public BufferedImage thumbnailizeImageWithHeight(int height) throws IllegalArgumentException {
		return null;
	}

	@Override
	public BufferedImage thumbnailizeImageCropping(int pixels) throws IllegalArgumentException {
		return null;
	}

	@Override
	public BufferedImage autoThumbnailizeImage(int pixels, boolean crop) throws IllegalArgumentException {
		return null;
	}

	@Override
	public BufferedImage thumbnailizeImage(float scale) throws IllegalArgumentException {
		
		BufferedImage thumbnail = null;
		
		//if the scale is wrong then exception
		if (scale > 1.0f || scale < 0.0f)
		{
			this.logger.error("Can not continue, the scale needs to be between 0.0 adn 1.0");
			throw new IllegalArgumentException("The scale needs to be between 0.0 adn 1.0");
		}else
		{
			try 
			{
				//apply the power of thumbnailator!!
				thumbnail = Thumbnails.of(this.img).scale(scale).asBufferedImage();	
			} catch (IOException e) 
			{
				this.logger.error("Can not load Image for creating Thumbnail");
			}
			
			this.logger.info("Thumbnail created with scale: {}", scale);
			
			return thumbnail;			
		}
		
		
	}
	
	//Util methods
	
	private float guessScaleFromWidth(int widthScale)
	{
		return  (float)widthScale / (float)this.width;
	}
	
	private float guessScaleFromHeight(int heightScale)
	{
		return  (float)heightScale / (float)this.height;
	}

}
