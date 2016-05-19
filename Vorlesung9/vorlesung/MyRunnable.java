package vorlesung;

import java.util.concurrent.SynchronousQueue;

public class MyRunnable implements Runnable {

	private String text;

	public MyRunnable(String text) {
		this.text = text;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(text);
	}

	public static void main(String[] args) {
		Thread t1 = new Thread(new MyRunnable("ich lebe auch"));
		t1.start();
		t1.interrupt();
	}

}
