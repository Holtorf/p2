package readWriteObjects;
import java.io.Serializable;

//ihr müsst eigene Klassen serialisierbar machen, damit sie geschrieben werden können. 
//Achtet darauf dass alle Attribute der Klasse auch Serialisierbar sind.
//alle standard daten typen sind es (String, int, etc)
public class MeinObjekt implements Serializable{

	
	//die serialVersionUID gibt die Version des Projekts an. Manchmal erweitert man Klassen so, 
	//dass die mit alten Daten nicht mehr mit der aktuellen Version der Klassen zusammen passen. 
	//Dann sollte die serialVersionUID auch verändert werden. "1L" ist der Defaultwert.
	private static final long serialVersionUID = 1L;
	
	// verwendet ihr in einer Klasse weitere selbst geschriebene Klassen (z.b. als Attribut), müssen diese auch Serializable sein
	//private WeiteresObjekt objekt;   
	private int anzahl;
	private String name;

	public MeinObjekt(int anzahl, String name) {
		this.anzahl = anzahl;
		this.name = name;
	}
	
	public void setAnzahl(int anzahl) {
		this.anzahl = anzahl;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getAnzahl() {
		return anzahl;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name + ", " + anzahl;
	}
}
