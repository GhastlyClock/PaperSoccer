package inteligenca;

import java.util.LinkedList;
import java.util.List;

import logika.Igra;
import logika.Igralec;
import logika.Premik;
import logika.Stanje;

public class Minimax {
	
	private static final int ZMAGA = (1 << 20);
	private static final int ZGUBA = - ZMAGA;
	
	public static List<OcenjenPremik> oceniPremike(Igra igra, int globina, Igralec jaz) {
		List<OcenjenPremik> ocenjeniPremiki = new LinkedList<OcenjenPremik>();
		for (Premik p : igra.aktivnaTocka.veljavnePoteze) {
			Igra tempIgra = new Igra(igra);
			tempIgra.odigraj(p);
			int ocena = minimaxPozicijo (tempIgra, globina-1, jaz);
			ocenjeniPremiki.add(new OcenjenPremik(p, ocena));
		}
		return ocenjeniPremiki;
	}
	
	public static int minimaxPozicijo(Igra igra, int globina, Igralec jaz) {
		Stanje stanje = igra.stanje();
		switch (stanje) {
		case ZMAGA_A: return (jaz == Igralec.A ? ZMAGA : ZGUBA);
		case ZMAGA_B: return (jaz == Igralec.B ? ZMAGA : ZGUBA);
		default:
			// Nekdo je na potezi
			if (globina == 0) {return oceniPozicijo(igra, jaz);}
			// globina > 0
			List<OcenjenPremik> ocenjeniPremiki = oceniPremike(igra, globina, jaz);
			if (igra.naPotezi == jaz) {return maxOcena(ocenjeniPremiki);}
			else {return minOcena(ocenjeniPremiki);}
		}
	}
	
	public static int maxOcena(List<OcenjenPremik> ocenjeniPremiki) {
		int max = ZGUBA;
		for (OcenjenPremik ocenjenaPoteza : ocenjeniPremiki) {
			if (ocenjenaPoteza.vrednost > max) {max = ocenjenaPoteza.vrednost;}
		}
		return max;
	}
	
	public static Premik maxPremik(List<OcenjenPremik> ocenjeniPremiki) {
		int max = ZGUBA;
		Premik premik = null;
		for (OcenjenPremik ocenjenPremik : ocenjeniPremiki) {
			if (ocenjenPremik.vrednost >= max) {
				max = ocenjenPremik.vrednost;
				premik = ocenjenPremik.premik;
			}
		}
		return premik;
	}
	
	public static int minOcena(List<OcenjenPremik> ocenjeniPremiki) {
		int min = ZMAGA;
		for (OcenjenPremik ocenjenPremik : ocenjeniPremiki) {
			if (ocenjenPremik.vrednost < min) {min = ocenjenPremik.vrednost;}
		}
		return min;
	}
	public static float razdalja(int x1, int y1, int x2, int y2) {
		return (float) Math.sqrt((x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2));
	}
	public static int oceniPozicijo(Igra igra, Igralec jaz) {
		int x1 = igra.aktivnaTocka.getX();
		int y1 = igra.aktivnaTocka.getY();
		int y2 = Igra.STOLPEC / 2;
		int x2 = jaz == Igralec.A ? 0 : Igra.VRSTICA + 1;
		int merilo = Float.floatToIntBits(razdalja(0, 0, Igra.VRSTICA, Igra.STOLPEC)) / 10;
		int ocena = 20 - (Float.floatToIntBits(razdalja(x1,y1,x2,y2))) / merilo;
		return ocena;
	}

}
