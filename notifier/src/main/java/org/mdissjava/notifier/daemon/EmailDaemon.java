package org.mdissjava.notifier.daemon;

import java.io.IOException;
import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.apache.commons.mail.EmailException;
import org.mdissjava.commonutils.email.EmailUtils;
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
			this.logger.error("Error sending email: "+ e.toString());
		} catch (IOException e) {
			this.logger.error("Error sending email: "+ e.toString());
		}
	}

}
