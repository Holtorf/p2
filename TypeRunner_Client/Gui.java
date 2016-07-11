import javax.swing.*;
import javax.swing.text.Caret;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.*;

/**
 * Main Graphical User Interface of the game, handles all interaction between User and Client
 * @author Christopher von Bargen
 * @author Fynn Braren
 * @version 1.0
 */
public class Gui extends JFrame {

    //Cool Down Timer Settings
    static final int TIMER_MAX = 2000; //in ms, maximum cool down length
    static final int TIMER_MIN = 300; //ms,minimum cool down length
    static final int timerRefreshIncrement = 50; //increase of cool down after TIMER_MAX has passed
    private float timerBonusToRefreshRatio = 1f; //multiplies with TIMER_MAX to fully refresh the cool down. 1 = no cool down decrease possible
    private int timerBonus = (int)timerBonusToRefreshRatio * timerRefreshIncrement; //cool down decrease when hitting the right symbol, refresh rate is TIMER_MAX/100
    private int timer = TIMER_MAX; //initial value

    //gui parts
    private GamePanel gamePanel;
    private JMenuBar menuBar;
    private JMenu mainMenu;
    private JPanel overviewPanel;
    private JPanel playersPanel;
    private JList playerList;
    private JTextArea console;
    private JTextField inputLine;
    private JTextField asciiDisplay;
    private DefaultListModel<String> players;
    private Dimension screenResolution;
    private Dimension gameScreenSize;
    private Dimension hudElements;
    private PlayerPanel playerPanel0;
    private PlayerPanel playerPanel1;
    private PlayerPanel playerPanel2;
    private PlayerPanel playerPanel3;

    private final Client client;
    private int player;
    private char serverSymbol;
    private Timer[] animationTimers;
    private Timer unlockTimer;
    private boolean gameRunning;
    private boolean locked = false;
    private int w = 0;
    private String welcomeText = "WELCOME TO TYPERUNNER\n /connect ip:port to connect\n /name to set your name \n /ready to get ready!";

