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
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 * With this JDialog the user can edit entries in the ToDoList.
 * 
 * @version 1.0
 * @author Florian Völkers, Irena Becker, Peter Oetker
 *
 */
@SuppressWarnings("serial")
public class EditDialog extends JDialog {
	
	/**
	 * The constructor receives several parameters.
	 * The listModel and the list to change it's content after editing the entry.
	 * The ToDoListPanel to set the location of the JDialog
	 * @param todo
	 * @param listModel
	 * @param list
	 */
	public EditDialog(ToDoListPanel todo, DefaultListModel<Entry> listModel, JList<Entry> list) {

		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		//setting up the elements of the JDialog
		JLabel instruction = new JLabel("Please specify your entry:");
		JLabel enddate = new JLabel("Fill in the date for the reminder [DD.MM.YYYY]");

		SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy");
		String dat = ft.format(listModel.getElementAt(list.getSelectedIndex()).getDate());
		JTextField date = new JTextField(dat);

		JLabel text = new JLabel("Fill in the note of your entry");
		JTextField note = new JTextField(listModel.getElementAt(list.getSelectedIndex()).getNote());

		JLabel prio = new JLabel("Choose a priority for your entry");
		JRadioButton low = new JRadioButton("Low");
		JRadioButton normal = new JRadioButton("Normal");
		JRadioButton high = new JRadioButton("High");
		ButtonGroup priogroup = new ButtonGroup();
		priogroup.add(low);
		priogroup.add(normal);
		priogroup.add(high);
		int pri = listModel.getElementAt(list.getSelectedIndex()).getPriority();
		if (pri == 1) {
			high.setSelected(true);
		}
		if (pri == 2) {
			normal.setSelected(true);
		}
		if (pri == 3) {
			low.setSelected(true);
		}

		JButton save = new JButton("Save entry");
		JButton cancel = new JButton("Cancel");

		//adding the elements to the JDialog
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

		//setting up the JDialog
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(todo);
		setVisible(true);
		pack();

		/*
		 * when save is clicked the selected entry is updated
		 * also sortEntryList() is called to sort the entries and put them in an ArrayList to later save the entries.
		 * in the end the dialog is closed
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
				listModel.getElementAt(list.getSelectedIndex()).setPriority(prio);

				listModel.getElementAt(list.getSelectedIndex()).setNote(note.getText());

				SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy");
				Date dat;
				try {
					dat = ft.parse(date.getText());
					listModel.getElementAt(list.getSelectedIndex()).setDate(dat);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				todo.sortEntryList();
				dispose();
			}
		});

		/**
		 * when the cancel button is clicked the dialog is closed without saving
		 */
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

	}
}