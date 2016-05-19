package vorlesung;

import java.util.concurrent.locks.ReentrantLock;

public class Thread1 implements Runnable{

	private ReentrantLock lock1, lock2;
	
	public Thread1(ReentrantLock lock1, ReentrantLock lock2) {
		this.lock1 = lock1;
		this.lock2 = lock2;
	}
	
	@Override
	public void run() {
		lock1.lock();
		
		if (lock2.tryLock()) {
			System.out.println("lock 2 ist gelockt");
			lock2.unlock();
		}
		
		lock1.unlock();
	}
	
}
