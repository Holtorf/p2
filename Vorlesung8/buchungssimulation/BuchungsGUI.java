package buchungssimulation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;


public class BuchungsGUI extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Sitze sitze;
	private HashMap<Integer, JLabel> sitzmap;
	
	public BuchungsGUI(Controller ctrl, Sitze sitze) {
		this.sitze = sitze;
		this.sitzmap = new HashMap<>();
		JButton simulieren = new JButton("Simulation starten");
		
		sitze.setBuchungsGUI(this);
		
		
		initUI();
		
		add (simulieren, BorderLayout.SOUTH);
		
		simulieren.addActionListener(listener -> {
			ctrl.startSimulation();
			simulieren.setEnabled(false);
		});
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(4 * 50, (sitze.getAnzahlSitze()/4) * 50);
		setVisible(true);
	}

	
	public void initUI() {
		JPanel sitzPanel = new JPanel();
		add (sitzPanel, BorderLayout.CENTER);

		sitzPanel.setLayout(new GridLayout((int)Math.round(sitze.getAnzahlSitze() / 4.0), 4));
		for (int i = 0; i < sitze.getAnzahlSitze(); i++) {
			JLabel label = new JLabel("");
			label.setOpaque(true);
			label.setBackground(Color.GREEN);
			label.setBorder(LineBorder.createGrayLineBorder());
			sitzmap.put(i, label);
			sitzPanel.add(label);
		}
		
		
	}
	
	public void reservieren(int number) {
		JLabel label = sitzmap.get(number);
		label.setOpaque(true);
		label.setBackground(Color.RED);
	}

}
