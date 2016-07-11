package RectangleMan;

/**
 *
 * @author Sina, Bager, Walid
 * 
 * Methode für die Spieler Daten
 *
 */
public class SpielerDaten {

	//Variablen
	private String name;
	private String beschreibung;
	private int lvlExp;
	
	//Konstruktor
	public SpielerDaten(String name, String beschreibung, int lvlExp)
	{
		this.name = name;
		this.beschreibung = beschreibung;
		this.lvlExp = lvlExp;
	}
	/**
	 * 
	 * @return beschreibung
	 */
	public String getBeschreibung() 
	{
		return beschreibung;
	}
	
	/**
	 * 
	 * @return lvlExp
	 */
	public int getLvlExp()
	{
		return lvlExp;
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
	 * toString Methode
	 */
	@Override
	public String  toString ()
	{
		return "(" + String.valueOf(lvlExp) + ")" + " " + name;
	}
}