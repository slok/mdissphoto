package org.mdissjava.notifier.daemon;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.mdissjava.mdisscore.model.dao.MdissNotificationDao;
import org.mdissjava.mdisscore.model.dao.UserDao;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.MdissNotificationDaoImpl;
import org.mdissjava.mdisscore.model.dao.impl.UserDaoImpl;
import org.mdissjava.mdisscore.model.pojo.User;
import org.mdissjava.mdisscore.model.pojo.notifications.FollowingNotification;
import org.mdissjava.mdisscore.model.pojo.notifications.PhotoUploadedNotification;
import org.mdissjava.mdisscore.model.pojo.notifications.MdissNotification.NotificationType;
import org.mdissjava.mdisscore.model.pojo.notifications.ReportPhotoNotification;
import org.mdissjava.notifier.event.NewFollowerEvent;
import org.mdissjava.notifier.event.PhotoUploadedEvent;
import org.mdissjava.notifier.event.ReportPhotoEvent;

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

	            	}
	    			else if(obj instanceof NewFollowerEvent)
	    			{
	    				NewFollowerEvent eventReceived = (NewFollowerEvent)obj;
	    				
	        			this.logger.info("New Message event received: {}", eventReceived.getEventType());
	        			this.storeNewFollowerNotification(eventReceived, notificationDao);   
	    			}
	    			else if(obj instanceof ReportPhotoEvent)
	    			{
	    				ReportPhotoEvent eventReceived = (ReportPhotoEvent)obj;
	    				
	        			this.logger.info("New Message event received: {}", eventReceived.getEventType());
	        			this.storeReportPhotoNotification(eventReceived, notificationDao);   
	    			}
	        		else
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

	
	private void storeNewFollowerNotification(NewFollowerEvent event, MdissNotificationDao notificationDao)
	{
		FollowingNotification followerNotification = new FollowingNotification(event.getFollowerUsername());
		followerNotification.setDate(new Date());
		followerNotification.setFollowerUserName(event.getFollowerUsername());
		followerNotification.setRead(false);
		followerNotification.setSelfUserName(event.getSelfUserName());
		
		notificationDao.insertMdissNotification(followerNotification);
	}
	
	private void storePhotoUploadNotification(PhotoUploadedEvent eventReceived, MdissNotificationDao notificationDao) {
	
		//create the notifiacion/s
		PhotoUploadedNotification notification = new PhotoUploadedNotification(eventReceived.getUserNick(), eventReceived.getPhotoId());
		
		List<PhotoUploadedNotification> new_follower_notifications = new ArrayList<PhotoUploadedNotification>();
		
		UserDao userDao = new UserDaoImpl();
		
		User uploaderUser = userDao.getUserByNick(eventReceived.getUserNick());
		List<User> followers = uploaderUser.getFollowers();
		System.out.println("Followers' size: "+followers.size());
		for(User u : followers)
		{
			notification = new PhotoUploadedNotification(eventReceived.getUserNick(), eventReceived.getPhotoId());
			notification.setPhotoId(eventReceived.getPhotoId());
			notification.setSelfUserName(u.getNick());
			System.out.println("Notification selfUserName: "+notification.getSelfUserName());
			notification.setRead(false);
			notification.setDate(eventReceived.getEventDate());
			notification.setNotificationType(NotificationType.PHOTO_UPLOADED);
			notification.setUploaderUsername(eventReceived.getUserNick());
			
			//save
			System.out.println("Insertando notification");
			new_follower_notifications.add(notification);
			System.out.println("notifications size: "+new_follower_notifications.size());
		}
		
		for(PhotoUploadedNotification puNot : new_follower_notifications)
		{
			notificationDao.insertMdissNotification(puNot);
		}

	}

	private void storeReportPhotoNotification(ReportPhotoEvent eventReceived,
			MdissNotificationDao notificationDao) {

		//create the notifiacion/s
		Datastore db = MorphiaDatastoreFactory.getDatastore("mdissphoto");
		ReportPhotoNotification notification = new ReportPhotoNotification(eventReceived.getUsername(), eventReceived.getPhotoId(), eventReceived.getDescription());
				
		notification = new ReportPhotoNotification(eventReceived.getUsername(), eventReceived.getPhotoId(), eventReceived.getDescription());
		notification.setRead(false);
		notification.setDate(new Date());
		notification.setNotificationType(NotificationType.PHOTO_UPLOADED);
		notificationDao.insertMdissNotification(notification);
		
	}

	
	@Override
	public void stopDaemon() {
		System.out.println("run persistence daemon");

	}

}
