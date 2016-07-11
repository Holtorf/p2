package calen_do;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

/**
 * This class sets up one of the three main JPanels in the JFrame of Calen-Do.
 * In this class the JPanel in the middle is set up.
 * It contains all necessary elements of a calendar, which are displayed by several custom designed images.
 * It also contains a JTextField to change the month and year in the calendar.
 * When you click on a day, the eventPanel to the left of the calendarPanel updates and shows all of the events of that day.
 * You can also use the arrows to navigate through the months one by one. 
 * The current day is always displayed by a blue-ish button while days with events are displayed by a red-ish button. 
 * Days without events are displayed with grey-ish buttons. 
 * @version 1.0
 * @author Florian Völkers, Irena Becker, Peter Oetker
 *
 */
@SuppressWarnings("serial")
public class CalendarPanel extends JPanel {
	
	//42 buttons because a month could possibly be stretched over 6 weeks
	private JButton[] days = new JButton[42];	
	
	//Images needed to display the Icons when hovering over a button
	private Image transparentButtonIcon;
	private Image transparentButtonEventIcon;
	private Image transparentButtonTodayIcon;
	private Image transparentButtonTodayEventIcon;
	private Image transparentButtonIconMouseOver;
	private Image transparentButtonEventIconMouseOver;
	private Image transparentButtonTodayIconMouseOver;
	private Image transparentButtonTodayEventIconMouseOver;
	
	//The current and clicked date
	private LocalDate dateClicked;
	private LocalDate dateToday = LocalDate.now();
	
	//The current month and year
	private int currentMonth = dateToday.getMonthValue()-1;
	private int currentYear = dateToday.getYear();
	
	//A list contain all the dates of events.
	private List<LocalDate> eventDates;
	
	/**
	 * The constructor needs several Objects to communicate with the CalenDoView and an object of the class Transparent to change the images to transparent images
	 * @param eventDates
	 * @param transparent
	 * @param dateClicked
	 * @param view
	 */
	public CalendarPanel(List<LocalDate> eventDates, Transparent transparent, LocalDate dateClicked, CalenDoView view){
		final byte MONTHS_IN_YEAR = 12;
		
		//saving some of the parameters given by the constructor in member variables
		this.dateClicked = dateClicked;
		this.eventDates = eventDates;
		
		//setting up the JPanel
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(600, 500));
		setBorder(new TitledBorder("Calendar"));
		
		//initializing a JTextField for every month in the year, but only one is visible at the time.
		JTextField[] monthYear = new JTextField[MONTHS_IN_YEAR];
		for(int i = 0; i < MONTHS_IN_YEAR;i++) {
			monthYear[i] = new JTextField(LocalDate.of(currentYear,(i+1),1).getMonth() + " " + String.valueOf(currentYear));
			monthYear[i].setAlignmentY(TOP_ALIGNMENT);
			monthYear[i].setAlignmentX(CENTER_ALIGNMENT);
			monthYear[i].setFont(new Font("Arial", Font.BOLD, 32));
			monthYear[i].setHorizontalAlignment(SwingConstants.CENTER);
			monthYear[i].setVisible(false);
			monthYear[i].setOpaque(false);
			monthYear[i].setBorder(BorderFactory.createEmptyBorder());
			monthYear[i].setEditable(false);
			monthYear[i].setSelectionColor(Color.LIGHT_GRAY);
			monthYear[i].setSelectedTextColor(Color.DARK_GRAY);
			int index = i;
			/**
			 * when the user clicks on the JTextArea it becomes editable
			 */
			monthYear[i].addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
				}
				
