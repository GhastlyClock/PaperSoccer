package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import logika.*;

@SuppressWarnings("serial")
public class Platno extends JPanel implements MouseListener, MouseMotionListener{
	
	int sirina, visina;
	
	Tocka trenutnaTocka;
	
	Tocka[][] vseTocke;
	Set<Tocka> mozneTocke;
	
	Set<Tocka> odigraneTocke;
	
	Set<Povezava> poteze;
	Set<Povezava> trenutnaPoteza;
	
	Color barvaTock, barvaMoznihTock, barvaOzadja, barvaCrt, barvaTrenutneTocke;
	Color barvaPotencialnePoteze;
	
	private static boolean potencialnaPoteza = false;
	private static Tocka naslednjaPoteza = null;
	
	double polmer;
	float debelinaPovezave;
	
	private int klikX, klikY;
	private int premikX, premikY;
	
	public Platno(int sirina, int visina) {
		this.sirina = sirina;
		this.visina = visina;
		
		vseTocke = new Tocka[11][7];
		for(int i = 0; i < vseTocke.length; i++){
		    for(int j = 0; j < vseTocke[i].length; j++){
		       vseTocke[i][j] = null;
		    }
		}
		for (int i=0; i<=10; i++) {
			for (int j=0; j<7; j++) {
				if (i==0 || i==10) {
					Tocka tocka = new Tocka(i, 3);
					vseTocke[i][3] = tocka;
				}
				else {
					Tocka tocka = new Tocka(i, j);
					vseTocke[i][j] = tocka;
				}
			}
		}
		
		trenutnaTocka = vseTocke[5][3];
		
		odigraneTocke = new HashSet<Tocka>();
		
		mozneTocke = new HashSet<Tocka>();
		mozneTocke.add(vseTocke[6][3]);
		mozneTocke.add(vseTocke[4][3]);
		mozneTocke.add(vseTocke[5][4]);
		mozneTocke.add(vseTocke[5][2]);
		mozneTocke.add(vseTocke[6][4]);
		mozneTocke.add(vseTocke[6][2]);
		mozneTocke.add(vseTocke[4][2]);
		mozneTocke.add(vseTocke[4][4]);
//		for (int[] premik : trenutnaTocka.veljavnePoteze) {
//			try {
//				mozneTocke.add(vseTocke[trenutnaTocka.x + premik[0]][trenutnaTocka.y + premik[1]]);
//			}
//			catch (Exception e) {
//			}
//		}
		
		poteze = new HashSet<Povezava>();
		trenutnaPoteza = new HashSet<Povezava>();
		
		barvaTock = Color.GRAY;
		barvaMoznihTock = Color.YELLOW;
		barvaOzadja = Color.WHITE;
		barvaCrt = Color.BLACK;
		barvaTrenutneTocke = Color.CYAN;
		barvaPotencialnePoteze = Color.MAGENTA;
		
		polmer = 10;
		debelinaPovezave = 4;

		addMouseListener(this);
		addMouseMotionListener(this);
		setFocusable(true);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(sirina, visina);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		this.setBackground(barvaOzadja);
		
		g2.setStroke(new BasicStroke(2));
		
		g.setColor(barvaCrt);
		int m = 60;
		int n = m/2 + m;
		g.drawLine(m, n, sirina - m, n);
		g.drawLine(m, n, m, visina - n);
		g.drawLine(m, visina - n, sirina - m, visina - n);
		g.drawLine(sirina - m, n, sirina - m, visina - n);
		
		g.drawLine(3*m, n - m, 3*m, n);
		g.drawLine(5*m, n - m, 5*m, n);
		g.drawLine(3*m, n - m, 5*m, n - m);
		
		g.drawLine(3*m, visina - n + m, 3*m, visina - n);
		g.drawLine(5*m, visina - n + m, 5*m, visina - n);
		g.drawLine(3*m, visina - n + m, 5*m, visina - n + m);
		
		g.drawLine(m, visina/2, sirina - m, visina/2);
		
		g.drawOval(sirina/2 - n/2, visina/2 - n/2, n, n);
		
		g.setColor(barvaOzadja);
		g.drawLine(3*m, n, 5*m, n);
		g.drawLine(3*m, visina - n, 5*m, visina - n);
		
		g.setColor(barvaCrt);
		g2.setStroke(new BasicStroke(1));
		g.drawLine(3*m, n, 5*m, n);
		g.drawLine(3*m, visina - n, 5*m, visina - n);
		
		
		g2.setStroke(new BasicStroke(debelinaPovezave));
		for (Povezava p : poteze) {
			Tocka t1 = p.tocka1;
			Tocka t2 = p.tocka2;
			int i1 = t1.y;
			int j1 = t1.x;
			int i2 = t2.y;
			int j2 = t2.x;
			Color barva = p.barva;
			if (barva != null) {
				g.setColor(barva);
				g.drawLine(m + m*i1, n - m + m*j1, m + m*i2, n - m + m*j2);
			}
		}
		
		if (potencialnaPoteza == true) {
			int i1 = trenutnaTocka.y;
			int j1 = trenutnaTocka.x;
			int i2 = naslednjaPoteza.y;
			int j2 = naslednjaPoteza.x;
			g2.setStroke(new BasicStroke(debelinaPovezave));
			g.setColor(barvaPotencialnePoteze);
			g.drawLine(m + m*i1, n - m + m*j1, m + m*i2, n - m + m*j2);
		}
		
		g2.setStroke(new BasicStroke(1));
		g.setColor(Color.BLACK);
		int r = round(polmer);
		
		for (Tocka[] tocke : vseTocke) {
			for (Tocka t : tocke) {
				if (t == null) ;
				else if (odigraneTocke.contains(t)) {
					g.setColor(Color.BLACK);
					int a = t.x;
					int b = t.y;
					g.drawOval(m + m*b - r/2, n - m + m*a - r/2, r, r);
					g.fillOval(m + m*b - r/2, n - m + m*a - r/2, r, r);
				}
				else if (mozneTocke.contains(t)) {
					g.setColor(Color.YELLOW);
					int a = t.x;
					int b = t.y;
					g.drawOval(m + m*b - r/2, n - m + m*a - r/2, r, r);
					g.fillOval(m + m*b - r/2, n - m + m*a - r/2, r, r);
				}
				else if (t == trenutnaTocka) {
					g.setColor(barvaTrenutneTocke);
					int a = t.x;
					int b = t.y;
					g.drawOval(m + m*b - r/2, n - m + m*a - r/2, r, r);
					g.fillOval(m + m*b - r/2, n - m + m*a - r/2, r, r);
				}
				else {
					g.setColor(barvaTock);
					int a = t.x;
					int b = t.y;
					g.drawOval(m + m*b - r/2, n - m + m*a - r/2, r, r);
					g.fillOval(m + m*b - r/2, n - m + m*a - r/2, r, r);
				}
				
			}
		}
		
		
	}
	
