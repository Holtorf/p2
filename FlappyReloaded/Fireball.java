// KLASSE FÜR FEUERBÄLLE
/**
 * <h1>FIREBALL</h1>
 * Fireballs extends Sprite and is used to create the fireballs shot by flappy bird
 * The image file could easily be change -> imgSrc
 * The speed of the flying fireballs is defined with fireballSpeed 
 * @author TMA
 * @since 2016-06-12
 *
 */
public class Fireball extends Sprite{
	
	// INSTANZVARIABLEN
	// fireballSpeed definiert die Fluggeschwindigkeit der Feuerbälle
	// auf der X-Achse - höherer Wert = schneller
	// boardWidth legt den rechnte Bildrand des Spielbildschirmes fest
	private final int fireballSpeed = 4;
	private final int boardWidth = 800;
	private final String imgSrc = "images/fireball.gif";
	
	// METHODEN
	/**
	 * <h1>CONSTRUCTOR</h1>
	 * -> calls initFireball()
	 * @param x (integer) x-coordinate of the fireball
	 * @param y (integer) y-coordinate of the fireball
	 */
	public Fireball(int x, int y){
		super(x, y);
		initFireball();
	}
	/**
	 * -> calls loadImage and getImageDimensions()
	 * for the image file and its dimensions
	 * initialize the fireball
	 */
	private void initFireball(){
		loadImage(imgSrc);
		getImageDimensions();
	}
	/**
	 * moves the fireball in its x-direction,
	 * if the border of the gamepanel is reach,
	 * the fireballs visibility is set to false
	 */
	public void move(){
		x += fireballSpeed;
		if(x > boardWidth){
			visible = false;
		}
	} 
}
