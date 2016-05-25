package app;

public class Enregistrement implements Comparable {

    private int  id ;
    private String key;

    private int demands;

    public Enregistrement(int id, String key) {
        this.id = id;
        this.key = key;
        this.demands=0;
    }

    public int getDemands() {
        return demands;
    }
    public int getId() {return id;}
    public String getKey() {return key;}
    public void increment(){this.demands++;}



    @Override
    public int compareTo(Object o) {
        if (this == o) return 0;
        Enregistrement that = (Enregistrement) o;
        return that.getId()-this.getId();
    }
}