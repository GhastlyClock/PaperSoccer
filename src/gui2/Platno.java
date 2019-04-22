package gui2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import logika.*;

@SuppressWarnings("serial")
public class Platno extends JPanel implements MouseListener, MouseMotionListener{
	
	int sirina, visina;

	private Vodja vodja;
	
	Color barvaTock, barvaMoznihTock, barvaOzadja, barvaTrenutneTocke, barvaCrt;
	Color barvaPotencialnePoteze;
	
	private static boolean potencialnaPoteza = false;
	private static Polje naslednjaPoteza = null;
	
	double polmer;
	float debelinaPovezave;
	
	private int klikX, klikY;
	private int premikX, premikY;
	
	public Platno(int sirina, int visina, Vodja vodja) {
		this.sirina = sirina;
		this.visina = visina;
		
		this.vodja = vodja;

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
		for (Povezava p : vodja.igra.povezave) {
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
		
		if (potencialnaPoteza) {
			int i1 = vodja.igra.aktivnaTocka.getY();
			int j1 = vodja.igra.aktivnaTocka.getX();
			int i2 = naslednjaPoteza.getY();
			int j2 = naslednjaPoteza.getX();
			g2.setStroke(new BasicStroke(debelinaPovezave));
			g.setColor(barvaPotencialnePoteze);
			g.drawLine(m + m*i1, n - m + m*j1, m + m*i2, n - m + m*j2);
		}
		
		g2.setStroke(new BasicStroke(1));
		g.setColor(Color.BLACK);
		int r = round(polmer);
		
		for (Polje[] vrsticaPolj : vodja.igra.getPlosca()) {
			for (Polje t : vrsticaPolj) {
				if ((t.getX() == 0 || t.getX() == Igra.VRSTICA + 1) && t.getY() != Igra.STOLPEC / 2) {
					continue;
				}
				else if (vodja.igra.sosednjaVeljavnaPolja().contains(t)) {
					g.setColor(Color.YELLOW);
					int a = t.getX();
					int b = t.getY();
					g.drawOval(m + m*b - r/2, n - m + m*a - r/2, r, r);
					g.fillOval(m + m*b - r/2, n - m + m*a - r/2, r, r);
				}
				else if (t.getX() == vodja.igra.aktivnaTocka.getX() && t.getY() == vodja.igra.aktivnaTocka.getY()) {
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
		Polje najblizja = null;
		double razdalja = Double.POSITIVE_INFINITY;
		for (Polje t : vodja.igra.sosednjaVeljavnaPolja()) {
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
			if (vodja.igra.sosednjaVeljavnaPolja().contains(najblizja)) {
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
		if (vodja.clovekNaVrsti) {
			Polje najblizja = null;
			double razdalja = Double.POSITIVE_INFINITY;
			for (Polje t : vodja.igra.sosednjaVeljavnaPolja()) {
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
				// Minus, zato ker plosca obrnjeno simetricno kot platno
				int premikX = - (vodja.igra.aktivnaTocka.getX() - najblizja.getX());
				int premikY = - (vodja.igra.aktivnaTocka.getY() - najblizja.getY());
				for (Premik p : vodja.igra.aktivnaTocka.veljavnePoteze) {
					if (p.getX() == premikX && p.getY() == premikY) {
						vodja.clovekovaPoteza(p);
						break;
					}
				}
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

