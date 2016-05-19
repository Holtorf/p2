package progressbar;

import javax.swing.SwingUtilities;

public class Controller {

	private PrimeThread prime;
	private View view;
	public Controller() {

		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				view = new View(Controller.this);
				
			}
		});
		
	}
	
	public void pruefeNummer(Long number) {
		prime = new PrimeThread(number, this);
		Thread t = new Thread(prime);
		
		t.start();
		System.out.println("Thread gestartet " + number );
		
	}

	public void cancelOperation() {
		prime.cancel();
		
	}

	
	public static void main(String[] args) {
		new Controller();
	}

	public void setPrime(boolean isPrime) {
		view.setPrime(isPrime);
		
	}

	public void setPercent(int percent) {
		view.setProgress(percent);
		
	}
}
