package app;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICommunication extends Remote {

	public boolean rebind(String key, Serializable serializable) throws RemoteException;


	public Serializable lookup(String key) throws RemoteException;


	// TODO really return string[] ? maybe a generic type "Data" ?
	public String[] getSomeInformation(int amount) throws RemoteException;


	public String[] getSomeService(int amount) throws RemoteException;

}
