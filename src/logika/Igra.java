package logika;

import java.util.*;

public class Igra {
	// Velikost igralne plosce je N x M.
	public static final int N = 7;
	public static final int M = 9;
	
	// Igralno polje
	private Tocka[][] plosca;
	
	// Mnozica potez
	Set<Povezava> povezaneTocke;
	
	public Igra() {
		plosca = new Tocka[N][M];
		for (int i = -1; i <= N; ++i) {
			for (int j = -1; j <= M; ++j) {
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
		plosca = new Tocka[N][M];
		povezaneTocke = new HashSet<Povezava>();
		for (int i = -1; i <= N; ++i) {
			for (int j = -1; j <= M; ++j) {
				plosca[i][j].veljavnePoteze(N, M);
				plosca[i][j] = igra.plosca[i][j];
			}
		}
	}
	
	
}
