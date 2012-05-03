package org.mdissjava.notifier.broker.connection;

import static org.junit.Assert.assertSame;

import java.io.Serializable;
import java.util.Random;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mdissjava.notifier.broker.connection.MessageBrokerConnection.ConnectionType;
import org.mdissjava.notifier.event.MdissEvent;
import org.mdissjava.notifier.event.VerifyAccountEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class STOMPConnectionTest {

	STOMPConnection connection;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Before
	public void setUp() throws Exception {
		
		String destinationName = "testingQueue";
		
		connection = new STOMPConnection(ConnectionType.QUEUE, destinationName, 
										DeliveryMode.NON_PERSISTENT, Session.AUTO_ACKNOWLEDGE);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void publisherConsumerQueueTest() throws JMSException {
		
		Random generator = new Random();
		int MESSAGES = generator.nextInt(49) + 1;
		
		MessageConsumer consumer = connection.createConsumer();
		MessageProducer producer = connection.createProducer();
		
		Session session = connection.getSession();
		
		//produce X messages message (from 1 to 50)
		VerifyAccountEvent event  = new VerifyAccountEvent("slok");
		ObjectMessage msg = session.createObjectMessage(event);
		for (int i=0; i < MESSAGES; i++)
		{
			//this.logger.info("New Message event generated");
			producer.send(msg);
		}
		
		this.logger.info("Total messages generated: {}", MESSAGES);
		
		//produce the event that triggers the shutdown of the listener (we only have one listener in the queue)
		TextMessage exitMsg = session.createTextMessage("EXIT");
		producer.send(exitMsg);
		
		//Receive all the messages
		int countMessages = 0;
        while(true) {
        	Message msgReceived = consumer.receive();
            //is an object message?
        	if(msgReceived instanceof ObjectMessage) { // also instance of StompJmsObjectMessage
            	
        		Serializable obj = ((ObjectMessage) msgReceived).getObject();
            	//if is MdissEvent type object
        		if (obj instanceof MdissEvent)
            	{
            		//MdissEvent eventReceived = (MdissEvent)obj;
            		//this.logger.info("New Message event received: {}", eventReceived.getEventType());
            		countMessages++;
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
        //connection.close();

        this.logger.info("Total messages received: {}", countMessages);
        assertSame(MESSAGES, countMessages);
        
	}

}
