package RectangleMan;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * @author Sina, Bager, Walid
 * 
 *         In dieser Klasse wir das SpielerAdd Fenster erstellt, welches die
 *         Aufgabe hat Spieler hinzuzufügen.
 */
public class SpielerAdd extends JDialog {

	private static final long serialVersionUID = 1L;

	// JPanels
	private JPanel pWest = new JPanel();

	private JPanel pCenter = new JPanel();
	private JPanel pCenterNorth = new JPanel();
	private JPanel pCenterCenter = new JPanel();
	private JPanel pCenterSouth = new JPanel();
	private JPanel pCenterSouth2 = new JPanel();

	// JLabels 4
	private JLabel labelProjektListe = new JLabel("[Spieler:]");
	private JLabel labelProjektName = new JLabel("[Spieler Name:] ");
	private JLabel labelBeschreibung = new JLabel("[Spieler Beschreibung:] ");
	private JLabel labelPrio = new JLabel("[Erfahrungslevel des Spielers: (Nur Zahlen)] ");

	// JTextFields 2
	private JTextField name = new JTextField();
	private JTextField level = new JTextField();

	// JTextArea
	private JTextArea beschreibung = new JTextArea();

	// JButton Add und Ok
	private JButton addButton = new JButton("[Spieler hinzufügen]");
	private JButton okButton = new JButton("[Ok]");
	private JButton saveButton = new JButton("[Save]");
	private JButton loadButton = new JButton("[Load]");

	// JList und ScrollPane
	private DefaultListModel<SpielerDaten> listeArray = new DefaultListModel<>();
	private JList<SpielerDaten> liste = new JList<SpielerDaten>(listeArray);
	private JScrollPane scroll = new JScrollPane(liste);

