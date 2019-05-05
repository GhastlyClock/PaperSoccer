package gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import logika.*;


@SuppressWarnings("serial")
public class Okno extends JFrame implements ActionListener {
	
	Platno platno;
	
	Nastavitve nastavitve;
	
	private JMenuItem menuNovaIgra, menuNastavitve, menuOdpri, menuShrani, menuKoncaj;
	private JMenuItem menuClovekClovek, menuClovekRacunalnik, menuRacunalnikClovek, menuRacunalnikRacunalnik;
	
	private JLabel status;
	
	private Vodja vodja;
	
	public Okno() {
		super();
		this.setTitle("Paper Soccer");
		this.setLayout(new GridBagLayout());

		this.vodja = new Vodja(this);
		
		nastavitve = new Nastavitve(vodja, this);
		
		platno = new Platno(vodja);
		
		JMenuBar menubar = new JMenuBar();
		
		JMenu menuIgra = new JMenu("Igra");
		JMenu menuTipIgre = new JMenu("Tip igre");
		
		menuNovaIgra = new JMenuItem("Nova igra");
		menuNastavitve = new JMenuItem("Nastavitve ...");
		menuOdpri = new JMenuItem("Odpri igro ...");
		menuShrani = new JMenuItem("Shrani igro ...");			
		menuKoncaj = new JMenuItem("Koncaj ...");
		
		menuClovekClovek = new JMenuItem("Clovek proti cloveku ...");
		menuClovekRacunalnik = new JMenuItem("Clovek proti racunalniku ...");
		menuRacunalnikClovek = new JMenuItem("Racunalnik proti cloveku ...");
		menuRacunalnikRacunalnik = new JMenuItem("Racunalnik proti racunalniku ...");
		
		menuIgra.add(menuNovaIgra);
		menuIgra.addSeparator();
		menuIgra.add(menuNastavitve);
		menuIgra.add(menuOdpri);
		menuIgra.add(menuShrani);
		menuIgra.addSeparator();
		menuIgra.add(menuKoncaj);
		
		menuTipIgre.add(menuClovekClovek);
		menuTipIgre.add(menuClovekRacunalnik);
		menuTipIgre.add(menuRacunalnikClovek);
		menuTipIgre.add(menuRacunalnikRacunalnik);
		
		menubar.add(menuIgra);
		menubar.add(menuTipIgre);
		
		setJMenuBar(menubar);
		
		menuNovaIgra.addActionListener(this);
		
		menuNastavitve.addActionListener(this);
		menuOdpri.addActionListener(this);
		menuShrani.addActionListener(this);			
		
		menuKoncaj.addActionListener(this);
		
		menuClovekClovek.addActionListener(this);
		menuClovekRacunalnik.addActionListener(this);
		menuRacunalnikClovek.addActionListener(this);
		menuRacunalnikRacunalnik.addActionListener(this);
		
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
		
		else if (source == menuNovaIgra) {
			vodja.novaIgra(Vodja.igralecA, Vodja.igralecB);
		}
		else if (source == menuNastavitve) {
			nastavitve.frame.setVisible(true);
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
		else if (source == menuRacunalnikClovek) {
			vodja.novaIgra(VrstaIgralca.RACUNALNIK, VrstaIgralca.CLOVEK);
		}
		else if (source == menuRacunalnikRacunalnik) {
			vodja.novaIgra(VrstaIgralca.RACUNALNIK, VrstaIgralca.RACUNALNIK);
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
