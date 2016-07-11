package calen_do;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

/**
 * This class sets up the panel for to show the events of a certain day.
 * It is on the left side of the JFrame and holds information about all the events of a certain day.
 * You can add other events, show/edit events and remove them.
 * @version 1.0
 * @author Florian Völkers, Irena Becker, Peter Oetker
 *
 */
@SuppressWarnings("serial")
public class EventPanel extends JPanel {
	
	private List<Event> allEvents;
	private DefaultListModel<Event> dLMEventListSorted;
	private LocalDate dateClicked;
	
	/**
	 * The constructor receives several parameters.
	 * allEvents and eventDates hold the information about all the events the user already put in.
	 * dateClicked determines the day that is shown in the eventPanel.
	 * calendarPanel is used to communicate with the calendarPanel and to change the days buttons when events are added or removed
	 * @param allEvents
	 * @param dateClicked
	 * @param calendarPanel
	 * @param eventDates
	 */
	public EventPanel(List<Event> allEvents, LocalDate dateClicked, CalendarPanel calendarPanel, List<LocalDate> eventDates){		
		this.allEvents = allEvents;
		this.dateClicked = dateClicked;
		dLMEventListSorted = new DefaultListModel<Event>();
		
		//sets up the JPanel
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(300, 500));
		setBorder(new TitledBorder("Events on " + dateClicked));
		
		//setting up the elements of the JPanel
		JList<Event> eventJList = new JList<Event>(dLMEventListSorted);
		eventJList.setForeground(Color.white);
		eventJList.setBackground(Color.DARK_GRAY);
		eventJList.setFont(new Font("Arial", Font.PLAIN, 16));
		eventJList.setSelectionBackground(new Color(142, 142, 142));
		JScrollPane scrollPaneEventList = new JScrollPane();
		scrollPaneEventList.setViewportView(eventJList);
		
		//JPanel with all the buttons that is shown at the top
		JPanel btnPanel = new JPanel(new GridLayout(2, 2));
		JButton show = new JButton("Show/Edit");
		show.setFocusable(false);
		JButton add = new JButton("Add Event");
		add.setFocusable(false);
		JButton remove = new JButton("Remove");
		remove.setFocusable(false);
		JButton removeAll = new JButton("Remove All");
		removeAll.setFocusable(false);
		btnPanel.add(show);
		btnPanel.add(add);
		btnPanel.add(remove);
		btnPanel.add(removeAll);
		
		//adding the elements to the main JPanel
		add(scrollPaneEventList, BorderLayout.CENTER);
		add(btnPanel, BorderLayout.NORTH);
		
		/**
		 * when show is clicked a new ShowEvent Dialog is instantiated. It receives allEvents, the selected value and the dateClicked as parameters
		 */
		show.addActionListener(l -> {
			if(eventJList.getSelectedValue() != null) {
				new ShowEvent(allEvents, eventJList.getSelectedValue(), this, dateClicked);				
			}
		});
		
		/**
		 * when add is clicked a new AddEvent Dialog is instantiated. 
		 * It receives allEvents, dateClicked, eventDates, the calendarPanel, a title and the eventPanel as parameters to update everything
		 */
		add.addActionListener(l -> {
			String title = String.valueOf(this.dateClicked.getDayOfMonth()+"."+this.dateClicked.getMonthValue()+"."+this.dateClicked.getYear());
			new AddEvent(allEvents, this.dateClicked, eventDates, calendarPanel, title, this);
		});
		
		/**
		 * when remove is clicked a new DeleteEvent Dialog is instantiated.
		 * It receives false (not all Events should be removed), the eventPanel, the lists eventDates and allEvents, the calendarPanel and the JList for the events as parameters
		 * to remove the chosen event from the lists and update the eventPanel and the calendarPanel
		 */
		remove.addActionListener(l -> {
			new DeleteEvent(false, EventPanel.this, eventDates, allEvents, calendarPanel, eventJList);
		});
		
		/**
		 * when removeAll is clicked a new DeleteEvent Dialog is instantiated.
		 * It receives true (all Events should be removed), the eventPanel, the lists eventDates and allEvents, the calendarPanel and the JList for the events as parameters
		 * to remove the chosen event from the lists and update the eventPanel and the calendarPanel
		 */
		removeAll.addActionListener(l -> {
			new DeleteEvent(true, EventPanel.this, eventDates, allEvents, calendarPanel, eventJList);
		});
		
		//showEventList() is called to show the events in the eventPanel
		showEventList();
	}
	
	/**
	 * Aus dem Attribut allEvents die events raussuchen, die auch für den Tag gespeichert wurden und in eine
	 * Liste packen, da ein Tag mehrere events haben kann. 2 Listen, da eins eine List<Event> ist die ersteinmal
	 * befüllt und dann sortiert wird und dann ein DefaultListModel (für die JList) wo die sortierten events reinkommen,
	 * damit die JList am Ende die events in der Reihenfolge der Startzeit gezeigt werden
	 */
	protected void showEventList() {
		List<Event> eventListUnsorted = new ArrayList<Event>();
		dLMEventListSorted.removeAllElements();
		eventListUnsorted.clear();
		for(Event a : allEvents) {
			if(a.getID().equals(dateClicked)) {
				eventListUnsorted.add(a);
			}
		}
		Collections.sort(eventListUnsorted, (o1,o2) -> {
			if(Integer.parseInt(o1.getStartHours()) < Integer.parseInt(o2.getStartHours())) {
				return -1;
			} else if(o1.getStartHours() == o2.getStartHours()) {
				return Integer.parseInt(o1.getStartMins()) > Integer.parseInt(o2.getEndMins()) ? 1 : -1;
			} else {
				return 1;
			}
		});
		for(Event a : eventListUnsorted) {
			dLMEventListSorted.addElement(a);
		}
	}
	
	/**
	 * This method is called when a different day is clicked on.
	 * It receives dateClicked to know which day was clicked on, then it sets the title of the border and calls showEventList()
	 * to change the content of the eventPanel to the events of that particular day
	 * @param dateClicked
	 */
	protected void refreshEventList(LocalDate dateClicked){
		this.dateClicked = dateClicked;
		setBorder(new TitledBorder("Events on " + dateClicked));
		showEventList();
	}
}
