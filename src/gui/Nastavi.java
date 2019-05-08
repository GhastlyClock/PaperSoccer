package gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import logika.Vodja;

@SuppressWarnings("serial")
public class Nastavi extends JFrame {
	JPanel p;
	
	Vodja vodja;
	Platno platno;
	
	Color barva1;
	
	public Nastavi(Vodja vodja, Platno platno) {
		super();
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		this.setTitle("Nastavitve");
		
		this.vodja = vodja;
		this.platno = platno;
		
		
		p = new JPanel(new GridLayout(1, 1));
		
		
		JTabbedPane platnoZavihki = new JTabbedPane();
         
        JComponent zavihek1 = new JPanel(false);
        
        JButton btn0 = new JButton("Barva");
        JButton btn1 = new JButton("Shrani");
        JButton btn2 = new JButton("Prekliči");
        btn0.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		barva1 = izbiraBarve(platno.barvaIgralecA);
        	}
        });
        btn1.setBackground(Color.BLUE);
        btn2.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		setVisible(false);
        	}
        });
        zavihek1.add(btn0);
        zavihek1.add(btn1);
        zavihek1.add(btn2);
        
        platnoZavihki.addTab("Barva",zavihek1);
        
        
        
        
        // Alt + B odpre zavihtek "Barva"
        platnoZavihki.setMnemonicAt(0, KeyEvent.VK_B);
         
        JComponent zavihek2 = new JPanel(false);
        platnoZavihki.addTab("Velikost",zavihek2);
        
        // Alt + V odpre zavihtek "Velikost"
        platnoZavihki.setMnemonicAt(1, KeyEvent.VK_V);
         
        JComponent zavihek3 = new JPanel(false);
        platnoZavihki.addTab("Igra", zavihek3);

        // Alt + I odpre zavihtek "Igra"
        platnoZavihki.setMnemonicAt(2, KeyEvent.VK_I);
         
        //Add the tabbed pane to this panel.
        p.add(platnoZavihki);
         
        //The following line enables to use scrolling tabs.
        platnoZavihki.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    
		
		this.add(p, BorderLayout.CENTER);
	} 
	
	private Color izbiraBarve(Color trenutnaBarva) {
		Color barva = JColorChooser.showDialog(this, "Barva", trenutnaBarva);
		return barva;
	}
	
}
