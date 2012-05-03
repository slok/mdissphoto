package org.mdissjava.notifier.event.observable;

import java.util.Observable;

import org.mdissjava.notifier.event.MdissEvent;
import org.mdissjava.notifier.event.PhotoUploadedEvent;

public class PhotoUploadedObservable extends Observable{
	
	
	public void photoUploaded(String username, String photoId){
		
		MdissEvent event = new PhotoUploadedEvent(username, photoId);
		
		//set changed and notify observers
		this.setChanged();
		this.notifyObservers(event);
		
	}

}
