package finalBsp;

import java.awt.Toolkit;

import javax.swing.JFrame;

public class FinalBsp extends JFrame{

	private final int HEIGHT;
	private final int WIDTH;

	public FinalBsp() {

		HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
		WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		setVisible(true);

	}
	
	public static void main(String[] args) {
		new FinalBsp();
	}
}
