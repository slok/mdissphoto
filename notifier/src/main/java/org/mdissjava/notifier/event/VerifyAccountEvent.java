package org.mdissjava.notifier.event;


public class VerifyAccountEvent extends MdissEvent{
	
	private String userNick;

	public VerifyAccountEvent(String userNick) {
		super("verifyAccount");
		this.userNick = userNick; 
	}
	
	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}
	
	

}
