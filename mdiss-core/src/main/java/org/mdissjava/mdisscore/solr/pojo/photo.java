package org.mdissjava.mdisscore.solr.pojo;

import org.apache.solr.client.solrj.beans.Field;

import com.google.code.morphia.annotations.Entity;

@Entity
public class photo {

	@Field
	private String photoId;
	
	@Field
	private String albumId;
	
	@Field
	private String titleAlbum;

	@Field
	private String userNick;
	
	@Field
	private boolean publicPhoto;

	@Field
	private String titleFoto;	
		
	@Field 
	private boolean plus18;
	
	@Field
	private String[] tags;
	
	//metadata fields
	@Field
	private String brand;
	
	@Field
	private String model;
	
	@Field
	private String shutterSpeed;
	
	@Field
	private String aperture;
	
	@Field
	private int focalLength;
	
	@Field
	private int ISOSpeed;
	
	@Field
	private int sensorSize;
	
	//end metadata fields

	
	public String getTitleFoto() {
		return titleFoto;
	}

	public void setTitleFoto(String titleFoto) {
		this.titleFoto = titleFoto;
	}

	public String getTitleAlbum() {
		return titleAlbum;
	}

	public void setTitleAlbum(String titleAlbum) {
		this.titleAlbum = titleAlbum;
	}

	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public boolean isPublicPhoto() {
		return publicPhoto;
	}

	public void setPublicPhoto(boolean publicPhoto) {
		this.publicPhoto = publicPhoto;
	}

	public boolean isPlus18() {
		return plus18;
	}

	public void setPlus18(boolean plus18) {
		this.plus18 = plus18;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getShutterSpeed() {
		return shutterSpeed;
	}

	public void setShutterSpeed(String shutterSpeed) {
		this.shutterSpeed = shutterSpeed;
	}

	public String getAperture() {
		return aperture;
	}

	public void setAperture(String aperture) {
		this.aperture = aperture;
	}

	public int getFocalLength() {
		return focalLength;
	}

	public void setFocalLength(int focalLength) {
		this.focalLength = focalLength;
	}

	public int getISOSpeed() {
		return ISOSpeed;
	}

	public void setISOSpeed(int iSOSpeed) {
		ISOSpeed = iSOSpeed;
	}

	public int getSensorSize() {
		return sensorSize;
	}

	public void setSensorSize(int sensorSize) {
		this.sensorSize = sensorSize;
	}

	public String getPhotoId() {
		return photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	public String getAlbumId() {
		return albumId;
	}

	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}
}
