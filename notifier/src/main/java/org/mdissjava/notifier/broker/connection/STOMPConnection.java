package org.mdissjava.notifier.broker.connection;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.fusesource.stomp.jms.StompJmsConnectionFactory;
import org.fusesource.stomp.jms.StompJmsDestination;

public class STOMPConnection extends MessageBrokerConnection{

	
	private Destination destination; 
	
	public STOMPConnection(String host, int port, String user, String password, ConnectionType type, 
			String destinationName, int persistent, int acknowledgeMode) throws JMSException {
		
		super(host, port, user, password, type, destinationName, persistent, acknowledgeMode);
		
		this.destination = createDestination();
		
	}
	
	/**
	 * Default connection
	 * 
	 * @param type
	 * @param destinationName
	 * @param persistent
	 * @param acknowledgeMode
	 */
	public STOMPConnection(ConnectionType type, String destinationName, int persistent, int acknowledgeMode) throws JMSException{
		
		this("localhost", 61613, "admin", "password", type, destinationName, persistent, acknowledgeMode);
		
	}
	
	
	@Override
	protected synchronized void createConnection() throws JMSException {
		
        this.logger.info("Initializing Apollo Connection");
        
        if(connection != null) {
           try { connection.stop(); } catch (Exception e) {}
           try { connection.close(); } catch (Exception e) {}
        }
        
        StompJmsConnectionFactory factory = new StompJmsConnectionFactory();
        factory.setBrokerURI("tcp://" + this.host + ":" + this.port);
        this.connection = factory.createConnection(this.user, this.password);
        connection.start();
        this.session = connection.createSession(false, this.acknowledgeMode);
        
	}
	
	@Override
	public MessageConsumer createConsumer() throws JMSException {
		
		return session.createConsumer(this.destination);
	}

	@Override
	public MessageProducer createProducer() throws JMSException {
		
		return session.createProducer(this.destination);
	}

	
	public Session getSession(){
		return this.session;
	}
	
	private Destination createDestination()
	{
		String type = this.type == ConnectionType.QUEUE? "queue": "topic";
		
		String destination = "/" + type + "/" + this.destinationName; 
		return new StompJmsDestination(destination);
	}
	

	

}
