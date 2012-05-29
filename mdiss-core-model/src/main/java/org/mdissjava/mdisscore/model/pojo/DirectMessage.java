package org.mdissjava.mdisscore.model.pojo;

import java.util.Date;
import java.util.UUID;

import org.mdissjava.mdisscore.model.dao.UserDao;
import org.mdissjava.mdisscore.model.dao.impl.UserDaoImpl;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

@Entity
public class DirectMessage {

	@Id
	private String id;
	private int fromUserId;
	private int toUserId;
	private String text;
	private boolean read;
	private String fromUserName;
	private String fromUserAvatar;
	@Embedded
	private Date sentDate;
	
	public DirectMessage() {
		
	}
	
	public DirectMessage(int from, int to, String text) {
		this.id = UUID.randomUUID().toString();
		this.fromUserId = from;
		this.toUserId = to;
		this.text = text;
		this.sentDate = new Date();
		this.read = false;
		UserDao ud = new UserDaoImpl();
		User fromUser = ud.getUserById(from);
		this.fromUserName = fromUser.getNick();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(int fromUserId) {
		this.fromUserId = fromUserId;
	}

	public int getToUserId() {
		return toUserId;
	}

	public void setToUserId(int toUserId) {
		this.toUserId = toUserId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public Date getSentDate() {
		return sentDate;
	}

	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getFromUserAvatar() {
		return fromUserAvatar;
	}

	public void setFromUserAvatar(String fromUserAvatar) {
		this.fromUserAvatar = fromUserAvatar;
	}
	
	
	
}
