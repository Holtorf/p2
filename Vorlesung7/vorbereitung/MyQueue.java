package vorbereitung;

import java.util.LinkedList;

public class MyQueue {

	LinkedList<String> sportList = new LinkedList<>();
	public MyQueue() {
		String [] sportArray = {"Basketball", "Fussball", "Hockey", "Kampfsport", "Laufen", "Schwimmen", "Volleyball", "Yoga"};

// 		Alternative 1
//		for (int i = 0; i < sportArray.length; i++) {
//			sportList.add(sportArray[i]);
//		}

		for (int i = 0; i < sportArray.length; i++) {
			sportList.push(sportArray[i]);
		}

	}
	
	public void printList () {
// 		Alternative 1
//		for (int i = 0; i < sportList.size(); i++) {
//			System.out.println(i + ". " + sportList.get(i));
//		}
		
// 		Alternative 2
//		while (sportList.peekFirst() != null) {
//			System.out.println(sportList.pollFirst());
//		}
		
// 		Alternative 3
		while (sportList.peekLast() != null) {
			System.out.println(sportList.pollLast());
		}
	}

	
	public static void main(String[] args) {
		MyQueue queue = new MyQueue();
		queue.printList();
	}

}
