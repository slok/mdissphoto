package org.mdissjava.mdisscore.model.pojo;

import java.util.Date;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Reference;


@Entity
public class Album {

	
	@Id
	private ObjectId id;
	
	private String title;
	private Date creationDate;
	
	@Reference
	private User user;
	
	@Reference
	private Photo photos;
	
	
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Photo getPhotos() {
		return photos;
	}
	public void setPhotos(Photo photos) {
		this.photos = photos;
	}
	
	
	
}
	
	

	