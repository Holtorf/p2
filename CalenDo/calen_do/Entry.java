package calen_do;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Entry class is a blueprint for entries in the ToDoList.
 * Every entry object has an enddate, a note and a priority.
 * Other classes can access the member of Entry by using the getter and setter methods
 * It implements Serializable, because otherwise there were problems loading the ArrayList with Array-Objects
 * It implements Comparable to be able to compare the entries with each other
 * @version 1.0
 * @author Florian Völkers, Irena Becker, Peter Oetker
 *
 */
@SuppressWarnings("serial")
public class Entry implements Comparable<Object>, Serializable {
	
	private Date enddate;
	private String note;
	private int priority;

	/**
	 * The constructor receives enddate, note and priority as parameters from the users input from the AddDialog
	 * and saves them as members.
	 * @param enddate
	 * @param note
	 * @param priority
	 */
	public Entry(Date enddate, String note, int priority) {
		this.enddate = enddate;
		this.note = note;
		this.priority = priority;
	}

	/**
	 * returns the enddate of an entry
	 * @return
	 */
	public Date getDate() {
		return enddate;
	}

	/**
	 * returns the note of an entry with information about the entry
	 * @return
	 */
	public String getNote() {
		return note;
	}

	/**
	 * returns the priority of the entry (low, middle, high)
	 * @return
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * changes the date of the entry according to newDate
	 * @param newDate
	 */
	public void setDate(Date newDate) {
		enddate = newDate;
	}

	/**
	 * changes the note in the entry according to newNote
	 * @param newNote
	 */
	public void setNote(String newNote) {
		note = newNote;
	}

	/**
	 * changes the priority of the entry according to newPriority
	 * @param newPriority
	 */
	public void setPriority(int newPriority) {
		priority = newPriority;
	}

	/**
	 * overrides the toString method from Object
	 * returns the date and the note of the entry
	 */
	@Override
	public String toString() {
		SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy");
		return (ft.format(enddate) + ":  " + note);
	}

	/**
	 * overrides the method compareTo to compare objects of entry
	 */
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
