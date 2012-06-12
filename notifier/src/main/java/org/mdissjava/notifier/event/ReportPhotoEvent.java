package org.mdissjava.notifier.event;

import java.util.Date;

public class ReportPhotoEvent extends MdissEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2015682760798824511L;
	
	private String username;
	private String photoId; 
	private String description;
	
	public ReportPhotoEvent(String username, String photoId, String description) {
		super("reportPhoto");
		this.username = username;
		this.photoId = photoId;
		this.description = description;
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhotoId() {
		return photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
}
