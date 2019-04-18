package logika;

import java.util.*;

public class Tocka {
	
	// Dogovor: y predstavlja vrstico, x predstavlja stolpec
	int x, y;
	public static final Set<int[]> mozniPremiki;
	public Set<int[]> veljavnePoteze;
	
	static {
		
		// Ustvarim množico vseh možnih premikov, katero bomo uporabljali pri vsaki potezi
		mozniPremiki = new HashSet<>();
		for (int i = -1; i <= 1; ++i) {
			for (int j = -1; j <= 1; ++j) {
				int[] v = {i, j};
				mozniPremiki.add(v);
			}
		}
		int[] n = {0,0};
		mozniPremiki.remove(n);
	}
	
	public Tocka(int x, int y) {
		// Vsaki tocki bomo morali podati položaj, ki bo enolicno dolocal tocko
		this.x = x;
		this.y = y;
	}
	
	public void veljavnePoteze (int stStolpcev, int stVrstic) {		
		// Pogledamo moznosti za vse robne tocke
		// Tocka je robna kadar velja en od naslednjih pogojev
		// Torej premik je lahko samo v polje
		int sredina = stStolpcev/2;
		
		// Posebej pogledamo za tocke izven igrisca oziroma v golu
		if (y == -1 && x == sredina) {
			for (int i = -1; i <= 1; ++i) {
				int[] v = {i, 1};
				mozniPremiki.add(v);
			}
			return;
		} else if (y == stVrstic && x == sredina) {
			for (int i = -1; i <= 1; ++i) {
				int[] v = {i, -1};
				mozniPremiki.add(v);
			}
			return;
		} else if (y == stVrstic || y == -1) {
			return;
		}
	
		// Da preverim ali je poteza pristala v polju brez roba ali v golu
		Set<int[]> poljeBrezRobaZGolom = new HashSet<int[]>();
		int[] gol1 = {-1, sredina};
		int[] gol2 = {stVrstic, sredina};
		poljeBrezRobaZGolom.add(gol1);
		poljeBrezRobaZGolom.add(gol2);
		for (int i = 1; i < (stStolpcev - 1); ++i) {
			for (int j = 1; j < (stVrstic - 1); ++j) {
				int[] polje = {i, j};
				poljeBrezRobaZGolom.add(polje);
			}
		}
		
		if (x == 0 || x == (stStolpcev - 1) || y == 0 || y == (stVrstic - 1)) {
			for (int[] premik : mozniPremiki) {
				int[] polje = {x + premik[0], y + premik[1]};
				if (poljeBrezRobaZGolom.contains(polje)) {
					veljavnePoteze.add(premik);
				}
			}
		} else {
			// Drugace je tocka v polju
			// Ce je tocka v polju so mozne poteze ravno vse
			for (int[] premik : mozniPremiki) {
				veljavnePoteze.add(premik);
			}
		}
		return;
	}
	
	
	
}