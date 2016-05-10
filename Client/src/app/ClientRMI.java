package app;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientRMI {
 	//-Djava.rmi.server.hostname="192.168.0.13"
	//-Djava.rmi.server.hostname="172.19.250.132"
	
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
			Serializable res = id.lookup("first");
			System.out.println(res.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

}