	private static int round(double x) {
		return (int)(x + 0.5);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		premikX = e.getX();
		premikY = e.getY();
		System.out.println("" + premikX + ", " + premikY);
		Tocka najblizja = null;
		double razdalja = Double.POSITIVE_INFINITY;
		for (Tocka t : mozneTocke) {
				int m = 60;
				int n = m/2 + m;
				int i = m + m*t.y;
				int j = n - m + m*t.x;
				double r = Math.sqrt(Math.pow(premikX - i, 2) + Math.pow(premikY - j, 2));
				if (r < razdalja) {
					razdalja = r;
					naslednjaPoteza = najblizja = t;
				}
		}
		if (razdalja < polmer + 5) {
			if (mozneTocke.contains(najblizja)) {
				potencialnaPoteza = true;
				repaint();
			}
		}
		else {
			naslednjaPoteza = null;
			potencialnaPoteza = false;
			repaint();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		klikX = e.getX();
		klikY = e.getY();
		Tocka najblizja = null;
		double razdalja = Double.POSITIVE_INFINITY;
		for (Tocka t : mozneTocke) {
				int m = 60;
				int n = m/2 + m;
				int i = m + m*t.y;
				int j = n - m + m*t.x;
				double r = Math.sqrt(Math.pow(klikX - i, 2) + Math.pow(klikY - j, 2));
				if (r < razdalja) {
					razdalja = r;
					najblizja = t;
				}
		}
		if (razdalja < polmer + 5) {
			if (mozneTocke.contains(najblizja)) {
				poteze.add(new Povezava(trenutnaTocka, najblizja, Color.BLUE));
				odigraneTocke.add(trenutnaTocka);
				trenutnaTocka = najblizja;
				repaint();
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
