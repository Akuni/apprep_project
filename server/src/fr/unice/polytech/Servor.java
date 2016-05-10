package fr.unice.polytech;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Servor extends UnicastRemoteObject implements Distante{

	public Servor(int numPort) throws RemoteException {
		super(numPort);
	}
	
	public Servor() throws RemoteException {
	}

	public void echo() throws RemoteException {
		System.out.println("Hello");
		
	}

	public String sayHello() throws RemoteException {
		System.out.println("   ");
		return "Hello";
	}

	public Resultat sayResultat() throws RemoteException {
		Resultat r = new Resultat();
		r.i = 5001;
		return r;
	}

}
