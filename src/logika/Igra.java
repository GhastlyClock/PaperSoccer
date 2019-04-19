package logika;

import java.util.*;

public class Igra {
	// Velikost igralne plosce je N x M.
	public static final int STOLPEC = 7;
	public static final int VRSTICA = 9;
	
	// Igralno polje
	public Tocka[][] plosca;
	
	// Spremljanje potez
	private Tocka aktivnaTocka;
	
	// Mnozica potez
	Set<Povezava> povezaneTocke;
	
	// Igralec, ki je trenutno na potezi.
	public Igralec naPotezi;
	
	// Mnozica vseh povezanih tock
	public Set<Povezava> povezave;
	
	// OPOMBA: gol bo na mestu (0, STOLPEC/2) in (VRSTICA+1, STOLPEC/2)
	// Polje bo na mestih od (1, *) do (VRSTICA, *)
	public Igra() {
		plosca = new Tocka[VRSTICA+2][STOLPEC];
		// Vrstici moramo dodati 2 zaradi golov
		for (int i = 0; i <= VRSTICA+1; ++i) {
			for (int j = 0; j < STOLPEC; ++j) {
				Tocka v = new Tocka(i, j);
				v.nastavitevVeljavnePoteze(VRSTICA, STOLPEC);
				plosca[i][j] = v;
			}
		}
		naPotezi = Igralec.A;
		// Vrstic je VRSTICA+2 zato moramo paziti pri iskanju srediscne koordinate
		aktivnaTocka = new Tocka((VRSTICA/2)+1, STOLPEC/2);
		povezave = new HashSet<>();
	}
	
	
	/**
	 * Nova kopija dane igre
	 * 
	 * @param igra
	 */
	public Igra(Igra igra) {
		plosca = new Tocka[STOLPEC][VRSTICA];
		povezaneTocke = new HashSet<Povezava>();
		for (int i = 0; i <= VRSTICA+1; ++i) {
			for (int j = 0; j <= STOLPEC; ++j) {
				plosca[i][j].nastavitevVeljavnePoteze(STOLPEC, VRSTICA);
				plosca[i][j] = igra.plosca[i][j];
			}
		}
		naPotezi = igra.naPotezi;
		
		// Aktivno tocko postavimo v izhodisce, ki je 
		aktivnaTocka.x = (VRSTICA / 2) + 1;
		aktivnaTocka.y = STOLPEC / 2;
		
		//Zbrisati moramo vse povezave
		povezave.clear();
	}
	
	
}
