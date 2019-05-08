package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import logika.*;

@SuppressWarnings("serial")
public class Platno extends JPanel implements MouseListener, MouseMotionListener{
	
	int sirina, visina;

	private Vodja vodja;
	
	Color barvaTock, barvaMoznihTock, barvaOzadja, barvaTrenutneTocke, barvaCrt;
	Color barvaPotencialnePoteze, barvaIgralecA, barvaIgralecB; 
	
	private static boolean potencialnaPoteza = false;
	private static Polje naslednjaPoteza = null;
	
	double polmer;
	float debelinaPovezave;
	
	private int klikX, klikY;
	private int premikX, premikY;
	
	int visinaZaslona;
	
	int razmikTock;
	
	public Platno(Vodja vodja) {
		Dimension velikostZaslona = Toolkit.getDefaultToolkit().getScreenSize();
		visinaZaslona = (int) velikostZaslona.getHeight();
		
		// Odstejem (visina * 0.3) zaradi "taskbar"
		razmikTock = (int) ((visinaZaslona - (visinaZaslona * 0.3)) / Igra.VRSTICA);

		this.sirina = (Igra.STOLPEC + 1) * razmikTock;
		this.visina = (Igra.VRSTICA + 2) * razmikTock;
		
		this.vodja = vodja;

		barvaTock = Color.GRAY;
		barvaMoznihTock = Color.YELLOW;
		barvaOzadja = new Color(0, 115, 30);
		barvaCrt = Color.WHITE;
		barvaTrenutneTocke = Color.CYAN;
		barvaPotencialnePoteze = new Color(220, 255, 180);
		barvaIgralecA = Color.RED;
		barvaIgralecB = Color.BLACK;
		
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
		int n = razmikTock/2 + razmikTock;
		g.drawLine(razmikTock, n, sirina - razmikTock, n);
		g.drawLine(razmikTock, n, razmikTock, visina - n);
		g.drawLine(razmikTock, visina - n, sirina - razmikTock, visina - n);
		g.drawLine(sirina - razmikTock, n, sirina - razmikTock, visina - n);
		
		int k = (Igra.STOLPEC / 2);
		int l = ((Igra.STOLPEC / 2) + 2);
		
		// Dodam barvo v gol
		g.setColor(barvaIgralecB);
		g.fillRect(k*razmikTock, n - razmikTock, l*razmikTock - k*razmikTock, razmikTock/2);
		g.setColor(barvaIgralecA);
		g.fillRect(k*razmikTock, visina - razmikTock, l*razmikTock - k*razmikTock, razmikTock/2);
		
		g.setColor(barvaCrt);
		
		g.drawLine(k*razmikTock, n - razmikTock, k*razmikTock, n);
		g.drawLine(l*razmikTock, n - razmikTock, l*razmikTock, n);
		g.drawLine(k*razmikTock, n - razmikTock, l*razmikTock, n - razmikTock);
		
		g.drawLine(k*razmikTock, visina - n + razmikTock, k*razmikTock, visina - n);
		g.drawLine(l*razmikTock, visina - n + razmikTock, l*razmikTock, visina - n);
		g.drawLine(k*razmikTock, visina - n + razmikTock, l*razmikTock, visina - n + razmikTock);
		
		g.drawLine(razmikTock, visina/2, sirina - razmikTock, visina/2);
		
		g.drawOval(sirina/2 - n/2, visina/2 - n/2, n, n);
		
		g.setColor(barvaOzadja);
		g.drawLine(k*razmikTock, n, l*razmikTock, n);
		g.drawLine(k*razmikTock, visina - n, l*razmikTock, visina - n);
		
		g.setColor(barvaCrt);
		g2.setStroke(new BasicStroke(1));
		g.drawLine(k*razmikTock, n, l*razmikTock, n);
		g.drawLine(k*razmikTock, visina - n, l*razmikTock, visina - n);
		
		
		g2.setStroke(new BasicStroke(debelinaPovezave));
		for (Povezava p : vodja.igra.povezave) {
			Polje t1 = p.tocka1;
			Polje t2 = p.tocka2;
			int i1 = t1.getY();
			int j1 = t1.getX();
			int i2 = t2.getY();
			int j2 = t2.getX();
			Color barva = p.igralec == Igralec.A ? barvaIgralecA : barvaIgralecB;
			if (barva != null) {
				g.setColor(barva);
				g.drawLine(razmikTock + razmikTock*i1, n - razmikTock + razmikTock*j1, razmikTock + razmikTock*i2, n - razmikTock + razmikTock*j2);
			}
		}
		
		if (potencialnaPoteza) {
			int i1 = vodja.igra.aktivnaTocka.getY();
			int j1 = vodja.igra.aktivnaTocka.getX();
			int i2 = naslednjaPoteza.getY();
			int j2 = naslednjaPoteza.getX();
			g2.setStroke(new BasicStroke(debelinaPovezave));
			g.setColor(barvaPotencialnePoteze);
			g.drawLine(razmikTock + razmikTock*i1, n - razmikTock + razmikTock*j1, razmikTock + razmikTock*i2, n - razmikTock + razmikTock*j2);
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
					g.setColor(barvaMoznihTock);
					int a = t.getX();
					int b = t.getY();
					g.drawOval(razmikTock + razmikTock*b - r/2, n - razmikTock + razmikTock*a - r/2, r, r);
					g.fillOval(razmikTock + razmikTock*b - r/2, n - razmikTock + razmikTock*a - r/2, r, r);
				}
				else if (t.getX() == vodja.igra.aktivnaTocka.getX() && t.getY() == vodja.igra.aktivnaTocka.getY()) {
					g.setColor(barvaTrenutneTocke);
					int a = t.getX();
					int b = t.getY();
					g.drawOval(razmikTock + razmikTock*b - r/2, n - razmikTock + razmikTock*a - r/2, r, r);
					g.fillOval(razmikTock + razmikTock*b - r/2, n - razmikTock + razmikTock*a - r/2, r, r);
				}
				else {
					g.setColor(barvaTock);
					int a = t.getX();
					int b = t.getY();
					g.drawOval(razmikTock + razmikTock*b - r/2, n - razmikTock + razmikTock*a - r/2, r, r);
					g.fillOval(razmikTock + razmikTock*b - r/2, n - razmikTock + razmikTock*a - r/2, r, r);
				}
			}
		}
	}
	
	private static int round(double x) {
		return (int)(x + 0.5);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		premikX = e.getX();
		premikY = e.getY();
		Polje najblizja = null;
		double razdalja = Double.POSITIVE_INFINITY;
		for (Polje t : vodja.igra.sosednjaVeljavnaPolja()) {
				int n = razmikTock/2 + razmikTock;
				int i = razmikTock + razmikTock*t.getY();
				int j = n - razmikTock + razmikTock*t.getX();
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
				int n = razmikTock/2 + razmikTock;
				int i = razmikTock + razmikTock*t.getY();
				int j = n - razmikTock + razmikTock*t.getX();
				double r = Math.sqrt(Math.pow(klikX - i, 2) + Math.pow(klikY - j, 2));
				if (r < razdalja) {
					razdalja = r;
					najblizja = t;
				}
			}
			if (razdalja < polmer + 5) {
				int premikX = najblizja.getX() - vodja.igra.aktivnaTocka.getX();
				int premikY = najblizja.getY() - vodja.igra.aktivnaTocka.getY();
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
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
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

