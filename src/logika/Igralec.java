package logika;

import java.awt.Color;

public class Igralec {
	
	public Color barva;
	
	public Igralec(int i) {
		if (i == 1) barva = Color.BLUE;
		else barva = Color.RED;
	}
}
