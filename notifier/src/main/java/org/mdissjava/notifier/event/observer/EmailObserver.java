package org.mdissjava.notifier.event.observer;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import org.apache.commons.mail.EmailException;
import org.mdissjava.commonutils.email.EmailUtils;
import org.mdissjava.notifier.event.VerifyAccountEvent;

public class EmailObserver implements Observer{

	private String userNick;
	private String email;
	private String key;
	
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof VerifyAccountEvent)
		{
			this.userNick = ((VerifyAccountEvent)arg).getUserNick();
			this.email = ((VerifyAccountEvent)arg).getEmail();
			this.key = ((VerifyAccountEvent)arg).getKey();
			//send verification email
			try {
				EmailUtils.sendValidationEmail(email, userNick, key);
			} catch (EmailException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	

}
