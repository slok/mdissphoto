package org.mdissjava.notifier.event.observable;

import java.util.Observable;
import java.util.UUID;

import org.mdissjava.notifier.event.MdissEvent;
import org.mdissjava.notifier.event.NewFollowerEvent;

public class NewFollowerObservable extends Observable {

	public void newFollower(String username, String followerUsername){
		
		MdissEvent event = new NewFollowerEvent(username, followerUsername);
		
		//set changed and notify observers
		this.setChanged();
		this.notifyObservers(event);
		
	}
	
	public void newPrivateFollower(String username, String followerUsername){
		
		NewFollowerEvent event = new NewFollowerEvent(username, followerUsername);
		event.setPrivateProfileAcceptToken(UUID.randomUUID().toString());
		
		//set changed and notify observers
		this.setChanged();
		this.notifyObservers(event);
		
	}
}
