package calen_do;

import java.awt.Color;
import java.awt.Font;
import java.time.LocalDate;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * With this dialog you can add Events to the Calendar and the EventPanel.
 * The user will be asked to put in the time and a short text to recognize the event.
 * The event is then added to the JList component by calling the method showEventList() 
 * and to the calendar by calling the method showDays().
 * @version 1.0
 * @author Florian Völkers, Irena Becker, Peter Oetker
 *
 */
@SuppressWarnings("serial")
public class AddEvent extends JDialog {
	
	/**
	 * The constructor receives several parameters to make changes to the events list and the calendar.
	 * Also the title of the dialog depends on what day the event is etc.
	 * In the constructor the GUI of the dialog is set up and actionListeners for the buttons are implemented.
	 * @param allEvents
	 * @param dateClicked
	 * @param eventDates
	 * @param calendarPanel
	 * @param title
	 * @param eventPanel
	 */
	public AddEvent(List<Event> allEvents, LocalDate dateClicked, List<LocalDate> eventDates, CalendarPanel calendarPanel, String title, EventPanel eventPanel){
		
		//setting up the JDialog frame
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setTitle(title);
		setVisible(true);
		setSize(300, 300);
		setResizable(false);
		setLocation(eventPanel.getLocationOnScreen());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		//setting up the elements of the GUI
		JPanel timePanel = new JPanel();
		timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.X_AXIS));
		
		JLabel startTimeGap = new JLabel(":");
		JLabel endTimeGap = new JLabel(":");	
		JLabel startTimeText = new JLabel("Start:");
		JLabel endTimeText = new JLabel("End");	
		
		JTextArea notice = new JTextArea();
		notice.setBackground(Color.DARK_GRAY);
		notice.setForeground(Color.WHITE);
		notice.setFont(new Font("Arial", Font.PLAIN, 14));
		notice.setLineWrap(true);
		notice.setWrapStyleWord(true);	
		
		JPanel noticePanel = new JPanel();
		noticePanel.setLayout(new BoxLayout(noticePanel, BoxLayout.X_AXIS));
		noticePanel.add(Box.createHorizontalStrut(10));
		noticePanel.add(notice);
		noticePanel.add(Box.createHorizontalStrut(10));	
		
		JButton sendNotice = new JButton();
		sendNotice.setText("Save");
		sendNotice.setFocusable(false);
		
		JButton closeAddEvent = new JButton();
		closeAddEvent.setText("Close");
		closeAddEvent.setFocusable(false);	
		
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));
		add(Box.createHorizontalStrut(20));
		btnPanel.add(sendNotice);
		add(Box.createHorizontalStrut(20));
		btnPanel.add(closeAddEvent);
		add(Box.createHorizontalStrut(20));
		
		// setting up the Combo Boxes for adjusting the time
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
		 * When the sendNotice button is clicked a new Event is instantiated.
		 * It uses the input of the combo boxes and the text area.
		 * The Event is then added to the Lists eventDates and allEvents.
		 * To update the eventPanel the method showEventList() is called.
		 * To update the calendarPanel the method showDays() is called.
		 * Then the dialog is closed.
		 */
		sendNotice.addActionListener(l -> {
			if(!notice.getText().isEmpty() && Integer.parseInt((String) startHoursComboBox.getSelectedItem()) >= 0 && Integer.parseInt((String) startMinsComboBox.getSelectedItem()) >= 0 
					&& Integer.parseInt((String) endHoursComboBox.getSelectedItem()) >= 0 && Integer.parseInt((String) endMinsComboBox.getSelectedItem()) >= 0) {
				if(startHoursComboBox.getSelectedIndex() > endHoursComboBox.getSelectedIndex()) {	
				} else if(Integer.parseInt((String) startHoursComboBox.getSelectedItem()) == Integer.parseInt((String) endHoursComboBox.getSelectedItem()) 
						&& Integer.parseInt((String) startMinsComboBox.getSelectedItem()) >= Integer.parseInt((String) endMinsComboBox.getSelectedItem())) {
				} else {
					allEvents.add(new Event(dateClicked,startHoursComboBox.getSelectedIndex(),startMinsComboBox.getSelectedIndex(),endHoursComboBox.getSelectedIndex(),endMinsComboBox.getSelectedIndex(),notice.getText()));
					eventDates.add(dateClicked);
					startHoursComboBox.setSelectedIndex(0);
					startMinsComboBox.setSelectedIndex(0);
					endHoursComboBox.setSelectedIndex(0);
					endMinsComboBox.setSelectedIndex(0);
					notice.setText("");
					eventPanel.showEventList();
					calendarPanel.showDays();
					dispose();
				}
			}
		});
		
		/**
		 * When the user clicks the close button the dialog is closed without adding an Event
		 */
		closeAddEvent.addActionListener(l -> {
			dispose();
		});
		
		//adding the components for setting up the time to its panel
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
		
		//adding all components to the GUI of the JDialog
		add(Box.createVerticalStrut(10));
		add(timePanel);
		add(Box.createVerticalStrut(20));
		add(noticePanel);
		add(Box.createVerticalStrut(20));
		add(btnPanel);
		add(Box.createVerticalStrut(10));
		
	}
}
