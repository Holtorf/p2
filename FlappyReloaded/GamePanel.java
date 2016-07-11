import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer; 
// GAMEPANEL FÜR DAS SPIEL 
/**
 * <h1>GAMEPANEL</h1>
 * 
 * @author TMA
 * @sinde 2016-06-13
 *
 */
public class GamePanel extends JPanel implements ActionListener{
	
	// INSTANZVARIABLEN
	private Timer timer;
	private Flappy flappy;
	private Background bg1;
	private Background bg2; 
	
	private boolean ingame; 
	
	// STARTPOSITION FLAPPY
	private final int flappyX = 50;
	private final int flappyY = 150;
	// HÖHE DER SPALTE IN JEDER SÄULE
	private final int saeulenGap = 80;
	// BREITE JEDER SÄULE
	private final int saeulenWidth = 30; 
	// VARIANTE DER SÄULE
	private int sauelenType = 0;
	// HÖHE DES SPIELPANELS
	private final int gamePanelHeight = 600;
	// DELAY NACH START DES SPIELS -> BIS DIE ERSTEN SÄULEN ERSCHEINEN
	private final int DELAY = 20;
	// COUNTER FÜR DIE SÄULEN
	private int counter = 0;
	// COUNTERMATCH - IST DER WERT ERREICHT, WERDEN NEUE SÄULEN HINZUGEFÜGT
	private int counterMatch = 180;
	// AKTUELLER PUNKTESTAND
	private int score = 0;
	// MALUS FÜR FEUERBÄLLE
	private int fireballMalus = -150;
	
	private Highscore highscore;
	private Player player;
	private boolean initDone = false;
	
	ArrayList<SaeulenDouble> saeulen = new ArrayList<>();
	//KONSTRUKTOR FÜR GAMEPANEL RUFT DIE METHODE INIGAMEPANEL() AUF   
	public GamePanel(){
		initGamePanel();
	}
	
	//METHODE UM SPIELPANEL ZU INITIALISIEREN
	/**
	 * Method to initialize the gamepanel<br>
	 * adds key-/mouselistener, buttons, wrapper<br>
	 * actionlistener, 
	 * 
	 */
	private void initGamePanel(){
		
		// KEY-/MOUSELISTENER FÜR DIE STEUERUNG VON FLAPPY
		addKeyListener(new keyTranslateAdapter());
		addMouseListener(new mouseTranslateAdapter());
		
		setFocusable(true); 
		setBackground(new Color(101, 205, 230));
		ingame = true;
		
		setPreferredSize(new Dimension(800, gamePanelHeight));
		
		// WRAPPER UND BUTTONS ERSTELLEN
		JPanel wrapper = new JPanel();
		JTextField tfName = new JTextField("Spielername", 15);  
		JButton startButton = new JButton("START");
		JButton bt01 = new JButton(new ImageIcon("images/flappy01.gif"));
		JButton bt02 = new JButton(new ImageIcon("images/flappy02.gif"));
		JButton bt03 = new JButton(new ImageIcon("images/flappy03.gif")); 
		 
		bt01.setBackground(new Color(0,0,0,0));
		bt02.setBackground(new Color(0,0,0,0));
		bt03.setBackground(new Color(0,0,0,0));
		
		// ELEMENTE HINZUFÜGEN
		wrapper.add(tfName);
		wrapper.add(bt01);
		wrapper.add(bt02); 
		wrapper.add(bt03); 
		wrapper.add(startButton);
		add(wrapper);
		
		// BUTTONHANDLER FÜR DIE AUSWAHL DER SPIELFIGUR / LEVEL 
		// UND START
		ActionListener buttonHandler = new ActionListener(){
			public void actionPerformed(ActionEvent e){  
				// START
				if(e.getSource() == startButton){ 
					remove(wrapper);
					startTimer();
					player = new Player(tfName.getText(), 0); 
				}
				// FLAPPY NORMALE WELT
				if(e.getSource() == bt01){
					flappy.setImgSrc(0);
					bg1.setImgSrc(0);
					bg2.setImgSrc(0);
					sauelenType = 0;
				} 
				// FLAPPY EISWELT
				if(e.getSource() == bt02){
					flappy.setImgSrc(1);
					bg1.setImgSrc(1);
					bg2.setImgSrc(1);
					sauelenType = 1;
				}
				// FLAPPY FEUERWELT
				if(e.getSource() == bt03){
					flappy.setImgSrc(2);
					bg1.setImgSrc(2);
					bg2.setImgSrc(2);
					sauelenType = 2;
				} 
			}
		};
		// ACTIONLISTENER HINZUFÜGEN
		startButton.addActionListener(buttonHandler);
		bt01.addActionListener(buttonHandler);
		bt02.addActionListener(buttonHandler);
		bt03.addActionListener(buttonHandler); 
		
		// FLAPPY UND HINTERGRÜNDE ERSTELLEN
		flappy = new Flappy(flappyX, flappyY);
		bg1 = new Background(0,0);
		bg2 = new Background(1599,0); 
	}
	
