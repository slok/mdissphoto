package org.mdissjava.mdisscore.model.pojo;

import com.google.code.morphia.annotations.Embedded;

/**
 * Represents the resolution of a photo with the height and width in number of
 * pixels
 * 
 * @author mdiss_java
 */
@Embedded
public class Resolution {

	/** The height in number of pixels. */
	private Integer height;

	/** The width in number of pixels. */
	private Integer width;

	/**
	 * Gets the height.
	 * 
	 * @return the height
	 */
	public Integer getHeight() {
		return this.height;
	}

	/**
	 * Sets the height.
	 * 
	 * @param height
	 *            the new height
	 */
	public void setHeight(Integer height) {
		this.height = height;
	}

	/**
	 * Gets the width.
	 * 
	 * @return the width
	 */
	public Integer getWidth() {
		return this.width;
	}

	/**
	 * Sets the width.
	 * 
	 * @param width
	 *            the new width
	 */
	public void setWidth(Integer width) {
		this.width = width;
	}

	@Override
	public String toString() {
		return "" + this.width + "x" + this.height;
	}

}
