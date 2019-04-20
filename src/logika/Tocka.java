package logika;

public class Tocka {
	int x, y;
	int sirinaPolja, visinaPolja;
	
	public Tocka(int steviloVrstic, int steviloStolpcev) {
		sirinaPolja = steviloVrstic;
		visinaPolja = steviloStolpcev;
		
		// Na zacetku bomo tocko postavili v sredisce
		this.reset();
	}
	
	public void reset() {
		x = sirinaPolja / 2;
		y = sirinaPolja / 2;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	@Override
	public String toString () {
		return "Tocka (" + x + "," + y + ")";
	}
}
