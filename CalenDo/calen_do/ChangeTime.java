package calen_do;

import java.time.LocalDate;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class is a JDialog to change the time of an event.
 * @version 1.0
 * @author Florian Völkers, Irena Becker, Peter Oetker
 *
 */
@SuppressWarnings("serial")
public class ChangeTime extends JDialog {
	
	/**
	 * The constructor is given several objects to update everything in the panel, the showEvent Dialog and in the eventLists
	 * @param allEvents
	 * @param selectedEvent
	 * @param eventPanel
	 * @param showEvent
	 * @param dateClicked
	 */
	public ChangeTime(List<Event> allEvents, Event selectedEvent, EventPanel eventPanel, ShowEvent showEvent, LocalDate dateClicked){
		
		//setting up the JDialog
		setTitle("Change Time");
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setVisible(true);
		setSize(300, 120);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(eventPanel);
		
		//setting up the elements of the JDialog
		JLabel startTimeGap = new JLabel(":");
		JLabel endTimeGap = new JLabel(":");
		
		JLabel startTimeText = new JLabel("Start:");
		JLabel endTimeText = new JLabel("End");
		
		JButton saveNewTime = new JButton();
		saveNewTime.setText("Save");
		saveNewTime.setSize(75,25);
		saveNewTime.setFocusable(false);		
		
		// setting up the JComboBoxes to change the time
		String[] hours = new String[24];
		String[] mins = new String[60];
		
		for(int i = 0;i < mins.length;i++) {
			if(i<10) {
				hours[i] = "0"+i;
				mins[i] = "0"+i;
			} else if(i>9 && i < hours.length) {
				hours[i] = String.valueOf(i);
				mins[i] = String.valueOf(i);
			} else {
				mins[i] = String.valueOf(i);
			}
		}
		
		JComboBox<String> startHoursComboBox = new JComboBox<String>(hours);
		JComboBox<String> startMinsComboBox = new JComboBox<String>(mins);
		JComboBox<String> endHoursComboBox = new JComboBox<String>(hours);
		JComboBox<String> endMinsComboBox = new JComboBox<String>(mins);
				
		startHoursComboBox.setFocusable(false);
		startMinsComboBox.setFocusable(false);
		endHoursComboBox.setFocusable(false);
		endMinsComboBox.setFocusable(false);
		
		/**
		 * when the saveNewTime button is pressed the time that the user chose for the event is updated by calling the method showEventList()
		 * It also updates the title of the showEvent dialog
		 */
		saveNewTime.addActionListener(l -> {
			if(Integer.parseInt((String) startHoursComboBox.getSelectedItem()) >= 0 && Integer.parseInt((String) startMinsComboBox.getSelectedItem()) >= 0 && Integer.parseInt((String) endHoursComboBox.getSelectedItem()) >= 0 && Integer.parseInt((String) endMinsComboBox.getSelectedItem()) >= 0) {
				if(startHoursComboBox.getSelectedIndex() > endHoursComboBox.getSelectedIndex()) {	
				} else if(Integer.parseInt((String) startHoursComboBox.getSelectedItem()) == Integer.parseInt((String) endHoursComboBox.getSelectedItem()) && Integer.parseInt((String) startMinsComboBox.getSelectedItem()) >= Integer.parseInt((String) endMinsComboBox.getSelectedItem())) {
				} else {
					for(Event a : allEvents) {
						if(a.equals(selectedEvent)) {
							a.changeStartHours(startHoursComboBox.getSelectedIndex());
							a.changeStartMins(startMinsComboBox.getSelectedIndex());
							a.changeEndHours(endHoursComboBox.getSelectedIndex());
							a.changeEndMins(endMinsComboBox.getSelectedIndex());
						}
					}
					startHoursComboBox.setSelectedIndex(0);
					startMinsComboBox.setSelectedIndex(0);
					endHoursComboBox.setSelectedIndex(0);
					endMinsComboBox.setSelectedIndex(0);
					showEvent.setTitle("On " + dateClicked + " from " + selectedEvent.getStartHours() + ":" + selectedEvent.getStartMins() 
					+ " to " + selectedEvent.getEndHours() + ":" + selectedEvent.getEndMins());
					dispose();
					eventPanel.showEventList();
				}
			}
		});
		
		//JPanel for adjusting the time
		JPanel timePanel = new JPanel();
		timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.X_AXIS));
		timePanel.add(Box.createHorizontalStrut(10));
		timePanel.add(startTimeText);
		timePanel.add(Box.createHorizontalStrut(10));
		timePanel.add(startHoursComboBox);
		timePanel.add(Box.createHorizontalStrut(5));
		timePanel.add(startTimeGap);
		timePanel.add(Box.createHorizontalStrut(5));
		timePanel.add(startMinsComboBox);
		timePanel.add(Box.createHorizontalStrut(30));
		timePanel.add(endTimeText);
		timePanel.add(Box.createHorizontalStrut(10));
		timePanel.add(endHoursComboBox);
		timePanel.add(Box.createHorizontalStrut(5));
		timePanel.add(endTimeGap);
		timePanel.add(Box.createHorizontalStrut(5));
		timePanel.add(endMinsComboBox);
		timePanel.add(Box.createHorizontalStrut(10));
		
		//Panel with the save button
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));
		btnPanel.add(Box.createHorizontalStrut(20));
		btnPanel.add(saveNewTime);
		btnPanel.add(Box.createHorizontalStrut(20));
		
		//adding the panels and some strut for the design to the JDialog
		add(Box.createVerticalStrut(10));
		add(timePanel);
		add(Box.createVerticalStrut(20));
		add(btnPanel);
		add(Box.createVerticalStrut(10));
		
	}
}
