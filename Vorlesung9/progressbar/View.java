package progressbar;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

public class View extends JFrame{

	private JTextField zahl;
	private JButton prfBtn;
	private JButton cnzlBtn;
	private JProgressBar progress;
	private JLabel status;
	private Controller cntl;
	private static final String NEUE_ZAHL = "Bitte geben Sie eine Zahl ein:";
	
	public View(Controller cntl) {
		this.cntl = cntl;
		
		status = new JLabel(NEUE_ZAHL);
		zahl = new JTextField();
		prfBtn = new JButton("prüfen");
		cnzlBtn = new JButton("Canzel");
		progress = new JProgressBar();
		
		setLayout(new GridLayout(5, 1));
		
		add (status);
		add (zahl);
		add (prfBtn);
		add (cnzlBtn);
		add (progress);
		
		
		prfBtn.addActionListener(listener -> {
			try {
				Long number = Long.valueOf(zahl.getText());

				status.setText("prüfen");
				cntl.pruefeNummer (number);
				prfBtn.setEnabled(false);
				
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Falsche Eingabe, bitte eine Zahl eingeben", "Number Format Exception", JOptionPane.WARNING_MESSAGE);
			}
			
		});
		
		cnzlBtn.addActionListener(listener -> {
			cntl.cancelOperation ();
			prfBtn.setEnabled(true);
			status.setText(NEUE_ZAHL);
			progress.setValue(0);
			progress.setString("Canceled");
		});
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	public void setProgress (int progressValue) {
		progress.setValue(progressValue);
		progress.setString("" + progressValue + " %");
		progress.setStringPainted(true);
	}
	
	public void setPrime (boolean isPrime) {
		prfBtn.setEnabled(true);
		status.setText("" + zahl.getText() + " ist Prime? " + isPrime);
	}
	
}
