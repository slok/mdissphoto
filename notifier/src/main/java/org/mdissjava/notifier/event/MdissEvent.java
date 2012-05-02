package org.mdissjava.notifier.event;

import java.io.Serializable;

public abstract class MdissEvent implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6701547885565504945L;
	
	protected String eventType;

	public MdissEvent(String eventType)
	{
		this.eventType = eventType;
	}
	
	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	
	

}
