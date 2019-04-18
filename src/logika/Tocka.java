package logika;

import java.util.*;

public class Tocka {
	
	// Dogovor: y predstavlja vrstico, x predstavlja stolpec ALJAZ: Zakaj pa to? V Platno.java je x vrstica in y stolpec.
	public int x, y;
	public static final Set<int[]> mozniPremiki;
	public Set<int[]> veljavnePoteze;
	
	static {
		
		// Ustvarim mno�ico vseh mo�nih premikov, katero bomo uporabljali pri vsaki potezi
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
		// Vsaki tocki bomo morali podati polo�aj, ki bo enolicno dolocal tocko
		this.x = x;
		this.y = y;
	}
	
	public void veljavnePoteze (int stVrstic, int stStolpcev) {
		
		// Na zacetku v mno�ico veljavnih potez dodamo vse mo�ne premike
		for (int[] premik : mozniPremiki) veljavnePoteze.add(premik);
		
		// Moramo pogledati v katero smer je pomik veljaven, ce ni ga odstranimo
		// 1. Pogledamo ali tocka le�i na katerem od levem ali desnem robu
		if (x == 0) {
			for (int i = -1; i < 1; ++i) {
				// Do something....
			}
		}
	}
	
	
	
}
