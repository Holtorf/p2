package vorlesung;

import java.util.ArrayList;

public class Consumer implements Runnable{

	private ArrayList<Integer> list;
	
	public Consumer(ArrayList<Integer> list) {
		this.list = list;
	}
	
	@Override
	public void run() {
		while (true) {
			synchronized (list) {
				while (list.size() == 0) {
					try {
						list.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}				
				System.out.println(list.remove(0));
				list.notify();
			}			
		}
		
	}
}