	// TIMER STARTEN UND SAEULEN INITIALISIERNE
	/**
	 * Method to start the timer<br>
	 * calls -> initSaeulen();
	 */
	public void startTimer(){ 
		initSaeulen();
		timer = new Timer(DELAY, this);
		timer.start();  
	}
	
	//SÄULEN INITIALISIEREN 
	/**
	 * Method to initialize the barricades
	 */
	public void initSaeulen(){ 
		saeulen.add(new SaeulenDouble(800, saeulenGap, saeulenWidth, gamePanelHeight, sauelenType)); 
	}
	//ACTION PERFORMED FÜR JEDEN TIMER TICK
	@Override
    public void actionPerformed(ActionEvent e) {
		inGame();
		updateFlappy();
		updateSaeulen();
		updateFireballs();
		updateBackground();
		repaint(); 
		checkCollision();
    }
	// METHODE UM ZU CHECKEN OB INGAME = TRUE/FALSE: TRUE => TIMER STOPPEN
	/**
	 * Method to check if game is still 'ingame' / still running<br>
	 * else it stops the timer
	 */
	private void inGame(){
		if(!ingame){
			timer.stop();
		} 
	}
	// METHODE FLAPPY UPDATEN: BEWEGEN UND SINKEN LASSEN
	/**
	 * Method to update flappy - fly or sink<br>
	 * -> calls flappy.move() / flappy.fall() 
	 */
	private void updateFlappy(){
		if(flappy.visible){
			flappy.move();
			flappy.fall();
		}
	}
	// METHODE UM SÄULENELEMENTE ZU BEWEGEN UND NEUE HINZUZUFÜGEN
	/**
	 * Method to update each barricade<br>
	 * move the existing barricades and add new ones<br>
	 * if the counter matches counterMatch
	 */
	private void updateSaeulen(){ 
		// EXISTIERENDE SÄULEN BEWEGEN
		for(SaeulenDouble sp: saeulen){  
			for(Saeulen sa: sp.saeulenElemente){
				sa.move();
			}
		}
		// NEUE SÄULEN HINZUFÜGEN
		if(counter == counterMatch){
			saeulen.add(new SaeulenDouble(800, saeulenGap, saeulenWidth, gamePanelHeight, sauelenType));
			counter = 0;
		}
		counter++;
	}
	// METHODE UM FEUERBÄLLE ZU BEWEGEN UND ZU LÖSCHEN
	/**
	 * Method to update the fireballs<br>
	 * iterate the arrayList of fireballs and move each one<br>
	 * if it's still visible, else erase it. 
	 */
	private void updateFireballs(){
		ArrayList<Fireball> fireballs = flappy.getFireballs();
		// ALLE FEUERBÄLLE DER ARRAYLIST DURCHLAUFEN
		for(int i = 0; i < fireballs.size(); i++){
			Fireball fb = fireballs.get(i);
			// WENN NOCH SICHTBAR -> FEUERBALL BEWEGEN
			if(fb.visible){
				fb.move();  
			}
			// SONST AUS DER ARRAYLIST ENTFERNEN
			else{
				fireballs.remove(i);
			}
		}
	}
	
