package RectangleMan;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;



/**
 * @author Sina, Bager, Walid
 * 
 * Programm des Games
 */
public class Game extends JDialog implements ActionListener, KeyListener{
	
	private static final long serialVersionUID = 1L;
	
	// VARIABLEN
	
	// Variablen für das GUI. Breite, Länge
	public final int BREITE = 1200, LÄNGE = 800;
	public int spielerPosition = LÄNGE - 300;
	
	// Boolean für den Spielstart. Ist auf false gesetzt, weil der Spieler den Gamestart auf Knopfdrück angibt
	public boolean gameStart = false;

	// Punkte und Leben
	int score = 0;
	int leben = 5;
	
	// Das erstellte GraphicPanel und Random()
	MyPanel panel;
	Random rand;
	
	// Verschiedene Rectangles und Arraylisten mit Rectangle
	Rectangle hinterGrund;
	Rectangle boden;
	Rectangle grass;
	Rectangle spieler;
	Rectangle redLine;
	ArrayList<Rectangle> gegner;
	ArrayList<Rectangle> kugel;

	// Konstruktor
	public Game()
	{
		// GraphicPanel und Timer mit 20 ms und ActionPerformed this (weiter unten)
		panel = new MyPanel();
		Timer t = new Timer(20, this);
		
		// Rectangle Arrays werden erstellt
		gegner = new ArrayList<Rectangle>();
		kugel = new ArrayList<Rectangle>();
		
		// Bestehende Rectangels, wie Hintergrund und Boden und auch Spieler werden gesetzt
		hinterGrund = new Rectangle(0,0 , BREITE,LÄNGE);
		boden = new Rectangle(0,LÄNGE - 150 , BREITE,75);
		grass = new Rectangle(0,LÄNGE - 100 , BREITE,75);
		
			// Spieler erhält variable "spielerPosition" um die Position beliebig ändern zu können
		spieler = new Rectangle(150,spielerPosition , 50,150);
		
			// Rote Linie um intersects() zu benutzen
		redLine = new Rectangle (350,0 , 1,LÄNGE);
		
		// Erste Gegner wird gesetzt per Methode
		addGegner(true);
		
		// Panel + repaint und KeyListener werden hinzugefügt
		add(panel);
		panel.repaint();
		addKeyListener(this);
		
		// Timer Start
		t.start();
		
		// GUI Settings
		setVisible(true);
		setSize(BREITE, LÄNGE);
		setLocation(350, 100);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Rectangle Run");
	}
	
	// ACTIONPERFORMED TIMER
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// Variablen : Geschwindigkeit von Gegner etc.
		int kugelGeschwindigkeit = 50;
		int gegnerGeschwindigkeit = 10;
		
		// Gegner werden automatisch eingesetzt sobald der letzte verschwindet
		addGegner(true);
		
		// Falls Leben auf 0 sinkt, hat der Spieler Verloren und wird wieder ins anfängliche Feld zurück gesetzt
		if(leben <= 0)
		{
			gameStart = false;
			leben = 5;
			
			// Der Score wird beim Verlieren gespeichert durch Methode saveScore
			saveScore();
		}
		
		// Falls gameStart auf true gesetzt wurde durch Spielerbefehl, beginnt das Game zu starten bzw die Figuren können sich bewegen
		if(gameStart)
		{
			// Schwierigkeitsgrad verändert sich per PunkteStand
			if(score >= 20 && score < 30)
			{
				gegnerGeschwindigkeit = 15;
			}
			else if(score > 30 && score < 40)
			{
				gegnerGeschwindigkeit = 25;
			}
			// Wird keiner schaffen...
			else if(score >= 40)
			{
				gegnerGeschwindigkeit = 40;
			}
			
			//GEGNER
			
			// Gegnerbewegung und Zugriff auf ArrayList 
			Rectangle gegnern = gegner.get(0);
			gegnern.x -= gegnerGeschwindigkeit;
			
			// Falls Gegner die rote Linie an Stelle x = 350 erreicht, verschwindet der Gegner und man verliert ein Leben 
				if(gegnern.intersects(redLine))
				{
					gegner.remove(0);
					leben--;
				}

				// For Schleife um Kugel per Knopfdruck Kugeln zu erstellen ähnliche Konstellation wie bei Gegner
			for (int i = 0; i < kugel.size(); i++)
			{
				Rectangle kugeln =  kugel.get(i);
				
				kugeln.x += kugelGeschwindigkeit;
				
				//Falls Kugel mit den Gegner kollidiert bzw ihre x Achse trifft
				if (kugeln.intersects(gegnern))
				{
					// Score wird erhöht, ein weitere Gegner erscheint, Gegner wird weggenommen wie auch Kugel
					score ++;
					
					addGegner(true);
					
					gegner.remove(0);
					kugel.remove(0);
				}
				// Falls Kugel zu weit fliegt, wird die Kugel entfernt
				if(kugeln.x >= 1500)
				{
					kugel.remove(0);
				}
			}
				
		}
		
