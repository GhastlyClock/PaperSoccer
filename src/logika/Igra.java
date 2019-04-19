package logika;

import java.util.*;

public class Igra {
	// Velikost igralne plosce je N x M.
	public static final int STOLPEC = 7;
	public static final int VRSTICA = 9;
	
	// Igralno polje
	private Tocka[][] plosca;
	
	// Mnozica potez
	Set<Povezava> povezaneTocke;
	
	
	// OPOMBA: gol bo na mestu (0, M/2) in (N+1, M/2)
	// Polje bo na mestih od (1, *) do (N, *)
	public Igra() {
		plosca = new Tocka[VRSTICA+2][STOLPEC];
		// Vrstici moramo dodati 2 zaradi golov
		for (int i = 0; i <= VRSTICA+1; ++i) {
			for (int j = 0; j < STOLPEC; ++j) {
				Tocka v = new Tocka(i, j);
				plosca[i][j] = v;
			}
		}
	}
	
	
	/**
	 * Nova kopija dane igre
	 * 
	 * @param igra
	 */
	public Igra(Igra igra) {
		plosca = new Tocka[STOLPEC][VRSTICA];
		povezaneTocke = new HashSet<Povezava>();
		for (int i = -1; i <= VRSTICA+1; ++i) {
			for (int j = -1; j <= STOLPEC; ++j) {
				plosca[i][j].veljavnePoteze(STOLPEC, VRSTICA);
				plosca[i][j] = igra.plosca[i][j];
			}
		}
	}
	
	
}
