import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
// KLASSE FÜR DIE HIGHSCORE
// DIE HIGHSCORE WIRD MIT FILE I/O IN EINER DATEI GESPEICHERT UND AUS 
// DISER AUCH WIEDER GELESEN - DER DATEIPFAD LÄSST SICH EINFACH WECHSELN
// 
/**
 * <h1>HIGHSCORE</h1>
 * Highscore Serializable and is used to create a highscore
 * for a gaming instance - Serializable is used, because the highscore
 * is send as an object via the client / server stream.
 * 
 * You can change the directory of the highscore file at the beginning
 * - (e.g.: private String directory = "res/highscore";)
 *  
 * @author TMA
 * @since 2016-06-12
 *
 */
public class Highscore implements Serializable{
	
	// INSTANZVARIABLEN
	private String directory = "res/highscore";
	
	// KONSTRUKTOR
	public Highscore(){
		
	}
	
	// METHODEN
	// HIGHSCORE AUS DATEI "HIGHSCORE" LESEN UND ALS ARRAYLIST ZURÜCKGEBEN
	/**
	 * read the file with the highscore<br>
	 * parse the content (PLAYER: ... / SCORE: ...)<br>
	 * create a Player object and add to highScoreArrayList
	 * @return (ArrayList) highScoreArrayList
	 */
	public ArrayList<Player> getHighscore(){
		// STRING UND ARRAYLIST ERSTELLEN
		String highscore = "";
		ArrayList<Player> highScoreArrayList = new ArrayList<>(); 
		try {
			// HIGHSCORE AUS DATEI EINLESEN
			File file = new File(directory);
			Scanner reader = new Scanner(file);
			String player = "";
			int score;
			// ZEILENWEISE DURCHGEHEN
			while(reader.hasNext()){
				String line = reader.nextLine(); 
				Boolean isScore;
				// JEDE ZEILE PARSEN UND BEI ENEM MATCH VON "PLAYER: / SCORE: "
				if(line.contains("PLAYER:")){
					player = reader.nextLine();
				}
				if(line.contains("SCORE:")){
					score = Integer.parseInt(reader.nextLine());
					isScore = true;
					if(isScore){
						// PLAYER OBJEKT MIT GEPARSTEM NAMEN / SCORE ERSTELLEN
						// UND DER ARRAYLIST HINZUFÜGEN
						highScoreArrayList.add(new Player(player, score));
						isScore = false;
					}
				}
			}   
		}catch(IOException e) {
			e.printStackTrace(); 
		}  
		// ARRAYLIST ZURÜCKGEBEN
		return highScoreArrayList;
	}
	  
	// HIGHSCORE AUS DATEI "HIGHSCORE" LESEN UND MIT RETURN ALS STRING ZURÜCKGEBEN 
	/**
	 * Method to get the highscore from the file (directory) as a String 
	 * @return (String) highscore -> returns the highscore as a String
	 */
	public String getHighscoreAsString(){
		String highscore;
		// TRY-BLOCK
		try {
			// FILE / BUFFERED READER & STRING ERSTELLEN
			File file = new File(directory);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			// BUFFEREDRAEDER DURCHLAUFEN UND DIE ZEILEN ZUM STRINGBUFFER HINZUFÜGEN
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
				stringBuffer.append("\n");
			}
			// FILEREADER SCHLIESSEN UND DIE HIGHSCORE ALS STRING KONVERTIEREN
			fileReader.close(); 
			highscore = stringBuffer.toString(); 
		} catch (IOException e) {
			e.printStackTrace();
			highscore = "Highscore nicht gefunden!";
		}  
		// HIGHSCORE ALS STRING ZURÜCKGEBEN
		return highscore;
	}
	 
	// HIGHSCORE MIT ALTEN UND NEUEN WERTEN SPEICHERN -> directory
	/**
	 * Method to save new player values (name / score) to highscore file -> directory
	 * Get old highscore and add new values
	 * @param player
	 * @param score
	 */
	public void setHighscore(String player, int score){
		// ALTE HIGHSCORE 
		String oldHighscore = getHighscoreAsString();
		// NEUEN WERT HINZUFÜGEN
		oldHighscore += "PLAYER:\n" + player + "\nSCORE:\n" + score;
		// TRY-BLOCK
		// HIGHSCORE IN DATEI SPEICHERN -> directory
		try {
			File file = new File(directory);
			file.createNewFile();
			FileWriter writer = new FileWriter(file);
			writer.write(oldHighscore);
			writer.flush();
			writer.close(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	// ARRAYLIST SORTIERN UND AUSGEBEN
	/**
	 * Method to get the highscore via getHighscore()<br>
	 * sort the results, create a string and return the output
	 * 
	 * @return (String) output -> sorted highscore
	 */
	public String printHighScore(){ 
		//ARRAYLIST MIT PLAYER-OBJEKTEN VIA getHighscore ERHALTEN
		ArrayList<Player> highscore = getHighscore();
		
		// ARRAYLIST NACH PUNKTEANZAHL SORTIEREN -> COLLECTIONS SORT 
		Collections.sort(highscore, new Comparator<Player>() {
	        @Override
	        public int compare(Player player2, Player player1)
	        { 
	        	// player1/player2.getScore() LIEFERT DIE PUNKTEZAHL ALS INTEGER
	        	return Integer.compare(player1.getScore(), player2.getScore());
	        }
	    }); 
		
		String output = "";
		// DIE ARRAYLIST ITERIEREN UND DIE WERTE DER PLAYER-ELEMENT IN OUTPUT SPEICHERN
		for (Player player: highscore) {
		    output += player.getName() + " " + player.getScore() + "\n";
		}
		return output;
	}
	 
}
