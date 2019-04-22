package logika;

import java.util.*;

import gui2.*;
//import inteligenca.Minimax;
//import inteligenca.OcenjenPremik;

public class Vodja {
	
	private Random random;
	
	// Glavno okno
	private Okno okno;
	
	// Igra, ki jo trenutno igramo
	public Igra igra;
	
	// Ali je clovek igralec A ali B?
	private VrstaIgralca igralecA;
	private VrstaIgralca igralecB;
	
	public boolean clovekNaVrsti;
	
	public Vodja(Okno okno) {
		random = new Random();
		this.okno = okno;
		clovekNaVrsti = false;
	}
	
	public void novaIgra(VrstaIgralca igralecA, VrstaIgralca igralecB) {
		// Ustvarim novo igro
		this.igra = new Igra();
		this.igralecA = igralecA;
		this.igralecB = igralecB;
		igramo();
	}
	
	public void igramo() {
		okno.osveziGUI();
		
		switch (igra.stanje()) {
		case ZMAGA_A:
		case ZMAGA_B:
			break;
		case NA_POTEZI_A: 
			if (igralecA == VrstaIgralca.CLOVEK) clovekNaVrsti = true;
			else {
				racunalnikovaPoteza();
			}
			break;
		case NA_POTEZI_B:
			if (igralecB == VrstaIgralca.CLOVEK) clovekNaVrsti = true;
			else {
				racunalnikovaPoteza();
			}
			break;
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
