package org.geektimes.event.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.geektimes.event.EventListener;
import org.geektimes.event.distributed.DistributedEventObject;
import org.geektimes.event.reactive.stream.ListenerSubscriberAdapter;
import org.geektimes.reactive.streams.SimplePublisher;

import javax.jms.*;
import java.util.EventObject;

/**
 * JmsEventPublisher
 *
 * @author qrXun on 2021/9/1
 */
public class JmsEventPublisher {

    private final SimplePublisher<EventObject> simplePublisher;

    private final Connection connection;

    public JmsEventPublisher(String userName, String password, String brokerUrl, boolean hasConsumer) throws JMSException {
        simplePublisher = new SimplePublisher<>();
        ActiveMQConnectionFactory mqConnectionFactory = new ActiveMQConnectionFactory(userName, password, brokerUrl);
        connection = mqConnectionFactory.createConnection();
        connection.start();
        // Create a Session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create the destination (Topic or Queue)
        Destination destination = session.createQueue("test");

        // Create a MessageProducer from the Session to the Topic or Queue
        MessageProducer producer = session.createProducer(destination);
        addEventListener(event -> {
            if (event instanceof DistributedEventObject) {
                try {
                    TextMessage textMessage = session.createTextMessage("hello world");
                    producer.send(textMessage);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        if (!hasConsumer){
            MessageConsumer consumer = session.createConsumer(destination);
            // 创建一个监听器
            consumer.setMessageListener(message -> {
                try {
                    TextMessage textMessage = (TextMessage) message;
                    publish(new EventObject(textMessage.getText()));
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            });
        }


    }

    public void publish(Object event) {
        simplePublisher.publish(new DistributedEventObject(event));
    }

    private void publish(EventObject event) {
        simplePublisher.publish(event);
    }

    public void addEventListener(EventListener eventListener) {
        simplePublisher.subscribe(new ListenerSubscriberAdapter(eventListener));
    }

    public void close() throws JMSException {
        connection.close();
    }


    public static void main(String[] args) throws JMSException {
        JmsEventPublisher publisher = new JmsEventPublisher("admin", "admin", "tcp://localhost:61616", true);
        publisher.publish("hello world");
        publisher.close();
    }

}
