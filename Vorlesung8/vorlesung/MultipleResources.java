package vorlesung;

import java.util.concurrent.locks.ReentrantLock;

public class MultipleResources {

	
	
	public static void main(String[] args) {
		
		ReentrantLock lock1 = new ReentrantLock();
		ReentrantLock lock2 = new ReentrantLock();
		
		Thread t1 = new Thread(new Thread1(lock1, lock2));
		Thread t2 = new Thread(new Thread2(lock1, lock2));
		
		t1.start();
		t2.start();
		
	}
}
