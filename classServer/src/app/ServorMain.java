package app;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class ServorMain {

	/**
	 * Vm options
	 * -Djava.rmi.server.hostname="127.0.0.1" -Djava.rmi.server.codebase=http://xxx:1234/
	 * xxx = donné par le serveur de classe une fois lancé
	 */

	public static void main(String args[]) {
		try {
			Servor s = new Servor();
			System.out.println("Binding Servor ...");
			Naming.rebind("rmi://localhost:2000/Servor", s);
			System.out.println("Done ! ");
		} catch (RemoteException | MalformedURLException e) {
			e.printStackTrace();
		}
	}

}
