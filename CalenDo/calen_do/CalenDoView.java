package calen_do;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * This class sets up the view of the program.
 * It contains three panels: EventPanel, CalendarPanel and ToDoListPanel
 * It also loads all saved entries and events from previous sessions.
 * When closing the JFrame it saves all entries and events.
 * @version 1.0
 * @author Florian Völkers, Irena Becker, Peter Oetker
 *
 */
@SuppressWarnings("serial")
public class CalenDoView extends JFrame{

	private List<LocalDate> eventDates = new ArrayList<LocalDate>();
	private List<Event> allEvents = new ArrayList<Event>();
	private final EventPanel eventPanel;
	private ArrayList<Entry> entriesList = new ArrayList<Entry>();
	
	private LocalDate dateClicked = LocalDate.now();
	
	/**
	 * The constructor receives loadSaveCalendar as a parameter to load and save events the user put in.
	 * It receives loadSaveEntries as a parameter to load and save entries the user put into the ToDoList
	 * It also sets up the architecture of the program.
	 * With three panels: eventPanel, calendarPanel and toDoListPanel the GUI is set up.
	 * The JFrame will always pop up in the middle of the screen. 
	 * @param loadSaveCalendar
	 * @param loadSaveEntries
	 */
	public CalenDoView (LoadSaveCalendar loadSaveCalendar, LoadSaveEntries loadSaveEntries) {
		
		//loads the Events
		loadSaveCalendar.loadObject(eventDates, allEvents, this);
		loadSaveEntries.loadObject(entriesList, this);
		
		//saves the Events when the window is closed
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				loadSaveCalendar.saveObject(eventDates, allEvents);
				loadSaveEntries.saveObject(entriesList);
			}
		});
		
		//sets the Look and Feel of the UI
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		//Setting up the Frame
		setTitle("Calen-Do: Organize yourself!");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(1200, 550);
		//to put the JFrame at the start of the program directly in the middle
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int) screenSize.getWidth();
		int screenHeight = (int) screenSize.getHeight();
		setLocation(screenWidth/2 - getWidth()/2, screenHeight/2 - getHeight()/2);
		
		//Initializing the three panels of the program
		final CalendarPanel calendarPanel = new CalendarPanel(eventDates, new Transparent(), dateClicked, this);
		eventPanel = new EventPanel(allEvents, dateClicked, calendarPanel, eventDates);
		add(eventPanel, BorderLayout.WEST);
		add(calendarPanel, BorderLayout.CENTER);
		final ToDoListPanel toDoListPanel = new ToDoListPanel(entriesList);
		add(toDoListPanel, BorderLayout.EAST);
	}
	
	/**
	 * This method is called from LoadSaveCalendar and updates the values of eventDates and allEvents
	 * @param eventDates
	 * @param allEvents
	 */
	protected void refreshEvents(List<LocalDate> eventDates, List<Event> allEvents) {
		this.eventDates = eventDates;
		this.allEvents = allEvents;
	}
	
	/**
	 * This method is called from LoadSaveEntries and updates the values in the entriesList
	 * @param entriesList
	 */
	protected void refreshEntries(ArrayList<Entry> entriesList) {
		this.entriesList = entriesList;
	}
	
	/**
	 * returns the eventPanel
	 * @return
	 */
	protected EventPanel getEventPanel(){
		return eventPanel;
	}

	/**
	 * returns the list allEvents
	 * @return
	 */
	public List<Event> getAllEvents() {
		return allEvents;
	}

}
