package fr.unice.polytech;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class TestRMI {
	public static void main(String args[]) {
		// -Djava.rmi.server.hostname="10.212.120.205"
		try {
			Servor s = new Servor();
			System.out.println("Binding Hello ...");
			Naming.rebind("rmi://localhost:2000/Hello", s);
			System.out.println("Done ! ");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
