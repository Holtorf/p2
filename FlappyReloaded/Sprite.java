import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
// KLASSE SPRITE FÜR ALLE GENUTZTEN SPIELELEMENTE
// SO LASSEN SICH EINFACH SPRITES AUS GRAFIKEN ERSTELLEN
/**
 * <h1>SPRITE</h1>
 * Easy was to create game elements with given image files.
 * Best to use gif files and create them with an alpha layer 
 * due to the transparent background.
 * Extend the class sprite for specific game classes - (e.g.: Flappy / Saeulen / Fireball)  
 * @author TMA
 * @since 2016-06-13
 *  
 */
public class Sprite {
	
	// INSTANZVARIABLEN
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected boolean visible;
	protected Image image;
	
	//KONSTRUKTOR
	/**
	 * first constructor for the sprite object - basic initialization
	 * @param x x-coordinate of the sprite (interger)
	 * @param y y-coordinate of the sprite (interger)
	 */
	public Sprite(int x, int y){
		this.x = x;
		this.y = y;
		visible = true;
	}
	/**
	 * second constructor FOR the sprite object - has more parameters
	 * @param x x-coordinate of the sprite (interger)
	 * @param y y-coordinate of the sprite (interger)
	 * @param w width of the sprite (interger)
	 * @param h height of the sprite (interger)
	 */
	public Sprite(int x, int y, int w, int h){
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		visible = true;
	}
	
	// METHODEN
	/**
	 * get the dimensions of the sprite's image - width and height
	 */
	protected void getImageDimensions(){
		width = image.getWidth(null);
		height = image.getHeight(null);
	} 
	/**
	 * create an imageicon object with the given imageName parameter -> set as image of the sprite object
	 * @param imageName (String) is the directory of the stored image file - e.g.:("images/flappy01.gif")
	 */
	protected void loadImage(String imageName){
		ImageIcon imageIcon = new ImageIcon(imageName);
		image = imageIcon.getImage();
	} 
	/**
	 * returns the whole image of the actual sprite object 
	 * @return image 
	 */
	public Image getImage(){
		return image;
	}
	/**
	 * get the x-coordinate of the sprite object
	 * @return x-coorinate as interger
	 */
	public int getX(){
		return x;
	}
	/**
	 * get the y-coordinate of the sprite object
	 * @return y-coordinate as integer
	 */
	public int getY(){
		return y;
	}
	/**
	 * get the width of the sprite object
	 * @return width value as integer 
	 */
	public int getWidth(){
		return width;
	}
	/**
	 * get the actual height of the sprite object
	 * @return height value as integer
	 */
	public int getHeight(){
		return height;
	}
	/**
	 * returns if sprites is visible or not
	 * @return the actual variable visible of the sprite: visible true / false
	 */
	public boolean isVisible(){
		return visible;		
	}
	/**
	 * set the visibility of the sprite as true / false -> variable of the object: Boolean visible
	 * @param val is set true / false
	 * */
	public void setVisible(Boolean val){
		visible = val;
	}
	/**
	 * returns the bounds of the rectangle
	 * @return x / y coordinate and the width / height of the rectangle 
	 */
	public Rectangle getBounds(){
		return new Rectangle(x,y,width,height);
	} 
}
