package app;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientRMI {

	/**
	 * VM options
	 * -Djava.security.policy=java.policy -Djava.rmi.server.codebase=http://xxx:1234/
	 * xxx = donné par le serveur de classe une fois lancé
	 */
	
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
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

}
