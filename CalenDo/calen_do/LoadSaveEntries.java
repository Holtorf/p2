package calen_do;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * This class saves all the entries the user made into the ToDoList in the file savedEntries.bin.
 * It also loads all the entries saved when the program is started so that information can be stored. * 
 * @version 1.0
 * @author Florian Völkers, Irena Becker, Peter Oetker
 *
 */
public class LoadSaveEntries {

	private final String fileName = "savedEntries.bin";

	/**
	 * This method saves the entriesList that contains the information about all entries in the file savedEntries.bin.
	 * It is called at the start of the program.
	 * @param entriesList
	 */
	public void saveObject(ArrayList<Entry> entriesList) {
		try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName))) {
			os.writeObject(entriesList);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method loads the entries from the savedEntries.bin and puts the into the entriesList.
	 * Then the information is passed to the JList in the toDoListPanel to show all the entries in the GUI.
	 * @param entriesList
	 * @param view
	 */
	@SuppressWarnings("unchecked")
	public void loadObject(ArrayList<Entry> entriesList, CalenDoView view) {
		if (!new File(fileName).exists()) {
			try {
				new File(fileName).createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileName))) {
				entriesList = (ArrayList<Entry>) is.readObject();
				view.refreshEntries(entriesList);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}