package buchungssimulation;

public class Sitz {

	private int sitzNummer;
	private boolean isFree;
	
	public Sitz(int sitznummer) {
		this.sitzNummer = sitznummer;
		isFree = true;
	}
	
	public boolean isFree() {
		return isFree;
	}
	
	public boolean reservate () {	
		if (isFree) {
			isFree = false;
			return true;
		}
		
		return false;
	}

	
	public int getSitzNummer() {
		return sitzNummer;
	}
}
