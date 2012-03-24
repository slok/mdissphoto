package org.mdissjava.thumbnailer.thumbnails;

import java.awt.image.BufferedImage;

/**
 * This Interface wraps utils to make different type of thumbnails
 * 
 * @author slok
 *
 */
public interface IThumbnailer 
{
	
	/**
	 * Creates a thumbnail based on the width in pixels. For example if width is 300px 
	 * then we guess the height for a good aspect ratio.
	 * 
	 * @param width is the top of pixels in the horizontal side, the v side will be proportional
	 * @return The thumbnail is returned
	 * @throws IllegalArgumentException Is thrown if the value passed isn't a good resolution 
	 * to execute the action of minimize, for example if the image is smaller than the resolution to
	 * minimize
	 */
	BufferedImage thumbnailizeImageWithWidth(int width) throws IllegalArgumentException;
	
	/**
	 * Creates a thumbnail based on the height in pixels. For example if height is 300px 
	 * then we guess the width for a good aspect ratio.
	 * 
	 * @param height is the top of pixels in the vertical side, the horizontal side will be proportional
	 * @return The thumbnail is returned
	 * @throws IllegalArgumentException Is thrown if the value passed isn't a good resolution 
	 * to execute the action of minimize, for example if the image is smaller than the resolution to
	 * minimize
	 */
	BufferedImage thumbnailizeImageWithHeight(int height) throws IllegalArgumentException;
	
	/**
	 * This mwthod will make a thumbnail cropping the image id the bounds aren't inside the pixels
	 * param square
	 * 
	 * @param pixels is the max size for both sides (square)
	 * @return The thumbnail is returned
	 * @throws IllegalArgumentException Is thrown if the value passed isn't a good resolution 
	 * to execute the action of minimize, for example if the image is smaller than the resolution to
	 * minimize
	 */
	BufferedImage thumbnailizeImageCropping(int pixels) throws IllegalArgumentException;
	
	/**
	 * Creates a thumbnail based on the width or height (only one) in pixels. This is calculated 
	 * automatically if the image is vertically or horizontally based. For example if an image is 
	 * 3000x1000 then the 1024 resize will be 1024x????, if the image is 1000x3000 then the 1024
	 * resize will be ????x1024. ALWAYS: [Width]x[Height] = horizontal x vertical 
	 * 
	 * @param pixels are the pixels in width or height to scale
	 * @return The thumbnail is returned
	 * @throws IllegalArgumentException Is thrown if the value passed isn't a good resolution 
	 * to execute the action of minimize, for example if the image is smaller than the resolution to
	 * minimize
	 */
	BufferedImage autoThumbnailizeImage(int pixels) throws IllegalArgumentException;
	
	/**
	 * 
	 * Creates a thumbnail based on the scale in percent. For example
	 * a 800x600 image with 0.5f scale will create a 400x300 thumbnail
	 * 
	 * @param scale in float form (50% = 0.5f)
	 * @return The thumbnail is returned
	 * @throws IllegalArgumentException Is thrown if the value passed isn't between 1.0 and 0.0 
	 */
	BufferedImage thumbnailizeImage(float scale) throws IllegalArgumentException;;
	
}
