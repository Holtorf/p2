package buchungssimulation;

import java.util.Random;

public class Bucher implements Runnable {
	
	private Sitze sitze;
	
	public Bucher(Sitze sitze) {
		this.sitze = sitze;
	}
	
	@Override
	public void run() {
		Random rand = new Random();
		
		try {

			while (sitze.getAnzahlFreieSitze() > 0) {

				Thread.sleep(rand.nextInt(1000));
				
				
				sitze.reservieren(rand.nextInt(sitze.getAnzahlSitze()));
			}
			
		} catch (InterruptedException e) {
			// TODO: handle exception
		}
		
		System.out.println("Thread stirbt");
	}
	
	

}
