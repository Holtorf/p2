import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

// ECHO-CLIENT FÜR DEN HIGHSCOR-SERVER
// MIT HILFE DES ECHO CLIENTS LÄSST SICH EIN SPIELER OBJEKT AN DEN ECHOSERVER SENDEN,
// DIESER GIBT DANN EIN HIGHSCORE OBJEKT ZURÜCK
// 
/**
 * <h1>ECHO-CLIENT</h1>
 * The echo client is used to send the acutal player object (name and points) to the 
 * highscore and receives the sorted highscore in response.
 * The host / port can easily be changed to any requirements 
 * @author TMA
 * @since 2016-06-13
 */
public class EchoClient {
	
	// INSTANZVARIABLEN
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;
	private Socket socket;
	private Highscore highscore;
	private int port = 4444;
	private String host = "localhost";
	
	// KONSTRUKTOR
	/**
	 * <h1>CONSTRUCTOR</h1>
	 * inside the try-part an in-/outputObjectStream is created
	 * the catch-part is defined to handle any occurring exceptions
	 * but the user feedback is missing 
	 */
	public EchoClient(){
		// TRY-BLOCK
		try{
			// SOCKET  
			socket = new Socket(host, port); 
			
			// STREAMS
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			inputStream = new ObjectInputStream(socket.getInputStream());
			  
		// CATCH-BLOCK	
		}catch(IOException e){
			System.err.println(e);
		}catch (Exception e){ 
			e.printStackTrace();
		}
		finally{
			//closeConnection();
		}
	}
	
	// METHODEN
	/**
	 * writes a Player object via the outputStream and afterwards flushes the stream<br>
	 * in return it waits for a Highscore object in response of the EchoServer
	 * @param player (Player) actual Player (name/score)
	 * @return (Highscore) returns the sorted Highscore
	 */
	public Highscore updateHighscore(Player player){
		// TRY-BLOCK
		// DAS SPILER-OBJEKT AN DEN ECHOSERVER SENDEN
		try{
			outputStream.writeObject(player); 
			outputStream.flush();
			// AUF ANTWORT DES ECHOSERVERS WARTEN -> DANN DAS HIGHSCORE OBJEKT ZURÜCKGEBEN 
			while((highscore = (Highscore)inputStream.readObject()) != null){
				return highscore; 
			}	
		// CATCH-BLOCK	
		}catch(Exception e){
			return highscore;
		}  
		return highscore;
	}
	// STREAMS UND SOCKET SCHLIESSEN
	/**
	 * close the in-/outputStream and the open socket
	 */ 
	public void closeConnection(){
		try{
			outputStream.close();
			inputStream.close(); 
			socket.close();	 
		}catch(Exception e){
			System.err.println(e);
		}		
	}	 
}
