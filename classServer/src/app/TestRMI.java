package app;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class TestRMI {

	/**
	 * Vm options
	 * -Djava.rmi.server.hostname="127.0.0.1" -Djava.rmi.server.codebase=http://xxx:1234/
	 * xxx = donné par le serveur de classe une fois lancé
	 */

	public static void main(String args[]) {
		try {
			Servor s = new Servor();
			System.out.println("Binding Hello ...");
			Naming.rebind("rmi://localhost:2000/Hello", s);
			System.out.println("Done ! ");
			s.rebind("first", new Data("MY DATA !"));
			s.rebind("service", new RentService("APPREP_TEAM"));
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
