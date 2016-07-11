package RectangleMan;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;

/**
 * @author Sina, Bager, Walid
 * 	
 * Beschreibung dient dazu dem Spieler die in einer JList die Spielelemente zu schildern
 */
public class Beschreibung extends JDialog{
	
	private static final long serialVersionUID = 1L;

	//Array List für JList
	private String[] liste = {"Spiel", "Spieler", "Gegner", "Credits"};
	
    /**
     * 
     */
	public Beschreibung() 
	{
		// GUI Layout mit BoxLayout und FlowLayout
		setLayout(new FlowLayout());
		Box left = Box.createVerticalBox();
		
		//JLabel
		left.add(new JLabel("[Klicke ein Element auf der Liste]"));
		
		//Jlist mit String Array "liste"
		JList <String> list = new JList<String>(liste); 
		JScrollPane scroll = new JScrollPane(list);
		scroll.setPreferredSize(new Dimension(50,200));
		left.add(scroll);
		
		// center als BoxLayout für die Beschreibung der Punkte in der JList
		Box center = Box.createVerticalBox();
		
		//JLabel
		center.add(new JLabel("[Beschreibung]"));
		
		// JTextArea soll uneditable sein und wird geaddet
		JTextArea txtArea = new JTextArea(12,40);
		txtArea.setEditable(false);
		txtArea.setLineWrap(true);
		center.add(txtArea);
		
		// add die Boxpanles
		add(left);
		add(center);

		//Jlist erhält einen SelectionListener damit der User eins per Mausklick auswählen kann. 
		list.addListSelectionListener(
			new ListSelectionListener()
			{
				// Index des Arrays wird festgestellten und die jeweilige If Anweisung mit Text wird ausgeführt
				@Override
				public void valueChanged(ListSelectionEvent e)
				{
					if(list.getSelectedIndex() == 0)
					{
						txtArea.setText("Versuche Gegner zu beschießen, bevor sie die rote Linie erreichen. Je nach "
										+ "\nPunktestand, erhöht sich die Schwierigkeit.");
					}
					else if(list.getSelectedIndex() == 1)
					{
						txtArea.setText("Du repräsentierst den roten 'Rectangle Man', welcher die Mission "
										+ "\nhat die Welt vor bösen Rectanglen zu retten. Bevor.. "
										+ "die Welt selbst böse wird?? Bewege dich mit 'W' und 'E' und um zu schießen drücke 'Space'");
					}
					else if(list.getSelectedIndex() == 2)
					{
						txtArea.setText("Deine Gegner sind böse... Und ich will jetzt nicht anstiften .. "
										+ "\naber der eine hat voll deine Mutter beleidigt. ");
					}
					else if(list.getSelectedIndex() == 3)
					{
						txtArea.setText("Dieses Spiel wurde im Auftrag der HAW erstellt. Autoren: Sina, Bager");
					}
				}
			});
	
		// Backgroundfarbe auf getContentPane() weil das der richtige Hintergrund ist
		getContentPane().setBackground(new Color(0,175,255));
		
		// Gui settings
		setTitle("Beschreibung");
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(700,300);
		setLocation(500, 250);
		setResizable(false);
	}
	
}