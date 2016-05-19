package buchungssimulation;

import javax.swing.SwingUtilities;

public class Controller {

	private Thread[] threads;
	
	public Controller(int anzahl) {
		Sitze sitze = new Sitze(anzahl);

		threads = new Thread[4];

		// erstelle alle Bucherthreads
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(new Bucher(sitze));
		}

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new BuchungsGUI(Controller.this, sitze);
			}
		});

	}

	public boolean startSimulation() {
		for (int i = 0; i < threads.length; i++) {
			threads[i].start();
		}
		System.out.println("Starte Threads");
		return true;
	}
}
