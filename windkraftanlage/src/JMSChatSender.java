
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.HashMap;

public class JMSChatSender implements MessageListener {

  private static String user = ActiveMQConnection.DEFAULT_USER;
  private static String password = ActiveMQConnection.DEFAULT_PASSWORD;
  private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
  private String subject;
  private Session session = null;
  private Connection connection = null;
  private MessageProducer producer = null;
  private MessageConsumer consumer = null;
  private Destination dest1 = null;
  private Destination dest2 = null;
  private HashMap<String, Boolean> map;
  private Windkraftanlage w1;


	public JMSChatSender(Windkraftanlage w, String id) {
	  // Create the connection.
	  subject = id;
	  w1 = w;
	  map = new HashMap<>();
	  try {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory( user, password, url );
			connection = connectionFactory.createConnection();
			connection.start();
		
			// Create the session
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			dest1 = session.createQueue( subject );
			dest2 = session.createQueue( subject+"check");
			//System.out.println(((Queue) dest1).getQueueName());
		  	//System.out.println(((Queue) dest2).getQueueName());


		  	//Create the producer.
			producer = session.createProducer(dest1);
			producer.setDeliveryMode( DeliveryMode.NON_PERSISTENT );
			consumer = session.createConsumer( dest2 );
			consumer.setMessageListener(this);
			System.out.println("--Übertragung gestartet--");
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
		  map.put(message.getText(),false);
		  try {
			  Thread.sleep(100);
		  } catch (InterruptedException e) {
			  e.printStackTrace();
		  }
		  if (map.get(message.getText()) == true) {
		  	w1.log(nachricht, true);
		  	System.out.println("SUCCESS");
		  } else {
			  w1.log(nachricht, false);
			  System.out.println("FAILED");
		  }
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

	@Override
	public void onMessage(Message message) {
		System.out.println("Message empfangen");
		TextMessage textMessage = (TextMessage) message;
		try {
			map.put(textMessage.getText(), true);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
