package logika;

import java.util.*;

public class Igra {
	// Velikost igralne plosce je N x M.
	public static final int STOLPEC = 7;
	public static final int VRSTICA = 9;
	
	// Igralno polje
	private Polje[][] plosca;
	
	// Spremljanje potez
	private Tocka aktivnaTocka;
	
	// Igralec, ki je trenutno na potezi.
	public Igralec naPotezi;
	
	// Mnozica vseh povezanih tock
	public Set<Povezava> povezave;
	
	// OPOMBA: gol bo na mestu (0, STOLPEC/2) in (VRSTICA+1, STOLPEC/2)
	// Polje bo na mestih od (1, *) do (VRSTICA, *)
	public Igra() {
		plosca = new Polje[VRSTICA + 2][STOLPEC];
		// Vrstici moramo dodati 2 zaradi golov
		for (int i = 0; i <= VRSTICA + 1; ++i) {
			for (int j = 0; j < STOLPEC; ++j) {
				Polje v = new Polje(i, j);
				v.nastavitevVeljavnePoteze(VRSTICA, STOLPEC);
				plosca[i][j] = v;
			}
		}
		naPotezi = Igralec.A;
		// Vrstic je VRSTICA+2 zato moramo paziti pri iskanju srediscne koordinate
		aktivnaTocka = new Tocka(VRSTICA + 2, STOLPEC);
		povezave = new HashSet<>();
	}
	
	// Nova kopija dane igre
	public Igra(Igra igra) {
		plosca = new Polje[STOLPEC][VRSTICA];
		for (int i = 0; i <= VRSTICA+1; ++i) {
			for (int j = 0; j <= STOLPEC; ++j) {
				plosca[i][j].nastavitevVeljavnePoteze(STOLPEC, VRSTICA);
				plosca[i][j] = igra.plosca[i][j];
			}
		}
		naPotezi = igra.naPotezi;
		
		// Aktivno tocko postavimo v izhodisce
		aktivnaTocka.reset();
		
		//Zbrisati moramo vse povezave
		povezave.clear();
	}
	
	public Polje[][] getPlosca() {
		return plosca;
	}
	
	
	// Trenutno stanje igre
	public Stanje stanje() {
		// Preverimo ali je zoga v katerem golu
		if (aktivnaTocka.getX() == 0 && aktivnaTocka.getY() == STOLPEC / 2) {
			return Stanje.ZMAGA_A;
		} else if (aktivnaTocka.getX() == VRSTICA + 1 && aktivnaTocka.getY() == STOLPEC / 2) {
			return Stanje.ZMAGA_B;
		} else if (plosca[aktivnaTocka.getX()][aktivnaTocka.getY()].veljavnePoteze.size() == 7) {
			// V tem primeru prides na polje, ki se ni bilo obiskano in ni na robu
			// Zato je na vrsti nasprotnik
			switch (naPotezi) {
			case A : return Stanje.NA_POTEZI_A;
			case B : return Stanje.NA_POTEZI_B;
			}
		} else if (plosca[aktivnaTocka.getX()][aktivnaTocka.getY()].veljavnePoteze.size() == 0) {
			// Ce pristanes na polju brez veljavnih potez izgubis
			switch (naPotezi) {
			case A : return Stanje.ZMAGA_B;
			case B : return Stanje.ZMAGA_A;
			}
		}
		Stanje koncno = Stanje.NA_POTEZI_A;
		switch (naPotezi) {
		case A : koncno = Stanje.NA_POTEZI_A;
		case B : koncno = Stanje.NA_POTEZI_B;
		}
		return koncno;
	}
}
