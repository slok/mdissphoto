package org.mdissjava.mdisscore.solr.pojo;

import org.apache.solr.client.solrj.beans.Field;

import com.google.code.morphia.annotations.Entity;

@Entity
public class followuserid {
	
	@Field
	private int userid;
	@Field
	private int followuserid;
	
	public int getUserid() {
		return userid;
	}
	public int getFollowuserid() {
		return followuserid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public void setFollowuserid(int followuserid) {
		this.followuserid = followuserid;
	}
	
}
