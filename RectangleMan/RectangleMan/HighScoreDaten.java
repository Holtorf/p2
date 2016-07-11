package RectangleMan;

/**
 * 
 * @author Sina, Bager, Walid
 *	
 *	Mit dieser Methoden wird das Objekt HighScoreDaten erstellt mit den Strings name und score
 */
public class HighScoreDaten {

	//Variablen name und Score
	String name;
	String score;
	
	//Konstruktor mit name und score
	public HighScoreDaten(String name, String score)
	{
		this.name = name;
		this.score = score;
	}
	/**
	 * 
	 * @return name
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * 
	 * @return score
	 */
	public String getScore() 
	{
		return score;
	}
	
	/**
	 * toString methode
	 */
	@Override
	public String toString()
	{
		return "Highscore " + name + " : " + score;
	}
}
