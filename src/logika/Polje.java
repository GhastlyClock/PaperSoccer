package logika;

import java.util.*;

public class Polje {
	
	// Dogovor: v - vrstica, s - stolpec
	private int x, y;
	public static final Set<int[]> mozniPremiki;
	public Set<int[]> veljavnePoteze;
	
	static {
		
		// Ustvarim mnozico vseh moznih premikov, katero bomo uporabljali pri vsaki potezi
		mozniPremiki = new HashSet<>();
		for (int i = -1; i <= 1; ++i) {
			for (int j = -1; j <= 1; ++j) {
				if (i == 0 && j == 0) continue;
				int[] v = {i, j};
				mozniPremiki.add(v);
			}
		}
	}
	
	public Polje(int x, int y) {
		// Vsaki tocki bomo morali podati polozaj, ki bo enolicno dolocal tocko
		this.x = x;
		this.y = y;
		veljavnePoteze = new HashSet<>();
	}
	
	@Override
	public String toString() {
		return "Polje (" + x + "," + y + ")";
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void nastavitevVeljavnePoteze (int stVrstic, int stStolpcev) {
		// y koordinata gola
		int sredina = stStolpcev/2;
		
		// Posebej pogledamo za tocke izven igrisca oziroma v golu		
		// Ce tocka ni v igralnem polju, potem nima veljavnih potez
		if (x == 0 || x == stVrstic+1) return;
		if (y == sredina && (x == 0 || x == stVrstic+1)) {
			for (int[] premik : mozniPremiki) {
				int[] v = {premik[0], premik[1]};
				veljavnePoteze.add(v);
			}
		}
		else if (x == 1 || x == stVrstic || y == 0 || y == stStolpcev-1) {
			// Tocke na robu
			for (int[] premik : mozniPremiki) {
				int prX = x + premik[0];
				int prY = y + premik[1];
				
				boolean pogoj1 = prY == sredina && (prX == 0 || prX == stVrstic + 1);
				boolean pogoj2 = (prX > 1 && prX < stVrstic) && (prY > 0 && prY < stStolpcev - 1);
				// Gledamo še za tocko pred golom
				boolean pogoj3 = prY == sredina && (prX == 1 || prX == stVrstic);
				if (pogoj1 || pogoj2 || pogoj3) {
					veljavnePoteze.add(premik);
				}
			}
		} else {

			// Drugace je tocka v polju
			// Ce je tocka v polju so mozne poteze vse
			for (int[] premik : mozniPremiki) {
				int[] v = {premik[0], premik[1]};
				veljavnePoteze.add(v);
			}
		}
		return;
	}
	
	public void odstraniPotezo(int x, int y) {
		for (int[] premik : veljavnePoteze) {
			if (premik[0] == x && premik[1] == y) {
				veljavnePoteze.remove(premik);
				return;
			}
		}
	}
	
	
	
}