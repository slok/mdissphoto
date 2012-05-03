package org.mdissjava.notifier.event.observer;

import java.util.Observable;
import java.util.Observer;

import org.mdissjava.notifier.event.PhotoUploadedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerObserver implements Observer{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void update(Observable o, Object arg) {
		
		//Photo uploaded event
		if (arg instanceof PhotoUploadedEvent)
		{
			PhotoUploadedEvent event= (PhotoUploadedEvent)arg;
			this.logger.info("Photo uploaded event received");
			this.logger.info("User Nickname: {}", event.getUserNick());
			this.logger.info("Photo id: {}", event.getPhotoId());
			this.logger.info("Event time: {}", event.getEventDate());
			
		}
		
	}

}
