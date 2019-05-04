package logika;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gui.*;
import inteligenca.Minimax;
import inteligenca.OcenjenPremik;

public class Vodja {
	
	// Glavno okno
	private Okno okno;
	
	// Igra, ki jo trenutno igramo
	public Igra igra;
	
	// Ali je clovek igralec A ali B?
	public static VrstaIgralca igralecA;
	public static VrstaIgralca igralecB;
	
	public boolean clovekNaVrsti;
	
	// Globina minimaxa
	public static int globinaMinimax = 3;
	
	public Vodja(Okno okno) {
		this.okno = okno;
		clovekNaVrsti = false;
	}
	
	public void novaIgra(VrstaIgralca igralecA, VrstaIgralca igralecB) {
		// Ustvarim novo igro
		this.igra = new Igra();
		Vodja.igralecA = igralecA;
		Vodja.igralecB = igralecB;
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
				racunalnikovaPoteza(Igralec.A);
			}
			break;
		case NA_POTEZI_B:
			if (igralecB == VrstaIgralca.CLOVEK) clovekNaVrsti = true;
			else {
				racunalnikovaPoteza(Igralec.B);
			}
			break;
		}
	}
	
	public void racunalnikovaPoteza(Igralec igralec) {
		List<OcenjenPremik> ocenjeniPremiki = Minimax.oceniPremike(igra, globinaMinimax, igralec);
		Premik premik = Minimax.maxPremik(ocenjeniPremiki);
		igra.odigraj(premik);
		igramo();
	}
	
	public void clovekovaPoteza(Premik p) {
		if (igra.odigraj(p)) {
			clovekNaVrsti = false;
			igramo();
		}
	}
	
	public void shrani(String ime) {
		try {
			PrintWriter dat = new PrintWriter(new FileWriter(ime));
			// Da preverimo ali se ujemajo stolpci in vrstice
			dat.println(Igra.STOLPEC + "," + Igra.VRSTICA);
			dat.println("***");
			dat.println(igra.aktivnaTocka);
			dat.println("***");
			for (Povezava p : igra.povezave) {
				dat.println(p);
			}
			dat.println("***");
			dat.println(igralecA + "," + igralecB);
			dat.println("***");
			dat.println(igra.naPotezi);
			dat.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Igra preberi (String ime) {
		try {
			Igra igra = new Igra();
			BufferedReader dat = new BufferedReader(new FileReader(ime));
			int blok = 1;
			while (dat.ready()) {
				String vrstica = dat.readLine().trim();
				if (vrstica.equals("")) continue;
				if (vrstica.equals("***")) ++blok;
				else if (blok == 1) {
					String[] v = vrstica.split(",");
					if (Igra.STOLPEC != Integer.parseInt(v[0]) || Igra.VRSTICA != Integer.parseInt(v[1])) {
						System.out.println("Prišlo je do napake pri nalaganju!");
						System.out.println("Zaganjam novo igro...");
						break;
					}
				}
				else if (blok == 2) {
					Pattern p = Pattern.compile("Polje \\[x=(\\d+), y=(\\d+)\\]");
					Matcher m = p.matcher(vrstica);
					if (m.find()) {
						int x = Integer.parseInt(m.group(1));
						int y = Integer.parseInt(m.group(2));
						igra.aktivnaTocka = igra.getPlosca()[x][y];
						}
				}
				else if (blok == 3) {
					Pattern p = Pattern.compile("Povezava \\[Polje \\[x=(\\d+), y=(\\d+)\\], Polje \\[x=(\\d+), y=(\\d+)\\], (A|B)\\]");
					Matcher m = p.matcher(vrstica);
					if (m.find()) {
						int x1 = Integer.parseInt(m.group(1));
						int y1 = Integer.parseInt(m.group(2));
						int x2 = Integer.parseInt(m.group(3));
						int y2 = Integer.parseInt(m.group(4));
						Igralec i = m.group(5).equals("A") ? Igralec.A : Igralec.B;
						igra.povezave.add(new Povezava(igra.getPlosca()[x1][y1], igra.getPlosca()[x2][y2], i));
						
						// Odstranimo veljavne premike
						igra.getPlosca()[x1][y1].odstraniPotezo(x2 - x1, y2 - y1);
						igra.getPlosca()[x2][y2].odstraniPotezo(x1 - x2, y1 - y2);
					}
				}
				else if (blok == 4) {
					String[] podatka = vrstica.split(",");
					igralecA = podatka[0].equals("CLOVEK") ? VrstaIgralca.CLOVEK : VrstaIgralca.RACUNALNIK;
					igralecB = podatka[1].equals("CLOVEK") ? VrstaIgralca.CLOVEK : VrstaIgralca.RACUNALNIK;
				}
				else if (blok == 5) {
					igra.naPotezi = vrstica.equals("A") ? Igralec.A : Igralec.B;
				}
			}
			dat.close();
			return igra;
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
