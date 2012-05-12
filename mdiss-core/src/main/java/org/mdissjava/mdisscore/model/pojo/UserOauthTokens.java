package org.mdissjava.mdisscore.model.pojo;

import java.util.Map;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

@Entity
public class UserOauthTokens {
	
	public enum Service { TWITTER,
	}
	
	@Id 
	private ObjectId id;
	private String username;
	private Map<Service, String> tokens;
	
	
	
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Map<Service, String> getTokens() {
		return tokens;
	}
	public void setTokens(Map<Service, String> tokens) {
		this.tokens = tokens;
	}
	
	
	

}
