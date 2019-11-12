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
	private MessageProducer producer = null;
	private MessageConsumer consumer = null;
	private Destination dest1 = null;
	private Destination dest2 = null;

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
			dest1 = session.createQueue( subject );
			  dest2 = session.createQueue( subject+"check");
			  //System.out.println(((Queue) dest1).getQueueName());
			  //System.out.println(((Queue) dest2).getQueueName());
				  
			// Create the consumer
			consumer = session.createConsumer( dest1 );
		  	consumer.setMessageListener(this);

		  // Create the producer.
		  producer = session.createProducer(dest2);
		  producer.setDeliveryMode( DeliveryMode.NON_PERSISTENT );
		  System.out.println("--Übertragung gestartet--");
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
		try { producer.close(); } catch ( Exception e ) {}
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
			TextMessage m1 = null;
			try {
				//System.out.println(this.message);
				//System.out.println(producer);
				m1 = session.createTextMessage(this.message);
				//System.out.println(m1);
				producer.send(m1);
				System.out.println("Bestätigung versendet");
				//System.out.println("Bestätigung nicht versendet");
			} catch (JMSException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			System.out.println("Hier1");
			e.printStackTrace();
		}
	}
}
	