	// METHODE UM DEN HINTERGRUND ZU BEWEGEN
	/**
	 * Method to move the background images along the x-direction
	 */
	private void updateBackground(){ 
		bg1.move(); 
		bg2.move();
	}
	// METHODE UM KOLLISIONEN ZU PRÜFEN: INTERSECTS FÜR FLAPPY / SÄULEN UND FEUERBÄLLE
	/**
	 * Method to check intersects between flappy / barricades<br>
	 * and fireballs / barricades.<br>
	 * It creates rectangles for each fireball / barricade / flappy<br>
	 * and checks for intersects between them.<br>
	 * It also checks if Flappy leaves the screen or hits the ground.<br>
	 * -> in case: sets ingame as FALSE;<br>
	 * Which causes GAME OVER. 
	 * 
	 */
	private void checkCollision(){ 
		// RECTANGLE UM FLAPPY ERSTELLENT
		Rectangle r1 = flappy.getBounds();
		
		// ALLE SÄULENDOUBLE UND JEWEILS ALLE SÄULENELMENTE DURCHLAUFEN
		// FÜR JEDES SÄULENELEMENT EIN RECTANGLE ERSTELLEN
		// -> FÜR JEDES AUF EINE KOLLISION (INTERSECT) PRÜFEN
		// BEI KOLLISION INGAME AUF FALSE SETZEN
		for(SaeulenDouble sp : saeulen){
			for(Saeulen sa: sp.saeulenElemente){ 
				if(sa.isVisible()){
					Rectangle r2 = sa.getBounds();
					if(r1.intersects(r2)){ 
						ingame = false;
					} 	
				} 
			} 
		}
		// PRÜFEN OB FLAPPY OBEN ODER UNTEN AN DEN BILDRAND STÖßT
		if(flappy.getY() < 0){
			flappy.y =0;
		}
		if(flappy.getY() >= 475){
			ingame = false;
		}
		
		// ALLE FEUERBÄLLE UND SÄULENELEMENTE DURCHLAUFEN 
		// UND FÜR JEDES JEWEILS EIN RECTANGLE ERSTELLEN
		// -> FÜR JEDES AUF EINE KOLLISION (INTERSECT) PRÜFEN
		// BEI KOLLISION WERDEN BEIDE UNSICHTBAR
		ArrayList<Fireball> fireballs = flappy.getFireballs();
		for(Fireball fb: fireballs){ 
			Rectangle r3 = fb.getBounds();
			
			for(SaeulenDouble sp : saeulen){
				for(Saeulen sa: sp.saeulenElemente){ 
					if(sa.isVisible()){
						Rectangle r2 = sa.getBounds();
						if(r3.intersects(r2)){  
							// BEI KOLLISION
							sa.setVisible(false);
							fb.setVisible(false);
							score -= fireballMalus;
						}  
					} 
				} 
			}
		}
	}
	
	// DRAWOBJECTS FÜR ALLE SPIELELEMENTE
	/**
	 * Drawobject for all elements of the game<br>
	 * background 1/2, flappy, fireballs, barricades<br>
	 * and the score at the bottom of the screen
	 */
	private void drawObjects(Graphics g){  
		//HINTERGRUND 
		g.drawImage(bg1.getImage(), bg1.getX(), bg1.getY(), this);
		g.drawImage(bg2.getImage(), bg2.getX(), bg2.getY(), this); 
		
		// JEWEILS ENTSPRECHENDE HINTERGRUNDGRAFIK WIEDER AM RECHTEN BILDRAND NACHSCHIEBEN,
		// WENN DIESE AUS DEM BILD GELAUFEN IST
		if(bg2.getX() == -1599){ 
			bg2.x = 1599;
		}
		if(bg1.getX() == -1599){ 
			bg1.x = 1599;
		}
		
		// FLAPPY
		if(flappy.visible){
			g.drawImage(flappy.getImage(), flappy.getX(), flappy.getY(), this);
		} 
		
		//SÄULEN
		for(SaeulenDouble sp: saeulen){ 
			for(Saeulen sa: sp.saeulenElemente){ 
				if(sa.isVisible()){
					g.drawImage(sa.getImage(), sa.getX(), sa.getY(), this);
				} 
			} 
		}
		
		//FEUERBÄLLE
		ArrayList<Fireball> fireballs = flappy.getFireballs();
		for(Fireball fb : fireballs){
			if(fb.isVisible()){
				g.drawImage(fb.getImage(), fb.getX(), fb.getY(), this);   
			}
		} 
		//PUNKTE
		score++;
		Font small = new Font("HELVETICA", Font.BOLD, 30);
        FontMetrics fm = getFontMetrics(small);
        String message = ""+score+"";
        if(score < 0){
        	 g.setColor(Color.red);
        }
        else{
        	g.setColor(Color.white);	
        } 
        g.setFont(small); 
        g.drawString(message, (750 - fm.stringWidth(message)), 580);
	}
	
