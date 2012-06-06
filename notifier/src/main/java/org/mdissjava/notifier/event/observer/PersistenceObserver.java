package org.mdissjava.notifier.event.observer;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.mdissjava.mdisscore.model.dao.MdissNotificationDao;
import org.mdissjava.mdisscore.model.dao.UserDao;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.MdissNotificationDaoImpl;
import org.mdissjava.mdisscore.model.dao.impl.UserDaoImpl;
import org.mdissjava.mdisscore.model.pojo.User;
import org.mdissjava.mdisscore.model.pojo.notifications.FollowingNotification;
import org.mdissjava.mdisscore.model.pojo.notifications.MdissNotification.NotificationType;
import org.mdissjava.mdisscore.model.pojo.notifications.PhotoUploadedNotification;
import org.mdissjava.notifier.broker.connection.STOMPConnection;
import org.mdissjava.notifier.broker.connection.MessageBrokerConnection.ConnectionType;
import org.mdissjava.notifier.event.MdissEvent;
import org.mdissjava.notifier.event.PhotoUploadedEvent;
import org.mdissjava.notifier.event.VerifyAccountEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.Datastore;

public class PersistenceObserver implements Observer{

		private String DESTINATION_PERSISTENCE = "notifications_persistence";
		private final Logger logger = LoggerFactory.getLogger(this.getClass());

		@Override
		public void update(Observable o, Object arg) {	
			STOMPConnection connection;
			try {
				connection = new STOMPConnection(ConnectionType.QUEUE, DESTINATION_PERSISTENCE, 
						DeliveryMode.NON_PERSISTENT, Session.AUTO_ACKNOWLEDGE);
			
				MessageProducer producer = connection.createProducer();
				Session session = connection.getSession();
				
				ObjectMessage msg = session.createObjectMessage((MdissEvent)arg);
				
				producer.send(msg);
			} catch (JMSException e) {
				this.logger.error("Error sending event to the broker: {}", e.toString());
			}
			
		}

	}