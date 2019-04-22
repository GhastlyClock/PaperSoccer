package logika;

import java.util.*;

public class Igra {
	// Velikost igralne plosce je N x M.
	public static final int STOLPEC = 7;
	public static final int VRSTICA = 9;
	
	// Igralno polje
	private Polje[][] plosca;
	
	// Spremljanje potez
	AktivnaTocka aktivnaTocka;
	
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
		aktivnaTocka = new AktivnaTocka(VRSTICA + 2, STOLPEC);
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
		Stanje koncno = Stanje.NA_POTEZI_A;
		switch (naPotezi) {
		case A : koncno = Stanje.NA_POTEZI_A;
		case B : koncno = Stanje.NA_POTEZI_B;
		}
		return koncno;
	}

	public boolean odigraj(Premik p) {
		int x1 = aktivnaTocka.getX();
		int y1 = aktivnaTocka.getY();
		if (plosca[x1][y1].veljavnePoteze.contains(p)) {
			Premik nasprotni = p.nasprotniPremik();
			plosca[x1][y1].odstraniPotezo(p.getX(), p.getY());
			
			// Premaknemo aktivno tocko
			aktivnaTocka.x += p.getX();
			aktivnaTocka.y += p.getY();

			int x2 = aktivnaTocka.getX();
			int y2 = aktivnaTocka.getY();
			plosca[x2][y2].odstraniPotezo(nasprotni.getX(), nasprotni.getY());
			if (plosca[x2][y2].veljavnePoteze.size() < 6 && 
					!(y2 == STOLPEC / 2 && (x2 == 0 || x2 == VRSTICA))) {
				// V tem primeru prides na polje, ki je ze bilo obiskano oziroma na rob
				// Zato imas ponoven premik
				return true;
			}
			naPotezi.nasprotnik();
			return true;
		} 
		else {
			return false;
		}
	}
	
}
