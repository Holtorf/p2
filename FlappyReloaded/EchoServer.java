import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

// ECHO-SERVER UM DIE HIGHSCORE 
/**
 * The EchoServer creates a serverSockt and waits/accept<br>
 * a clientSocket -> (serverSocket.accept())<br>
 * The server has a input/outputStream<br>
 * After receiving a Player-object and adding it to the highscore<br>
 * the highscore gets transfered back to the echoClient in response.<br>
 * 
 * The port of the can is adjustable at the beginning -> private int port = 4444;
 * @author TMA
 *
 */
public class EchoServer {
		
	// INSTANZVARIABLEN 
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private Socket clientSocket;
	private int port = 4444;
	 
	// KONSTRUKTOR
	/**
	 * <h1>CONSTRUCTOR</h1>
	 * create the echoServer and waits for an ObjectInputStream<br>
	 * the received Player gets added to the highscore and echoes it in response.  
	 */
	public EchoServer(){ 
		// TRY-BLOCK
		try{
			Highscore highscore = new Highscore();
			// SOCKET
			port = 4444;
			ServerSocket serverSocket = new ServerSocket(port); 
			
			while(true){
				clientSocket = serverSocket.accept(); 
				
				// STREAMS
				inputStream = new ObjectInputStream(clientSocket.getInputStream());
				outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
			
				// AUF DATEN WARTEN UND ECHOEN
				Player player;
				while((player = (Player)inputStream.readObject()) != null){ 
					// PLAYER DER HIGHSCORE HINZUFÜGEN					
					highscore.setHighscore(player.getName(), player.getScore());   
					// HIGHSCORE AN CLIENT ECHOEN
					outputStream.writeObject(highscore); 
					outputStream.flush(); 
					break;
				}   
			}
		// CATCH-BLOCK	
		}catch(IOException e) {
			System.err.println(e);
		}catch(ClassNotFoundException e) {
			System.err.println(e);
		}finally{
			try{
				closeConnection(); 
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	// METHODEN
	// VERBINDUNG BEENDEN
	/**
	 * method to close the input-/outpuStream and the socket
	 */
	public void closeConnection(){
		try{
			inputStream.close();
			outputStream.close(); 
			clientSocket.close();
			System.out.println("CONNECTIONS CLOSED");
		}catch(Exception e){
			System.err.println(e);
		}
	}
	
	// MAIN 
	public static void main(String[] arg)throws Exception{
		EchoServer echoServer = new EchoServer();
	} 
}
