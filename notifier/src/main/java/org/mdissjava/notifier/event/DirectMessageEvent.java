package org.mdissjava.notifier.event;

import java.util.Date;


public class DirectMessageEvent extends MdissEvent{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7533667537111729226L;
	/**
	 * 
	 */
	
	private String fromUserNick;
	private String text;
	private Date date;

	public DirectMessageEvent(String fromUserNick, String text, Date date) {
		super("directMessage");
		this.fromUserNick = fromUserNick;
		this.text = text;
		this.date = date;
	}

	public String getFromUserNick() {
		return fromUserNick;
	}

	public void setFromUserNick(String fromUserNick) {
		this.fromUserNick = fromUserNick;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
	
	

}
