package org.mdissjava.commonutils.photo.status;

import java.util.Date;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

/**
 * This class is basically a class to know the status of the photos.
 * There are three states:
 * 		* ID (String): Photo uploaded 
 * 		* Processed (bool): The image has been processed (thumbnails done)
 * 		* Detailed (bool): The first details have been added
 * 
 * This means that depends on the state different actions will take place
 * 
 *  | 	ID		Processed		Detailed		Action
 *  |---------------------------------------------------  
 *  | 	xxxxx	false			false			Delete
 *  | 	xxxxx	true			false			Delete (2 days)
 *  |	xxxxx	false			true			Process
 * 
 * 	If the three states are true then we delete from the database
 * 
 */
@Entity
public class PhotoStatus {

	@Id 
	private ObjectId id;
	private String name;
	private Boolean processed;
	private Boolean detailed;
	private Date updateDate;
	
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean isProcessed() {
		return processed;
	}
	public void setProcessed(Boolean processed) {
		this.processed = processed;
	}
	public Boolean isDetailed() {
		return detailed;
	}
	public void setDetailed(Boolean detailed) {
		this.detailed = detailed;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	
	
	
	
}
