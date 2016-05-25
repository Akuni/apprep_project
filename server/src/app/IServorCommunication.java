package app;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IServorCommunication extends Remote {

    public boolean rebind(String key, Serializable serializable) throws RemoteException;


    public Serializable lookup(String key) throws RemoteException;


    List<Object> getLast(int x) throws RemoteException;

    List<String> lastKeys(int x) throws RemoteException;

    List<String> popularKeys(int x) throws RemoteException;

    List<Object> exactSearch(String type)throws RemoteException;

    List<Object> deepSearch(String type) throws RemoteException;

    javax.jms.Queue getQueueServiceQueue()throws RemoteException;

    // TODO really return string[] ? maybe a generic type "app.Data" ?
    public String[] getSomeInformation(int amount) throws RemoteException;


    public String[] getSomeService(int amount) throws RemoteException;

}
