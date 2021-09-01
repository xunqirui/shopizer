package org.geektimes.event.jms;

import org.geektimes.event.distributed.DistributedEventObject;

import javax.jms.JMSException;

/**
 * JmsEventSubscriber
 *
 * @author qrXun on 2021/9/1
 */
public class JmsEventSubscriber {

    public static void main(String[] args) throws JMSException {
        JmsEventPublisher publisher = new JmsEventPublisher("admin", "admin", "tcp://localhost:61616", false);
        publisher.addEventListener(event -> {
            if (!(event instanceof DistributedEventObject)) {
                System.out.println(event.getSource().toString());
            }
        });
    }

}