				@Override
				public void mouseExited(MouseEvent e) {					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {				
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					monthYear[index].setEditable(true);
					monthYear[index].selectAll();
				}
			});
			/**
			 * when the user presses enter with the JTextField focused he can change the month
			 * but only if he made a valid input
			 */
			monthYear[i].addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					boolean validMonth = false;
					boolean validYear = true; 
					monthYear[index].setVisible(false);
					monthYear[index].select(0, 0);
					monthYear[index].setEditable(false);
					String [] input = monthYear[index].getText().split(" ");
					//tests if what the user put in is actually the name of a month
					for (int i = 0; i < MONTHS_IN_YEAR; i++){
						if (input[0].equalsIgnoreCase(LocalDate.of(currentYear, i+1, 1).getMonth().toString())){
							validMonth = true;
							currentMonth = i;
						}
					}
					//tests if the user put in more than one "word"
					if (input.length > 1){
						//tests if the user put in only numbers
						for (int i = 0; i < input[1].length(); i++){
							if (!(input[1].charAt(i) >= 48 && input[1].charAt(i) <= 57)){
								validYear = false;
							}
						}
					}
					//only if the user put in a valid month and a valid year and nothing else the year is changed
					if (validMonth && validYear && input.length == 2){
						currentYear = Integer.parseInt(input[1]);	
					}
					//if the input wasn't correct the currentMonth is reverted to its initial state
					else {
						currentMonth = index;
					}
					monthYear[currentMonth].setVisible(true);
					for(int i = 0; i < MONTHS_IN_YEAR; i++) {
						monthYear[i].setText(LocalDate.of(currentYear,(i+1),1).getMonth() + " " + String.valueOf(currentYear));
					}
					//by calling the method showDays() the days are updated, is needed when things change
					showDays();
				}
			});
		}
		
		//A panel with the left and right JButtons and the JTextArea
		JPanel monthAndYear = new JPanel();
		JButton previousMonth = new JButton();
		Image previousBtn;
		Image previousBtnMouseOver;
		Image transparentPreviousBtnMouseOver;
		//assigning two images to the button to switch to the previous month
		try {
			previousBtn = ImageIO.read(getClass().getResource("/resources/Button1_Links.png")).getScaledInstance( 50, 50,  java.awt.Image.SCALE_SMOOTH);
			previousMonth.setIcon(new ImageIcon(previousBtn));
			previousBtnMouseOver = ImageIO.read(getClass().getResource("/resources/Button1_Links_Gedrückt.png")).getScaledInstance( 50, 50,  java.awt.Image.SCALE_SMOOTH);
			transparentPreviousBtnMouseOver = transparent.makeTransparent(transparent.toBufferedImage(previousBtnMouseOver), new Color(0,0,0));
			previousMonth.setRolloverIcon(new ImageIcon(transparentPreviousBtnMouseOver));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		previousMonth.setFocusable(false);
		previousMonth.setOpaque(false);
		previousMonth.setContentAreaFilled(false);
		previousMonth.setBorderPainted(false);
		/*
		 * actionListener for the button, when clicked it change the currentMonth
		 */
		previousMonth.addActionListener(l -> {
			currentMonth--;
			monthYear[currentMonth+1].setVisible(false);
			
			if(currentMonth == -1) {
				currentMonth = 11;
				currentYear--;
				for(int i = 0; i < MONTHS_IN_YEAR; i++) {
					monthYear[i].setText(LocalDate.of(currentYear,(i+1),1).getMonth() + " " + String.valueOf(currentYear));
				}
			}
			monthYear[currentMonth].setVisible(true);
			//to update the days in the calendarPanel
			showDays();
		});
		monthAndYear.add(previousMonth);
		//making the chosen month visible
		for(int j = 0; j < MONTHS_IN_YEAR;j++) {
			if(LocalDate.of(currentYear, currentMonth+1, 1).getMonthValue()-1 == j) {
				monthYear[j].setVisible(true);
			}
			monthAndYear.add(monthYear[j]);
		}
		
		JButton nextMonth = new JButton();
		Image nextBtn;
		Image nextBtnMouseOver;
		Image transparentNextBtnMouseOver;
		//assigning two images to the button to switch to the next month
		try {
			nextBtn = ImageIO.read(getClass().getResource("/resources/Button1_Rechts.png")).getScaledInstance( 50, 50,  java.awt.Image.SCALE_SMOOTH);
			nextMonth.setIcon(new ImageIcon(nextBtn));
			nextBtnMouseOver = ImageIO.read(getClass().getResource("/resources/Button1_Rechts_Gedrückt.png")).getScaledInstance( 50, 50,  java.awt.Image.SCALE_SMOOTH);
			transparentNextBtnMouseOver = transparent.makeTransparent(transparent.toBufferedImage(nextBtnMouseOver), new Color(0,0,0));
			nextMonth.setRolloverIcon(new ImageIcon(transparentNextBtnMouseOver));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		nextMonth.setFocusable(false);
		nextMonth.setOpaque(false);
		nextMonth.setContentAreaFilled(false);
		nextMonth.setBorderPainted(false);
		/*
		 * actionListener for the nextMonth button. When it is clicked it changes the currentMonth
		 */
		nextMonth.addActionListener(l -> {
			currentMonth++;
			monthYear[currentMonth-1].setVisible(false);
			
			if(currentMonth == MONTHS_IN_YEAR) {
				currentMonth = 0;
				currentYear++;
				for(int i = 0; i < MONTHS_IN_YEAR; i++) {
					monthYear[i].setText(LocalDate.of(currentYear,(i+1),1).getMonth() + " " + String.valueOf(currentYear));
				}
			}
			monthYear[currentMonth].setVisible(true);
	
			showDays();
		});
		monthAndYear.add(nextMonth);
		
		//JPanel for the days of the week
		JPanel week = new JPanel(new GridLayout(1,7));
		final String [] dayStrings = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
		JLabel [] weekdays = new JLabel[7];
		//a new JLabel for every day of the week
		for (int i = 0; i < weekdays.length; i++){
			weekdays[i] = new JLabel(dayStrings[i], JLabel.CENTER);
			weekdays[i].setFont(new Font("Arial", Font.BOLD, 20));
			week.add(weekdays[i]);
		}
		
		//JPanel for the days of a month in 6 rows, 7 cols GridLayout
		JPanel daysPanel = new JPanel(new GridLayout(6, 7));
		for(int i = 0; i < days.length; i++) {
			days[i] = new JButton();
			days[i].setVisible(false);
			days[i].setContentAreaFilled(false);
			days[i].setFocusable(false);
			daysPanel.add(days[i]);
			int cacheI = i;
			/*
			 * actionListener for every button representing a day. When it is clicked the eventPanel is refreshed and the user can see the events of the current day
			 */
			days[i].addActionListener(l -> {
				this.dateClicked = LocalDate.of(currentYear,currentMonth+1,Integer.parseInt(days[cacheI].getText()));
				//show events on this day in event panel
				view.getEventPanel().refreshEventList(this.dateClicked);
			});
		}
		
		add(monthAndYear, BorderLayout.NORTH);
		add(week, BorderLayout.CENTER);
		add(daysPanel, BorderLayout.SOUTH);
		
		//assigning the images to the buttons
		try {
			Image buttonIcon = ImageIO.read(getClass().getResource("/resources/button.png")).getScaledInstance( 50, 50,  java.awt.Image.SCALE_SMOOTH);
			transparentButtonIcon = transparent.makeTransparent(transparent.toBufferedImage(buttonIcon), new Color(0,0,0));
			Image buttonEventIcon = ImageIO.read(getClass().getResource("/resources/button3.png")).getScaledInstance( 50, 50,  java.awt.Image.SCALE_SMOOTH);
			transparentButtonEventIcon = transparent.makeTransparent(transparent.toBufferedImage(buttonEventIcon), new Color(0,0,0));
			Image buttonTodayIcon = ImageIO.read(getClass().getResource("/resources/button5.png")).getScaledInstance( 50, 50,  java.awt.Image.SCALE_SMOOTH);
			transparentButtonTodayIcon = transparent.makeTransparent(transparent.toBufferedImage(buttonTodayIcon), new Color(0,0,0));
			Image buttonTodayEventIcon = ImageIO.read(getClass().getResource("/resources/button7.png")).getScaledInstance( 50, 50,  java.awt.Image.SCALE_SMOOTH);
			transparentButtonTodayEventIcon = transparent.makeTransparent(transparent.toBufferedImage(buttonTodayEventIcon), new Color(0,0,0));
			Image buttonIconMouseOver = ImageIO.read(getClass().getResource("/resources/button2.png")).getScaledInstance( 50, 50,  java.awt.Image.SCALE_SMOOTH);
			transparentButtonIconMouseOver = transparent.makeTransparent(transparent.toBufferedImage(buttonIconMouseOver), new Color(0,0,0));
			Image buttonEventIconMouseOver = ImageIO.read(getClass().getResource("/resources/button4.png")).getScaledInstance( 50, 50,  java.awt.Image.SCALE_SMOOTH);
			transparentButtonEventIconMouseOver = transparent.makeTransparent(transparent.toBufferedImage(buttonEventIconMouseOver), new Color(0,0,0));
			Image buttonTodayIconMouseOver = ImageIO.read(getClass().getResource("/resources/button6.png")).getScaledInstance( 50, 50,  java.awt.Image.SCALE_SMOOTH);
			transparentButtonTodayIconMouseOver = transparent.makeTransparent(transparent.toBufferedImage(buttonTodayIconMouseOver), new Color(0,0,0));
			Image buttonTodayEventIconMouseOver = ImageIO.read(getClass().getResource("/resources/button8.png")).getScaledInstance( 50, 50,  java.awt.Image.SCALE_SMOOTH);
			transparentButtonTodayEventIconMouseOver = transparent.makeTransparent(transparent.toBufferedImage(buttonTodayEventIconMouseOver), new Color(0,0,0));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//is called to make the days visible
		showDays();
	}
	
	/**
	 * Alle days Buttons erstmal auf false gesetzt und dann mittels difference und maxMonthDay die days sichtbar machen
	 * die diesen Monat auch sichtbar sein sollen. Mittels if die richtigen icons setzen.
	 */
	protected void showDays() {
		for(int i = 0; i<days.length; i++) {
			days[i].setVisible(false);
		}
		int difference = LocalDate.of(currentYear,currentMonth+1,1).getDayOfWeek().getValue()-1;
		int maxMonthDay = LocalDate.of(currentYear,currentMonth+1,1).lengthOfMonth();
		for(int daysNumber = difference; daysNumber<maxMonthDay+difference; daysNumber++) {
			if(eventDates.contains(LocalDate.of(currentYear, currentMonth+1, daysNumber+1-difference)) 
					&& !LocalDate.of(currentYear, currentMonth+1, daysNumber+1-difference).equals(dateToday)) {
				days[daysNumber].setIcon(new ImageIcon(transparentButtonEventIcon));
				days[daysNumber].setRolloverIcon(new ImageIcon(transparentButtonEventIconMouseOver));
			} else if (!eventDates.contains(LocalDate.of(currentYear, currentMonth+1, daysNumber+1-difference)) 
					&& LocalDate.of(currentYear, currentMonth+1, daysNumber+1-difference).equals(dateToday)) {
				days[daysNumber].setIcon(new ImageIcon(transparentButtonTodayIcon));
				days[daysNumber].setRolloverIcon(new ImageIcon(transparentButtonTodayIconMouseOver));
			} else if (eventDates.contains(LocalDate.of(currentYear, currentMonth+1, daysNumber+1-difference)) 
					&& LocalDate.of(currentYear, currentMonth+1, daysNumber+1-difference).equals(dateToday)) {
				days[daysNumber].setIcon(new ImageIcon(transparentButtonTodayEventIcon));
				days[daysNumber].setRolloverIcon(new ImageIcon(transparentButtonTodayEventIconMouseOver));
			} else {
				days[daysNumber].setIcon(new ImageIcon(transparentButtonIcon));
				days[daysNumber].setRolloverIcon(new ImageIcon(transparentButtonIconMouseOver));
			}
			days[daysNumber].setText(String.valueOf(daysNumber+1-difference));
			days[daysNumber].setHorizontalTextPosition(JButton.CENTER);
			days[daysNumber].setVerticalTextPosition(JButton.CENTER);
			days[daysNumber].setBorderPainted(false);
			days[daysNumber].setVisible(true);
		}
	}
}
