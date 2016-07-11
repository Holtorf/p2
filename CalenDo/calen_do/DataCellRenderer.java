package calen_do;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * In this class the look of the JList in the ToDoListPanel is set. 
 * Depending on the priority every Cell has a different background color. 
 * @version 1.0
 * @author Florian Völkers, Irena Becker, Peter Oetker
 *
 */
@SuppressWarnings("serial")
public class DataCellRenderer extends JLabel implements ListCellRenderer<Entry> {

	/**
	 * the constructor sets up some attributes of the cells
	 */
	public DataCellRenderer() {
		setOpaque(true);
		setFont(new Font("Arial", Font.PLAIN, 16));
		setFocusable(true);
	}

	/**
	 * this method is callend when instantiating a new DataCellRenderer Object
	 * depending on the priority it gives the cell a different background color
	 * depending on whether it is selected or not it gives the cell a border
	 */
	@Override
	public Component getListCellRendererComponent(JList<? extends Entry> list, Entry value, int index,
			boolean isSelected, boolean cellHasFocus) {

		setText(value.toString());
		
		//gives the cell a border to see when it is selected
		if (isSelected){
			setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}
		else {
			setBorder(BorderFactory.createEmptyBorder());
		}

		//changes the background color of the cell depending on the priority
		if (value.getPriority() == 1) {
			setBackground(new Color(159, 39, 39));
		} else if (value.getPriority() == 2) {
			setBackground(new Color(255, 209, 58));
		} else {
			setBackground(new Color(0, 105, 182));
		}

		return this;

	}
}