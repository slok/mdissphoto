package org.mdissjava.mdisscore.model.pojo.notifications;


public class PhotoUploadedNotification extends MdissNotification{

	private String uploaderUsername;
	private String photoId; 
	
	//this constructor shound't exists, Morphia needs it :/
	public PhotoUploadedNotification() {
		super(MdissNotification.NotificationType.PHOTO_UPLOADED);
	}
	
	public PhotoUploadedNotification(String uploaderUsername, String photoId) {
		super(MdissNotification.NotificationType.PHOTO_UPLOADED);
		this.uploaderUsername = uploaderUsername;
		this.photoId = photoId;
	}

	public String getUploaderUsername() {
		return uploaderUsername;
	}

	public void setUploaderUsername(String uploaderUsername) {
		this.uploaderUsername = uploaderUsername;
	}

	public String getPhotoId() {
		return photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}
	
	@Override
	public String toString() {
		
		String returnStr = this.uploaderUsername + " has uploaded a photo named '" + this.getPhotoId() + "' on "+ this.getDate();
		return returnStr;
	}

}
