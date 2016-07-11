import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * The ClientWorker listens for incoming messages from the client in background and handles them.
 * @author Christopher von Bargen
 * @author Fynn Braren
 * @version 1.0
 */
public class ClientWorker extends SwingWorker<Void, Void> {

    private Socket socket;
    private HostThread hostThread;
    private Scanner scn;

    /**
     * Creates a new ClientWorker and scanner
     * @param socket socket to listen on
     * @param hostThread the corresponding hostThread
     */
    public ClientWorker(Socket socket, HostThread hostThread){
        this.socket = socket;
        this.hostThread = hostThread;
        try {
            scn = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * background task and handling
     */
    @Override
    protected Void doInBackground(){
        String line = scn.nextLine();
        if (line.startsWith("#s:")) {
            hostThread.run(Integer.parseInt(line.substring(3)));
        } else if (line.startsWith("#b:")) {
            hostThread.bail();
        } else if (line.startsWith("#n:")) {
            hostThread.setClientName(line.substring(3));
            System.out.println(line.substring(3) + " is set as name");
        } else if (line.startsWith("#c:")) {
            hostThread.server.newMessage(line.substring(3));
            System.out.println("MESSAGE: " + line.substring(3));
        } else if (line.startsWith("#r")) {
            hostThread.ready();
        } else if (line.startsWith("#w")) {
            hostThread.win();
        }
        return null;
    }

    /**
     * creates the next worker after a task
     */
    @Override
    protected void done() {
        ClientWorker cw =  new ClientWorker(socket, hostThread);
        cw.execute();
        super.done();
    }
}
