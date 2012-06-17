package org.mdissjava.mdisscore.model.pojo.notifications;


public final class FollowingNotification extends MdissNotification{

	private String followerUserName;
	private String privateProfileAcceptToken;	
	
	//this constructor shound't exists, Morphia needs it :/
	public FollowingNotification() {
		super(MdissNotification.NotificationType.FOLLOWING);
	}
	
	public FollowingNotification(String followerUserName) {
		super(MdissNotification.NotificationType.FOLLOWING);
		this.followerUserName = followerUserName;
	}

	public String getFollowerUserName() {
		return followerUserName;
	}


	public void setFollowerUserName(String followerUserName) {
		this.followerUserName = followerUserName;
	}
	
	public String getPrivateProfileAcceptToken() {
		return privateProfileAcceptToken;
	}

	public void setPrivateProfileAcceptToken(String privateProfileAcceptToken) {
		this.privateProfileAcceptToken = privateProfileAcceptToken;
	}

	@Override
	public String toString() {
		
		String returnStr = this.getFollowerUserName() + " is now following you";
		return returnStr;
	}
	
}