	boolean once = true;	
	// GAME OVER ANZEIGE
	/**
	 * Method transfer the highscore and to print the game over screen.
	 */
	private void drawGameOver(Graphics g) { 
		g.drawImage(bg1.getImage(), bg1.getX(), bg1.getY(), this);
		g.drawImage(bg2.getImage(), bg2.getX(), bg2.getY(), this);
		
		//HIGHSCORE CLIENT
        if(once){
        	EchoClient echoClient = new EchoClient();  
            player.setScore(score);
            highscore = echoClient.updateHighscore(player); 
            echoClient.closeConnection();
             
            once = false;	 
        }
         
		String msg = "- Game Over - \n Highscore ";  
        Font small = new Font("Helvetica", Font.BOLD, 25);
        FontMetrics fm = getFontMetrics(small);
        msg += "\n" + highscore.printHighScore();
        
        g.setColor(Color.white);
        g.setFont(small); 
        drawString(g, msg, 250, 50);
	}
	 
	// PAINTCOMPONENT 
	// ALLE SPIELELEMENTE ZEICHNEN WENN INGAME == TRUE
	// -> SONST DRAWGAMEOVER()
	/**
	 * Draw all objects as long as ingame is TRUE,<br>
	 * -> calls drawObjects(g)<br>
	 * else draw the game over screen<br>
	 * -> calls drawGameOver(g) 
	 */
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g); 
		if(ingame){ 
			drawObjects(g);
		}
		if(!ingame){ 
			drawGameOver(g);
		}
	}
	
	// DRAWSTRING MIT ZEILENUMBRUCH BZW. JEWEILS NEU GEZEICHNET BEI JEDEM 
	// GEFUNDENEN "\n" -> NEUE Y-KOORDINATE IST DANN DIE HÖHE DES FONTS ALS OFFSET
	// TOP 15 DER HIGHSCORE AUSGEBEN
	/**
	 * Method to draw a String with offset as linebreak<br>
	 * for each parsed "\n"<br>
	 * prints the top 15 of the highscore
	 * @param g (Graphics)
	 * @param text (String) text to print
	 * @param x (integer) x-coordinate where to start printing
	 * @param y (integer) y-coordinate where to start printing
	 */
	private void drawString(Graphics g, String text, int x, int y) { 
    	int i = 0;
		for (String line : text.split("\n")){
    		if(i<=15){
    			g.drawString(line, x, y += g.getFontMetrics().getHeight());
    		} 
    		i++;
        }
    }
	
	// KEYADAPTER FÜR MAUS UND TASTATUREINGABE 
	/**
	 * KeyTranslateAdapter to translate keyinput to movement of flappy
	 */
	private class keyTranslateAdapter extends KeyAdapter{
		@Override
        public void keyReleased(KeyEvent ke) {
            flappy.keyReleased(ke);  
        } 
        @Override
        public void keyPressed(KeyEvent ke) { 
        	flappy.keyPressed(ke); 
        }
	}
	/**
	 * MouseTranslateAdapter to translate mouseinput to movement of flappy
	 */
	private class mouseTranslateAdapter extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent me){
			flappy.mouseClicked(me); 
		}
		@Override
		public void mouseReleased(MouseEvent me){
			flappy.mouseReleased(me);
		}
	}
	 
}
