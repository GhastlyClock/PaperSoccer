package logika;

import java.util.*;

public class Igra {
	// Velikost igralne plosce je VRSTICA x STOLPEC.
	// Opomba: igra deluje za STOLPEC >= 3 in VRSTICA >= 1
	public static final int STOLPEC = 9;
	public static final int VRSTICA = 13;
	
	// Igralno polje
	private Polje[][] plosca;
	
	// Spremljanje potez
	public Polje aktivnaTocka;
	
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
		aktivnaTocka = plosca[(VRSTICA + 1) / 2][STOLPEC / 2];
		povezave = new HashSet<>();
	}
	
	// Nova kopija dane igre za minimax
	public Igra(Igra igra) {
		plosca = new Polje[VRSTICA + 2][STOLPEC];
		// Vrstici moramo dodati 2 zaradi golov
		for (int i = 0; i <= VRSTICA + 1; ++i) {
			for (int j = 0; j < STOLPEC; ++j) {
				plosca[i][j] = igra.plosca[i][j].vrniKopijo();
			}
		}
		naPotezi = igra.naPotezi;
		aktivnaTocka = plosca[igra.aktivnaTocka.getX()][igra.aktivnaTocka.getY()];
		povezave = new HashSet<>();
		for (Povezava p : igra.povezave) {
			povezave.add(new Povezava(plosca[p.tocka1.getX()][p.tocka1.getY()], plosca[p.tocka2.getX()][p.tocka2.getY()], p.igralec));
			
		}
	}
	
	public Polje[][] getPlosca() {
		return plosca;
	}
	
	public Set<Polje> sosednjaVeljavnaPolja() {
		Set<Polje> sosedi = new HashSet<>();
		int x = aktivnaTocka.getX();
		int y = aktivnaTocka.getY();
		for (Premik p : plosca[x][y].veljavnePoteze) {
			sosedi.add(plosca[x + p.getX()][y + p.getY()]);
		}
		return sosedi;
	}
	
	// Trenutno stanje igre
	public Stanje stanje() {
		// Preverimo ali je zoga v katerem golu
		if (aktivnaTocka.getX() == 0 && aktivnaTocka.getY() == STOLPEC / 2) {
			return Stanje.ZMAGA_A;
		} 
		else if (aktivnaTocka.getX() == VRSTICA + 1 && aktivnaTocka.getY() == STOLPEC / 2) {
			return Stanje.ZMAGA_B;
		} 
		else if (plosca[aktivnaTocka.getX()][aktivnaTocka.getY()].veljavnePoteze.size() == 0) {
			// Ce pristanes na polju brez veljavnih potez izgubis
			switch (naPotezi) {
			case A : return Stanje.ZMAGA_B;
			case B : return Stanje.ZMAGA_A;
			}
		}
		if (naPotezi == Igralec.A) {
			return Stanje.NA_POTEZI_A;
		}
		else {
			return Stanje.NA_POTEZI_B;
		}
	}

	public boolean odigraj(Premik p) {
		int x1 = aktivnaTocka.getX();
		int y1 = aktivnaTocka.getY();
		if (aktivnaTocka.veljavnePoteze.contains(p)) {
			Premik nasprotni = p.nasprotniPremik();
			aktivnaTocka.odstraniPotezo(p.getX(), p.getY());
			
			// Premaknemo aktivno tocko
			aktivnaTocka = plosca[aktivnaTocka.getX() + p.getX()][aktivnaTocka.getY() + p.getY()];

			aktivnaTocka.odstraniPotezo(nasprotni.getX(), nasprotni.getY());
			
			// Dodamo povezavo med tockama
			// Barve so zaenkrat take kot so, lahko se spremeni
			povezave.add(new Povezava(plosca[x1][y1], aktivnaTocka, naPotezi));
			if (aktivnaTocka.getY() == STOLPEC / 2 && (aktivnaTocka.getX() == 1 || aktivnaTocka.getX() == VRSTICA)) {
				// Posebej moram obravnavam tocko pred golom
				// Ce prvic pridemo na polje pred golom imamo natanko 5 moznih premikov
				if (aktivnaTocka.veljavnePoteze.size() != 5) return true;
				naPotezi = naPotezi.nasprotnik();
				return true;
			}
			if (aktivnaTocka.veljavnePoteze.size() < 7) {
				// V tem primeru prides na polje, ki je ze bilo obiskano oziroma na rob
				// Zato imas ponoven premik
				return true;
			}
			naPotezi = naPotezi.nasprotnik();
			return true;
		} 
		else {
			return false;
		}
	}
	
}
