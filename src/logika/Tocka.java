package logika;

import java.util.*;

public class Tocka {
	
	public int x,y;
	
	public Set<Vector<Integer>> mozniPremiki;
	
	public Tocka(int x,int y) {
		this.x = x;
		this.y = y;
		
		mozniPremiki = new HashSet<Vector<Integer>>();
		if (x != 0 && x != 10 && x != 1 && x != 9 && y != 0 && y != 6) {
			Vector<Integer> v = new Vector<Integer>();
			v.add(0, 1);
			v.add(1, 1);
			mozniPremiki.add(v);
			v.add(1,0);
			mozniPremiki.add(v);
			v.add(1,-1);
			mozniPremiki.add(v);
			v.add(0,0);
			mozniPremiki.add(v);
			v.add(1,1);
			mozniPremiki.add(v);
			v.add(0,-1);
			mozniPremiki.add(v);
			v.add(1,0);
			mozniPremiki.add(v);
			v.add(1,-1);
			mozniPremiki.add(v);
		}
		else ;
	}
	
	
	
}
