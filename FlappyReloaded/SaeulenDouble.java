import java.util.ArrayList;
// KLASSE FÜR SÄULENSTAPEL
// DER SPALT WIRD RANDOM ZWISCHEN DEM 1TEN UND 7TEN SÄULENTEIL ERSTELLT
// -> MINDESTENS MIT DER HÖHE GAP)
/**
 * <h1>SAEULENDOUBLE</h1>
 * SauelenDouble contains the barricade elements "Saeulen",
 * the gap is generated at random between the 1st/7th element
 * gap defines the height of the gap 
 * posGap defines the position of the gap
 * @author TMA
 * @since 2016-06-13
 *
 */
public class SaeulenDouble{
	
	// INSTANZVARIABLEN
	private int y; 
	protected  ArrayList<Saeulen> saeulenElemente = new ArrayList<>();
	
	// KONSTRUKTOR
	/**
	 * <h1>CONSTRUCTOR</h1> 
	 * @param x (int) x-coordinate
	 * @param gap (int) 'height' of the gap
	 * @param width (int) width
	 * @param gamePanelHeight (int) height of the gamePanel
	 * @param type (int) type of the barricade (ice/fire/...)
	 */
	SaeulenDouble(int x, int gap, int width, int gamePanelHeight, int type){ 
		y = 0;
		// posGap LIEFERT PER ZUFALL DIE POSITION DER SPALTE
		int posGap = (int)(Math.random()*8)+1;
		for(int i = 0; i <= 9; i++){ 
			if(i != posGap){
				saeulenElemente.add(new Saeulen(x, y, 78, 42, type)); 
			}
			else{
				// gap GIBT DIE HÖHE DER SPALTE IN JEDER SÄULE AN
				y += gap;
			}
			// JEDES SÄULENELEMENT HAT EINE HÖHE VON 42 PIXELN,
			// DESWEGEN WIRD DAS NÄCHSTE TEIL IN DER Y-ACHSE
			// um y+= 42 PIXEL VERSCHOBEN
			y+=42; 
		}   
	} 
}
