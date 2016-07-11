package RectangleMan;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * @author Sina, Bager, Walid
 * 
 * 	HighScoreMenu um die gespeicherten Scores der letzten Spieler zu öffnen
 */
public class HighScore extends JDialog{

	private static final long serialVersionUID = 1L;
	
	// GUI KOMPONENTEN
	
	// JMenu
	private JMenuBar menuBar = new JMenuBar();
	private JButton close = new JButton("[Programm schließen!]");
	private JButton backGame = new JButton("[Zum Spiel zurück!]");
	
	// JPanels
	private JPanel pNorth = new JPanel();
	private JPanel pCenter = new JPanel();
	
	// JList mit JScrollPanel und DefaultListModel mit Objekt <HighScoreDaten>
	private DefaultListModel <HighScoreDaten> model = new DefaultListModel<>();
	private JList<HighScoreDaten> list = new JList<>(model);
	private JScrollPane scroll = new JScrollPane(list);
	
	// Konstruktor
	public HighScore()
	{
		// Panels hinzugefügt
		add(pNorth, BorderLayout.NORTH);
		add(pCenter, BorderLayout.CENTER);
		
		// JMenu + Buttons hinzugefügt
		setJMenuBar(menuBar);
		menuBar.add(close);
		menuBar.add(backGame);
		
		// JLabel + Hintergrundfarbe
		pNorth.add(new JLabel("Highscore RectangleMan"));
		pNorth.setBackground(new Color(0,175,255));
		
		// Programm schließen bei close Button
		close.addActionListener(e -> 
		{
			System.exit(0);	
		});
		
		// HighScore Menu schließen mit backGame Button
		backGame.addActionListener(e ->
		{
			dispose();
		});
		
		// JList hinzugefügt
		add(scroll);
		
		// Methode um die Scores der Spieler zu laden
		loadScore();
		
		// JDialog settings
		setTitle("HighScore");
		setVisible(true);
		setSize(320,500);
		setLocation(450, 200);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	/**
	 * Liest die gespeicherten Daten (String name, String score) von der Datei "Scores"
	 * Speichert sie anschließend als Objekt HighScoreDaten und fügt sie der Liste hinzu 
	 */
	public void loadScore()
	{
		// File deklaration
		File file = new File("Score");
		try
		{
			@SuppressWarnings("resource")
			// Scanner 
			Scanner in = new Scanner(file);
			
			// While-Schleife um zu überprüfen, ob die Datei eine nächste noch nicht gelesene Zeile besitzt 
			while(in.hasNextLine())
			{
				// Name und Score werden von der Datei gelesen
				String name = in.nextLine();
				String score = in.nextLine();
				
				// Als HighScoreDaten gespeichert
			HighScoreDaten daten = new HighScoreDaten(name, score);
				
			// Und der DefaultListModel hinzugefügt
			model.addElement(daten);
			}
		}
		// Fängt Exceptions auf
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
}
