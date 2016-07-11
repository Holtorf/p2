package calen_do;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

/**
 * With this JDialog Events are removed.
 * Instantly the eventPanel and the calendarPanel, where the events are displayed, are updated.
 * @version 1.0
 * @author Florian Völkers, Irena Becker, Peter Oetker
 *
 */
@SuppressWarnings("serial")
public class DeleteEvent extends JDialog {

	/**
	 * The constructor receives several parameters. The lists to remove events from them, the calendarPanel and eventPanel to update it's GUI
	 * and the boolen deleteAll to
	 * @param deleteAll
	 * @param eventPanel
	 * @param eventDates
	 * @param allEvents
	 * @param calendarPanel
	 * @param eventJList
	 */
	public DeleteEvent(boolean deleteAll, EventPanel eventPanel, List<LocalDate> eventDates, List<Event> allEvents, CalendarPanel calendarPanel, JList<Event> eventJList) {
		
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		JPanel buttonPanel = new JPanel();
		JPanel instrPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		JLabel instruction = new JLabel("Do you want to remove this event?");
		if (deleteAll) {
			instruction.setText("Do you want to remove all events?");
		}
		JButton ok = new JButton("Ok");
		JButton cancel = new JButton("Cancel");

		instrPanel.add(instruction);
		buttonPanel.add(ok);
		buttonPanel.add(cancel);

		add(instrPanel);
		add(buttonPanel);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(eventPanel);
		setVisible(true);
		pack();

		/**
		 * when the ok button is clicked the event is removed from the eventDates and the allEvents list.
		 * to update the eventPanel showEventList() is called and to update the calendarPanel showDays() is called
		 * in the end the dialog is closed
		 */
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (deleteAll){
					eventJList.setSelectionInterval(0, eventJList.getModel().getSize()-1);
					for(Event a : eventJList.getSelectedValuesList()) {
						eventDates.remove(a.getID());
						allEvents.remove(a);
					}
					eventPanel.showEventList();
					calendarPanel.showDays();					
				}
				else{
					eventDates.remove(eventJList.getSelectedValue().getID());
					allEvents.remove(eventJList.getSelectedValue());
					eventPanel.showEventList();
					calendarPanel.showDays();
				}
				dispose();
			}
		});

		/**
		 * when cancel is clicked the user doesn't remove anything and the dialog is closed
		 */
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
}