package logika;

public class Premik {
	
	private int x, y;
	
	public Premik(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Premik nasprotniPremik() {
		int x1 = - this.getX();
		int y1 = - this.getY();
		return new Premik(x1, y1);
	}
	
	@Override
	public String toString() {
		return "Premik [x=" + x + ", y=" + y + "]";
	}
}
