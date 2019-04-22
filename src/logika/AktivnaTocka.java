package logika;

public class AktivnaTocka {
	int x, y;
	int sirinaPolja, visinaPolja;
	
	public AktivnaTocka(int steviloVrstic, int steviloStolpcev) {
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
		return "Tocka [x=" + x + ", y=" + y + "]";
	}
}
