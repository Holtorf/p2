package calen_do;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 * sets up the GUI for a dialog to add new Entries to the ToDoList
 * The user has to specify the date, the task and the priority of the task.
 * @version 1.0
 * @author Florian Völkers, Irena Becker, Peter Oetker
 *
 */
@SuppressWarnings("serial")
public class AddDialog extends JDialog {

	/**
	 * The constructor gets the Panel of the ToDoList and the listModel of the ToDoList as parameters. 
	 * It sets up the GUI and implements actionListener for the save and cancel buttons. 
	 * @param todo: The Panel with the ToDoList
	 * @param listModel: The DefaultListModel of the ToDoList
	 */
	public AddDialog(ToDoListPanel todo, DefaultListModel<Entry> listModel) {

		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		//initializing the elements of the GUI
		JLabel instruction = new JLabel("Please specify your entry:");
		JLabel enddate = new JLabel("Fill in the date for the reminder [DD.MM.YYYY]");
		JTextField date = new JTextField(); 
		JLabel text = new JLabel("Fill in the note of your entry");
		JTextField note = new JTextField();
		JLabel prio = new JLabel("Choose a priority for your entry");
		JRadioButton low = new JRadioButton("Low");
		JRadioButton normal = new JRadioButton("Normal");
		JRadioButton high = new JRadioButton("High");
		ButtonGroup priogroup = new ButtonGroup();
		priogroup.add(low);
		priogroup.add(normal);
		priogroup.add(high);
		JButton save = new JButton("Save entry");
		JButton cancel = new JButton("Cancel");
		
		//adding the elements to the Panel
		add(instruction);
		add(enddate);
		add(date);
		add(text);
		add(note);
		add(prio);
		add(low);
		add(normal);
		add(high);
		add(save);
		add(cancel);
		
		//setting up the Panel
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocation(todo.getLocationOnScreen()); //to put directly at the ToDoListPanel
		setVisible(true);
		pack();

		/**
		 * actionListener for the save button. When it is clicked it instantiates a new Entry-Object and adds it to the listModel.
		 * It also calls the sortEntryList method of the ToDoPanel and then closes the dialog.
		 */
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int prio = 0;
				if (low.isSelected()) {
					prio = 3;
				} else if (normal.isSelected()) {
					prio = 2;
				} else {
					prio = 1;
				}

				SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy");

				try {
					Date dat = ft.parse(date.getText());
					String entry = note.getText();
					new Entry(dat, entry, prio);
					listModel.addElement(new Entry(dat, entry, prio));
					todo.sortEntryList();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				
				dispose();
			}
		});
		
		/**
		 * when the cancel button is clicked the dialog is closed
		 */
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

	}
}
