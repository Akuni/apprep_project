package fr.unice.polytech;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Distante extends Remote {
	void echo() throws RemoteException;
	
	String sayHello() throws RemoteException;
	
	Resultat sayResultat() throws RemoteException;
}
