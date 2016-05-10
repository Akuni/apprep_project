package app;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;
import java.util.Map;

public class Servor extends UnicastRemoteObject implements ICommunication {

	Map<String, Serializable> map;

	public Servor(int numPort) throws RemoteException {
		super(numPort);
		map = new Hashtable<String, Serializable>();
	}
	
	public Servor() throws RemoteException {
		map = new Hashtable<String, Serializable>();
	}

	@Override
	public boolean rebind(String key, Serializable serializable) {
		System.out.println("Calling rebing method with " + key + " key !");
		map.put(key, serializable);
		return true;
	}

	@Override
	public Serializable lookup(String key) {
		if(map.containsKey(key)){
			return map.get(key);
		} else {
			return null;
		}
	}

	@Override
	public String[] getSomeInformation(int amount) {
		return new String[0];
	}

	@Override
	public String[] getSomeService(int amount) {
		return new String[0];
	}
}
