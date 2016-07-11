// KLASSE FÜR DIE HINTERGRUNDGRAFIK
/**
 * <h1>BACKGROUND</h1>
 * Background extends the class Sprite and is used to create the background objects
 * By editing the imgSrc path of a background type, you can easily edit/create other levels 
 * directionX sets the movement speed of the background per timer click
 * @author TMA
 * @since 2016-06-12
 *
 */
public class Background extends Sprite{
	
	// INSTANZVARIABLEN 
	// directionX definiert die Bewegungsgeschwindigkeit der Hintergrundgrafik
	// pro Timertick - größerer Wert = schneller
	private int directionX = 3;;
	private String imgSrc = "images/bg01.gif";
	
	// METHODEN
	/**
	 * <h1>CONSTRUCTOR</h1> 
	 * -> calls initBackground to initialize the background 
	 * @param x (integer) x-coordinate 
	 * @param y (integer) y-coordinate 
	 */
	public Background(int x, int y){
		super(x, y);
		initBackground(); 
	}
	/**
	 * initialize the background
	 * -> load the image (depending on image source imgSrc)
	 * and get the image dimensions 
	 */
	private void initBackground(){ 
		loadImage(imgSrc); 
		getImageDimensions(); 
	} 
	/**
	 * move the background in its x-direction 
	 * as per defined directionX
	 */
	public void move(){ 
		x -= directionX; 
	}
	/**
	 * set the image source for the different possible background
	 * -> sets imgSrc for every type - (e.g.: "images/bg01.gif")  
	 * @param type (integer) for different cases of the switch/case
	 * -> calls initBackground()
	 */
	public void setImgSrc(int type){
		switch(type){
			case 0:
				imgSrc = "images/bg01.gif"; 
				break;
			case 1:
				imgSrc = "images/bg02.gif"; 
				break;
			case 2:
				imgSrc = "images/bg03.gif"; 
				break; 
			default: 
				imgSrc = "images/bg01.gif"; 
		}
		initBackground();
	}
}
