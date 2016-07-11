import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
// KLASSE FÜR FLAPPY
/**
 * <h1>FLAPPY BIRD</h1>
 * Flappy extends the class Sprite and is used to create the flappy-bird figure
 * You can change the image via setImgSrc() to the specific path 
 * and can easily change the appearance of flappy bird
 * @author TMA
 * @since 2016-06-12
 * 
 */
public class Flappy extends Sprite{
	
	// INSTANZVARIABLEN
	// fallVelocity gibt an, wie schnell Flappy pro Timertick fällt
	// climbRate gibt an, wie schnell Flappy pro Mausklick steigt
	// -> größerer Wert = schneller
	private int directionY;
	private int fallVelocity = 1;
	private int climbRate = 15;
	private ArrayList<Fireball> fireballs;
	private String imgSrc = "images/flappy01.gif";
	
	//KONSTRUKTOR
	/**
	 * <h1>CONSTRUCTOR</h1>
	 * @param x (integer) x-coordinate of flappy
	 * @param y (integer) y-coordinate of flappy
	 * calls initFlappy()
	 */
	public Flappy(int x, int y){
		super(x, y);
		initFlappy();
	}
	// METHODEN
	/**
	 * initialize flappy bird 
	 * -> load the image file and get the dimensions
	 * -> initialize the ArrayList for flappy's fireballs 
	 */
	private void initFlappy(){ 
		loadImage(imgSrc);
		getImageDimensions(); 
		fireballs = new ArrayList<>();
	} 
	/**
	 * moves flappy in its y-direction
	 */
	public void move(){
		y -= directionY;
		directionY = 0;
	}
	/**
	 * moves flappy in its x-direction
	 * he "falls" per timer tick equals the given fallVelocity 
	 */
	public void fall(){
		y += fallVelocity;
	} 
	/**
	 * get the ArrayList with the actual fireballs of flappy
	 * @return fireballs (ArrayList)
	 */
	public ArrayList getFireballs(){
		return fireballs;
	}
	
	// KEY/MOUSE-EVENTS FÜR DIE STEUERUNG VON FLAPPY
	/**
	 * <h1>KEY-/MOUSE-EVENTS</h1>
	 * mouseClicked and arrow key up rises flappy bird
	 * spacebar releases fireballs
	 * @param ke (KeyEvent)
	 */
	public void keyPressed(KeyEvent ke){
		int key = ke.getKeyCode(); 
		if(key == KeyEvent.VK_SPACE){ 
			fire();
		}
		if(key == KeyEvent.VK_UP){
			directionY = climbRate; 
		}
		System.out.println("SPACE PRESSED");
	}  
	
	public void keyReleased(KeyEvent ke){
		int key = ke.getKeyCode();
		if(key == KeyEvent.VK_SPACE){
			directionY = 0; 
		}
	}
	/**
	 * each mouseClicked rises flappy bird as per given climbRate
	 * @param me
	 */
	public void mouseClicked(MouseEvent me){ 
		directionY = climbRate;  
	}
	/**
	 * mouseReleased stops flappy birds rise
	 * @param me
	 */
	public void mouseReleased(MouseEvent me){
		directionY = 0; 
	}
	/**
	 * adds a fire at x-/y-coordinate
	 * -> "fires" at horizontal direction
	 */
	public void fire(){
		fireballs.add(new Fireball(x+width, y-10));  
	}
	// HIER KÖNNEN ANDERE GRAFIKEN FÜR FLAPPY BIRD EINGEBUNDEN WERDEN
	// DIE UNTERSCHIEDLICHEN VERSIONEN (type) ENTSPRECHEN DER 
	// AUSWAHL AUF DEM STARTBILDSCHIRM
	/**
	 * set the source of the image file for each version of flappy bird
	 * simply add / change the image or path to create your own flappy bird
	 * @param type (integer) for different cases of the switch/case
	 */
	public void setImgSrc(int type){
		switch(type){
			case 0:
				imgSrc = "images/flappy01.gif"; 
				break;
			case 1:
				imgSrc = "images/flappy02.gif"; 
				break;
			case 2:
				imgSrc = "images/flappy03.gif"; 
				break; 
			default: 
				imgSrc = "images/flappy01.gif"; 
		}
		initFlappy();
	}
}
