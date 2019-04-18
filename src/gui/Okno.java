package gui;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Okno extends JFrame {
	
	Platno platno;
	
	public Okno() {
		super();
		this.setTitle("Paper Soccer");
		platno = new Platno((7+2-1)*70, (11+2-2)*70);
		this.add(platno);
		platno.setBackground(platno.barvaOzadja);
	}
	
}
