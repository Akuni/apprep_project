package app;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;

public class ServerMain {

    /**
     * Vm options
     * -Djava.rmi.server.hostname="127.0.0.1" -Djava.rmi.server.codebase=http://xxx:1234/
     * xxx = donné par le serveur de classe une fois lancé
     */
    private Connection connect = null;
    private Session sendSession = null;
    private MessageProducer sender = null;
    private Queue queue = null;

    public static void main(String args[]) {
        try {
            Servor s = new Servor();
            System.out.println("Binding Hello ...");
            Naming.lookup("Hello"); //("rmi://localhost:2000/Hello", s);
            System.out.println("Done ! ");
            s.rebind("first", new Data("MY DATA !"));
            s.rebind("service", new RentService("APPREP_TEAM"));



            //QUEUE
            new ServerMain().Config(s);


        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void Config(Servor s) {
        try {
            ConnectionFactory factory;
            factory = new ActiveMQConnectionFactory("user", "password", "tcp://localhost:61616");

            connect = factory.createConnection ("user", "password");

            configP(s);

            connect.start();

            Scanner sc = new Scanner(System.in);
            System.out.println("Please Enter our message : \n");
            String m = sc.nextLine();
            postMessageInQ(m);

        } catch (JMSException jmse){
            jmse.printStackTrace();
        }
    }

    private void configP(Servor s) throws JMSException {
        sendSession = connect.createSession(false, Session.AUTO_ACKNOWLEDGE);
        try {
            queue = s.getQueueServiceQueue();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        sender = sendSession.createProducer(queue);
    }

    private void postMessageInQ(String mess) throws JMSException {
        TextMessage msg = sendSession.createTextMessage();
        msg.setText(mess);
        sender.send(queue,msg);
    }

}
