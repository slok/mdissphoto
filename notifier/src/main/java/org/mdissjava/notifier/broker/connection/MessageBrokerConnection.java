package org.mdissjava.notifier.broker.connection;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract public class MessageBrokerConnection {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public enum ConnectionType {TOPIC, QUEUE}
	
	protected String host; //localhost, ip...
	protected int port; //the port
	protected String user;
	protected String password;
	protected ConnectionType type; //queue or topic
	protected String destinationName; //name of the queue or topic
	protected int persistent; //save or not
	protected int acknowledgeMode;
	protected Connection connection;
	protected Session session;
	
	public MessageBrokerConnection(String host, int port, String user, String password, ConnectionType type, 
									String destinationName, int persistent, int acknowledgeMode) throws JMSException {
		
		this.host = host;
		this.port = port;
		this.user = user;
		this.password = password;
		this.type = type;
		this.destinationName = destinationName;
		this.persistent = persistent;
		this.acknowledgeMode = acknowledgeMode;
		
		this.createConnection();
		
	}
	
	abstract protected void createConnection() throws JMSException;
	abstract public MessageConsumer createConsumer() throws JMSException;
	abstract public MessageProducer createProducer() throws JMSException;
	
	
}
