package progressbar;

public class PrimeThread implements Runnable {

	private long number;
	private boolean cancel;
	private boolean isPrime;
	private Controller cntr;
	
	public PrimeThread(long number, Controller cntr) {
		this.cntr = cntr;
		this.number = number;
		this.isPrime = true;
		this.cancel = false;
		System.out.println("primethread konstruktor");
	}
	
	@Override
	public void run() {
					
		System.out.println("in run");
		int percent = 0;
		for(long divisor = 2; divisor < number; divisor ++) {
		
			if (!cancel) {
				if ((percent) <= (((divisor * 100) / number))) {
					percent = (int) ((divisor * 100) / number) + 1;
					cntr.setPercent (percent);
				}
			
				if (number % divisor == 0) {
					isPrime = false;
					System.out.println("keine PRIMZAHL, " + divisor);
					break;
				}
			}			
		}

		if (!cancel)
			cntr.setPrime(isPrime);
	}
	

	public boolean isPrime() {
		return isPrime;
	}
	
	public void cancel () {
		cancel = true;
	}
}
