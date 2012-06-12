package org.mdissjava.mdisscore.model.pojo.notifications;

public class ReportPhotoNotification extends MdissNotification {

	private String username;
	private String photoId;
	private String description;
	
	public ReportPhotoNotification() {
		super(MdissNotification.NotificationType.REPORT_PHOTO);
	}

	public ReportPhotoNotification(String username, String photoId, String description) {
		super(MdissNotification.NotificationType.REPORT_PHOTO);
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

	@Override
	public String toString() {
		
		String returnStr = this.username + " has uploaded a report for the photo '" + photoId + "' on "+ this.getDate()+
				" with the following description "+description;
		return returnStr;
	}
}
