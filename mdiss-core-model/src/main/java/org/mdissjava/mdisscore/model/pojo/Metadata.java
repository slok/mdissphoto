package org.mdissjava.mdisscore.model.pojo;

import java.util.Date;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Reference;

/**
 * Represents all of the metadata of a single photo.
 * 
 * @author mdiss_java
 */
@Embedded
public class Metadata {
	
	/** The camera which took the photo */
	@Reference
	private Camera camera;

	/** The shutter speed in seconds. */
	private String shutterSpeed;

	/** The aperture. */
	private String aperture;

	/** The focal length in millimeters. */
	private Integer focalLength;

	/** The ISO speed. */
	private Integer ISOSpeed;

	/** The sensor size. */
	private Integer sensorSize;

	/** The resolution provided by EXIF metadata */
	@Embedded
	private Resolution resolutionPPI;
	
	/** The resolution provided by BufferedImage */
	@Embedded
	private Resolution resolutionREAL;
	
	/** The size in KiloBytes. */
	private Float size;

	/** The date taken. */
	private Date dateTaken;

	/** The format of the file (Ex. JPG, BMP, PNG...). */
	private String format;
	
	/**
	 * Gets the camera.
	 * 
	 * @return the camera
	 */
	public Camera getCamera() {
		return this.camera;
	}

	/**
	 * Sets the camera.
	 * 
	 * @param camera
	 *            the new camera
	 */
	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	/**
	 * Gets the shutter speed.
	 * 
	 * @return the shutter speed
	 */
	public String getShutterSpeed() {
		return this.shutterSpeed;
	}

	/**
	 * Sets the shutter speed.
	 * 
	 * @param shutterSpeed
	 *            the new shutter speed
	 */
	public void setShutterSpeed(String shutterSpeed) {
		this.shutterSpeed = shutterSpeed;
	}

	/**
	 * Gets the aperture.
	 * 
	 * @return the aperture
	 */
	public String getAperture() {
		return this.aperture;
	}

	/**
	 * Sets the aperture.
	 * 
	 * @param aperture
	 *            the new aperture
	 */
	public void setAperture(String aperture) {
		this.aperture = aperture;
	}

	/**
	 * Gets the focal length.
	 * 
	 * @return the focal length
	 */
	public Integer getFocalLength() {
		return this.focalLength;
	}

	/**
	 * Sets the focal length.
	 * 
	 * @param focalLength
	 *            the new focal length
	 */
	public void setFocalLength(Integer focalLength) {
		this.focalLength = focalLength;
	}

	/**
	 * Gets the iSO speed.
	 * 
	 * @return the iSO speed
	 */
	public Integer getISOSpeed() {
		return this.ISOSpeed;
	}

	/**
	 * Sets the iSO speed.
	 * 
	 * @param iSOSpeed
	 *            the new iSO speed
	 */
	public void setISOSpeed(Integer iSOSpeed) {
		this.ISOSpeed = iSOSpeed;
	}

	/**
	 * Gets the sensor size.
	 * 
	 * @return the sensor size
	 */
	public Integer getSensorSize() {
		return this.sensorSize;
	}

	/**
	 * Sets the sensor size.
	 * 
	 * @param sensorSize
	 *            the new sensor size
	 */
	public void setSensorSize(Integer sensorSize) {
		this.sensorSize = sensorSize;
	}

	/**
	 * Gets the resolution.
	 * 
	 * @return the resolution
	 */
	public Resolution getResolutionPPI() {
		return this.resolutionPPI;
	}

	public Resolution getResolutionREAL() {
		return this.resolutionREAL;
	}
	/**
	 * Sets the resolution.
	 * 
	 * @param resolution
	 *            the new resolution
	 */
	public void setResolutionPPI(Resolution resolution) {
		this.resolutionPPI = resolution;
	}
	public void setResolutionREAL(Resolution resolution) {
		this.resolutionREAL = resolution;
	}
	/**
	 * Gets the size.
	 * 
	 * @return the size
	 */
	public Float getSize() {
		return this.size;
	}

	/**
	 * Sets the size.
	 * 
	 * @param size
	 *            the new size
	 */
	public void setSize(Float size) {
		this.size = size;
	}

	/**
	 * Gets the date taken.
	 * 
	 * @return the date taken
	 */
	public Date getDateTaken() {
		return this.dateTaken;
	}

	/**
	 * Sets the date taken.
	 * 
	 * @param dateTaken
	 *            the new date taken
	 */
	public void setDateTaken(Date dateTaken) {
		this.dateTaken = dateTaken;
	}

	/**
	 * Gets the format.
	 * 
	 * @return the format
	 */
	public String getFormat() {
		return this.format;
	}

	/**
	 * Sets the format.
	 * 
	 * @param format
	 *            the new format
	 */
	public void setFormat(String format) {
		this.format = format;
	}

}
