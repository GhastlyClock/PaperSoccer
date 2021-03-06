package logika;

import java.util.*;

public class Polje {
	
	// Dogovor: v - vrstica, s - stolpec
	private int x, y;
	public static final Set<Premik> mozniPremiki;
	public Set<Premik> veljavnePoteze;
	
	static {
		// Ustvarim mnozico vseh moznih premikov, katero bomo uporabljali pri vsaki potezi
		mozniPremiki = new HashSet<>();
		for (int i = -1; i <= 1; ++i) {
			for (int j = -1; j <= 1; ++j) {
				if (i == 0 && j == 0) continue;
				Premik v = new Premik(i, j);
				mozniPremiki.add(v);
			}
		}
	}
	
	public Polje(int x, int y) {
		// Vsaki tocki bomo morali podati polozaj, ki bo enolicno dolocal polje
		this.x = x;
		this.y = y;
		veljavnePoteze = new HashSet<>();
	}
	
	@Override
	public String toString() {
		return "Polje [x=" + x + ", y=" + y + "]";
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void nastavitevVeljavnePoteze (int stVrstic, int stStolpcev) {
		// y koordinata gola
		int sredina = stStolpcev/2;
		
		// Posebej pogledamo za tocke izven igrisca oziroma v golu		
		// Ce tocka ni v igralnem polju, potem nima veljavnih potez
		if (x == 0 || x == stVrstic+1) return;
		if (y == sredina && (x == 1 || x == stVrstic)) {
			// Tocka pred golom
			for (Premik premik : mozniPremiki) {
				int x1 = x + premik.getX();
				int y1 = y + premik.getY();
				if ((x1 > 0 && x1 < stVrstic + 1) || y1 == sredina) veljavnePoteze.add(premik);
			}
		}
		else if (x == 1 || x == stVrstic) {
			// Tocke na robu (zgoraj ali spodaj)
			for (Premik p : mozniPremiki) {
			int prY = y + p.getY();	
			int prX = x + p.getX();
			boolean pogoj1 = prX > 1 && prX < stVrstic;
			boolean pogoj2 = prY == sredina && (prX == 0 || prX == stVrstic + 1 || prX == 1 || prX == stVrstic);
			if (y == 0 || y == stStolpcev - 1) {
				// Tocke v kotu
				boolean pogoj3 = prY < stStolpcev - 1 && prY > 0;
				if ((pogoj1 && pogoj3) || pogoj2) veljavnePoteze.add(p);
			}
			else if (pogoj1 || pogoj2) veljavnePoteze.add(p);
			}
		}
		else if (y == 0 || y == stStolpcev - 1) {
			// Tocke na robu (desno ali levo)
			for (Premik p : mozniPremiki) {
				int prY = y + p.getY();		
				int prX = x + p.getX();
				boolean pogoj1 = prY > 0 && prY < stStolpcev - 1;
				boolean pogoj2 = prY == sredina && (prX == 0 || prX == stVrstic + 1 || prX == 1 || prX == stVrstic);
				if (pogoj1 || pogoj2) veljavnePoteze.add(p);
				}
		}
		else {
			// Drugace je tocka v polju
			// Ce je tocka v polju so mozne poteze vse
			for (Premik premik : mozniPremiki) {
				veljavnePoteze.add(premik);
			}
		}
		return;
	}
	
	public void odstraniPotezo(int x, int y) {
		// Odstranimo potezo iz veljavnih potez
		for (Premik premik : veljavnePoteze) {
			if (premik.getX() == x && premik.getY() == y) {
				veljavnePoteze.remove(premik);
				return;
			}
		}
	}	
	
	public Polje vrniKopijo() {
		Polje p = new Polje(x, y);
		for (Premik premik : veljavnePoteze) {
			p.veljavnePoteze.add(premik);
		}
		return p;
	}
}