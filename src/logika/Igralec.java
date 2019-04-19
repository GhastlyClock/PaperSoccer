package logika;

public enum Igralec {
	A, B;
	
	public Igralec nasprotnik() {
		return (this == A ? B : A);
	}

}
