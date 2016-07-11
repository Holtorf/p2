package projektVokabeltrainer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;

import org.omg.CORBA.ExceptionList;

/**
 * Die GUI für unseren Vokabeltrainer
 * 
 * @author Moritz Herrmann & Jordanis Lazaridis
 * @version FinalVersion 1.1
 */
public class VokabelGui extends JFrame {

	private JButton pruefen = new JButton("Prüfen");
	private VokabelnVerwalten vokabeln = new VokabelnVerwalten();
	private JPanel pruefPanel = new JPanel();
	private JPanel listPanel = new JPanel();
	private JList<String> abgefrageteWoerterListe = new JList<>(vokabeln.getAbfrageModel());
	private JButton tipp = new JButton();
	private JButton spracheWechseln = new JButton();
	private JPanel progressPanel = new JPanel();

	/**
	 * Konstruktor für die Klasse GUI.
	 * 
	 * @author Moritz Herrmann
	 * @throws Bild
	 *             nicht geladen werden kann
	 */
	public VokabelGui() {

		setJMenuBar(createMenu());
		int width = 28;
		int height = 28;

		try {
			// Laedt Image-Bild
			Image img2 = ImageIO.read(getClass().getResource("tipp.png"));

			tipp.setIcon(new ImageIcon(img2));

		} catch (Exception e) {
		}

		try {
			// Laedt Image-Bild
			Image img = ImageIO.read(getClass().getResource("kugel.png"));
			spracheWechseln.setIcon(new ImageIcon(img));

		} catch (Exception e) {
		}

		tipp.setPreferredSize(new Dimension(width, height));
		spracheWechseln.setPreferredSize(new Dimension(width, height));

		// Fragpanel
		JPanel fragePanel = new JPanel();
		fragePanel.add(vokabeln.getFragFeld());
		fragePanel.setBorder(BorderFactory.createEmptyBorder(150, 0, 0, 0));
		vokabeln.getFragFeld().setFont(new Font("Dialog", 0, 42));

		// AntwortPanel
		JPanel antwortPanel = new JPanel();
		antwortPanel.add(spracheWechseln);
		antwortPanel.add(vokabeln.getAntwortFeld());
		tipp.setEnabled(false);
		antwortPanel.add(tipp);
		pruefPanel.add(pruefen);
		pruefPanel.add(vokabeln.getRichtigesIcon());
		pruefPanel.add(vokabeln.getFalschesIcon());

		// Die Abgefragten Woerter
		abgefrageteWoerterListe.setPreferredSize(new Dimension(300, 850));
		abgefrageteWoerterListe.setBorder(BorderFactory.createTitledBorder("Abgefragte Woerter"));

		listPanel.add(abgefrageteWoerterListe);
		add(listPanel, BorderLayout.EAST);
		antwortPanel.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
		pruefPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		progressPanel.add(vokabeln.getFortschritt());
		progressPanel.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));

		// JPanel haelt alle Panel in Form
		JPanel combinePanel = new JPanel();
		combinePanel.setLayout(new BoxLayout(combinePanel, BoxLayout.Y_AXIS));
		combinePanel.add(fragePanel);
		combinePanel.add(antwortPanel);
		combinePanel.add(pruefPanel);
		combinePanel.add(progressPanel);
		add(combinePanel);
		// Wenn Enter gedrueckt wird, wird die vergleichs Methode aufgerufen
		vokabeln.getAntwortFeld().addKeyListener(new KeyAdapter() {
			/**
			 * Vergleicht die Eingabe mit dem Zufallswort
			 * 
			 * author Jordanis Lazaridis
			 */
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {

					vokabeln.vergleichen();
					vokabeln.getFortschritt().setValue(vokabeln.getAnzahlRichtigeWoerter());

					for (int i = 0; i < vokabeln.getVokabelListe().size(); i++) {
						if (!vokabeln.getAbfrageModel().contains(vokabeln.getFragFeld().getText())) {
							// Fuegt die abgefragten Woerter in unserer Liste
							// hinzu
							vokabeln.getAbfrageModel().addElement(vokabeln.getFragFeld().getText());
						}
					}
				}
			}
		});

		// Wenn auf das X gedrueckt wird, faengt das schließen ab.
		addWindowListener(new WindowAdapter() {

			/**
			 * Verhindert das einfach schließen des Fenster
			 * 
			 * @author Jordi Lazaridis
			 */
			@Override
			public void windowClosing(WindowEvent e) {
				if (JOptionPane.showConfirmDialog(null, "Wirklich schliessen?", "Bestaetigung",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

					if (vokabeln.getIsSaved() == true) {
						System.exit(0);
					}

					else if (vokabeln.getIsSaved() == false) {
						vokabeln.saveFile();
						System.exit(0);
					}
				}
			}
		});

		setTitle("Vokabeltrainer");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(1000, 850);
		setVisible(true);

	}

	/**
	 * Erstellt das FileMenu mit allen ActionListenern
	 * 
	 * @author Moritz & Jordi
	 * @return menubar
	 */
	private JMenuBar createMenu() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu fileMenu = new JMenu("File");
		// Erzeugt einen Tastenkuerzel
		fileMenu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(fileMenu);

		JMenuItem vokabelListeErstellen = new JMenuItem("Vokabeliste erstellen");
		JMenuItem importItem = new JMenuItem("Vokabeln importieren");
		JMenuItem exportItem = new JMenuItem("Vokabel exportieren (falsch beantwortete)");
		JMenuItem closeItem = new JMenuItem("Vokabeltrainer beenden");

		exportItem.setEnabled(false);
		pruefen.setEnabled(false);
		vokabeln.getRichtigesIcon().setVisible(false);
		vokabeln.getFalschesIcon().setVisible(false);

		fileMenu.add(vokabelListeErstellen);
		fileMenu.add(importItem);
		fileMenu.add(exportItem);
		fileMenu.addSeparator();
		fileMenu.add(closeItem);

		// Tastenkuerzel und erzeugt ein neues Obejekt von NeuVokabelListe
		vokabelListeErstellen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		vokabelListeErstellen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new NeuVokabelListe();
			}
		});
		// Tastenkuerzel und Importiert eine neue Liste
		importItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
		importItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				werteZuruecksetzen();
				vokabeln.fileOpen();
				exportItem.setEnabled(true);
				tipp.setEnabled(true);
				pruefen.setEnabled(true);
				vokabeln.getFortschritt().setMaximum(vokabeln.getVokabelListe().size());
				vokabeln.getFortschritt().setStringPainted(true);
				vokabeln.getFragFeld().setText(vokabeln.getZufallswort());
				vokabeln.getAbfrageModel().addElement(vokabeln.getFragFeld().getText());
			}
		});
		// Tastenkuerzel und Exportiert die Liste
		exportItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		exportItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				vokabeln.saveFile();
			}
		});
		// Ruft die vergleichs Methode auf.
		pruefen.addActionListener(new ActionListener() {

			/** Überprüft ob das Wort schon in der Liste vorhanden ist */
			@Override
			public void actionPerformed(ActionEvent arg0) {
				vokabeln.vergleichen();

				vokabeln.getFortschritt().setValue(vokabeln.getAnzahlRichtigeWoerter());
				// Durchlaeuft die gesamte Vokabeliste und ueberprueft ob eine
				// Vokabel schon mal abgefragt wurde
				for (int i = 0; i < vokabeln.getVokabelListe().size(); i++) {

					if (!vokabeln.getAbfrageModel().contains(vokabeln.getFragFeld().getText())) {
						vokabeln.getAbfrageModel().addElement(vokabeln.getFragFeld().getText());
					}
				}

			}
		});
		// Gibt die Moeglichtkeit eine neue Liste zu impotieren
		spracheWechseln.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Wähle eine Sprache:  ", "Sprache wählen:",
						JOptionPane.INFORMATION_MESSAGE);
				if (JOptionPane.showConfirmDialog(null, "Wirklich importieren?Alte Liste wird gelöscht", "Bestaetigung",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

					werteZuruecksetzen();
					vokabeln.fileOpen();
					exportItem.setEnabled(true);
					tipp.setEnabled(true);
					pruefen.setEnabled(true);
					vokabeln.getFortschritt().setMaximum(vokabeln.getVokabelListe().size());
					vokabeln.getFortschritt().setStringPainted(true);
					vokabeln.getFragFeld().setText(vokabeln.getZufallswort());
					vokabeln.getAbfrageModel().addElement(vokabeln.getFragFeld().getText());
				}

			}
		});

		// Gibt einen Tipp zurueck
		tipp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JOptionPane.showMessageDialog(null, "Anfangsbuchstaben:  " + vokabeln.getTipp().substring(0, 3), "Tipp",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		// Tastenkuerzel und schließt das Programm,ueberprueft ob zuvor
		// gespeichert wurde
		closeItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		closeItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (vokabeln.getIsSaved() == true) {
					System.exit(0);
				}

				else if (vokabeln.getIsSaved() == false) {
					vokabeln.saveFile();
					System.exit(0);
				}
			}
		});

		return menuBar;
	}

	/**
	 * Setzt alle Werte zum Standard zurück
	 * 
	 * @author Jordi
	 */
	public void werteZuruecksetzen() {
		vokabeln.getFragFeld().setText("");
		vokabeln.getVokabelListe().clear();
		vokabeln.getAbgefragteListe().clear();
		vokabeln.getAnzahlRichtigeWoerter();
		vokabeln.getAbfrageModel().clear();
		vokabeln.getAntwortFeld().setText("");
		vokabeln.getFortschritt().setValue(vokabeln.getAnzahlRichtigeWoerter());
		vokabeln.getFortschritt().setMaximum(vokabeln.getVokabelListe().size());
	}
}
