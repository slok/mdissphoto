package org.mdissjava.notifier.event;

import java.util.Date;

public class PhotoUploadedEvent extends MdissEvent{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2110626049535062368L;
	
	private String userNick;
	private String photoId;
	private Date eventDate; //this is for not checking the photo date (we don't need to retrieve the data)
	
	public PhotoUploadedEvent(String userNick, String photoId) {
		super("photoUploaded");
		this.userNick = userNick;
		this.photoId = photoId;
		this.eventDate = new Date(); 
	}

	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	public String getPhotoId() {
		return photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	
	
	

}
