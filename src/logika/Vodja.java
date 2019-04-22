package logika;

import java.util.*;

import gui.Okno;

public class Vodja {
	
	private Random random;
	
	// Glavno okno
	private Okno okno;
	
	// Igra, ki jo trenutno igramo
	public Igra igra;
	
	// Ali je clovek igralec A ali B?
	private Igralec clovek;
	
	public boolean clovekNaVrsti;
	
	public Vodja(Okno okno) {
		random = new Random();
		this.okno = okno;
		clovekNaVrsti = false;
	}
	
	public void novaIgra(Igralec clovek) {
		// Ustvarim novo igro
		this.igra = new Igra();
		this.clovek = clovek;
		igramo();
	}
	
	public void igramo() {
		// Manjka del kode za osvezitev GUI!!!
		
		switch (igra.stanje()) {
		case ZMAGA_A:
		case ZMAGA_B:
			break;
		case NA_POTEZI_A:
		case NA_POTEZI_B:
			if (igra.naPotezi == clovek) {
				clovekNaVrsti = true;
			} else {
				racunalnikovaPoteza();
			}
		}
	}
	
	public void racunalnikovaPoteza() {
		Polje p = igra.getPlosca()[igra.aktivnaTocka.getX()][igra.aktivnaTocka.getY()];
		Set<Premik> mozniPremiki = p.veljavnePoteze;
		int velikost = mozniPremiki.size();
		int item = random.nextInt(velikost);
		int i = 0;
		Premik premik = null;
		for(Premik obj : mozniPremiki)
		{
		    if (i == item)
		        premik = obj;
		    i++;
		}
		igra.odigraj(premik);
		igramo();
	}
	
	public void clovekovaPoteza(Premik p) {
		if (igra.odigraj(p)) {
			clovekNaVrsti = false;
			igramo();
		}
	}

}
