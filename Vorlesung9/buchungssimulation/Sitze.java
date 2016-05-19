package buchungssimulation;


public class Sitze {

	private Sitz [] sitze;	//Array ist besser, man weiﬂ ja die feste anzahl der Sitze
	private BuchungsGUI view;
	
	public Sitze(int anzahl) {
		sitze = new Sitz [anzahl];
		
		for (int i = 0; i < sitze.length; i++) {
			sitze[i] = new Sitz(i);
		}
		
	}
	
	public void setBuchungsGUI (BuchungsGUI view) {
		this.view = view;
	}

	public boolean reservieren(int sitzNummer) {
		boolean tmp = false;
		synchronized (sitze) {
			if (sitzNummer >= 0 && sitzNummer < sitze.length) {
				if (sitze[sitzNummer].isFree()) {
					tmp = sitze[sitzNummer].reservate();
					view.reservieren (sitzNummer);
				}
			}			
		}
		return tmp;
	}

	public int getAnzahlSitze() {
		return sitze.length;
	}

	public int getAnzahlFreieSitze() {
		int anzahlSitze = 0;

		synchronized (sitze) {	
			for (Sitz sitz : sitze) {
				if (sitz.isFree()) {
					anzahlSitze++;
				}
			}
		}
		return anzahlSitze;
	}

}
