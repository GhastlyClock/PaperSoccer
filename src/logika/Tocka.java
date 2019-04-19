package logika;

import java.util.*;

public class Tocka {
	
	// Dogovor: v - vrstica, s - stolpec
	int v, s;
	public static final Set<int[]> mozniPremiki;
	Set<int[]> veljavnePoteze;
	
	static {
		
		// Ustvarim mnozico vseh moznih premikov, katero bomo uporabljali pri vsaki potezi
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
		// Vsaki tocki bomo morali podati polozaj, ki bo enolicno dolocal tocko
		v = x;
		s = y;
	}
	
	public void veljavnePoteze (int stStolpcev, int stVrstic) {
		// Sredina nam povej, v katerem stolpcu se nahaja gol
		int sredina = stStolpcev/2;
		
		// Posebej pogledamo za tocke izven igrisca oziroma v golu		
		// Ce tocka ni v igralnem polju, potem nima veljavnih potez
		if (v == 0 || v == stStolpcev+1) return;
	
		// Da preverim ali je poteza pristala v polju brez roba ali v golu
		Set<int[]> poljeBrezRobaZGolom = new HashSet<int[]>();
		int[] gol1 = {0, sredina};
		int[] gol2 = {stVrstic+1, sredina};
		poljeBrezRobaZGolom.add(gol1);
		poljeBrezRobaZGolom.add(gol2);
		for (int i = 2; i < stVrstic; ++i) {
			// i = 1 je zgornji rob, i = stVrstic spodnji rob
			for (int j = 1; j < stStolpcev-1; ++j) {
				// j = 0 levi rob, i = stStolpcev-1 desni rob
				int[] polje = {i, j};
				poljeBrezRobaZGolom.add(polje);
			}
		}
		
		if (v == 1 || v == stVrstic || s == 0 || s == stStolpcev-1) {
			// Tocke na robu
			for (int[] premik : mozniPremiki) {
				int[] polje = {v + premik[0], s + premik[1]};
				// Ce bil premik v polje ali v gol potem je premik veljaven
				if (poljeBrezRobaZGolom.contains(polje)) {
					veljavnePoteze.add(premik);
				}
			}
		} else {
			// Drugace je tocka v polju
			// Ce je tocka v polju so mozne poteze vse
			for (int[] premik : mozniPremiki) {
				veljavnePoteze.add(premik);
			}
		}
		return;
	}
	
	
	
}