		// Repaint des Graphic Panels bzw Rendering
		panel.repaint();
	}
	
	
	//METHODEN
	/**
	 * Savemethode: Fragt den User nach dem Namen und speichert ihn + score in einer Datei
	 */
	public void saveScore()
	{
		// Fenster mit Eingabefeld öffnet sich und die Eingabe wird als String gespeichert
		String nameScore = 	JOptionPane.showInputDialog(null, "Gib deinen Namen ein:");
		
		// Speicherdatei wird erstellt
		File file = new File("Score");
		
		try
		{
			//FileWriter für die Überschreibung auf der Datei
			FileWriter fw2 = new FileWriter(file, true);
			
			// Schreibt den namen und dann den Score separat ein
			fw2.append(nameScore + "\n");
			fw2.append("" + score +  "\n");
			
			// Flusht den Text auf die Datei und schließt den FileWriter anschließend
			fw2.flush();
			fw2.close();
		}
		// Fängt  Exceptions
		catch(IOException ex)
		{
			Logger.getLogger(Game.class.getName()).log(Level.SEVERE,null ,ex );
		}
	}
		
	
	/**
	 *  Methode um den Spieler nach unten zu bringen (y Positon wird geändert)
	 */
	public void fall()
	{
		// Falls die Position des Spielers nicht zu weit unten ist wird der Spieler um 40 Pixel nach unten versetzt
		if(spielerPosition >= 20)
		{
		spielerPosition -= 50;
		}
	}
	
	
	/**
	 *  Ähnliche Methode wie fall() nur nach Oben versetzt
	 */
	public void jump()
	{
		if(spielerPosition <= LÄNGE - 320)
		{
			spielerPosition += 55;
		}
	}

	
	/**
	 * Ein Boolean wert wird weiter gegeben und falls true dann wird ein neuer Gegner erstellt
	 * @param start1
	 */
	public void addGegner(boolean start1)
	{
		//Variablen des Gegners
		int breiteGegner = 50;
		int längeGegner = 50;
		//Random Methode um die Größe und auch die Entfernung(Vom Spieler bzw welche Verzögerung sein erscheinen hat) mit verschiedenen Werten zu bestimmen
		rand = new Random();
		
		// Falls "start" auf true gesetzt ist wird ein neues Rectangle erstellt
		if(start1)
		{
			// Entfernung zum Spieler				Auf welcher höhe der Gegner ist      Breite          länge mit Random()
			gegner.add(new Rectangle(BREITE + 100 + rand.nextInt(250),rand.nextInt(500) , breiteGegner,längeGegner + rand.nextInt(150)));
		}
	}
	/**
	 * Farbe festsetzten für die Gegner
	 * @param g
	 * @param gegner
	 */
	public void paintGegner(Graphics g, Rectangle gegner)
	{
		g.setColor(Color.BLACK);
		g.fillRect(gegner.x,gegner.y,gegner.width,gegner.height);
	}
	
	/**
	 * Ähnliche Methode wie "addGegner()" nur für die Kugelnd
	 * @param start
	 */
	public void addKugel(boolean start)
	{
		int breiteKugel = 35;
		int längeKugel = 10;
		
		if(start)
		{
		kugel.add(new Rectangle(215,spielerPosition + 25,  breiteKugel,längeKugel));
		}
	}
	public void paintKugel(Graphics g, Rectangle kugel)
	{
		g.setColor(Color.GRAY);
		g.fillRect(kugel.x,kugel.y,kugel.width,kugel.height);
	}
	
	/**
	 * Klasse Panel für die Graphics. Also Farbe und Rectangle auf das Fenster sichtbar machen
	 */
	public class MyPanel extends JPanel
	{
		private static final long serialVersionUID = 1L;
		
		public void paint(Graphics g)
		{
			super.paint(g);
	
			// Hintergrund
			g.setColor(new Color(0,175,255));
			g.fillRect(hinterGrund.x,hinterGrund.y,hinterGrund.width,hinterGrund.height);
			
			// Boden
			g.setColor(new Color(50,255,0));
			g.fillRect(boden.x, boden.y, boden.width, boden.height);

			g.setColor(new Color(144,91,37));
			g.fillRect(grass.x, grass.y, grass.width, grass.height);
			
			// Spieler
			g.setColor(new Color(255,125,0));
			g.fillRect(spieler.x,spielerPosition,spieler.width,spieler.height);

			// rote Linie 
			g.setColor(Color.RED);
			g.drawRect(redLine.x,redLine.y , redLine.width,redLine.height);

			// Strings auf dem Panel
			g.setColor(Color.WHITE);
			g.setFont(new Font("HEYRO fun",1,50));
			g.drawString("Punkte: " + score, 25, 50);
			
			g.setColor(new Color(190,0,0));
			g.setFont(new Font("HEYRO fun",1,50));
			g.drawString("Leben: " + leben , 850, 50);
			
			
			// Falls das Spiel nicht gestartet ist
		if(gameStart == false)
		{
			// Strings auf dem Panel
			g.setColor(Color.WHITE);
			g.setFont(new Font("HEYRO fun",1,50));
			g.drawString("Klicke 'F' zum starten" , 150,300);
			
			g.setColor(new Color(50,255,0));
			g.setFont(new Font("HEYRO fun",1,40));
			g.drawString("Klicke 'H' fuer den HighScore" , 100, 400);
			
			g.setColor(new Color(255,125,0));
			g.setFont(new Font("HEYRO fun",1,40)); 
			g.drawString("Klicke 'M' fuer das GameMenu" , 100, 450);
		}
			
			// Gegner und Kuglen werden mit einer For-Each-Schleife	bemalt
			for(Rectangle gegnern : gegner)
			{
				paintGegner(g, gegnern);
			}
			for(Rectangle kugeln : kugel)
			{
				paintKugel(g, kugeln);
			}
		}
	}

	/**
	 * KeyListener 
	 */
	@Override
	public void keyPressed(KeyEvent e)
	{
		
		// Falls "F" gedrückt wurde, wird das Spiel gestarten und Score auf 0 gesetzt
		if(e.getKeyCode() == KeyEvent.VK_F)
		{
			gameStart = true;
			score = 0;
		}
		
		if(gameStart)
		{
			
			// Bei "W" und Pfeiltaste wird der Spieler nach oben verlagert 
			if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP)
			{
				fall();
			}
			// Bei "S" und Pfeiltaste nach unten
			if(e.getKeyCode() == KeyEvent.VK_E || e.getKeyCode() == KeyEvent.VK_DOWN)
			{
				jump();
			}
			// Bei Enter oder Space werden die Kugeln ins Bild gesetzt
			if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE)
			{
				addKugel(true);
			}
		}
		//Außerhalb des Gamestarts kann man ins GameMenu oder ins HighScore Menu 
		if(gameStart == false)
		{
			// Bei "H" wird das HighScore Fenster geöffnet
			if(e.getKeyCode() == KeyEvent.VK_H)
			{
				new HighScore();
			}
			// Bei "M" wird das GameMenu geöffnet
			if(e.getKeyCode() == KeyEvent.VK_M)
			{
				new GameMenu();
				dispose();
			}
		}
		// Bei "Escape" wird das Programm geschloßen
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			System.exit(0);
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0)
	{
	}

	@Override
	public void keyTyped(KeyEvent arg0)
	{
	}
}