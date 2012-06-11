package org.mdissjava.mdisscore.model.pojo;

import java.util.Map;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Id;

public class UserHmacTokens { 

	public enum HmacService{ MDISSPHOTO,
		
	}
	
	@Id 
	private ObjectId id;
	private String username;
	private Map<HmacService, String> tokens;
	
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
	public Map<HmacService, String> getTokens() {
		return tokens;
	}
	public void setTokens(Map<HmacService, String> tokens) {
		this.tokens = tokens;
	}
	
	
	
	
}
