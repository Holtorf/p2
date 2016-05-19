package staticBsp;

public class Kunde {
	
	private String vorname;
	private String nachname;
	private int id;
	private static int nextID = 1;
	
	public Kunde(String vorname, String nachname) {
		this.nachname = nachname;
		this.vorname = vorname;
		this.id = nextID;
		
		nextID++;
	}
		
	@Override
	public String toString() {
		return id + ": " + nachname + ", " + vorname;
	}
}
