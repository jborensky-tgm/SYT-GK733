/**
 *
 */

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;

public class JMSChatReceiver implements MessageListener {

  private static String user = ActiveMQConnection.DEFAULT_USER;
  private static String password = ActiveMQConnection.DEFAULT_PASSWORD;
  private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
  private static String subject = "1";
  private Session session = null;
  private Connection connection = null;
  private MessageConsumer consumer = null;
  private Destination destination = null;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private String message;
	
  public JMSChatReceiver() {
	  // Create the connection.
	  try {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);
			connection = connectionFactory.createConnection();
			connection.start();
		
			// Create the session
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue( subject );
				  
			// Create the consumer
			consumer = session.createConsumer( destination );
	  } catch (Exception e) {
			System.out.println("[MessageConsumer] Caught: " + e);
			e.printStackTrace();
	  } /*finally {
			try { consumer.close(); } catch ( Exception e ) {}
			try { session.close(); } catch ( Exception e ) {}
			try { connection.close(); } catch ( Exception e ) {}
	  }*/
  } // end main

	public String receive() {
		// Start receiving
		TextMessage message = null;
		//System.out.println(consumer.toString());
		try {
			consumer.setMessageListener(this);
		} catch (JMSException e) {
			e.printStackTrace();
		}
		/*try {
			message = (TextMessage) consumer.receive();
			if ( message != null ) {
				long yourmilliseconds = System.currentTimeMillis();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date resultdate = new Date(yourmilliseconds);
				String stamp = sdf.format(resultdate);
				System.out.println("Message received: " + stamp );
				this.message = message.getText();
				message.acknowledge();
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
		*/
		return this.message;
	}

	public void end() {
		try { consumer.close(); } catch ( Exception e ) {}
		try { session.close(); } catch ( Exception e ) {}
		try { connection.close(); } catch ( Exception e ) {}
		try {
			connection.stop();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage) message;
		try {
			System.out.println( " received "
					+ textMessage.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
	
