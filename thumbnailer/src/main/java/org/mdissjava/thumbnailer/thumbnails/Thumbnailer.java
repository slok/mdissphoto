package org.mdissjava.thumbnailer.thumbnails;

import java.awt.image.BufferedImage;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

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
		
		//if is bigger than the picture then exception
		if(width >= this.width)
		{
			this.logger.error("Cannot scale an object with a size equal or higher than its size");
			throw new IllegalArgumentException("Cannot scale an object with a size equal or higher than its size");
			
		}else
		{
			float scaleInPercent = 0.0f;
			
			//create the apropiate scale and create the new image
			scaleInPercent = this.guessScaleFromWidth(width);
			
			this.logger.info("Thumbnail created with width: {}", width);
			
			return this.thumbnailizeImage(scaleInPercent);
		}
	}

	@Override
	public BufferedImage thumbnailizeImageWithHeight(int height) throws IllegalArgumentException {

		//if is bigger than the picture then exception
		if(height >= this.height)
		{
			this.logger.error("Cannot scale an object with a size equal or higher than its size");
			throw new IllegalArgumentException("Cannot scale an object with a size equal or higher than its size");
			
		}else
		{
			float scaleInPercent = 0.0f;
			
			//create the apropiate scale and create the new image
			scaleInPercent = this.guessScaleFromHeight(height);
			
			this.logger.info("Thumbnail created with height: {}", height);
			
			return this.thumbnailizeImage(scaleInPercent);
		}
	}

	@Override
	public BufferedImage thumbnailizeImageCropping(int pixels) throws IllegalArgumentException {
		
		//if is bigger than the picture then exception
		if(pixels >= this.width || pixels >= this.height)
		{
			this.logger.error("Cannot scale an object with a size equal or higher than its size");
			throw new IllegalArgumentException("Cannot scale an object with a size equal or higher than its size");
			
		}else
		{
			BufferedImage thumbnail = null;
			try 
			{
				thumbnail = Thumbnails.of(this.img).crop(Positions.CENTER).size(pixels, pixels).asBufferedImage();	
			} catch (IOException e) 
			{
				this.logger.error("Can not load Image for creating Thumbnail");
			}
			
			this.logger.info("Thumbnail created cropping: {}", pixels);
			return thumbnail;
		}
	}

	@Override
	public BufferedImage autoThumbnailizeImage(int pixels) throws IllegalArgumentException {
		
		BufferedImage scaledImage = null;
		
		//detect which scale mode we need, the side with the largest pixel amount  
		//is the one that will be applied the pixels in the argument, the other side will
		//be scaled in proportional mode
		if (this.width > this.height)
			scaledImage = this.thumbnailizeImageWithWidth(pixels);
		else
			scaledImage = this.thumbnailizeImageWithHeight(pixels);			
		
		return scaledImage;
	}

	@Override
	public BufferedImage thumbnailizeImage(float scale) throws IllegalArgumentException {
		
		BufferedImage thumbnail = null;
		
		//if the scale is wrong then exception
		if (scale > 1.0f || scale < 0.0f)
		{
			this.logger.error("Can not continue, the scale needs to be between 0.0 and 1.0");
			throw new IllegalArgumentException("The scale needs to be between 0.0 and 1.0");
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
