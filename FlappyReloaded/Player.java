import java.io.Serializable;
// KLASSE FÜR EINEN SPIELER
/**
 * <h1>PLAYER</h1>
 * Player implements Serializable and is used to create a player
 * for a gaming instance - Serializable is used, because the actual
 * player object is send via socket to store the highscore at the echoserver
 * @author TMA
 * @since 2016-06-12
 *
 */
class Player implements Serializable{
	
	// INSTANZVARIABLEN
	private String name;
	private int score;
	
	// KONSTRUKTOR
	/**
	 * <h1>CONSTRUCTOR</h1>
	 * constructor to set up a player
	 * @param name (String) name of the actual player
	 * @param score (integer) amount of received points
	 */
	public Player(String name, int score){
		this.name = name;
		this.score = score; 
    }
	
	// METHODEN
	/**
	 * get the name of the player
	 * @return (string) name
	 */
	public String getName(){
		return name;
	}
	/**
	 * get the score of the player
	 * @return (integer) score
	 */
	public int getScore(){
		return score;
	} 
	/**
	 * set the score of the player
	 * @param score (integer) score of the player
	 */
	public void setScore(int score){
		this.score = score;
	}
	/**
	 * set the name of the player
	 * @param name (String) name of the player
	 */
	public void setName(String name){
		this.name = name;
	}
	
 } 