import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * The input worker listens for incoming messages from the server in background and handles them.
 * @author Christopher von Bargen
 * @author Fynn Braren
 * @version 1.0
 */
public class InputWorker extends SwingWorker<Void, Void> {

    private Socket socket;
    private Gui gui;
    private Scanner scn;

    /**
     * Creates a new InputWorker
     * @param socket socket to listen to
     * @param gui the client's gui
     * @throws IOException
     */
    public InputWorker(Socket socket, Gui gui) throws IOException {
        this.socket = socket;
        this.gui = gui;
        this.scn = new Scanner(socket.getInputStream());
    }

    /**
     * background task and handling
     * @throws Exception
     */
    @Override
    protected Void doInBackground() throws Exception {
        String line = scn.nextLine();
        if (line.startsWith("#r:")) { //PLAYER RUNS
            gui.animate(Integer.parseInt(line.substring(3,4)), Integer.parseInt(line.substring(4)));
        } else if (line.startsWith("#b:")){ //PLAYER FALLS
            gui.bail(Integer.parseInt(line.substring(3,4)));
        }  else if (line.startsWith("#s:")) { //SET SYMBOL
            gui.setAsciiDisplay(line.charAt(3));
        } else if (line.startsWith("#g:")) { //NEW GAME
            gui.startGame();
        } else if (line.startsWith("#f:")) { //FINISH
            gui.stopGame();
        } else if (line.startsWith("#u:")){ //YOU ARE PLAYER NO...
            gui.setPlayerNumber(Integer.parseInt(line.substring(3,4)));
        } else if (line.startsWith("#n:")){ //SET NAME
            gui.setPlayerName(Integer.parseInt(line.substring(3,4)),line.substring(4));
        }
        return null;
    }

    /**
     * creates the next InputWorker after handling an input
     */
    @Override
    protected void done() {
        super.done();
        InputWorker iw = null;
        try {
            iw = new InputWorker(socket, gui);
        } catch (IOException e) {
            e.printStackTrace();
        }
        iw.execute();
    }
}
