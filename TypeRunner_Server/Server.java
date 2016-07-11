
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The main server of the game. broadcasts information from one client to all.
 * Handles game starts and endings and connects to clients.
 * @author Christopher von Bargen
 * @author Fynn Braren
 * @version 1.0
 */
public class Server{

    private ServerSocket connectionSocket;
    private HostThread[] hostThreads;
    private boolean[] ready;
    private boolean gameRunning;

    /**
     * Make a new server.
     * @throws IOException
     */
    public Server() throws IOException {
    }

    /**
     * Sets the server for hosting
     * @param port
     * @throws IOException
     */
    public void host(int port) throws IOException{
        ready = new boolean[4];
        for (int i = 0; i < ready.length; i++){
            ready[i] = false;
        }
        hostThreads = new HostThread[4];
        connectionSocket = new ServerSocket(port, 100);
        Thread con = new Thread(new ConnectionThread(this));
        con.start();
    }

    /**
     * connects a client to the server
     * @param socket the corresponding socket
     */
    public void connect(Socket socket){
        if (!gameRunning) {
            System.out.println(socket.getRemoteSocketAddress() + "connected");
            for (int i = 0; i < hostThreads.length; i++) {
                if (hostThreads[i] == null) {
                    hostThreads[i] = new HostThread(this, socket, i);
                    hostThreads[i].run();
                    publishClientName(i, "Player " + (i + 1));
                    break;
                }
                //TODO ErrorMessage if server is full!
            }
        }
    }

    /**
     * receives and handles new client messages
     * @param message the message to hanlde
     */
    public void newMessage(String message){
        //TODO PROCESS MESSAGE
    }

    /**
     * publishes a new name of a player to all clients
     * @param player the corresponding player
     * @param name the new name that is set
     */
    public void publishClientName(int player, String name){
        for (HostThread hostThread : hostThreads){
            if (hostThread != null){
                hostThread.publishClientName(player, name);
            }
        }
    }

    /**
     * Returns the connection socket of the server.
     * @return the connection socket as ServerSocket
     */
    public ServerSocket getConnectionSocket(){
        return connectionSocket;
    }

    /**
     * sets a player to ready and checks if all players are ready.
     * if so, server starts a new game.
     * @param player the player that is set as ready
     */
    public void ready(int player){
        ready[player] = true;
        System.out.println("player " + player + " is ready");
        boolean go = true;
        for (int i = 0; i<ready.length; i++){
            if (!ready[i] && hostThreads[i] != null){
                go = false;
            }
        }
        if (go){
            newGame();
        }
    }

    /**
     * starts a new game
     */
    public void newGame(){
        for (HostThread hostThread : hostThreads){
            hostThread.newGame();
        }
        gameRunning = true;
    }

    /**
     * finishes the game and sets a player as winner
     * @param winner the player that is the winner
     */
    public void setWinner(int winner){
        for (HostThread hostThread : hostThreads) {
            if (hostThread != null) {
                hostThread.finishGame(winner);
            }
        }
        gameRunning = false;
        for (boolean r : ready ) {
            r = false;
        }
    }

    /**
     * tells all clients that a player is running for one animation
     * @param player the player that is running
     * @param timer the player's current cool down timer
     */
    public void playerRuns(int player, int timer){
        for (HostThread hostThread : hostThreads) {
            if (hostThread != null) {
                hostThread.playerRuns(player, timer);
            }
        }
    }

    /**
     * tells all clients that a player is falling down for one animation
     * @param player the player that is falling down
     */
    public void playerBails(int player){
        for (HostThread hostThread : hostThreads) {
            if (hostThread != null) {
                hostThread.playerBails(player);
            }
        }
    }

    /**
     * main method, starts the server application
     * @param args command line arguments
     */
    public static void main(String[] args){
        try {
            Server server = new Server();
            server.host(23552);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
