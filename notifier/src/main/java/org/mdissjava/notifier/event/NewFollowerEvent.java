package org.mdissjava.notifier.event;

import java.util.Date;

public class NewFollowerEvent extends MdissEvent {

	private String followerUsername;
	private String selfUserName;

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


}
