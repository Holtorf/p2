package calen_do;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

/**
 * This JPanel contains the ToDoList and is shown on the right side of the JFrame.
 * The ToDoList shows what tasks the user has to do. It shows the date, a short information about the event and the priority.
 * The priority is color coded. See DataCellRenderer for this.
 * @version 1.0
 * @author Florian Völkers, Irena Becker, Peter Oetker
 *
 */
@SuppressWarnings("serial")
public class ToDoListPanel extends JPanel {
	
	private ArrayList<Entry> entriesList;
	private DefaultListModel<Entry> listModel;
	private JList<Entry> list;
	
	/**
	 * The constructor receives the entriesList with all the entries of the ToDoList as a parameter to show, edit and add other entries.
	 * @param entriesList
	 */
	public ToDoListPanel(ArrayList<Entry> entriesList){
		
		this.entriesList = entriesList;
		
		//setting up the JPanel
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(300, 500));
		setBorder(new TitledBorder("To Do List"));
		setVisible(true);
		
		//setting up the list
		listModel = new DefaultListModel<Entry>();
		list = new JList<Entry>(listModel);
		list.setForeground(Color.white);
		list.setBackground(Color.DARK_GRAY);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setCellRenderer(new DataCellRenderer());
		fillListModel(); //fills the listModel with the entries
		JScrollPane scr = new JScrollPane(list);
		add(scr, BorderLayout.CENTER);
	
		//setting up the buttons in the JPanel
		JButton add = new JButton("Add");
		JButton delete = new JButton("Remove");
		JButton edit = new JButton("Edit");
		JButton deleteAll = new JButton("Remove All");
		JPanel buttonpan = new JPanel();
		buttonpan.setLayout(new GridLayout(2, 2));
		buttonpan.add(edit);
		buttonpan.add(add);
		buttonpan.add(delete);
		buttonpan.add(deleteAll);
		add(buttonpan, BorderLayout.NORTH);
		
		/**
		 * when add is clicked a new AddDialog is opened. 
		 */
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddDialog(ToDoListPanel.this, listModel);
			}
		});

		/**
		 * when delete is clicked a new DeleteDialog is opened, false determines that only one entry is removed
		 */
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new DeleteDialog(ToDoListPanel.this, entriesList, listModel, list, false);
			}
		});
		
		/**
		 * when deleteAll is clicked a new DeleteDialog is opened, true determines that all entries are removed
		 */
		deleteAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new DeleteDialog(ToDoListPanel.this, entriesList, listModel, list, true);
			}
		});

		/**
		 * when edit is clicked a new EditDialog is opened to edit the selected entry.
		 */
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new EditDialog(ToDoListPanel.this, listModel, list);
			}
		});
	}
	
	/**
	 * Sorts the entries of the ToDoList. When the date is smaller it is shown at the top.
	 * If the date is the same the higher the priority is, the higher entry is shown in the JList
	 */
	public void sortEntryList() {		
		entriesList.clear();
		for (int i = 0; i < listModel.getSize(); i++) {
			entriesList.add(listModel.getElementAt(i));
		}
		listModel.clear();

		Collections.sort(entriesList, (o1, o2) -> {
			if (((((Entry) o1).getDate()).getTime() < (((Entry) o2).getDate()).getTime())
					|| ((((Entry) o1).getDate() == ((Entry) o2).getDate())
							&& (((Entry) o1).getPriority() > ((Entry) o2).getPriority()))) {
				return -1;
			} else {
				return 1;
			}
		});

		fillListModel();
	}
	
	/**
	 * fills the listModel with all the entries from the entriesList
	 */
	protected void fillListModel() {
		for (int i = 0; i < this.entriesList.size(); i++) {
			listModel.add(i, this.entriesList.get(i));
		}		
	}	
}
