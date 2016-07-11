package calen_do;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.List;

/**
 * This class loads and saves the events of the calendar. It loads them when the program is started and saves them when the program is closed.
 * @version 1.0
 * @author Florian Völkers, Irena Becker, Peter Oetker
 *
 */
public class LoadSaveCalendar {
	
	private final String fileName = "savedEvents.bin";
	
	/**
	 * This method saves the content of the lists eventDates and allEvents in a file savedEvents.bin.
	 * This file then contains all the information about the events the user put in.
	 * @param eventDates
	 * @param allEvents
	 */
	public void saveObject(List<LocalDate> eventDates, List<Event> allEvents) {
		try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName))) {
			os.writeObject(eventDates);
	        os.writeObject(allEvents);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method loads the all the events from the file savedEvents.bin.
	 * It saves them in the lists eventDates and allEvents.
	 * It then calls the refreshEvents method of the CalenDoView so that the information is passed to the elements of the GUI
	 * @param eventDates
	 * @param allEvents
	 * @param calendar
	 */
	@SuppressWarnings("unchecked")
	public void loadObject(List<LocalDate> eventDates, List<Event> allEvents, CalenDoView calendar) {
		if(!new File(fileName).exists()) {
			try {
				new File(fileName).createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileName))) {
				eventDates = (List<LocalDate>) is.readObject();
				allEvents = (List<Event>) is.readObject();
				calendar.refreshEvents(eventDates, allEvents);
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
