package app;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQQueueSession;

import javax.jms.*;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class ClientRMI implements MessageListener {

	/**
	 * VM options
	 * -Djava.security.policy=java.policy -Djava.rmi.server.codebase=http://xxx:1234/
	 * xxx = donné par le serveur de classe une fois lancé
	 */
	private Connection connect = null;
	private Session receiveSession = null;
	private MessageProducer sender = null;
	private Queue queue = null;

	public static void main(String args[]) {
		if (System.getSecurityManager() == null) {
			System.out.println("Generating a new security manager ...");
			System.setSecurityManager(new SecurityManager());
			System.out.println("Done !");
		} else {
			System.out.println("Security manager alread in place ...");
			System.out.println("Done !");
		}

		try {
			ICommunication id = (ICommunication) Naming.lookup("rmi://localhost:2000/Hello");
			IData res = (IData) id.lookup("first");
			System.out.println(res.getDataAsString());
			IService service = (IService) id.lookup("service");
			System.out.println(service.getInfo());


			//QUEUE
			new ClientRMI().Config(id);


		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

	private void Config(ICommunication s) {
		try {
			ConnectionFactory factory;
			factory = new ActiveMQConnectionFactory("user", "password", "tcp://localhost:61616");

			connect = factory.createConnection ("user", "password");

			configC(s);

			connect.start();

		} catch (JMSException jmse){
			jmse.printStackTrace();
		}
	}

	private void configC(ICommunication s) throws JMSException {
		receiveSession = connect.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue tQueue = null;
		try {
			tQueue = s.getQueueServiceQueue();
			MessageConsumer mc = receiveSession.createConsumer(tQueue);
			mc.setMessageListener(this);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}


	@Override
	public void onMessage(Message message) {
		try {
			System.out.println("Le message " + ((TextMessage)message).getText().toString() + " a bien été recu");
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
