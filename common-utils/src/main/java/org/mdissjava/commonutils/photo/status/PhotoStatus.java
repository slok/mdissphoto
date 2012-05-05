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
 *  | 	xxxxx	true			false			Delete (X days)
 *  |	xxxxx	false			true			Process
 * 
 * 	If the three states are true then we delete from the database
 * 
 * The available states for the attributes are:
 * 
 * - ID: a String with the id of the new stored photo
 * - Processed: ¿The image has been processed (thumbnailization made)?
 * 		+ -1: No processed
 *		+ 0: The action has been delegate to gearman
 *		+ 1: Gearman has finished the job
 * - Detailed: ¿Some details have been saved?
 * 		+ false: no one has detailed the photo (title...)
 * 		+ true: Some details have been saved 
 * 
 */
@Entity
public class PhotoStatus {

	public enum ProcessedStatus {NONE, STARTED, FINISHED};
	
	
	@Id 
	private ObjectId id;
	private String name;
	private ProcessedStatus processed;
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
	public ProcessedStatus getProcessed() {
		return processed;
	}
	public void setProcessed(ProcessedStatus processed) {
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
