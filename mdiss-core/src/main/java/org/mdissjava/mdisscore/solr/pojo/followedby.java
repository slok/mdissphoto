package org.mdissjava.mdisscore.solr.pojo;

import org.apache.solr.client.solrj.beans.Field;

import com.google.code.morphia.annotations.Entity;

@Entity
public class followedby {
	
	@Field
	private int userid;
	@Field
	private int followedby;
	
	public int getUserid() {
		return userid;
	}
	public int getFollowedby() {
		return followedby;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public void setFollowedby(int followedby) {
		this.followedby = followedby;
	}

}
