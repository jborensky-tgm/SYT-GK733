
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSChatSender {

  private static String user = ActiveMQConnection.DEFAULT_USER;
  private static String password = ActiveMQConnection.DEFAULT_PASSWORD;
  private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
  private String subject;
  private Session session = null;
  private Connection connection = null;
  private MessageProducer producer = null;
  private Destination destination = null;
	
  public JMSChatSender(String id) {
	  // Create the connection.
	  subject = id;
	  try {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory( user, password, url );
			connection = connectionFactory.createConnection();
			connection.start();
		
			// Create the session
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue( subject );
			System.out.println(((Queue) destination).getQueueName());
				  
			// Create the producer.
			producer = session.createProducer(destination);
			producer.setDeliveryMode( DeliveryMode.NON_PERSISTENT );
	  } catch (Exception e) {
			System.out.println("[MessageProducer] Caught: " + e);
			e.printStackTrace();
	  } /*finally {
			try { producer.close(); } catch ( Exception e ) {}
			try { session.close(); } catch ( Exception e ) {}
			try { connection.close(); } catch ( Exception e ) {}
	  }*/
  }

  public void send(String nachricht) {
	  // Create the message
	  TextMessage message = null;
	  try {
		  message = session.createTextMessage(nachricht);
		  producer.send(message);
		  System.out.println( message.getText() );
	  } catch (JMSException e) {
		  e.printStackTrace();
	  }
  }

  public void end() {
  		  try { producer.close(); } catch ( Exception e ) {}
		  try { session.close(); } catch ( Exception e ) {}
		  try { connection.close(); } catch ( Exception e ) {}
	  try {
		  connection.stop();
	  } catch (JMSException e) {
		  e.printStackTrace();
	  }

  }
}
