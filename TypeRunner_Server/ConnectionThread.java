import java.io.IOException;

/**
 * Listens for possible connections to the server by a new client
 * @author Christopher von Bargen
 * @author Fynn Braren
 * @version 1.0
 */
public class ConnectionThread implements Runnable {

    Server server;

    /**
     * creates a new thread to listen for connections
     * @param server the corresponding server
     */
    public ConnectionThread(Server server){
        this.server = server;
    };
    @Override
    public void run() {
        try {
            System.out.println("***SERVER RUNNING**\nWaiting for client ...");
            server.connect(server.getConnectionSocket().accept());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ConnectionThread con = new ConnectionThread(server);
        con.run();
    }
}
