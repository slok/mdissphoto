package org.mdissjava.notifier.daemon;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.apache.commons.mail.EmailException;
import org.mdissjava.commonutils.email.EmailUtils;
import org.mdissjava.mdisscore.model.dao.MdissNotificationDao;
import org.mdissjava.mdisscore.model.dao.UserDao;
import org.mdissjava.mdisscore.model.dao.impl.MdissNotificationDaoImpl;
import org.mdissjava.mdisscore.model.dao.impl.UserDaoImpl;
import org.mdissjava.mdisscore.model.pojo.User;
import org.mdissjava.notifier.event.DirectMessageEvent;
import org.mdissjava.notifier.event.NewFollowerEvent;
import org.mdissjava.notifier.event.ReportPhotoEvent;
import org.mdissjava.notifier.event.VerifyAccountEvent;

public class EmailDaemon extends Daemon{

	public EmailDaemon(boolean QUEUE, String destination) throws JMSException {
		super(QUEUE, destination);
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
	        		
	        		//DO THINGS FOR EACH TYPE OF EVENT!!!!
	            	//if is MdissEvent type object
	        		if (obj instanceof VerifyAccountEvent)
	            	{
	        			VerifyAccountEvent eventReceived = (VerifyAccountEvent)obj;
	        			this.logger.info("New Message event received: {}", eventReceived.getEventType());
	        			this.sendVerificationEmail(eventReceived);
	        			
	            	}
	        		else if(obj instanceof NewFollowerEvent)
	        		{
	        			NewFollowerEvent eventReceived = (NewFollowerEvent)obj;
	        			this.logger.info("New Message event received: {}", eventReceived.getEventType());
	        			this.sendFollowerEmail(eventReceived);
	        		}
	        		else if(obj instanceof ReportPhotoEvent)
	        		{
	        			ReportPhotoEvent eventReceived = (ReportPhotoEvent)obj;
	        			this.logger.info("New Photo Report event received {}", eventReceived.getEventType());
	        			this.sendReportPhoto(eventReceived);
	        		}else if(obj instanceof DirectMessageEvent) {
	        			//TODO: Send Email
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

	@Override
	public void stopDaemon() {
		System.out.println("stop email daemon");
	}
	
	public void sendVerificationEmail(VerifyAccountEvent eventReceived){
		String userNick = eventReceived.getUserNick();
		String email = eventReceived.getEmail();
		String key = eventReceived.getKey();
		//send verification email
		try {
			EmailUtils.sendValidationEmail(email, userNick, key);
		} catch (EmailException e) {
			this.logger.error("Error sending email: {}", e.toString());
		} catch (IOException e) {
			this.logger.error("Error sending email: {}", e.toString());
		}
	}

	public void sendFollowerEmail(NewFollowerEvent eventReceived){
		String username = eventReceived.getSelfUserName();
		String followerUsername = eventReceived.getFollowerUsername();
		UserDao userDao = new UserDaoImpl();
		User selfUser = userDao.getUserByNick(eventReceived.getSelfUserName());
		String email = selfUser.getEmail();
	
		//TODO private profile accept notificacion
		
		//send follower email
		try {
			EmailUtils.sendFollowerEmail(email, username, followerUsername);
		} catch (EmailException e) {
			this.logger.error("Error sending email: {}", e.toString());
		} catch (IOException e) {
			this.logger.error("Error sending email: {}", e.toString());
		}
	}
	
	public void sendReportPhoto(ReportPhotoEvent eventReceived)
	{
		String username = eventReceived.getUsername();
		String photoId = eventReceived.getPhotoId();
		UserDao userDao = new UserDaoImpl();
		List<User> adminUsers = userDao.getUserByRole("admin");

		for(User u:adminUsers)
		{
			try {
				EmailUtils.sendReportPhotoEmail(u.getEmail(), username, photoId);
			} catch (EmailException e) {
				this.logger.error("Error sending email: {}", e.toString());
			} catch (IOException e) {
				this.logger.error("Error sending email: {}", e.toString());
			}
			
		}
	}
}
