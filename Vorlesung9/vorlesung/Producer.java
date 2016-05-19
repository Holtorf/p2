package vorlesung;

import java.util.ArrayList;

public class Producer implements Runnable{

	private ArrayList<Integer> list;

	public Producer(ArrayList<Integer> list) {
		this.list = list;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < 1000; i++){
			synchronized (list) {
				while (!list.isEmpty()){
					try {
						list.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				list.add(i);
				list.notify();
				
			}
		}
	}

}