	public SpielerAdd() {

		// Westliches Panel (JList und Label)
		add(pWest, BorderLayout.WEST);
		scroll.setPreferredSize(new Dimension(200, 10));
		pWest.setLayout(new BorderLayout());
		pWest.add(labelProjektListe, BorderLayout.NORTH);
		pWest.add(scroll);

		// Center Panel
		add(pCenter, BorderLayout.CENTER);
		pCenter.setLayout(new BoxLayout(pCenter, BoxLayout.Y_AXIS));

		pCenter.add(pCenterNorth, BorderLayout.NORTH);
		pCenter.add(pCenterCenter, BorderLayout.CENTER);
		pCenter.add(pCenterSouth, BorderLayout.SOUTH);

		// Center NORTH
		pCenterNorth.setLayout(new BorderLayout());
		pCenterNorth.add(labelProjektName, BorderLayout.NORTH);
		pCenterNorth.add(name, BorderLayout.CENTER);
		// TxtFeld1 Eigenschaften
		name.setEditable(false);

		// Center CENTER
		pCenterCenter.setLayout(new BorderLayout());
		pCenterCenter.add(labelBeschreibung, BorderLayout.NORTH);
		pCenterCenter.add(beschreibung, BorderLayout.CENTER);
		// TxtArea Eigenschaften
		beschreibung.setColumns(20);
		beschreibung.setRows(10);
		beschreibung.setLineWrap(true);
		beschreibung.setEditable(false);

		// Center SOUTH
		pCenterSouth.setLayout(new BorderLayout());
		pCenterSouth.add(labelPrio, BorderLayout.NORTH);
		pCenterSouth.add(level, BorderLayout.CENTER);
		pCenterSouth.add(pCenterSouth2, BorderLayout.SOUTH);

		// Center SOUTH2
		pCenterSouth2.setLayout(new FlowLayout());
		pCenterSouth2.add(saveButton);
		pCenterSouth2.add(loadButton);
		pCenterSouth2.add(addButton);
		pCenterSouth2.add(okButton);
		// TxtFeld2 und Button Eigenschaften
		level.setEditable(false);
		okButton.setEnabled(false);
		saveButton.setEnabled(false);

		// SelectionListener und per MausKlick wird dem User seine Informationen
		// gezeigt
		liste.addListSelectionListener(e -> {
			// SelectedValue für welches Element der User ausgewählt hat
			SpielerDaten daten = liste.getSelectedValue();

			// Schreibt die Informationen auf den jeweiligen Text
			name.setText(daten.getName());
			beschreibung.setText(daten.getBeschreibung());
			level.setText(String.valueOf(daten.getLvlExp()));

			// Der SaveButton wird enabled
			saveButton.setEnabled(true);
		});

		// Nachdem der Spieler seine Informationen hinzugefügt hat, werden seine
		// Einträge im Objekt SpielerDaten eingefügt
		okButton.addActionListener(e -> {

			SpielerDaten daten = new SpielerDaten(name.getText(), beschreibung.getText(),
					Integer.valueOf(level.getText()));
			;

			// Dem ListArray werden den Daten hinzugefügt
			listeArray.addElement(daten);

			// Alle Buttons Enabled == True bis auf "Add"
			okButton.setEnabled(false);

			name.setText("");
			name.setEditable(false);

			beschreibung.setText("");
			beschreibung.setEditable(false);

			level.setText("");
			level.setEditable(false);

			addButton.setEnabled(true);
		});

		// Der Text wird gesäubert und die Buttons werden auf true gesetzt bis
		// auf "Add", der Vorgang"Add" noch nicht abgeschloßen ist
		addButton.addActionListener(e -> {

			okButton.setEnabled(true);

			name.setText("");
			name.setEditable(true);

			beschreibung.setText("");
			beschreibung.setEditable(true);

			level.setText("");
			level.setEditable(true);

			addButton.setEnabled(false);
			saveButton.setEnabled(true); 
		});

		// Save Methode
		saveButton.addActionListener(e -> {

			@SuppressWarnings("unused")
			SpielerDaten daten = new SpielerDaten(name.getText(), beschreibung.getText(),
					Integer.valueOf(level.getText()));
			;
			String sTxt1 = name.getText(); 
			String sTxtA = beschreibung.getText();
			String sTxt2 = level.getText();
			// FileChooser
			File file = null;
			JFileChooser fc = new JFileChooser();
			int state = fc.showSaveDialog(null);
			if (state == JFileChooser.APPROVE_OPTION) {
				file = fc.getSelectedFile();
			}

			try {
				// Nimmt die Einträge der TextFelder
				FileWriter fw = new FileWriter(file, true);
				BufferedWriter bw = new BufferedWriter(fw);

				bw.write(sTxt1);
				bw.newLine();
				bw.write(sTxtA);
				bw.newLine();
				bw.write(sTxt2);
				bw.newLine();
				bw.flush();
				bw.close();
				fw.close();

			}
			// Nimmt Exception auf
			catch (IOException ex) {
				Logger.getLogger(SpielerDaten.class.getName()).log(Level.SEVERE, null, ex);
			}
		});

		// Ließt die Einträge aus der gespeicherten Datei aus
		loadButton.addActionListener(e -> {

			// Datei
			File file = null;

			// FileChooser
			JFileChooser fc = new JFileChooser();
			int state = fc.showOpenDialog(null);
			if (state == JFileChooser.APPROVE_OPTION) {
				file = fc.getSelectedFile();

				try {
					// Mit Scanner wird die Datei gelesen
					Scanner in = new Scanner(file);  
					while (in.hasNextLine()) {
						String txt1 = in.nextLine();
						String txt2 = in.nextLine();
						String txt3 = in.nextLine();
						// int txt3 = in.nextInt();
						System.out.println(txt1);
						System.out.println(txt2);
						System.out.println("text3:" + txt3);
						SpielerDaten daten = new SpielerDaten(txt1, txt2, Integer.valueOf(txt3));

						listeArray.addElement(daten);

						name.setText(txt1);
						beschreibung.setText(txt2);
						level.setText("" + txt3);
					}
					in.close();
				}
				// Nimmt Exceptions auf
				catch (FileNotFoundException ex) {
					// Logger.getLogger(SpielerDaten.class.getName()).log(Level.SEVERE
					// ,null ,e);
				}
			}

		});

		// Fenstereinstellungen
		setVisible(true);
		setSize(600, 325);
		setLocation(550, 250);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Spielerdatenbank");
	}
}
