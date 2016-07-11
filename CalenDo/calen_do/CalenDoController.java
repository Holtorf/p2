package calen_do;

import javax.swing.SwingUtilities;

/**
 * The Controller instantiates a new CalenDoView Object. So that the view can load and save
 * entries and events objects of LoadSaveCalendar and LoadSaveEntries are instantiated with it.
 * @version 1.0
 * @author Florian Völkers, Irena Becker, Peter Oetker
 *
 */
public class CalenDoController {
	
	/**
	 * by calling the SwingUtilities method invokeLater() the CalenDoView object is instantiated.
	 * It contains the JFrame with three panels. The heart of the program.
	 */
	public CalenDoController(){
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new CalenDoView(new LoadSaveCalendar(), new LoadSaveEntries());				
			}
		});
	}
}
