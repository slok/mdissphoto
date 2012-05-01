package org.mdissjava.notifier.event;

public abstract class MdissEvent {
	
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
