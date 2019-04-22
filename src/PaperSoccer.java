import gui.*;
import logika.*;

public class PaperSoccer {

	public static void main(String[] args) {
		Igra igra = new Igra();
		for (Polje[] vrstica : igra.getPlosca()) {
			for (Polje t : vrstica) {
				System.out.println(t + " ima " + t.veljavnePoteze.size() + " premikov:");
				for (Premik premik : t.veljavnePoteze) {
					System.out.println(premik);
				}
			}
		}
		
		Okno okno = new Okno();
		okno.pack();
		okno.setVisible(true);
	}

}
