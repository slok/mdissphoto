package org.mdissjava.notifier.event.observable;

import java.util.Observable;

import org.mdissjava.notifier.event.MdissEvent;
import org.mdissjava.notifier.event.VerifyAccountEvent;

public class VerifyAccountObservable extends Observable{

	public void userRegister(String username){
		
		MdissEvent event = new VerifyAccountEvent(username);
		
		//set changed and notify observers
		this.setChanged();
		this.notifyObservers(event);
		
	}
	
}
