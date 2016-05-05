package td1;

public class ResultatSousClasse extends Resultat {
	private int entier;
	public String string;
	
	public ResultatSousClasse(int entier, String string) {
		this.entier = entier;
		this.string = string;
	}
	
	public String toString() {
		return super.toString() + "\nentier = " + entier + "\nstring = " + string;
	}
	
	public int getEntier() {
		return this.entier;
	}
}
