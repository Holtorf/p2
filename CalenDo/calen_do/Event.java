package calen_do;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * This class is a blueprint for every event in the calendar and eventPanel.
 * It contains an ID with the date, start time, end time and a notice.
 * The members of this class are accessed by getter and setter methods
 * @version 1.0
 * @author Florian Völkers, Irena Becker, Peter Oetker
 *
 */
public class Event implements Serializable{

	private static final long serialVersionUID = 6996778651333850432L;	
	private LocalDate ID;
	private String startHours;
	private String startMins;
	private String endHours;
	private String endMins;
	private String notice;
	
	/**
	 * The constructor receives an ID, the start time, the end time and a notice.
	 * These parameters given by the addEvent Dialog are saved in the members of the class.
	 * @param ID
	 * @param startHours
	 * @param startMins
	 * @param endHours
	 * @param endMins
	 * @param notice
	 */
	public Event(LocalDate ID, int startHours, int startMins, int endHours, int endMins, String notice) {
		this.ID = ID;
		if(startHours < 10) {
			this.startHours = "0"+String.valueOf(startHours);
		} else {
			this.startHours = String.valueOf(startHours);
		}
		if(startMins < 10) {
			this.startMins = "0"+String.valueOf(startMins);
		} else {
			this.startMins = String.valueOf(startMins);
		}
		if(endHours < 10) {
			this.endHours = "0"+String.valueOf(endHours);
		} else {
			this.endHours = String.valueOf(endHours);
		}
		if(endMins < 10) {
			this.endMins = "0"+String.valueOf(endMins);
		} else {
			this.endMins = String.valueOf(endMins);
		}
		this.notice = notice;
	}

	/**
	 * returns the ID, the date, of the Event
	 * @return
	 */
	public LocalDate getID() {
		return ID;
	}

	/**
	 * returns the hour when the event starts
	 * @return
	 */
	public String getStartHours() {
		return startHours;
	}

	/**
	 * returns the minutes when the event stars
	 * @return
	 */
	public String getStartMins() {
		return startMins;
	}

	/**
	 * returns the hour when the event ends
	 * @return
	 */
	public String getEndHours() {
		return endHours;
	}

	/**
	 * returns the minutes when the event ends
	 * @return
	 */
	public String getEndMins() {
		return endMins;
	}
	
	/**
	 * returns the notice of the event
	 * @return
	 */
	public String getNotice() {
		return notice;
	}
	
	/**
	 * changes notice to the value of newNotice
	 * @param newNotice
	 */
	public void changeNotice(String newNotice) {
		this.notice = newNotice;
	}
	
	/**
	 * changes the hour when the event starts to the value of startHours
	 * @param startHours
	 */
	public void changeStartHours(int startHours) {
		if(startHours < 10) {
			this.startHours = "0"+String.valueOf(startHours);
		} else {
			this.startHours = String.valueOf(startHours);
		}
	}
	
	/**
	 * changes the minutes when the event starts to the value of startMins
	 * @param startMins
	 */
	public void changeStartMins(int startMins) {
		if(startMins < 10) {
			this.startMins = "0"+String.valueOf(startMins);
		} else {
			this.startMins = String.valueOf(startMins);
		}
	}
	
	/**
	 * changes the hour when the event ends to the value of endHours
	 * @param endHours
	 */
	public void changeEndHours(int endHours) {
		if(endHours < 10) {
			this.endHours = "0"+String.valueOf(endHours);
		} else {
			this.endHours = String.valueOf(endHours);
		}
	}
	
	/**
	 * changes the minutes when the event ends to the value of endMins
	 * @param endMins
	 */
	public void changeEndMins(int endMins) {
		if(endMins < 10) {
			this.endMins = "0"+String.valueOf(endMins);
		} else {
			this.endMins = String.valueOf(endMins);
		}
	}
	
	/**
	 * Overrides the toString method from object. Returns the start time, the end time and a notice for the event.
	 */
	@Override
	public String toString() {
		return startHours+":"+startMins+" to "+endHours+":"+endMins + ": " + notice;
	}
}
