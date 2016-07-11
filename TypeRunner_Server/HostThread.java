import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

/**
 * Thread for hosting for one client. Handles the server's output for one client.
 * @author Christopher von Bargen
 * @author Fynn Braren
 * @version 1.0
 */
public class HostThread implements Runnable {

    private Socket socket;
    private PrintWriter output;
    private char serverSymbol;
    public Server server;
    private int player;

    /**
     * creates a new thread for a client
     * @param server corresponding server
     * @param socket corresponding socket
     * @param player player's number
     */
    public HostThread(Server server, Socket socket, int player) {
        this.player = player;
        this.server = server;
        this.socket = socket;
        //create I/O
        try {
            output = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        rollSymbol();
        //Tell the client, which number he is.
        output.println("#u:" + player);
        output.flush();
    }

    /**
     * runs the thread and creates a ClientWorker
     */
    @Override
    public void run() {
        ClientWorker clientWorker = new ClientWorker(socket, this);
        System.out.println(1);
        clientWorker.execute();
    }
    public void setClientName(String name) {
        server.publishClientName(player, name);
    }
    public void publishClientName(int player, String name){
        output.println("#n:"+player + name);
        output.flush();
    }

    /**
     * rolls a new ascii symbol for the client
     */
    public void rollSymbol() {
        Random random = new Random();
        char symbol = "qwertzuiopasdfghjklyxcvbnm".charAt(random.nextInt(25));
        serverSymbol = symbol;
        output.println("#s:"+symbol);
        output.flush();
    }

    /**
     * processes the ascii symbol sent from the client
     * @param timer the client's current cool down timer
     */
    public void run(int timer){
        server.playerRuns(player, timer);
        rollSymbol();
    }
    public void bail() {
        server.playerBails(player);
    }

    /**
     * tells the server, that the player is ready
     */
    public void ready(){
        server.ready(player);
    }
    public void newGame(){
        output.println("#g:");
        output.flush();
    }

    /**
     * tells the client, that the game is finished
     * @param winner
     */
    public void finishGame(int winner){
        output.println("#f:"+winner);
        output.flush();
    }

    /**
     * tells the client, that a player is running forward for one animation
     * @param player the corresponding player
     * @param timer the players current cool down timer
     */
    public void playerRuns(int player, int timer){
        output.println("#r:"+player + timer);
        output.flush();
    }

    /**
     * tells the client, that a player is falling down for one animation
     * @param player the corresponding player
     */
    public void playerBails(int player){
        output.println("#b:"+player);
        output.flush();
    }

    /**
     * tells the server, that a player has won
     */
    public void win(){
        server.setWinner(player);
    }
}
