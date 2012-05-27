package org.mdissjava.notifier.daemon;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.mdissjava.mdisscore.model.dao.MdissNotificationDao;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.MdissNotificationDaoImpl;
import org.mdissjava.mdisscore.model.pojo.notifications.PhotoUploadedNotification;
import org.mdissjava.notifier.event.PhotoUploadedEvent;

import com.google.code.morphia.Datastore;

public class PersistenceDaemon extends Daemon {

	private Datastore datastore;
	
	public PersistenceDaemon(boolean QUEUE, String destination, String mongoDb) throws JMSException {
		super(QUEUE, destination);
		String db = mongoDb == null ? "mdissphoto": mongoDb;
		this.datastore = MorphiaDatastoreFactory.getDatastore(db);
	}
	
	@Override
	public void startDaemon() {
		this.logger.info("run persistence daemon");
		
		try{
			while(true) {
	        	Message msgReceived = consumer.receive();
	            //is an object message?
	        	if(msgReceived instanceof ObjectMessage) { // also instance of StompJmsObjectMessage
	            	
	        		Serializable obj = ((ObjectMessage) msgReceived).getObject();
	        		MdissNotificationDao notificationDao = new MdissNotificationDaoImpl(this.datastore);
	        		
	        		//DO THINGS FOR EACH TYPE OF EVENT!!!!
	            	//if is MdissEvent type object
	        		//TODO: Check followers etc...
	        		if (obj instanceof PhotoUploadedEvent)
	            	{
	        			PhotoUploadedEvent eventReceived = (PhotoUploadedEvent)obj;
	        			this.logger.info("New Message event received: {}", eventReceived.getEventType());
	        			this.storePhotoUploadNotification(eventReceived, notificationDao);     			
	        			
	            	}else
	            		throw new IllegalStateException("Wrong message received");
	
	            }else if(msgReceived instanceof TextMessage){
	            	String body = ((TextMessage) msgReceived).getText();
	            	if( "EXIT".equals(body)) {
	                    break;
	            	}
	            }else {
	            	throw new IllegalStateException("Wrong message received");
	            }
			}
		}catch(Exception e){
			this.logger.error("Exception Catchted: {}", e.toString());
		}

	}

	private void storePhotoUploadNotification(PhotoUploadedEvent eventReceived, MdissNotificationDao notificationDao) {
		
		//create the notifiacion/s
		PhotoUploadedNotification notification = new PhotoUploadedNotification(eventReceived.getUserNick(), eventReceived.getPhotoId());
		notification.setDate(eventReceived.getEventDate());
		notification.setSelfUserName("Python-Ruby");
		notification.setRead(false);
		
		//save
		notificationDao.insertMdissNotification(notification);	   
		
	}

	@Override
	public void stopDaemon() {
		System.out.println("run persistence daemon");

	}

}
