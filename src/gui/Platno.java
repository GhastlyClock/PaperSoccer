package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import logika.*;

@SuppressWarnings("serial")
public class Platno extends JPanel implements MouseListener, MouseMotionListener{
	
	int sirina, visina;
	
	Polje trenutnaTocka;
	
	Polje[][] vseTocke;
	Set<Polje> mozneTocke;
	
	Set<Polje> odigraneTocke;
	
	Set<Povezava> poteze;
	Set<Povezava> trenutnaPoteza;
	
	Color barvaTock, barvaMoznihTock, barvaOzadja, barvaCrt, barvaTrenutneTocke;
	Color barvaPotencialnePoteze;
	
	private static boolean potencialnaPoteza = false;
	private static Polje naslednjaPoteza = null;
	
	double polmer;
	float debelinaPovezave;
	
	private int klikX, klikY;
	private int premikX, premikY;
	
	public Platno(int sirina, int visina) {
		this.sirina = sirina;
		this.visina = visina;
		
		vseTocke = new Polje[11][7];
		for(int i = 0; i < vseTocke.length; i++){
		    for(int j = 0; j < vseTocke[i].length; j++){
		       vseTocke[i][j] = null;
		    }
		}
		for (int i=0; i<=10; i++) {
			for (int j=0; j<7; j++) {
				if (i==0 || i==10) {
					Polje tocka = new Polje(i, 3);
					vseTocke[i][3] = tocka;
				}
				else {
					Polje tocka = new Polje(i, j);
					vseTocke[i][j] = tocka;
				}
			}
		}
		
		trenutnaTocka = vseTocke[5][3];
		
		odigraneTocke = new HashSet<Polje>();
		
		mozneTocke = new HashSet<Polje>();
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
			Polje t1 = p.tocka1;
			Polje t2 = p.tocka2;
			int i1 = t1.getY();
			int j1 = t1.getX();
			int i2 = t2.getY();
			int j2 = t2.getX();
			Color barva = p.barva;
			if (barva != null) {
				g.setColor(barva);
				g.drawLine(m + m*i1, n - m + m*j1, m + m*i2, n - m + m*j2);
			}
		}
		
		if (potencialnaPoteza == true) {
			int i1 = trenutnaTocka.getY();
			int j1 = trenutnaTocka.getX();
			int i2 = naslednjaPoteza.getY();
			int j2 = naslednjaPoteza.getX();
			g2.setStroke(new BasicStroke(debelinaPovezave));
			g.setColor(barvaPotencialnePoteze);
			g.drawLine(m + m*i1, n - m + m*j1, m + m*i2, n - m + m*j2);
		}
		
		g2.setStroke(new BasicStroke(1));
		g.setColor(Color.BLACK);
		int r = round(polmer);
		
		for (Polje[] tocke : vseTocke) {
			for (Polje t : tocke) {
				if (t == null) ;
				else if (odigraneTocke.contains(t)) {
					g.setColor(Color.BLACK);
					int a = t.getX();
					int b = t.getY();
					g.drawOval(m + m*b - r/2, n - m + m*a - r/2, r, r);
					g.fillOval(m + m*b - r/2, n - m + m*a - r/2, r, r);
				}
				else if (mozneTocke.contains(t)) {
					g.setColor(Color.YELLOW);
					int a = t.getX();
					int b = t.getY();
					g.drawOval(m + m*b - r/2, n - m + m*a - r/2, r, r);
					g.fillOval(m + m*b - r/2, n - m + m*a - r/2, r, r);
				}
				else if (t == trenutnaTocka) {
					g.setColor(barvaTrenutneTocke);
					int a = t.getX();
					int b = t.getY();
					g.drawOval(m + m*b - r/2, n - m + m*a - r/2, r, r);
					g.fillOval(m + m*b - r/2, n - m + m*a - r/2, r, r);
				}
				else {
					g.setColor(barvaTock);
					int a = t.getX();
					int b = t.getY();
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
		Polje najblizja = null;
		double razdalja = Double.POSITIVE_INFINITY;
		for (Polje t : mozneTocke) {
				int m = 60;
				int n = m/2 + m;
				int i = m + m*t.getY();
				int j = n - m + m*t.getX();
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
		Polje najblizja = null;
		double razdalja = Double.POSITIVE_INFINITY;
		for (Polje t : mozneTocke) {
				int m = 60;
				int n = m/2 + m;
				int i = m + m*t.getY();
				int j = n - m + m*t.getX();
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
