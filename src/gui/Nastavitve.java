package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import logika.Vodja;
import logika.VrstaIgralca;

import java.awt.Color;

public class Nastavitve {

	Vodja vodja;
	JFrame frame;
	
	/**
	 * Create the application.
	 */
	public Nastavitve(Vodja vodja, Okno okno) {
		this.vodja = vodja;
		initialize(okno);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Okno okno) {
		frame = new JFrame();
		frame.setBounds(100, 100, 400, 250);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblNainIgre = new JLabel("Nacin igre:");
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Clovek proti cloveku", "Clovek proti racunalniku", "Racunalnik proti cloveku", "Racunalnik proti racunalniku"}));
		
		JLabel lblBarvaOzadja = new JLabel("Barva ozadja:");
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Bela", "Zelena"}));
		
		
		JLabel lblTeavnosteSe = new JLabel("Težavnost (ce se igra proti racunalniku):");
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"Lahka", "Srednja", "Težka"}));
		
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboBox_1.getSelectedItem().toString() == "Bela") {
					okno.platno.barvaOzadja = Color.WHITE;
					okno.platno.barvaCrt = Color.BLACK;
				}
				else if (comboBox_1.getSelectedItem().toString() == "Zelena") {
					okno.platno.barvaOzadja = new Color(0, 115, 30);
					okno.platno.barvaCrt = Color.WHITE;
				}
				if (comboBox_2.getSelectedItem().toString() == "Lahka") {
					System.out.println("Težavnost: Lahka");
					Vodja.globinaMinimax = 1;
				}
				else if (comboBox_2.getSelectedItem().toString() == "Srednja") {
					System.out.println("Težavnost: Srednja");
					Vodja.globinaMinimax = 3;
				}
				else if (comboBox_2.getSelectedItem().toString() == "Težka") {
					System.out.println("Težavnost: Težka");
					Vodja.globinaMinimax = 5;
				}
				if (comboBox.getSelectedItem().toString() == "clovek proti cloveku") {
					vodja.novaIgra(VrstaIgralca.CLOVEK, VrstaIgralca.CLOVEK);
					System.out.println("clovek clovek");
				}
				else if (comboBox.getSelectedItem().toString() == "clovek proti racunalniku") {
					vodja.novaIgra(VrstaIgralca.CLOVEK, VrstaIgralca.RACUNALNIK);
					System.out.println("clovek racunalnik");
				}
				else if (comboBox.getSelectedItem().toString() == "Racunalnik proti cloveku") {
					vodja.novaIgra(VrstaIgralca.RACUNALNIK, VrstaIgralca.CLOVEK);
					System.out.println("Racunalnik clovek");
				}
				else if (comboBox.getSelectedItem().toString() == "Racunalnik proti racunalniku") {
					vodja.novaIgra(VrstaIgralca.RACUNALNIK, VrstaIgralca.RACUNALNIK);
					System.out.println("Racunalnik racunalnik");
				}
				frame.setVisible(false);
			}
		});
		btnOk.setBackground(Color.BLUE);
		
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNainIgre)
								.addComponent(lblBarvaOzadja))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblTeavnosteSe)
							.addGap(18)
							.addComponent(comboBox_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(150, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(319, Short.MAX_VALUE)
					.addComponent(btnOk)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNainIgre)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblBarvaOzadja)
						.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTeavnosteSe)
						.addComponent(comboBox_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
					.addComponent(btnOk)
					.addContainerGap())
		);
		frame.getContentPane().setLayout(groupLayout);
	}
}