package app;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by Salah on 23/05/2016.
 */
public class QueueService {

    Queue q;
    Session sp;

    public javax.jms.Queue subscribeQueue(){
        return q;
    }

    public void createQueue() {
        ConnectionFactory cf;
        cf = new ActiveMQConnectionFactory("user", "password", "tcp://localhost:61616");
        Connection c = null;
        try {
            c = cf.createConnection("user","password");
            c.start();
            sp = c.createSession(false, Session.AUTO_ACKNOWLEDGE);
            q = sp.createQueue("test");
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
