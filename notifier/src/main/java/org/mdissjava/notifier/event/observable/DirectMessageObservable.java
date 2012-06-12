package org.mdissjava.notifier.event.observable;

import java.util.Date;
import java.util.Observable;

import org.mdissjava.notifier.event.DirectMessageEvent;
import org.mdissjava.notifier.event.MdissEvent;

public class DirectMessageObservable extends Observable{

	public void messageSent(String fromUserNick, String text, Date date){
		
		MdissEvent event = new DirectMessageEvent(fromUserNick, text, date);
		
		//set changed and notify observers
		this.setChanged();
		this.notifyObservers(event);
		
	}
	
}
