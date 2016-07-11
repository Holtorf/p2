package RectangleMan;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


/**
 * @author Sina, Bager, Walid
 * 
 * Das Start Menu für das Spiel
 */
public class GameMenu extends JFrame{

	private static final long serialVersionUID = 1L;
	
	// GUI KOMPONENTEN
	
	// Buttons
	private JButton startenButton = new JButton("[STARTEN]");
	private JButton spielerAddButton = new JButton("[Spieler hinzufügen]");
	private JButton spielBeschreibungButton = new JButton("[Spiele Beschreibung]");
	private JButton scoreMenu = new JButton("[Highscore]");
	private JButton schließenButton = new JButton("[Schließen]");
	
	//Konstruktor
	public GameMenu()
	{
		// GraphicPanel
		PaintPanel paintpanel = new PaintPanel();
		
		// Buttons werden hinzugefügt
		add(startenButton);
		add(spielerAddButton);
		add(spielBeschreibungButton);
		add(scoreMenu);
		add(schließenButton);
		
		//Buttons werden mit setBounds auf die entsprechenden Koordinaten gesetzt
		startenButton.setBounds			 (75,125 , 150,50);
		spielerAddButton.setBounds		 (75,200 , 150,50);
		spielBeschreibungButton.setBounds(50,275 , 200,50);
		scoreMenu.setBounds				 (75,350 , 150,50);
		schließenButton.setBounds		 (75,425 , 150,50);

		// Graphicspanel und Repaint Methode
		add(paintpanel);
		paintpanel.repaint();
		
		// ActionListener auf den Buttons. Bei Knopfdruck wird die jeweilige Methode geöffnet
		startenButton.addActionListener(l -> openGameMenu());
		startenButton.addActionListener(l -> dispose());
		spielBeschreibungButton.addActionListener(l -> openBeschreibung());
		spielerAddButton.addActionListener(l -> openSpielerAdd());
		scoreMenu.addActionListener(l -> openHighScore());
		schließenButton.addActionListener(l -> schließen());
				
		// Fenster settings
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(300, 575);
		setLocation(700, 150);
		setResizable(false);
		setTitle("Game Menu");
	}
	
	//METHODEN
	
	/**
	 * Fenster Game wird geöffnet
	 */
	public static void openGameMenu()
	{
		new Game();
	}
	/**
	 * Fenster SpierAdd wird geöffnet
	 */
	public static void openSpielerAdd()
	{
		new SpielerAdd();
	}
	/**
	 * Fenster Beschreibung wird geöffnet
	 */
	public static void openBeschreibung()
	{
		new Beschreibung();
	}
	/**
	 * Fenster HighScore wird geöffnet
	 */
	public static void openHighScore()
	{
		new HighScore();
	}
	/**
	 * Das Fenster schließt sich
	 */
	public void schließen()
	{
		// Dem Spieler wird mit einer JDialog von JOptionPane gefragt, ob er das Programm beenden will
		int dialogButton = JOptionPane.YES_NO_OPTION;
		int dialogResult = JOptionPane.showConfirmDialog(null,"Wollen sie wirklich beenden?","Beenden", dialogButton);
			if(dialogResult == JOptionPane.YES_OPTION)
			{
				System.exit(0);
			}
	}
	
	/**
	 *  Graphics Panel
	 */
	private class PaintPanel extends JPanel
	{
		private static final long serialVersionUID = 1L;

		@Override
		protected void paintComponent(Graphics g)
		{
			// Der Hintergrund des Menüs
			super.paintComponent(g);
			g.setColor(new Color(0,175,255));
			g.fillRect(0,0 , 300,600);
			
			//Die Überschrift mit Umrandung
			g.setColor(Color.BLACK);
			g.drawRect(3,25 , 290,75);
			
			g.setColor(new Color(255,125,0));
			g.setFont(new Font("HEYRO fun", 1, 24));
			g.drawString("Rectangle Man", 5, 75);
		}
	}
}
