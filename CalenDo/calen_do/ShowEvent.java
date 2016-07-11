package calen_do;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * This JDialog gives a closer look at chosen events, lets the user edit the notice and change the time.
 * It then passes the information to the eventPanel.
 * @version 1.0
 * @author Florian Völkers, Irena Becker, Peter Oetker
 *
 */
@SuppressWarnings("serial")
public class ShowEvent extends JDialog {
	
	/**
	 * The constructor receives several parameters to show/edit information from the lists and to update what is shown in the eventPanel.
	 * @param allEvents
	 * @param selectedEvent
	 * @param eventPanel
	 * @param dateClicked
	 */
	public ShowEvent(List<Event> allEvents, Event selectedEvent, EventPanel eventPanel, LocalDate dateClicked){
		
		//setting up the JDialog
		setTitle("On " + dateClicked + " from " + selectedEvent.getStartHours() + ":" + selectedEvent.getStartMins() 
		+ " to " + selectedEvent.getEndHours() + ":" + selectedEvent.getEndMins());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setLayout(new BorderLayout());
		setLocation(eventPanel.getLocationOnScreen());
		setVisible(true);
		setSize(250, 150);
		
		//setting up the elements of the JDialog
		JTextArea eventText = new JTextArea(selectedEvent.getNotice());
		eventText.setEditable(false);
		eventText.setBackground(Color.DARK_GRAY);
		eventText.setForeground(Color.WHITE);
		eventText.setFont(new Font("Arial", Font.PLAIN, 16));
		eventText.setLineWrap(true);
		eventText.setWrapStyleWord(true);
		JButton closeShowEvent = new JButton("Close");
		closeShowEvent.setFocusable(false);
		JButton edit = new JButton("Edit");
		edit.setFocusable(false);
		JButton editTime = new JButton("Edit Time");
		editTime.setFocusable(false);
		JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
		buttonPanel.add(edit);
		buttonPanel.add(editTime);
		buttonPanel.add(closeShowEvent);	
		
		//adding the elements to the GUI of the JDialog
		add(eventText, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		
		/**
		 * when close is clicked the JDialog is closed without saving
		 */
		closeShowEvent.addActionListener(l -> {
			dispose();
		});
		
		/*
		 * when editTime is clicked a new ChangeTime Dialog opens to change the time of an event
		 */
		editTime.addActionListener(l -> {
			new ChangeTime(allEvents, selectedEvent, eventPanel, this, dateClicked);
		});
		
		/**
		 * when edit is clicked the user can change the notice of an event. He has to click on save to confirm the change.
		 */
		edit.addActionListener(l -> {
			if(edit.getText().equals("Edit")){
				edit.setText("Save");
				eventText.setEditable(true);
			}
			else{
				edit.setText("Edit");
				eventText.setEditable(false);
				for(Event a : allEvents) {
					if(a.equals(selectedEvent)) {
						a.changeNotice(eventText.getText());
					}
				}
			}
		});		
	}
}
