package org.mdissjava.notifier.daemon;


import java.util.UUID;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.Test;
import org.mdissjava.notifier.broker.connection.MessageBrokerConnection.ConnectionType;
import org.mdissjava.notifier.broker.connection.STOMPConnection;
import org.mdissjava.notifier.event.NewFollowerEvent;
import org.mdissjava.notifier.event.PhotoUploadedEvent;
import org.mdissjava.notifier.event.ReportPhotoEvent;
import org.mdissjava.notifier.event.manager.NotificationManager;
import org.mdissjava.notifier.event.observable.ReportPhotoObservable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MdissNotificationDaemonTest {

	final private String DESTINATION_EMAIL = "notifications_email";
	final private String DESTINATION_PERSISTENCE = "notifications_persistence";
	
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Test
	public void PhotoUploadAndPersistenceDaemonTest() throws JMSException, InterruptedException {
		
		this.logger.info("[TEST] PhotoUploadAndPersistenceDaemonTest");
		//Receiver daemon in other thread
		this.logger.info("[TEST] Launch the daemon in new thread");
		String[] args = {"-p", "test"};
		args.equals(args);
        //new Thread(new daemonThread(args)).start();
		
		//Producer
		
		Thread.sleep(100);
		this.logger.info("[TEST] Send event to Apollo queue");
		STOMPConnection connection = new STOMPConnection(ConnectionType.QUEUE, DESTINATION_PERSISTENCE, 
				DeliveryMode.NON_PERSISTENT, Session.AUTO_ACKNOWLEDGE);
		
		MessageProducer producer = connection.createProducer();
		Session session = connection.getSession();
		
		PhotoUploadedEvent event = new PhotoUploadedEvent("cerealguy", UUID.randomUUID().toString());
		ObjectMessage msg = session.createObjectMessage(event);
		
		producer.send(msg);
		
		//produce the event that triggers the shutdown of the listener (we only have one listener in the queue)
		TextMessage exitMsg = session.createTextMessage("EXIT");
		producer.send(exitMsg);
		
		//check persistence
		/*this.logger.info("[TEST] Check stored notification");
		MdissNotificationDao notificationDao = new MdissNotificationDaoImpl(MorphiaDatastoreFactory.getDatastore("mdiss"));
		MdissNotification notification = new PhotoUploadedNotification();
		notification.setDate(event.getEventDate());
		notification.setNotificationType(MdissNotification.NotificationType.PHOTO_UPLOADED); 
		PhotoUploadedNotification notificationHelper = ((PhotoUploadedNotification)notificationDao.findMdissNotification(notification).get(0));
		assertEquals(event.getUserNick(), notificationHelper.getUploaderUsername());
		notificationDao.deleteMdissNotification(notificationHelper);*/

	}
	@Test
	public void NewFollowerPersistenceDaemonTest() throws JMSException, InterruptedException{
		
		this.logger.info("[TEST] NewFollowerPersistenceDaemonTest");
		//Receiver daemon in other thread
		this.logger.info("[TEST] Launch the daemon in new thread");
		String[] args = {"-p", "test"};
		args.equals(args);
        //new Thread(new daemonThread(args)).start();
		
		//Producer
		
		Thread.sleep(100);
		this.logger.info("[TEST] Send event to Apollo queue");
		STOMPConnection connection = new STOMPConnection(ConnectionType.QUEUE, DESTINATION_PERSISTENCE, 
				DeliveryMode.NON_PERSISTENT, Session.AUTO_ACKNOWLEDGE);
		
		MessageProducer producer = connection.createProducer();
		Session session = connection.getSession();
		
		NewFollowerEvent event = new NewFollowerEvent("cerealguy", "ikermatic");
		ObjectMessage msg = session.createObjectMessage(event);
		
		producer.send(msg);
		
		//produce the event that triggers the shutdown of the listener (we only have one listener in the queue)
		TextMessage exitMsg = session.createTextMessage("EXIT");
		producer.send(exitMsg);
		
	}
	@Test
	public void ReportPhotoPersistenceDaemonTest() throws JMSException, InterruptedException{
		
		this.logger.info("[TEST] ReportPhotoPersistenceDaemonTest");
		//Receiver daemon in other thread
		this.logger.info("[TEST] Launch the daemon in new thread");
		String[] args = {"-p", "test"};
		args.equals(args);
        //new Thread(new daemonThread(args)).start();
		
		//Producer
		
		Thread.sleep(100);
		this.logger.info("[TEST] Send event to Apollo queue");
		STOMPConnection connection = new STOMPConnection(ConnectionType.QUEUE, DESTINATION_PERSISTENCE, 
				DeliveryMode.NON_PERSISTENT, Session.AUTO_ACKNOWLEDGE);
		
		MessageProducer producer = connection.createProducer();
		Session session = connection.getSession();
		
		ReportPhotoEvent event = new ReportPhotoEvent("maifrup", "0987349928a", "Photo +18");
		ObjectMessage msg = session.createObjectMessage(event);
		
		producer.send(msg);
		
		//produce the event that triggers the shutdown of the listener (we only have one listener in the queue)
		TextMessage exitMsg = session.createTextMessage("EXIT");
		producer.send(exitMsg);
		
	}
	@Test
	public void NewFollowerEmailDaemonTest() throws JMSException, InterruptedException{
		
		this.logger.info("[TEST] NewFollowerEmailDaemonTest");
		//Receiver daemon in other thread
		this.logger.info("[TEST] Launch the daemon in new thread");
		String[] args = {"-e"};
		args.equals(args);
        //new Thread(new daemonThread(args)).start();
		
		//Producer
		
		Thread.sleep(100);
		this.logger.info("[TEST] Send event to Apollo queue");
		STOMPConnection connection = new STOMPConnection(ConnectionType.QUEUE, DESTINATION_EMAIL, 
				DeliveryMode.NON_PERSISTENT, Session.AUTO_ACKNOWLEDGE);
		
		MessageProducer producer = connection.createProducer();
		Session session = connection.getSession();
		
		NewFollowerEvent event = new NewFollowerEvent("maifrup", "cerealguy");
		ObjectMessage msg = session.createObjectMessage(event);
		
		producer.send(msg);
		
		//produce the event that triggers the shutdown of the listener (we only have one listener in the queue)
		TextMessage exitMsg = session.createTextMessage("EXIT");
		producer.send(exitMsg);
		
	}
	
	@Test
	public void ReportPhotoEmailDaemonTest() throws JMSException, InterruptedException{
		
		this.logger.info("[TEST] NewFollowerEmailDaemonTest");
		//Receiver daemon in other thread
		this.logger.info("[TEST] Launch the daemon in new thread");
		String[] args = {"-e"};
		args.equals(args);
        //new Thread(new daemonThread(args)).start();
		
		//Producer
		
		Thread.sleep(100);
		this.logger.info("[TEST] Send event to Apollo queue");
		STOMPConnection connection = new STOMPConnection(ConnectionType.QUEUE, DESTINATION_EMAIL, 
				DeliveryMode.NON_PERSISTENT, Session.AUTO_ACKNOWLEDGE);
		
		MessageProducer producer = connection.createProducer();
		Session session = connection.getSession();
		
		ReportPhotoEvent event = new ReportPhotoEvent("maifrup", "0987349928a", "Photo +18");
		ObjectMessage msg = session.createObjectMessage(event);
		
		producer.send(msg);
		
		//produce the event that triggers the shutdown of the listener (we only have one listener in the queue)
		TextMessage exitMsg = session.createTextMessage("EXIT");
		producer.send(exitMsg);
		
	}
	
	@Test
	public void ReportPhotoDaemonTest() throws JMSException, InterruptedException
	{
		 NotificationManager notifier = NotificationManager.getInstance();
		 ReportPhotoObservable rpo = notifier.getReportPhotoObservable();
		 rpo.reportPhoto("maifrup", "09348s9f3-asd", "Photo +18");
	}
	
	class daemonThread implements Runnable{
		
		private String[] params;
		
		public daemonThread(String[] mainParams) {
			this.params = mainParams;
		}
		
		@Override
		public void run() {
			
			try {
				MdissNotifierDaemonRunner.main(params);
			} catch (JMSException e) {
				logger.error("[TEST] Error in daemon thread: {}", e.toString());
			}
		}
		
	}
}
