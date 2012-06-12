package org.mdissjava.notifier.event.observable;

import java.util.Observable;

import org.mdissjava.notifier.event.MdissEvent;
import org.mdissjava.notifier.event.ReportPhotoEvent;

public class ReportPhotoObservable extends Observable{

	public void reportPhoto(String username, String photoId, String description){
		
		MdissEvent event = new ReportPhotoEvent(username, photoId, description);
		
		//set changed and notify observers
		this.setChanged();
		this.notifyObservers(event);
		
	}
}
