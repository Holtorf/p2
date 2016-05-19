package vorbereitung;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	public Server() {
		
		try {
			// 1. Server starten 			
			ServerSocket server = new ServerSocket(12345, 100);
			server.setSoTimeout(6000);	//Timeout nach einer Minute

			System.out.println("Waiting for connection"); 

			// 2. auf eingehende Verbindung warten 
			Socket socket = server.accept(); 
			System.out.println("Connection received from " + socket.getInetAddress().getHostName()); 
			OutputStream outStream = socket.getOutputStream(); 
			
			// 3. PrintWriter erzeugen 
			PrintWriter output = new PrintWriter(outStream); 
			output.println("Hallo, ich bin der Server"); 
			
			// 4. Nachricht an Client 
			output.flush(); 
			output.close(); 
			
			// 5. Verbindung schliessen 
			socket.close();

		
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
