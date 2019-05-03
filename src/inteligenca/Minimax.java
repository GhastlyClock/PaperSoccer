package inteligenca;

import java.util.LinkedList;
import java.util.List;

import logika.Igra;
import logika.Igralec;
import logika.Premik;
import logika.Stanje;

public class Minimax {
	
	private static final int ZMAGA = (1 << 20);
	private static final int PORAZ = - ZMAGA;
	
	public static List<OcenjenPremik> oceniPremike(Igra igra, int globina, Igralec jaz) {
		List<OcenjenPremik> ocenjeniPremiki = new LinkedList<OcenjenPremik>();
		for (Premik p : igra.aktivnaTocka.veljavnePoteze) {
			Igra tempIgra = new Igra(igra);
			tempIgra.odigraj(p);
			int ocena = minimaxPozicija(tempIgra, globina-1, jaz);
			ocenjeniPremiki.add(new OcenjenPremik(p, ocena));
		}
		return ocenjeniPremiki;
	}
	
	public static int minimaxPozicija(Igra igra, int globina, Igralec jaz) {
		Stanje stanje = igra.stanje();
		switch (stanje) {
		case ZMAGA_A: return (jaz == Igralec.A ? ZMAGA : PORAZ);
		case ZMAGA_B: return (jaz == Igralec.B ? ZMAGA : PORAZ);
		default:
			// Nekdo je na potezi
			if (globina == 0) return oceniPozicijo(igra, jaz);
			// globina > 0
			List<OcenjenPremik> ocenjeniPremiki = oceniPremike(igra, globina, jaz);
			if (igra.naPotezi == jaz) return maxOcena(ocenjeniPremiki);
			else return minOcena(ocenjeniPremiki);
		}
	}
	
	public static int maxOcena(List<OcenjenPremik> ocenjeniPremiki) {
		int max = PORAZ;
		for (OcenjenPremik ocenjenPremik : ocenjeniPremiki) {
			if (ocenjenPremik.vrednost > max) max = ocenjenPremik.vrednost;
		}
		return max;
	}
	
	public static Premik maxPremik(List<OcenjenPremik> ocenjeniPremiki) {
		
		int max = PORAZ;
		Premik premik = null;
		for (OcenjenPremik ocenjenPremik : ocenjeniPremiki) {
			if (ocenjenPremik.vrednost > max) {
				max = ocenjenPremik.vrednost;
				premik = ocenjenPremik.premik;
			}
			else if (ocenjenPremik.vrednost == max) {
				 if (Math.round(Math.random()) == 0) {
					// Da se izognemo nenehnemu ponavljanju potez, 
					// bo racunalnik izbiral med takimi, katerih vrednost je najvecja.
					premik = ocenjenPremik.premik;
				 }
			}
		}
		return premik;
	}
	
	public static int minOcena(List<OcenjenPremik> ocenjeniPremiki) {
		int min = ZMAGA;
		for (OcenjenPremik ocenjenPremik : ocenjeniPremiki) {
			if (ocenjenPremik.vrednost < min) min = ocenjenPremik.vrednost;
		}
		return min;
	}
	
	private static int round(double x) {
		return (int)(x + 0.5);
	}


	private static double razdalja(int x1, int y1, int x2, int y2) {
		return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
	}
	
	public static int oceniPozicijo(Igra igra, Igralec jaz) {
		int y1 = Igra.STOLPEC / 2;
		int x1 = jaz == Igralec.A ? 0 : Igra.VRSTICA + 1;
		int x2 = igra.aktivnaTocka.getX();
		int y2 = igra.aktivnaTocka.getY();
		double r = razdalja(x1, y1, x2, y2);
		int k = round(r);
		return ZMAGA - k;
	}

}
