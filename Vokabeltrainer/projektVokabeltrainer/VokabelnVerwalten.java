package projektVokabeltrainer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Enthält die gesamte Logik des Vokabeltrainers
 * 
 * @author Jordi
 * @version FinalVersion 1.1
 */
public class VokabelnVerwalten {

	private Map<String, String> vokabelListe = new HashMap<String, String>();
	private Map<String, String> falscheVokabeln = new HashMap<String, String>();
	private ArrayList<String> abgefragteListe = new ArrayList<>();
	private int anzahlRichtigeWoerter = 0;
	private JTextField antwortFeld = new JTextField(10);
	private JLabel fragFeld = new JLabel("");
	private JLabel richtigesIcon = new JLabel(new ImageIcon(getClass().getResource("haken_gruen.png")));
	private JLabel falschesIcon = new JLabel(new ImageIcon(getClass().getResource("rotes_kreuz.png")));
	private String zufallsWort = "";
	private JProgressBar fortschritt = new JProgressBar(0);
	private Timer timer = new Timer();
	private DefaultListModel<String> abfrageModel = new DefaultListModel<>();
	private boolean isSaved = false;

	/**
	 * Importiert eine Vokabeliste
	 * 
	 * @author Jordi
	 */
	public void fileOpen() {

		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Textdatei", "txt");
		chooser.setCurrentDirectory(new File(System.getProperty("user.home"), "Desktop"));
		chooser.setFileFilter(filter);
		int state = chooser.showOpenDialog(null);
		if (state == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			try {
				FileReader reader = new FileReader(file);
				BufferedReader buf = new BufferedReader(reader);

				String zeile = "";
				while ((zeile = buf.readLine()) != null) {
					// Durchlaeuft in einer While, solange ein Zeile etwas
					// enthaelt, wird diesedurchlaufen
					// Definiert die Formatierung der Vokabeliste, das Wort vor
					// dem - ist part1 und das wort danach part2 bis zum -
					// speichert es in part1 speichert sie es in den part2
					// speichert es in die HaspMap

					String[] parts = zeile.split("	-	");
					String part1 = parts[0];
					String part2 = parts[1];
					vokabelListe.put(part1, part2);
				}
				JOptionPane.showMessageDialog(null, "Vokabeln wurden importiert: Anzahl:" + vokabelListe.size(),
						"Meldung - Importieren", JOptionPane.INFORMATION_MESSAGE);
				buf.close();
			} catch (Exception e) {
			}
		}

	}

	/**
	 * Expotiert eine Vokabeliste, mit den falsch - eingegebenen Vokabeln Direkt
	 * auf dem Desktop - mit dem Namen "Falsche_Vokabeln.txt"
	 * 
	 * @author Jordi
	 */

	public void saveFile() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Textdatei", "txt");
		chooser.setSelectedFile(new File("Falsche_Vokabeln.txt"));
		chooser.setCurrentDirectory(new File(System.getProperty("user.home"), "Desktop"));
		chooser.setFileFilter(filter);
		chooser.setDialogTitle("Wähle eine Datei zu speichern aus");
		int userSelection = chooser.showSaveDialog(null);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File filetoSave = chooser.getSelectedFile();

