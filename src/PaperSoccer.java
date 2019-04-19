import gui.*;
import logika.*;

public class PaperSoccer {

	public static void main(String[] args) {
		Igra igra = new Igra();
		for (Tocka[] vrstica : igra.plosca) {
			for (Tocka t : vrstica) {
				System.out.println(t + " ima " + t.veljavnePoteze.size() + " premikov:");
				for (int[] premik : t.veljavnePoteze) {
					System.out.println("Premik = (" + premik[0] + "," + premik[1] + ")");
				}
			}
		}
//		
//		Tocka test = new Tocka(9,3);
//		test.nastavitevVeljavnePoteze(9, 7);
//		for (int[] premik : test.veljavnePoteze) {
//			System.out.println("Premik = (" + premik[0] + "," + premik[1] + ")" + " stevilo potez je " + test.veljavnePoteze.size());
//		}
//		System.out.println("+-+-+-+-++-+++-+--++-+-+");
//		test.odstraniPotezo(1, 0);
//		test.odstraniPotezo(-1, -1);
		
		
//		for (int[] premik : test.veljavnePoteze) System.out.println("Premik = (" + premik[0] + "," + premik[1] + ")"+ " stevilo potez je " + test.veljavnePoteze.size());
		
		Okno okno = new Okno();
		okno.pack();
		okno.setVisible(true);
	}

}
