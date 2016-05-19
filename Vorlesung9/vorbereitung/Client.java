package vorbereitung;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	public Client() {

		try {
			String host = "127.0.0.1"; 
			// 1. Verbindung zum Server 
			Socket socket = new Socket(InetAddress.getByName(host), 12345);

			InputStream inStream = socket.getInputStream(); 
			
			// 2. Scanner erzeugen 
			Scanner input = new Scanner(inStream); 
			String message = input.nextLine(); 
			
			// 3. Text vom Server lesen 
			System.out.println("RECEIVED MESSAGE: " + message); 
			input.close(); 
			
			// 4. Verbindung schlieﬂen 
			socket.close();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		
	}
}
