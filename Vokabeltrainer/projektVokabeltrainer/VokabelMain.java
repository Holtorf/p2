package projektVokabeltrainer;

import javax.swing.SwingUtilities;

/**
 * Dient zum starten der GUI
 * 
 * @author Moritz Herrmann
 * @version FinalVersion 1.1
 */

public class VokabelMain {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new VokabelGui();
			}
		});

	}
}
