package gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import logika.*;


@SuppressWarnings("serial")
public class Okno extends JFrame implements ActionListener {
	
	public Platno platno;
	
	Nastavi nastavitve;
	
	private JMenuItem menuNovaIgra, menuNastavitve, menuOdpri, menuShrani, menuKoncaj;
	
	private JLabel status;
	
	private Vodja vodja;
	
	public Okno() {
		super();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Paper Soccer");
		this.setLayout(new GridBagLayout());
 
		this.vodja = new Vodja(this);

		platno = new Platno(vodja);
		
		// uporabi okno nastavitve
		nastavitve = new Nastavi(vodja, platno);
		nastavitve.pack();
		
		// nov menu
		JMenuBar menubar = new JMenuBar();
		
		// zavihek v menuju
		JMenu menuIgra = new JMenu("Igra");
		
		// opcije v zavihku
		menuNovaIgra = new JMenuItem("Nova igra ...");
		menuNastavitve = new JMenuItem("Nastavitve ...");
		menuOdpri = new JMenuItem("Odpri igro ...");
		menuShrani = new JMenuItem("Shrani igro ...");			
		menuKoncaj = new JMenuItem("Koncaj ...");
		
		// doda opcije v zavihek
		menuIgra.add(menuNovaIgra);
		menuIgra.addSeparator();
		menuIgra.add(menuNastavitve);
		menuIgra.add(menuOdpri);
		menuIgra.add(menuShrani);
		menuIgra.addSeparator();
		menuIgra.add(menuKoncaj);
		
		// doda zavihek v menu
		menubar.add(menuIgra);
		
		// doda menu v okno
		setJMenuBar(menubar);
		
		// ActionListener-ji za opcije v zavihku
		menuNovaIgra.addActionListener(this);
		
		menuNastavitve.addActionListener(this);
		menuOdpri.addActionListener(this);
		menuShrani.addActionListener(this);			
		
		menuKoncaj.addActionListener(this);
		
		
		// postavitev v oknu
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
		
		// zacetek igre
		vodja.novaIgra(VrstaIgralca.CLOVEK, VrstaIgralca.RACUNALNIK);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == menuOdpri) {
			// moznost odprtja stare igre
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
			// nova igra
			vodja.novaIgra(Vodja.igralecA, Vodja.igralecB);
		}
		else if (source == menuNastavitve) {
			// odpre okno nastavitve
			nastavitve.setVisible(true);
		}
		else if (source == menuShrani) {
			// moznost shraniti trenutno igro
			JFileChooser chooser = new JFileChooser ();
			int gumb = chooser.showOpenDialog(this);
			if (gumb == JFileChooser.APPROVE_OPTION) {
				String ime = chooser.getSelectedFile().getPath();
				vodja.shrani(ime);
			}
		}
		else if (source == menuKoncaj) {
			// zapre aplikacijo
			System.exit(0);
		}
	}

	// tekst v statusni vrstici
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
