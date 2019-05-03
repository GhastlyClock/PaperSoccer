package logika;


public class Povezava {
	
	public Polje tocka1, tocka2;
	public Igralec igralec;
	
	public Povezava(Polje tocka1, Polje tocka2, Igralec igralec) {
		this.tocka1 = tocka1;
		this.tocka2 = tocka2;
		this.igralec = igralec;
	}
	
	@Override
	public String toString() {
		return "Povezava [" + tocka1 + ", " + tocka2 + ", " + igralec + "]";
	}
	
}
