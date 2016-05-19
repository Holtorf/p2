package vorlesung;

import java.util.ArrayList;

public class ConsumerProducerStart {

	public static void main(String[] args) {
		ArrayList<Integer> list = new ArrayList<>();
		Thread consumer = new Thread(new Consumer(list));
		Thread producer = new Thread(new Producer(list));

		consumer.start();
		producer.start();
		
	}

}
