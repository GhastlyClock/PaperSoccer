package gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import logika.Igralec;

@SuppressWarnings("serial")
public class Okno extends JFrame implements ActionListener {
	
	Platno platno;
	
	Color barvaIgralec1, barvaIgralec2;
	
	private JMenuItem menuClovekClovek, menuClovekRacunalnik;
	private JMenuItem menuLahko, menuSrednje, menuTezko;
	private JMenuItem menuOdpri, menuShrani, menuKoncaj;
	private JMenuItem menuBarvaIgralca1, menuBarvaIgralca2, menuBarvaOzadja, menuBarvaCrt;
	
	private JLabel status;
	
	public Okno() {
		super();
		this.setTitle("Paper Soccer");
		this.setLayout(new GridBagLayout());
		platno = new Platno((7+2-1)*60, (11+2-2)*60);
		
		barvaIgralec1 = Color.BLACK;
		barvaIgralec2 = Color.RED;
		
		JMenuBar menubar = new JMenuBar();
		
		JMenu menuIgra = new JMenu("Igra");
		JMenu menuNastavitve = new JMenu("Nastavitve");
		
		menuClovekClovek = new JMenuItem("Clovek proti cloveku ...");
		menuClovekRacunalnik = new JMenuItem("Clovek proti racunalniku ...");
		
		menuLahko = new JMenuItem("Lahka tezavnost ...");
		menuSrednje = new JMenuItem("Srednja tezavnost ...");
		menuTezko = new JMenuItem("Tezka tezavnost ...");
		
		menuOdpri = new JMenuItem("Odpri igro ...");
		menuShrani = new JMenuItem("Shrani igro ...");			
		menuKoncaj = new JMenuItem("Koncaj ...");
		
		menuBarvaIgralca1 = new JMenuItem("Barva igralca 1 ...");
		menuBarvaIgralca2 = new JMenuItem("Barva igralca 2 ...");
		menuBarvaOzadja = new JMenuItem("Barva ozadja ...");
		menuBarvaCrt = new JMenuItem("Barva crt igrisca ...");
		
		menuIgra.add(menuClovekClovek);
		menuIgra.add(menuClovekRacunalnik);
		menuIgra.addSeparator();
		menuIgra.add(menuOdpri);
		menuIgra.add(menuShrani);
		menuIgra.add(menuKoncaj);
		
		menuNastavitve.add(menuLahko);
		menuNastavitve.add(menuSrednje);
		menuNastavitve.add(menuTezko);
		menuNastavitve.addSeparator();
		menuNastavitve.add(menuBarvaIgralca1);
		menuNastavitve.add(menuBarvaIgralca2);
		menuNastavitve.addSeparator();
		menuNastavitve.add(menuBarvaOzadja);
		menuNastavitve.add(menuBarvaCrt);
		
		menubar.add(menuIgra);
		menubar.add(menuNastavitve);
		
		setJMenuBar(menubar);
		
		menuClovekClovek.addActionListener(this);
		menuClovekRacunalnik.addActionListener(this);
		
		menuLahko.addActionListener(this);
		menuSrednje.addActionListener(this);
		menuTezko.addActionListener(this);
		
		menuOdpri.addActionListener(this);
		menuShrani.addActionListener(this);			
		menuKoncaj.addActionListener(this);
		
		menuBarvaIgralca1.addActionListener(this);
		menuBarvaIgralca2.addActionListener(this);
		menuBarvaOzadja.addActionListener(this);
		menuBarvaCrt.addActionListener(this);
		
		GridBagConstraints polje_layout = new GridBagConstraints();
		polje_layout.gridx = 0;
		polje_layout.gridy = 0;
		polje_layout.fill = GridBagConstraints.BOTH;
		polje_layout.weightx = 1.0;
		polje_layout.weighty = 1.0;
		getContentPane().add(platno, polje_layout);
		
		// statusna vrstica za sporocila
		status = new JLabel();
		status.setFont(new Font(status.getFont().getName(),
							    status.getFont().getStyle(),
							    20));
		status.setText("Na potezi: Igralec 1");
		GridBagConstraints status_layout = new GridBagConstraints();
		status_layout.gridx = 0;
		status_layout.gridy = 1;
		status_layout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(status, status_layout);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == menuOdpri) {
			JFileChooser chooser = new JFileChooser ();
			int gumb = chooser.showOpenDialog(this);
			if (gumb == JFileChooser.APPROVE_OPTION) {
				
			}
		}
		else if (source == menuShrani) {
			JFileChooser chooser = new JFileChooser ();
			int gumb = chooser.showOpenDialog(this);
			if (gumb == JFileChooser.APPROVE_OPTION) {
				
			}
		}
		else if (source == menuKoncaj) {
			System.exit(0);
		}
		else if (source == menuClovekClovek) {
			System.out.println("CC DELA");
		}
		else if (source == menuClovekRacunalnik) {
			System.out.println("CR DELA");
		}
		else if (source == menuLahko) {
			int globina = 1;
			System.out.println(Integer.toString(globina));
		}
		else if (source == menuSrednje) {
			int globina = 3;
			System.out.println(Integer.toString(globina));
		}
		else if (source == menuTezko) {
			int globina = 5;
			System.out.println(Integer.toString(globina));
		}
		else if (source == menuBarvaIgralca1) {
			Color barva = JColorChooser.showDialog(this, "Barva igralca 1", barvaIgralec1);
			if (barva != null) {
				barvaIgralec1 = barva;
				platno.repaint();
				System.out.println("Platno repainted");
			}
		}
		else if (source == menuBarvaIgralca2) {
			Color barva = JColorChooser.showDialog(this, "Barva igralca 2", barvaIgralec2);
			if (barva != null) {
				barvaIgralec2 = barva;
				platno.repaint();
				System.out.println("Platno repainted");
			}
		}
		else if (source == menuBarvaOzadja) {
			Color barva = JColorChooser.showDialog(this, "Barva ozadja", platno.barvaOzadja);
			if (barva != null) {
				platno.barvaOzadja = barva;
				platno.repaint();
				System.out.println("Platno repainted");
			}
		}
		else if (source == menuBarvaCrt) {
			Color barva = JColorChooser.showDialog(this, "Barva crt igrisca", platno.barvaCrt);
			if (barva != null) {
				platno.barvaCrt = barva;
				platno.repaint();
				System.out.println("Platno repainted");
			}
		}
	}
	
}
