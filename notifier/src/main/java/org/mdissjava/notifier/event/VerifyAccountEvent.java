package org.mdissjava.notifier.event;


public class VerifyAccountEvent extends MdissEvent{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6278989467332175225L;
	
	private String userNick;
	private String email;
	private String key;

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
