package org.mdissjava.notifier.event.observer;

import java.util.Observable;
import java.util.Observer;

import org.mdissjava.notifier.event.VerifyAccountEvent;

public class EmailObserver implements Observer{

	private String userNick;
	
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof VerifyAccountEvent)
		{
			this.userNick = ((VerifyAccountEvent)arg).getUserNick();
			//send verification email
			
		}
		
	}

	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}
	
	

}
