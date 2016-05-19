package vorbereitung;

public class ClientServerConnection {

	public static void main(String[] args) {
		
		class ServerThread implements Runnable {
			public void run() {
				new Server();
			}
		}

		class ClientThread implements Runnable {
			public void run() {
				new Client();
			}
		}

		Thread server = new Thread(new ServerThread());
		
		Thread client = new Thread (new ClientThread());

		server.start();

		client.start();
		

	}


}