    /**
     * GUI constructor, many settings are hardcoded here
     * @param client the client, that is using this GUI
     */
    public Gui(final Client client){
        super("TYPERUNNER");
        this.client = client;
        // INITIATION
        TimerClock tc = new TimerClock(this);
        tc.execute();
        // SETTINGS : hard coded
        // TODO settings menu for variable input
        screenResolution = new Dimension(1240, 1080);
        gameScreenSize = new Dimension((int)screenResolution.getWidth(), 560);
        hudElements = new Dimension(300,300);
        // MENU BAR
        // TODO implement a useful menu bar
        menuBar = new JMenuBar();
        mainMenu = new JMenu("Menu");
        menuBar.add(mainMenu);
        add(menuBar, BorderLayout.NORTH);
        // GAME PANEL : create and add the player panels
        gamePanel = new GamePanel();
        gamePanel.setPreferredSize(gameScreenSize);
        add(gamePanel, BorderLayout.CENTER);
        playerPanel0 = new PlayerPanel(0, this);
        playerPanel1 = new PlayerPanel(1, this);
        playerPanel2 = new PlayerPanel(2, this);
        playerPanel3 = new PlayerPanel(3, this);
        gamePanel.add(playerPanel0);
        gamePanel.add(playerPanel1);
        gamePanel.add(playerPanel2);
        gamePanel.add(playerPanel3);
        // OVERVIEW PANEL : holds the player list, console/chat and the ascii-display
        overviewPanel = new JPanel();
        playersPanel = new JPanel();
        console = new JTextArea(welcomeText);
        inputLine = new JTextField("/connect ");
        asciiDisplay = new JTextField(" ");
        Caret blank = new DefaultCaret(){
            @Override
            public void paint(Graphics g) {
            }

            @Override
            public boolean isVisible() {
                return false;
            }

            @Override
            public boolean isSelectionVisible() {
                return false;
            }
        };
        asciiDisplay.setCaret(blank);
        overviewPanel.setLayout(new BorderLayout());
        overviewPanel.setPreferredSize(new Dimension(screenResolution.width, hudElements.height));
        players = new DefaultListModel<String>();
        playerList = new JList(players);
        playersPanel.setPreferredSize(hudElements);
        playersPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        playersPanel.setLayout(new BorderLayout());
        playersPanel.add(new JLabel("PLAYERS:"), BorderLayout.NORTH);
        playersPanel.add(playerList, BorderLayout.CENTER);
        overviewPanel.add(playersPanel, BorderLayout.WEST);
        //CONSOLE / CHAT
        console.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        console.setEditable(false);
        JPanel consolePanel = new JPanel();
        consolePanel.setLayout(new BorderLayout());
        overviewPanel.add(consolePanel);
        consolePanel.add(console, BorderLayout.CENTER);
        consolePanel.add(inputLine, BorderLayout.SOUTH);
        consolePanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        inputLine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String line = inputLine.getText();
                inputLine.setText("");
                client.sendChat(line);
            }
        });
        //ASCII-DISPLAY
        asciiDisplay.setEditable(false);
        asciiDisplay.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                //Keeps the focus on the ascii-display while game is running
                if (gameRunning){
                    asciiDisplay.requestFocus();
                }
            }
        });
        asciiDisplay.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (gameRunning){
                    if (!locked && serverSymbol == e.getKeyChar()) {
                        //the user has done a valid input to move his character
                        client.sendChat("/success " + timer);
                        timer -= timerBonus;
                        //lock();
                    } else {
                        //the user has failed and his character will fall
                        client.sendChat("/bail");
                        timer = TIMER_MAX;
                        lock();
                    }
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        unlockTimer = new Timer(timer, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unlock();
            }
        });
        unlockTimer.setRepeats(false);
        //FONT : symbol font for the ascii-display
        Font symbolFont = new Font(Font.MONOSPACED,Font.BOLD, 192);
        asciiDisplay.setFont(symbolFont);
        asciiDisplay.setPreferredSize(hudElements);
        asciiDisplay.setHorizontalAlignment(SwingConstants.CENTER);
        asciiDisplay.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        overviewPanel.add(asciiDisplay, BorderLayout.EAST);
        add(overviewPanel, BorderLayout.SOUTH);

        //MAKE THE FRAME
        setSize(screenResolution);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE); //TODO: Close window
        setVisible(true);


    }

    /**
     * starts a new game
     */
    public void startGame(){
        w = 0;
        playerPanel0.reset();
        playerPanel1.reset();
        playerPanel2.reset();
        playerPanel3.reset();
        asciiDisplay.setBackground(Color.GREEN);
        asciiDisplay.requestFocus();
        inputLine.setEnabled(false);
        gameRunning = true;
    }

    /**
     * stops the running game
     */
    public void stopGame(){
        inputLine.setEnabled(true);
        gameRunning = false;
    }

    /**
     * updates a players name
     * @param player the player to be edited
     * @param name the new name to be set
     */
    public void setPlayerName(int player, String name){
        players.add(player, name);
        players.removeElementAt(player+1);
    }

    /**
     * sets a new character to the ascii-display on overviewPanel
     * @param c the character to be set
     */
    public void setAsciiDisplay(char c){
        asciiDisplay.setText(Character.toString(c));
        serverSymbol = c;
    }

    /**
     * locks the gui for input
     * inputs made during lock always fail
     */
    public void lock(){
        locked = true;
        unlockTimer.setDelay(timer);
        unlockTimer.restart();
        asciiDisplay.setBackground(Color.RED);
    }

    /**
     * unlocks the gui for input
     */
    public void unlock(){
        locked = false;
        if (gameRunning) {
            asciiDisplay.setBackground(Color.GREEN);
        }
    }

    /**
     * places a new line on the console
     * TODO add lines instead of replacing them
     * @param text text to be set
     */
    public void setConsole(String text){
        console.setText(text);
    }

    /**
     * finds the corresponding player's PlayerPanel
     * @param player the players number
     * @return the player's PlayerPanel
     */
    public PlayerPanel getPlayerPanel(int player){
        final PlayerPanel panel;
        switch (player) {
            case 0:
                panel = playerPanel0;
                break;
            case 1:
                panel = playerPanel1;
                break;
            case 2:
                panel = playerPanel2;
                break;
            case 3:
                panel = playerPanel3;
                break;
            default:
                panel = null;
                System.out.println("invalid player number");
                break;
        }
        return panel;
    }

    /**
     * starts a player's running animation
     * @param player the players number
     * @param timerValue duration of the animation
     */
    public void animate(int player, int timerValue){
        final PlayerPanel panel = getPlayerPanel(player);
        //split the cool down timer into ten parts for ten frames
        animationTimers = new Timer[10];
        for (int i = 10; i>0; i--){
            animationTimers[10-i] = new Timer(i*(timerValue/10), new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    panel.next();
                }
            });
            animationTimers[10-i].setRepeats(false);
            animationTimers[10-i].start();
        }
    }

    /**
     * starts a player's falling animation
     * @param player the players number
     */

    public void bail(int player){
        for (int i = 0; i < animationTimers.length; i++){
            animationTimers[i].stop();
        }
        final PlayerPanel panel = getPlayerPanel(player);
        //split the cool down timer into ten parts for ten frames
        Timer[] t = new Timer[10];
        for (int i = 10; i>0; i--){
            final int s = i;
            t[10-i] = new Timer(i*(100), new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    panel.bail();
                }
            });
            t[10-i].setRepeats(false);
            t[10-i].start();
        }
    }

    /**
     * increases the timer of the player
     */
    public void increaseTimer(){
        if (timer < TIMER_MAX){
            timer += 10;
            if (timer < TIMER_MIN) {
                timer = TIMER_MIN;
            }
        }
    }

    /**
     * sets the clients own player number
     * @param player the player number
     */
    public void setPlayerNumber(int player){
        this.player = player;
    }
    public void win(int player){
        if (this.player == player){
            setConsole("CONGRATULATIONS! YOU HAVE WON THE MATCH, " + players.getElementAt(player) + "!");
            client.sendChat("/win");
            asciiDisplay.setBackground(Color.ORANGE);
        } else {
            setConsole("PLAYER " + players.getElementAt(player) + "HAS WON THE MATCH!");
            asciiDisplay.setBackground(Color.LIGHT_GRAY);
        }
        stopGame();
    }
}
