package calen_do;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

/**
 * This class is a JDialog which is called when the user wants to remove an Entry.
 * It makes sure the user really wants to remove the entry.
 * If he confirms the entry is removed from the listModel and the entriesList.
 * This dialog is also called when the user wants to remove all entries. 
 * @version 1.0
 * @author Florian Völkers, Irena Becker, Peter Oetker
 *
 */
@SuppressWarnings("serial")
public class DeleteDialog extends JDialog {

	/**
	 * The constructor receives several parameters to remove entries from them.
	 * @param todo
	 * @param entriesList
	 * @param listModel
	 * @param list
	 * @param deleteAll
	 */
	public DeleteDialog(ToDoListPanel todo, ArrayList<Entry> entriesList, DefaultListModel<Entry> listModel, JList<Entry> list, boolean deleteAll) {
		
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		//setting up the elements of the JDialog
		JPanel buttonPanel = new JPanel();
		JPanel instrPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		JLabel instruction = new JLabel("Do you want to remove this entry?");
		if (deleteAll) {
			instruction.setText("Do you want to remove all entries?");
		}
		JButton ok = new JButton("Ok");
		JButton cancel = new JButton("Cancel");

		//adding the elements to the JDialog
		instrPanel.add(instruction);
		buttonPanel.add(ok);
		buttonPanel.add(cancel);

		add(instrPanel);
		add(buttonPanel);

		//setting up the JDialog
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(todo);
		setVisible(true);
		pack();

		int selectedIndex = list.getSelectedIndex();
		/**
		 * when the Ok button is clicked it removes either all or just one entry from the listModel and the entriesList
		 * it also closes the dialog afterwards
		 */
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (deleteAll){
					listModel.removeAllElements();
					entriesList.clear();
					
				}
				else{
					listModel.remove(selectedIndex);
					entriesList.remove(selectedIndex);
				}
				dispose();
			}
		});

		/**
		 * when the cancel button is pressed, nothing is removed and the dialog is closed
		 */
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
}
