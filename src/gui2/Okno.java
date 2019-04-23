package gui2;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import logika.Igra;
import logika.Vodja;
import logika.VrstaIgralca;


@SuppressWarnings("serial")
public class Okno extends JFrame implements ActionListener {
	
	Platno platno;
	
	private JMenuItem menuClovekClovek, menuClovekRacunalnik;
	private JMenuItem menuLahko, menuSrednje, menuTezko;
	private JMenuItem menuOdpri, menuShrani, menuKoncaj;
	private JMenuItem menuBarvaIgralcaA, menuBarvaIgralcaB, menuBarvaOzadja, menuBarvaCrt;
	
	private JLabel status;
	
	private Vodja vodja;
	
	public Okno() {
		super();
		this.setTitle("Paper Soccer");
		this.setLayout(new GridBagLayout());

		this.vodja = new Vodja(this);
		
		platno = new Platno((Igra.STOLPEC+2-1)*60, (Igra.VRSTICA+2)*60, vodja);
		
		JMenuBar menubar = new JMenuBar();
		
		JMenu menuIgra = new JMenu("Igra");
		JMenu menuNastavitve = new JMenu("Nastavitve");
		
		menuClovekClovek = new JMenuItem("Clovek proti cloveku ...");
		menuClovekRacunalnik = new JMenuItem("Clovek proti racunalniku ...");
		
		menuLahko = new JMenuItem("Lahka tezavnost");
		menuSrednje = new JMenuItem("Srednja tezavnost");
		menuTezko = new JMenuItem("Tezka tezavnost");
		
		menuOdpri = new JMenuItem("Odpri igro ...");
		menuShrani = new JMenuItem("Shrani igro ...");			
		menuKoncaj = new JMenuItem("Koncaj");
		
		menuBarvaIgralcaA = new JMenuItem("Barva igralca A ...");
		menuBarvaIgralcaB = new JMenuItem("Barva igralca B ...");
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
		menuNastavitve.add(menuBarvaIgralcaA);
		menuNastavitve.add(menuBarvaIgralcaB);
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
		
		menuBarvaIgralcaA.addActionListener(this);
		menuBarvaIgralcaB.addActionListener(this);
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
		GridBagConstraints status_layout = new GridBagConstraints();
		status_layout.gridx = 0;
		status_layout.gridy = 1;
		status_layout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(status, status_layout);

		vodja.novaIgra(VrstaIgralca.CLOVEK, VrstaIgralca.RACUNALNIK);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object source = e.getSource();
		if (source == menuOdpri) {
			JFileChooser chooser = new JFileChooser ();
			int gumb = chooser.showOpenDialog(this);
			if (gumb == JFileChooser.APPROVE_OPTION) {
				String ime = chooser.getSelectedFile().getPath();
				Igra igra = Vodja.preberi(ime);
				vodja.igra = igra;
				vodja.igramo();
			}
		}
		else if (source == menuShrani) {
			JFileChooser chooser = new JFileChooser ();
			int gumb = chooser.showOpenDialog(this);
			if (gumb == JFileChooser.APPROVE_OPTION) {
				String ime = chooser.getSelectedFile().getPath();
				vodja.shrani(ime);
			}
		}
		else if (source == menuKoncaj) {
			System.exit(0);
		}
		else if (source == menuClovekClovek) {
			vodja.novaIgra(VrstaIgralca.CLOVEK, VrstaIgralca.CLOVEK);
		}
		else if (source == menuClovekRacunalnik) {
			vodja.novaIgra(VrstaIgralca.CLOVEK, VrstaIgralca.RACUNALNIK);
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
		else if (source == menuBarvaIgralcaA) {
			Color barva = JColorChooser.showDialog(this, "Barva igralca A", platno.barvaIgralecA);
			if (barva != null) {
				platno.barvaIgralecA = barva;
				platno.repaint();
				System.out.println("Platno repainted");
			}
		}
		else if (source == menuBarvaIgralcaB) {
			Color barva = JColorChooser.showDialog(this, "Barva igralca B", platno.barvaIgralecB);
			if (barva != null) {
				platno.barvaIgralecB = barva;
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

	public void osveziGUI() {
		if (vodja.igra == null) {
			status.setText("Igra ni v teku.");
		}
		else {
			switch(vodja.igra.stanje()) {
			case NA_POTEZI_A : status.setText("Na potezi je igralec A"); break;
			case NA_POTEZI_B : status.setText("Na potezi je igralec B"); break;
			case ZMAGA_A : status.setText("Zmagal je igralec A"); break;
			case ZMAGA_B : status.setText("Zmagal je igralec B"); break;			
			}
		}
		platno.repaint();
	}
	
}
