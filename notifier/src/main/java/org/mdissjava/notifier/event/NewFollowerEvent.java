package org.mdissjava.notifier.event;


public class NewFollowerEvent extends MdissEvent {

	private String followerUsername;
	private String selfUserName;
	private String privateProfileAcceptToken;

	public NewFollowerEvent(String selfUsername, String followerUsername) {
		super("newFollower");
		this.followerUsername = followerUsername;
		this.selfUserName = selfUsername;
		
	}

	public String getFollowerUsername() {
		return followerUsername;
	}


	public void setFollowerUsername(String followerUsername) {
		this.followerUsername = followerUsername;
	}
	
	public String getSelfUserName() {
		return selfUserName;
	}

	public void setSelfUserName(String selfUserName) {
		this.selfUserName = selfUserName;
	}

	public String getPrivateProfileAcceptToken() {
		return privateProfileAcceptToken;
	}

	public void setPrivateProfileAcceptToken(String privateProfileAcceptToken) {
		this.privateProfileAcceptToken = privateProfileAcceptToken;
	}

	

}
