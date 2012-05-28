package org.mdissjava.notifier.daemon;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.UUID;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.Test;
import org.mdissjava.mdisscore.model.dao.MdissNotificationDao;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.MdissNotificationDaoImpl;
import org.mdissjava.mdisscore.model.pojo.notifications.MdissNotification;
import org.mdissjava.mdisscore.model.pojo.notifications.PhotoUploadedNotification;
import org.mdissjava.notifier.broker.connection.MessageBrokerConnection.ConnectionType;
import org.mdissjava.notifier.broker.connection.STOMPConnection;
import org.mdissjava.notifier.event.PhotoUploadedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MdissNotificationDaemonTest {

	final private String DESTINATION_EMAIL = "notifications_email";
	final private String DESTINATION_PERSISTENCE = "notifications_persistence";
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//@Test
	public void PhotoUploadAndPersistenceDaemonTest() throws JMSException, InterruptedException {
		
		this.logger.info("[TEST] PhotoUploadAndPersistenceDaemonTest");
		//Receiver daemon in other thread
		this.logger.info("[TEST] Launch the daemon in new thread");
		new Thread(new daemonThread("-p")).start();
		
		//Producer
		
		Thread.sleep(100);
		this.logger.info("[TEST] Send event to Apollo queue");
		STOMPConnection connection = new STOMPConnection(ConnectionType.QUEUE, DESTINATION_PERSISTENCE, 
				DeliveryMode.NON_PERSISTENT, Session.AUTO_ACKNOWLEDGE);
		
		MessageProducer producer = connection.createProducer();
		Session session = connection.getSession();
		
		PhotoUploadedEvent event = new PhotoUploadedEvent("slok", UUID.randomUUID().toString());
		ObjectMessage msg = session.createObjectMessage(event);
		
		producer.send(msg);
		
		//produce the event that triggers the shutdown of the listener (we only have one listener in the queue)
		TextMessage exitMsg = session.createTextMessage("EXIT");
		producer.send(exitMsg);
		
		//check persistence
		this.logger.info("[TEST] Check stored notification");
		MdissNotificationDao notificationDao = new MdissNotificationDaoImpl(MorphiaDatastoreFactory.getDatastore("mdiss"));
		MdissNotification notification = new PhotoUploadedNotification();
		notification.setDate(event.getEventDate());
		notification.setNotificationType(MdissNotification.NotificationType.PHOTO_UPLOADED); 
		PhotoUploadedNotification notificationHelper = ((PhotoUploadedNotification)notificationDao.findMdissNotification(notification).get(0));
		assertEquals(event.getUserNick(), notificationHelper.getUploaderUsername());
		notificationDao.deleteMdissNotification(notificationHelper);

	}

	
	class daemonThread implements Runnable{
		
		private String params;
		
		public daemonThread(String mainParams) {
			this.params = mainParams;
		}
		
		@Override
		public void run() {
			String params[] = {this.params}; 
			try {
				MdissNotifierDaemonRunner.main(params);
			} catch (JMSException e) {
				logger.error("[TEST] Error in daemon thread: {}", e.toString());
			}
		}
		
	}
}
