import java.awt.Rectangle;
// KLASSE FÜR SÄULENTEILE
/**
 * <h1>BARRICADE</h1>
 * Sauelen extends the class Sprite and is used to create barricade objects.
 * You can change the image via setImgSrc() to the specific path
 * and can easily create different alternative barricades 
 * @author TMA
 * @since 2016-06-12
 *
 */
public class Saeulen extends Sprite{
	
	// INSTANZVARIABLEN
	// directionX ist quasi die Geschwindigkeit der Säulenteile auf der 
	// X-Achse pro Timertick - größerer Wert = schneller
	private int directionX = 3;
	private String imgSrc = "images/pipe01.gif";
	
	//KONSTRUKTOR
	/**
	 * <h1>CONSTRUCTOR</h1> 
	 * @param x (integer) x-coordinate of the barricade
	 * @param y (integer) y-coordinate of the barricade
	 * @param width (integer) width of the barricade
	 * @param height (integer) height of the barricade
	 * @param type (integer) type of the barricade -> different kinds (e.g. (red)fire / (blue)ice) 
	 */
	public Saeulen(int x, int y, int width, int height, int type){
		super(x, y, width, height);
		setImgSrc(type);
		initSaeulen(); 
	}
	
	//METHODEN
	/**
	 * initialize the Sauelen element
	 * -> calls loadImage() and getImageDimensions()
	 */
	private void initSaeulen(){ 
		loadImage(imgSrc); 
		getImageDimensions(); 
	}
	/**
	 * moves the barricade in its x-direction
	 * y-coordinate is steady
	 */
	public void move(){ 
		x -= directionX; 
	}
	/**
	 * set the image source for the different possible barricades
	 * -> sets imgSrc for every type - (e.g.: "images/pipe01.gif")  
	 * @param type (integer) for different cases of the switch/case
	 * -> calls initSaeulen()
	 */
	public void setImgSrc(int type){
		switch(type){
			case 0:
				imgSrc = "images/pipe01.gif"; 
				break;
			case 1:
				imgSrc = "images/pipe02.gif"; 
				break;
			case 2:
				imgSrc = "images/pipe03.gif"; 
				break; 
			default: 
				imgSrc = "images/pipe01.gif"; 
		}
		initSaeulen();
	}
}
