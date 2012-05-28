package org.mdissjava.notifier.daemon;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.mdissjava.notifier.broker.connection.MessageBrokerConnection.ConnectionType;
import org.mdissjava.notifier.broker.connection.STOMPConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Daemon {

	protected STOMPConnection connection;
	protected MessageConsumer consumer;
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	public Daemon(boolean QUEUE, String destination) throws JMSException {
		
		//create a connection and a consumer
		ConnectionType connectionType= QUEUE? ConnectionType.QUEUE: ConnectionType.TOPIC;
		int persistent = DeliveryMode.NON_PERSISTENT;
		int acknowledgeMode = Session.AUTO_ACKNOWLEDGE;
		
		this.connection = new STOMPConnection(connectionType, destination,persistent, acknowledgeMode);
		this.consumer = this.connection.createConsumer();
	}
	
	abstract public void startDaemon();
	abstract public void stopDaemon();
	
}
