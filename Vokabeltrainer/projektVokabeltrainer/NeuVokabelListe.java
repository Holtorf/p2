package projektVokabeltrainer;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Erstellt eine neue GUI, in der man Vokabellisten erstellen kann
 * 
 * @author Jordi
 * @version FinalVersion 1.1
 */
public class NeuVokabelListe extends JFrame {
	private Map<String, String> neueVokabelListe = new HashMap<String, String>();

	/**
	 * Konstruktor
	 */
	public NeuVokabelListe() {
		super("Neue Vokabeliste erstellen");
		setLayout(new BorderLayout());
		// Neue Komponenten fuer GUI erstellen
		Panel neuesVokabelPanel = new Panel();

		neuesVokabelPanel.setLayout(new GridLayout(3, 3));
		Label vokabelLabel = new Label("Vokabel:");
		vokabelLabel.setAlignment(Label.LEFT);
		TextField vokabelTextField = new TextField(20);
		JButton neueVokabel = new JButton("Neue Vokabel hinzufügen");
		JButton speichern = new JButton("Liste speichern");

		Label uebersetzungsLabel = new Label("Übersetzung:");
		uebersetzungsLabel.setAlignment(Label.LEFT);
		TextField uebersetzungsTextField = new TextField();

		neuesVokabelPanel.add(vokabelLabel);
		neuesVokabelPanel.add(uebersetzungsLabel);
		neuesVokabelPanel.add(vokabelTextField);
		neuesVokabelPanel.add(uebersetzungsTextField);
		add(neuesVokabelPanel, BorderLayout.NORTH);
		neuesVokabelPanel.add(neueVokabel);
		neuesVokabelPanel.add(speichern);

		speichern.setEnabled(false);
		// Speichert die Werte in eine Haspmap
		neueVokabel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				neueVokabelListe.put(vokabelTextField.getText(), uebersetzungsTextField.getText());
				vokabelTextField.setText("");
				uebersetzungsTextField.setText("");
				speichern.setEnabled(true);
			}
		});
		// Speichert die Hashmap Werte in eine Textdatei
		speichern.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveFile();
				dispose();
			}
		});

		addWindowListener(new WindowAdapter() {
			/**
			 * Verhindert das einfach schließen des Fenster
			 * 
			 * @author Jordi
			 */
			@Override
			public void windowClosing(WindowEvent e) {
				if (JOptionPane.showConfirmDialog(null, "Wirklich schliessen?\nListe wird gespeichert", "Bestaetigung",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					saveFile();
					dispose();
				}
			}

		});

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		pack();
		setVisible(true);
	}

	/**
	 * Expotiert eine Vokabeliste
	 * 
	 * @author Jordi
	 */
	public void saveFile() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Textdatei", "txt");
		chooser.setSelectedFile(new File(".txt"));
		chooser.setCurrentDirectory(new File(System.getProperty("user.home"), "Desktop"));
		chooser.setFileFilter(filter);
		chooser.setDialogTitle("Wähle eine Datei zum speichern aus");
		int userSelection = chooser.showSaveDialog(null);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File filetoSave = chooser.getSelectedFile();

			try {
				FileWriter writer = new FileWriter(filetoSave, true);
				BufferedWriter buf = new BufferedWriter(writer);

				for (Map.Entry<String, String> e : neueVokabelListe.entrySet()) {
					buf.write(e.getKey().toString() + "	-	" + e.getValue() + "\r\n");
				}

				buf.close();

			} catch (Exception e) {

			}
		}
	}

	/**
	 * 
	 * Erzeugt ein neues Objekt
	 */
	public static void main(String[] args) {
		new NeuVokabelListe();
	}
}
