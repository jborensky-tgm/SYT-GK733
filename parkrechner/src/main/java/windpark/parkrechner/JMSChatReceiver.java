package windpark.parkrechner; /**
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
  private static String subject;
  private Parkrechner zentral;
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
	
  public JMSChatReceiver(Parkrechner p1, String s) {
	  // Create the connection.
	  this.zentral = p1;
	  this.subject = s;
	  try {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);
			connection = connectionFactory.createConnection();
			connection.start();
		
			// Create the session
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue( subject );
				  
			// Create the consumer
			consumer = session.createConsumer( destination );
		  	consumer.setMessageListener(this);
	  } catch (Exception e) {
			System.out.println("[MessageConsumer] Caught: " + e);
		  System.out.println("Hier3");
			e.printStackTrace();
	  } /*finally {
			try { consumer.close(); } catch ( Exception e ) {}
			try { session.close(); } catch ( Exception e ) {}
			try { connection.close(); } catch ( Exception e ) {}
	  }*/
  } // end main

	public void end() {
		try { consumer.close(); } catch ( Exception e ) {}
		try { session.close(); } catch ( Exception e ) {}
		try { connection.close(); } catch ( Exception e ) {}
		try {
			connection.stop();
		} catch (Exception e) {
			System.out.println("Hier2");
			e.printStackTrace();
		}
	}

	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage) message;
		try {
			this.message = textMessage.getText();
			long yourmilliseconds = System.currentTimeMillis();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date resultdate = new Date(yourmilliseconds);
			String stamp = sdf.format(resultdate);
			System.out.println("Message received: " + stamp );
			this.zentral.empfangen(this.message);
			message.acknowledge();
		} catch (Exception e) {
			System.out.println("Hier1");
			e.printStackTrace();
		}
	}
}
	
