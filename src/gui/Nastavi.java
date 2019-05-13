package gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import logika.Igra;
import logika.Vodja;
import logika.VrstaIgralca;

@SuppressWarnings("serial")
public class Nastavi extends JFrame {
	// dodaj polja izbire
	JPanel p;
	
	Vodja vodja;
	Platno platno;
	
	GridBagConstraints c;
	
	Color barvaIgralcaA, barvaIgralcaB;
	JSlider sldPolmerTock, sldDebelinaPovezave, sldVisinaPlatna;
	int visinaZaslona, razmikTock;
	
	JTabbedPane platnoZavihki;
	JComponent zavihek1;
	JLabel lblBarvaB, lblBarvaA;
	
	public Nastavi(Vodja vodja, Platno platno) {
		super();
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		
		this.setTitle("Nastavitve");
		
		this.vodja = vodja;
		this.platno = platno;
		
		visinaZaslona = platno.visinaZaslona;
		razmikTock = platno.razmikTock;
		
		
		// nastavitev barv na null
		resetiraj();
		
		p = new JPanel(new GridLayout(1, 1));
		p.setPreferredSize(new Dimension(600,400));;
		
		
		platnoZavihki = new JTabbedPane();
        
		
		// nov zavihek
        zavihek1 = new JPanel(new GridBagLayout());
        
        
        // mreza na zavihku 1
        mreza();
        
        
        // JButton in JLabel za barvo igralca A
        JLabel lblBarvaIgralcaA = new JLabel("Barva igralca A:");
        JButton btnBarvaIgralcaA = new JButton("Nastavi barvo...");
        lblBarvaA = new JLabel("    ");
        lblBarvaA.setOpaque(true);
        lblBarvaA.setBackground(platno.barvaIgralecA);
        btnBarvaIgralcaA.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		barvaIgralcaA = izbiraBarve(platno.barvaIgralecA);
        		lblBarvaA.setBackground(barvaIgralcaA);
        	}
        });
        
        // JButton in JLabel za barvo igralca B
        JLabel lblBarvaIgralcaB = new JLabel("Barva igralca B:");
        JButton btnBarvaIgralcaB = new JButton("Nastavi barvo...");
        lblBarvaB = new JLabel("    ");
        lblBarvaB.setOpaque(true);
        lblBarvaB.setBackground(platno.barvaIgralecB);
        btnBarvaIgralcaB.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		barvaIgralcaB = izbiraBarve(platno.barvaIgralecB);
        		lblBarvaB.setBackground(barvaIgralcaB);
        	}
        });
        
        
        // JButton in JLabel za barvo ozadja
        JLabel lblBarvaOzadja = new JLabel("Barva ozadja:");
        JButton btnBarvaOzadjaBela = new JButton("Bela");
        btnBarvaOzadjaBela.setBackground(Color.WHITE);
        JButton btnBarvaOzadjaZelena = new JButton("Zelena");
        btnBarvaOzadjaZelena.setForeground(Color.WHITE);
        btnBarvaOzadjaZelena.setBackground(new Color(0, 115, 30));
        btnBarvaOzadjaBela.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		platno.barvaOzadja = Color.WHITE;
        		platno.barvaCrt = Color.BLACK;
        		platno.barvaMoznihTock = Color.ORANGE;
        		platno.repaint();
        	}
        });
        btnBarvaOzadjaZelena.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		platno.barvaOzadja = new Color(0, 115, 30);
        		platno.barvaCrt = Color.WHITE;
        		platno.barvaMoznihTock = Color.YELLOW;
        		platno.repaint();
        	}
        });
        
        
        // JButton-i za ponastavi, uporabi in preklici
        JButton btnPonastavi = new JButton("Ponastavi");
        JButton btnUporabi = new JButton("Uporabi");
        btnUporabi.setBackground(new Color(30, 220, 220));
        JButton btnPreklici = new JButton("Preklici");
        btnUporabi.addActionListener(shrani());
        btnPreklici.addActionListener(preklici());
        btnPonastavi.addActionListener(ponastavi());
        
        
        // dodajanje komponent v mrezo
        c.insets = new Insets(0,0,0,40);
        pozicija(0,0);
        zavihek1.add(lblBarvaIgralcaA,c);
        c.insets = new Insets(20,0,0,40);
        pozicija(2,0);
        zavihek1.add(lblBarvaIgralcaB,c);
        pozicija(4,0);
        zavihek1.add(lblBarvaOzadja,c);
        c.insets = new Insets(0,30,0,0);
        pozicija(1,3);
        zavihek1.add(lblBarvaA,c);
        pozicija(3,3);
        zavihek1.add(lblBarvaB,c);
        c.insets = new Insets(0,0,0,0);
        pozicija(2,3);
        c.gridwidth = 2;
        pozicija(1,1);
        zavihek1.add(btnBarvaIgralcaA,c);
        pozicija(3,1);
        zavihek1.add(btnBarvaIgralcaB,c);
        c.gridwidth = 1;
        pozicija(5,1);
        zavihek1.add(btnBarvaOzadjaBela,c);
        c.insets = new Insets(0,10,0,0);
        pozicija(5,2);
        zavihek1.add(btnBarvaOzadjaZelena,c);
        c.insets = new Insets(50,0,0,40);
        pozicija(6,0);
        zavihek1.add(btnPonastavi, c);
        c.insets = new Insets(50,20,0,0);
        pozicija(6,2);
        zavihek1.add(btnUporabi,c);
        c.insets = new Insets(50,20,0,0);
        pozicija(6,3);
        zavihek1.add(btnPreklici,c);
        
        
        // dodajanje zavihka 1 med zavihke
        platnoZavihki.addTab("Barva",zavihek1);
        
        
        
        
        // Alt + B odpre zavihtek "Barva"
        platnoZavihki.setMnemonicAt(0, KeyEvent.VK_B);
         
        
        // nov zavihek 2
        JComponent zavihek2 = new JPanel(new GridBagLayout());
        platnoZavihki.addTab("Velikost",zavihek2);
        
        // mreza na zavihku 2
        mreza();
        
        // JSlider in JLabel za polmer tock
        JLabel lblPolmerTock = new JLabel("Polmer tock:");
        sldPolmerTock = new JSlider(JSlider.HORIZONTAL, 0, 30, 10);
        sldPolmerTock.setLabelTable(sldPolmerTock.createStandardLabels(10, 0));
        sldPolmerTock.setMajorTickSpacing(10);
        sldPolmerTock.setMinorTickSpacing(2);
        sldPolmerTock.setPaintTicks(true);
        sldPolmerTock.setPaintLabels(true);
        
        // JSlider in JLabel za debelino povezave
        JLabel lblDebelinaPovezave = new JLabel("Debelina povezave:");
        sldDebelinaPovezave = new JSlider(JSlider.HORIZONTAL, 0, 20, 4);
        sldDebelinaPovezave.setLabelTable(sldDebelinaPovezave.createStandardLabels(5, 0));
        sldDebelinaPovezave.setMajorTickSpacing(5);
        sldDebelinaPovezave.setMinorTickSpacing(1);
        sldDebelinaPovezave.setPaintLabels(true);
        sldDebelinaPovezave.setPaintTicks(true);
        sldDebelinaPovezave.setPaintTrack(true);
        sldDebelinaPovezave.setSnapToTicks(true);
        
        // JSlider in JLabel za visino platna
        JLabel lblVisinaPlatna = new JLabel("Visina platna (v pixlih):");
        sldVisinaPlatna = new JSlider(JSlider.HORIZONTAL, platno.visinaZaslona/200*100, platno.visinaZaslona/100*120, platno.visinaZaslona);
        sldVisinaPlatna.setMajorTickSpacing(200);
        sldVisinaPlatna.setMinorTickSpacing(50);
        sldVisinaPlatna.setPaintTicks(true);
        sldVisinaPlatna.setPaintLabels(true);
        
        // JButton shrani na zavihku 2
        JButton btnShrani2 = new JButton("Uporabi");
        btnShrani2.setBackground(new Color(30, 220, 220));
        btnShrani2.addActionListener(shrani());
        
        
        // JButton preklici na zavihku 2
        JButton btnPreklici2 = new JButton("Preklici");
        btnPreklici2.addActionListener(preklici());
        
        // JButton ponastavi na zavihku 2
        JButton btnPonastavi2 = new JButton("Ponastavi");
        btnPonastavi2.addActionListener(ponastavi());
        
        // dodajanje komponent v mrezo
        pozicija(0,0);
        zavihek2.add(lblPolmerTock,c);
        c.insets = new Insets(20,0,0,0);
        pozicija(2,0);
        zavihek2.add(lblDebelinaPovezave,c);
        pozicija(4,0);
        zavihek2.add(lblVisinaPlatna,c);
        pozicija(1,1);
        c.insets = new Insets(0,0,0,0);
        zavihek2.add(sldPolmerTock,c);
        pozicija(3,1);
        zavihek2.add(sldDebelinaPovezave,c);
        pozicija(5,1);
        zavihek2.add(sldVisinaPlatna,c);
        c.insets = new Insets(50,0,0,30);
        pozicija(6,0);
        zavihek2.add(btnPonastavi2,c);
        c.insets = new Insets(50,150,0,0);
        pozicija(6,1);
        zavihek2.add(btnShrani2,c);
        c.insets = new Insets(50,20,0,0);
        pozicija(6,2);
        zavihek2.add(btnPreklici2,c);
        
        // Alt + V odpre zavihtek "Velikost"
        platnoZavihki.setMnemonicAt(1, KeyEvent.VK_V);
        
        
        // nov zavihek 3
        JComponent zavihek3 = new JPanel(new GridBagLayout());
        platnoZavihki.addTab("Igra", zavihek3);
        
        // mreza zavihek 3
        mreza();
        
        // JComboBox in JLabel za nacin igre
        JLabel lblNacin = new JLabel("Nacin igre:");
        String[] nacini = {"Clovek proti cloveku", "Clovek proti racunalniku", "Racunalnik proti cloveku", "Racunalnik proti racunalniku"};
        @SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cmbNacin = new JComboBox(nacini);
        cmbNacin.setSelectedIndex(1);
        
        // JRadioButton-i in JLabel za težavnost
        JLabel lblTezavnost = new JLabel("Tezavnost (ce se igra proti racunalniku):");
        JRadioButton rbtnLahka = new JRadioButton("Lahka");
        JRadioButton rbtnSrednja = new JRadioButton("Srednja");
        rbtnSrednja.setSelected(true);
        JRadioButton rbtnTezka = new JRadioButton("Tezka");
        
        // grupiranje JRadioButton-ov
        ButtonGroup group = new ButtonGroup();
        group.add(rbtnLahka);
        group.add(rbtnSrednja);
        group.add(rbtnTezka);
        
        // JButton nova igra
        JButton btnNovaIgra = new JButton("Nova igra");
        btnNovaIgra.setBackground(new Color(30, 150, 220));
        btnNovaIgra.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (rbtnLahka.isSelected()) Vodja.globinaMinimax = 1;
        		else if (rbtnSrednja.isSelected()) Vodja.globinaMinimax = 3;
        		else if (rbtnTezka.isSelected()) Vodja.globinaMinimax = 5;
        		if (cmbNacin.getSelectedItem().toString() == "Clovek proti cloveku") {
        			vodja.novaIgra(VrstaIgralca.CLOVEK, VrstaIgralca.CLOVEK);
        		}
        		else if (cmbNacin.getSelectedItem().toString() == "Clovek proti racunalniku") {
        			vodja.novaIgra(VrstaIgralca.CLOVEK, VrstaIgralca.RACUNALNIK);
        		}
        		else if (cmbNacin.getSelectedItem().toString() == "Racunalnik proti cloveku") {
        			vodja.novaIgra(VrstaIgralca.RACUNALNIK, VrstaIgralca.CLOVEK);
        		}
        		else if (cmbNacin.getSelectedItem().toString() == "Racunalnik proti racunalniku") {
        			vodja.novaIgra(VrstaIgralca.RACUNALNIK, VrstaIgralca.RACUNALNIK);
        		}
        		setVisible(false);
        	}
        });
        
        //JButton preklici
        JButton btnPreklici3 = new JButton("Preklici");
        btnPreklici3.addActionListener(preklici());
        
        // dodajanje komponent v mrezo
        pozicija(0,0);
        zavihek3.add(lblNacin,c);
        c.gridwidth = 2;
        c.insets = new Insets(10,0,0,0);
        pozicija(1,2);
        zavihek3.add(cmbNacin,c);
        c.gridwidth = 3;
        c.insets = new Insets(40,0,10,0);
        pozicija(2,0);
        zavihek3.add(lblTezavnost,c);
        c.gridwidth = 1;
        c.insets = new Insets(0,40,0,0);
        pozicija(3,1);
        zavihek3.add(rbtnLahka,c);
        pozicija(3,2);
        c.insets = new Insets(0,40,0,0);
        zavihek3.add(rbtnSrednja,c);
        pozicija(3,3);
        zavihek3.add(rbtnTezka,c);
        c.insets = new Insets(50,20,0,0);
        pozicija(4,3);
        zavihek3.add(btnNovaIgra,c);
        pozicija(4,4);
        zavihek3.add(btnPreklici3,c);
        
        
        

        // Alt + I odpre zavihtek "Igra"
        platnoZavihki.setMnemonicAt(2, KeyEvent.VK_I);
         
        //Add the tabbed pane to this panel.
        p.add(platnoZavihki);
         
        //The following line enables to use scrolling tabs.
        platnoZavihki.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    
		
		this.add(p, BorderLayout.CENTER);
	} 
	
	// funkcija, ki odpre JColorChooser
	private Color izbiraBarve(Color trenutnaBarva) {
		Color barva = JColorChooser.showDialog(this, "Barva", trenutnaBarva);
		return barva;
	}
	
	
	// nastavi vse, kot je bilo pred odprtjem okna nastavitve
	private void resetiraj() {
		barvaIgralcaA = barvaIgralcaB = null;
		try {
			sldPolmerTock.setValue((int) platno.polmer);
			sldDebelinaPovezave.setValue((int) platno.debelinaPovezave);
			sldVisinaPlatna.setValue((int) platno.visinaZaslona);
			platnoZavihki.setSelectedComponent(zavihek1);
			barvaIgralcaA = platno.barvaIgralecA;
			barvaIgralcaB = platno.barvaIgralecB;
    		lblBarvaB.setBackground(barvaIgralcaB);
    		lblBarvaA.setBackground(barvaIgralcaA);
		}
		catch (Exception NullPointerException) {};
	}
	
	
	// ustvari novo mrezo
	private void mreza() {
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
	}
	
	
	// doloci pozicijo v mrezi
	private void pozicija(int i, int j) {
		c.gridx = j;
		c.gridy = i;
	}
	
	// funkcija shrani, ki vrne ActionListener za JButton shrani/uporabi
	private ActionListener shrani() {
		ActionListener shrani = new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (barvaIgralcaA != null) platno.barvaIgralecA = barvaIgralcaA;
        		if (barvaIgralcaB != null) platno.barvaIgralecB = barvaIgralcaB;
        		platno.polmer = sldPolmerTock.getValue();
        		platno.debelinaPovezave = sldDebelinaPovezave.getValue();
        		int visinaZaslona1 = sldVisinaPlatna.getValue();
        		int razmikTock1 = platno.razmikTock = (int) ((visinaZaslona1 - (visinaZaslona1 * 0.3)) / Igra.VRSTICA);
        		platno.sirina = (Igra.STOLPEC + 1) * razmikTock1;
        		platno.visina = (Igra.VRSTICA + 2) * razmikTock1;
        		platno.repaint();
        		setVisible(false);
        	}
        };
        return shrani;
	}
	
	// funkcija preklici, ki vrne ActionListener za JButton preklici
	private ActionListener preklici() {
		ActionListener preklici = new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		// vsa polja izbire nastavi na null
        		resetiraj();
        		setVisible(false);
        	}
        };
        return preklici;
	}
	
	// funkcija ponastavi, ki vrne ActionListener za JButton ponastavi (tako, kot je bilo ob zagonu igre)
	private ActionListener ponastavi() {
		ActionListener ponastavi = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				platno.barvaOzadja = new Color(0, 115, 30);
        		platno.barvaCrt = Color.WHITE;
        		platno.barvaMoznihTock = Color.YELLOW;
				platno.barvaIgralecA = Color.RED;
				platno.barvaIgralecB = Color.BLACK;
				platno.polmer = 10;
				platno.debelinaPovezave = 4;
				platno.visinaZaslona = visinaZaslona;
				platno.razmikTock = razmikTock;
				platno.sirina = (Igra.STOLPEC + 1) * razmikTock;
        		platno.visina = (Igra.VRSTICA + 2) * razmikTock;
				platno.repaint();
				try {
					sldPolmerTock.setValue((int) platno.polmer);
					sldDebelinaPovezave.setValue((int) platno.debelinaPovezave);
					sldVisinaPlatna.setValue((int) visinaZaslona);
				}
				catch (Exception NullPointerException) {};
			}
		};
		return ponastavi;
	}
	
}
