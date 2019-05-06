package gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class Nastavi extends JFrame {
	JPanel p;
	public Nastavi() {
		super();
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		p = new JPanel(new GridLayout(1, 1));
		
		JTabbedPane platnoZavihtki = new JTabbedPane();
         
        JComponent zavihtek1 = new JPanel(false);
        zavihtek1.setPreferredSize(new Dimension(500, 300));
        platnoZavihtki.addTab("Barva",zavihtek1);
        
        // Alt + B odpre zavihtek "Barva"
        platnoZavihtki.setMnemonicAt(0, KeyEvent.VK_B);
         
        JComponent zavihtek2 = new JPanel(false);
        platnoZavihtki.addTab("Velikost",zavihtek2);
        
        // Alt + V odpre zavihtek "Velikost"
        platnoZavihtki.setMnemonicAt(1, KeyEvent.VK_V);
         
        JComponent zavihtek3 = new JPanel(false);
        platnoZavihtki.addTab("Igra", zavihtek3);

        // Alt + I odpre zavihtek "Igra"
        platnoZavihtki.setMnemonicAt(2, KeyEvent.VK_I);
         
        //Add the tabbed pane to this panel.
        p.add(platnoZavihtki);
         
        //The following line enables to use scrolling tabs.
        platnoZavihtki.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    
		
		this.add(p, BorderLayout.CENTER);
	} 
	
}
