import gui2.*;
import logika.*;

public class PaperSoccer {

	public static void main(String[] args) {
		
		Igra igra = new Igra();
		for (Polje[] vrstica : igra.getPlosca()) {
			for (Polje p : vrstica) {
				System.out.print(p + " ima "+ p.veljavnePoteze.size() + " povezave: ");
				for (Premik premik : p.veljavnePoteze) {
					System.out.print(premik + " ");
				}
				System.out.println();
			}
		}
		
		
		Okno okno = new Okno();
		okno.pack();
		okno.setVisible(true);
	}
}
