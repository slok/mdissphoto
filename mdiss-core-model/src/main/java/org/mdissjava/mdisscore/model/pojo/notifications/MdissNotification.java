package org.mdissjava.mdisscore.model.pojo.notifications;

import java.util.Date;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

@Entity("MdissNotification")

public abstract class MdissNotification {
	
	public enum NotificationType {
									FOLLOWING,
									PHOTO_UPLOADED,
									REPORT_PHOTO,
	}
	
	@Id 
	private ObjectId id;
	private String selfUserName;
	private NotificationType notificationType;
	private Date date;
	private Boolean read;

	public MdissNotification(NotificationType type){
		this.notificationType = type;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}
	
	public String getSelfUserName() {
		return selfUserName;
	}

	public void setSelfUserName(String selfUserName) {
		this.selfUserName = selfUserName;
	}

	public NotificationType getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(NotificationType notificationType) {
		this.notificationType = notificationType;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Boolean isRead() {
		return read;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}	
	
}