			try {
				FileWriter writer = new FileWriter(filetoSave, true);
				BufferedWriter buf = new BufferedWriter(writer);

				for (Map.Entry<String, String> e : falscheVokabeln.entrySet()) {
					buf.write(e.getKey().toString() + "	-	" + e.getValue() + "\r\n");
					// Und schreibt in die Textdatei,den passenden key + value
				}
				buf.close();
				// Sagt das schon gespeichert wurde
				isSaved = true;
				getIsSaved();
			} catch (Exception e) {

			}
		}
	}

	/**
	 * Gibt die Hashmap Vokabelliste zurück
	 * 
	 * @author Jordi
	 * @return vokabeListe
	 */
	public Map<String, String> getVokabelListe() {
		return vokabelListe;
	}

	/**
	 * Gibt die Hashmap der falschen Vokabeln zurück
	 * 
	 * @author Jordi
	 * @return falscheVokabeln
	 */
	public Map<String, String> getFalscheVokabelnListe() {
		return falscheVokabeln;
	}

	/**
	 * Erzeugt ein neues Zufallswort
	 * 
	 * @author Jordi
	 * @return Zufallswort
	 */
	public String getZufallswort() {
		String[] keys = new String[vokabelListe.size()];
		// Definiert ein String Array, der Laenge der Vokabeliste
		int i = 0;
		for (String key : vokabelListe.keySet()) {
			keys[i] = key;
			// Durchlaeuft dieses Array Überprüft ob der aktuelle wert von i in
			// der Vokabelliste vorhanden ist
			i++;
		}

		do {
			zufallsWort = keys[new Random().nextInt(keys.length)];
			// Erzeugt ein neues Zufallswort, in dem nach der Laenge der Liste
			// ein Wort gewaehlt wird
		} while (abgefragteListe.contains(zufallsWort));
		return zufallsWort;
	}

	/**
	 * Gibt das Wort von frag Feld zurück
	 * 
	 * @author Jordi
	 * @return Das Zufallswort
	 */

	public JLabel getFragFeld() {
		return fragFeld;
	}

	/**
	 * Gibt den grünen Haken zurück
	 * 
	 * @author Jordi
	 * @return richtigesIcon
	 */
	public JLabel getRichtigesIcon() {
		return richtigesIcon;
	}

	/**
	 * Gibt das rote Kreuz zurück
	 * 
	 * @author Jordi
	 * @return falschesIcon
	 */
	public JLabel getFalschesIcon() {
		return falschesIcon;
	}

	/**
	 * Gibt den Wert des Antwort Feldes zurück
	 * 
	 * @author Jordi
	 * @return antwortFeld
	 */
	public JTextField getAntwortFeld() {
		return antwortFeld;
	}

	/**
	 * Gibt die Anzahl der richtigen Wörter zurück
	 * 
	 * @author Jordi
	 * @return anzahlRichtigeWoerter
	 */

	public int getAnzahlRichtigeWoerter() {
		return anzahlRichtigeWoerter;
	}

	/**
	 * Gibt die Liste der abgefragten Wörter zurück
	 * 
	 * @author Jordi
	 * @return abgefragteListe
	 */
	public ArrayList<String> getAbgefragteListe() {
		return abgefragteListe;
	}

	/**
	 * Gibt die ersten 3 Buchstaben des gesuchten Wortes zurück
	 * 
	 * @author Jordi
	 * @return tipp
	 */
	public String getTipp() {
		String tipp = getVokabelListe().get(getFragFeld().getText());
		return tipp;
	}

	/**
	 * Vergleicht die Eingabe mit den Vokabeln in der Hashmap
	 * 
	 * @author Jordi
	 */
	public void vergleichen() {

		if (getAntwortFeld().getText().equalsIgnoreCase((getVokabelListe().get(getFragFeld().getText())))) {

			richtigesIcon.setVisible(true);
			timer.schedule(new Task(), 1200);
			// Zeigt das Icon für 1200 ms an
			anzahlRichtigeWoerter++;
			abgefragteListe.add(zufallsWort);

			if (vokabelListe.size() > abgefragteListe.size()) {
				// Überprüft wie oft ein neues Zufallswort abgefragt wird
				getFragFeld().setText(getZufallswort());
				getAntwortFeld().setText("");
			} else {
				getFortschritt().setValue(getAnzahlRichtigeWoerter());
				JOptionPane.showMessageDialog(null,
						"Glückwunsch: Du bist fertig :)\n" + " Richtig beantwotet: " + getAnzahlRichtigeWoerter()
								+ "\n Falsch beantwortet: " + getFalscheVokabelnListe().size()
								+ "\n\nImportiere eine neue Liste!",
						"Neue Liste?", JOptionPane.INFORMATION_MESSAGE);
				anzahlRichtigeWoerter = 0;
				getFragFeld().setText("");
				getAbfrageModel().clear();
				getVokabelListe().clear();
				getAntwortFeld().setText("");
				getAbgefragteListe().clear();
			}

		} else {
			richtigesIcon.setVisible(false);
			falschesIcon.setVisible(true);
			// Zeigt das falsch/richtig Icon für 1200 ms an
			timer.schedule(new Task(), 1200); 
			getFalscheVokabelnListe().put(getFragFeld().getText(), getVokabelListe().get(getFragFeld().getText()));
			// Und setzt das abgefragte Wort, welches falsch eingegeben wurde,
			// in die Liste der FalschenWoerter
		}
	}

	/**
	 * Dient zum verzögertem anzeigen des richtig/falsch Icon
	 * 
	 * @author Jordi
	 */

	class Task extends TimerTask {
		@Override
		public void run() {
			richtigesIcon.setVisible(false);
			falschesIcon.setVisible(false);
		}
	}

	/**
	 * Gibt den den aktuellen Fortschritt zurück
	 * 
	 * @return fortschritt
	 * @author Jordi
	 */

	public JProgressBar getFortschritt() {
		return fortschritt;
	}

	/**
	 * Gibt die Liste der abgefragen Wörter zurück
	 * 
	 * @author Jordi
	 * @return abfrageModel
	 */
	public DefaultListModel<String> getAbfrageModel() {
		return abfrageModel;
	}

	/**
	 * Gibt an ob gespeichert wurde oder nicht.
	 * @return isSaved
	 * @author Jordi
	 */
	public boolean getIsSaved() {
		return isSaved;
	}
}