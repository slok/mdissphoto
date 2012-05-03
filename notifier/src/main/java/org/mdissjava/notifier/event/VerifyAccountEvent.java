package org.mdissjava.notifier.event;


public class VerifyAccountEvent extends MdissEvent{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6278989467332175225L;
	
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
