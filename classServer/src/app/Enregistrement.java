package app;

public class Enregistrement implements Comparable {

    private int  id ;
    private String clé;

    private int demandes;

    public Enregistrement(int id, String clé) {
        this.id = id;
        this.clé = clé;
        this.demandes = 0;
    }

    public int getDemandes() {
        return demandes;
    }
    public int getId() {return id;}
    public String getClé() {return clé;}
    public void increment(){this.demandes++;}



    @Override
    public int compareTo(Object o) {
        if (this == o) return 0;
        Enregistrement that = (Enregistrement) o;
        return that.getId()-this.getId();
    }
}