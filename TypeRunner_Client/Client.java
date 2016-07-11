import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * used to build the connection and communication
 * between the gui and the game server
 * @author Christopher von Bargen
 * @author Fynn Braren
 * @version 1.0
 */
public class Client{

    private PrintWriter output;
    private Socket socket;
    private Gui gui;
    private int defaultPort;

    /**
     * sets default port and creates a gui
     */
    public Client(){
        defaultPort = 23552;
        gui = new Gui(this);
    }
    /**
     * connects the client to the server
     * @param hostIP server's IP-address
     * @param port server's port
     */
    public void connect(String hostIP, int port){
        String host = hostIP;
        try {
            socket = new Socket(InetAddress.getByName(host), port);
            output = new PrintWriter(socket.getOutputStream());
            InputWorker inputWorker = new InputWorker(socket, gui);
            inputWorker.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * sends any message to the server
     * @param line the string to be sent
     */
    public void sendChat(String line){ //TODO: Chat
        if (line.startsWith("/success ")) {
            output.println("#s:" + line.substring(9));
            output.flush();
        } else if (line.startsWith("/bail")){
            output.println("#b:");
            output.flush();
        } else if (line.startsWith("/connect ")) {
            if (!line.contains(":")){
                connect(line.substring(9), defaultPort);
            } else {
                int portIndex = line.indexOf(':') + 1;
                connect(line.substring(9, portIndex - 1), Integer.parseInt(line.substring(portIndex)));
            }
        } else if (line.startsWith("/name ")) {
            output.println("#n:" + line.substring(6));
            output.flush();
        } else if (line.startsWith("/ready")) {
            output.println("#r:");
            output.flush();
        } else if (line.startsWith("/win")) {
            output.println("#w:");
            output.flush();
        } else {
            output.println("#c:" + line);
            output.flush();
        }

    }

    /**
     * main method. starts the client application
     * @param args command line arguments
     */
    public static void main(String[] args){
        Client client = new Client();
    }

}
