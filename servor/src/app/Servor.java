package app;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import javax.jms.Queue;

public class Servor extends UnicastRemoteObject implements IServorCommunication {

    Map<String, Serializable> map;
    private Set<Enregistrement> historique;
    private int id = 0;


    private QueueService qs ;

    public Servor(int numPort) throws RemoteException {
        super(numPort);
        map = new Hashtable<String, Serializable>();
        historique = new TreeSet<>();
        qs = new QueueService();
    }

    public Servor() throws RemoteException {
        map = new Hashtable<String, Serializable>();
        qs = new QueueService();

    }

    @Override
    public boolean rebind(String key, Serializable serializable) {
        System.out.println("Calling rebing method with " + key + " key !");
        historique.add(new Enregistrement(id,key));
        map.put(key, serializable);
        return true;
    }

    @Override
    public Serializable lookup(String key) {
        for(Enregistrement e : historique){
            if(e.getKey().equals(key))
                e.increment();
        }
        if (map.containsKey(key)) {
            return map.get(key);
        } else {
            return null;
        }
    }

    @Override
    public List<Object> getLast(int x) throws RemoteException {
        if(x > map.size())
            x = map.size();
        List<Object> lasts = new ArrayList<>();
        for(Enregistrement e : historique){
            if(e.getId() >= x)
                lasts.add(map.get(e.getKey()));
        }
        return lasts;
    }

    @Override
    public List<String> lastKeys(int x) throws RemoteException {
        if(x > map.size())
            x = map.size();
        List<String> lasts = new ArrayList<>();

        for(Enregistrement e : historique){
            System.out.println(e.getId());
            if(e.getId() >= x)
                lasts.add(e.getKey());
        }

        return lasts;
    }

    @Override
    public List<String> popularKeys(int x) throws RemoteException {
        int max = 0;
        List<String> popular = new ArrayList<>();
        for (Enregistrement e : historique) {
            if (e.getDemands() == max) {
                popular.add(e.getKey());
            }
            if (e.getDemands() > max) {
                max = e.getDemands();
                popular.clear();
                popular.add(e.getKey());
            }
        }
        return popular;
    }

    @Override
    public List<Object> exactSearch(String type)throws RemoteException {
        List<Object> objects = new ArrayList<>();
        for (Map.Entry<String, Serializable> entry : map.entrySet()) {
            if(entry.getValue().getClass().getName().equals(type)){
                objects.add(entry.getValue());
            }
        }
        return objects;

    }

    @Override
    public List<Object> deepSearch(String type) throws RemoteException {
        List<Object> objects = new ArrayList<>();
        for (Map.Entry<String, Serializable> entry : map.entrySet()) {
            boolean father = entry.getValue().getClass().getName().equals(type);
            boolean son  = entry.getValue().getClass().getSuperclass().getName().equals(type);
            if(father || son){
                objects.add(entry.getValue());
            }
        }
        return objects;

    }

    @Override
    public Queue getQueueServiceQueue()throws RemoteException {
        qs.createQueue();
        return qs.subscribeQueue();
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
