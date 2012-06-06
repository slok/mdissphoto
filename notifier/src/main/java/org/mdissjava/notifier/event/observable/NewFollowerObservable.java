package org.mdissjava.notifier.event.observable;

import java.util.Observable;

import org.mdissjava.notifier.event.MdissEvent;
import org.mdissjava.notifier.event.NewFollowerEvent;

public class NewFollowerObservable extends Observable {

	public void newFollower(String username, String followerUsername){
		
		MdissEvent event = new NewFollowerEvent(username, followerUsername);
		
		//set changed and notify observers
		this.setChanged();
		this.notifyObservers(event);
		
	}
}
