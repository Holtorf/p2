package vorbereitung;

import java.util.Vector;

public class MyVector {

	Vector<String> sportVec = new Vector<String>();

	public MyVector() {
		String [] sportArray = {"Basketball", "Fussball", "Hockey", "Kampfsport", "Laufen", "Schwimmen", "Volleyball", "Yoga"};
		
		for (int i = 0; i < sportArray.length; i++) {
			sportVec.add(sportArray[i]);
		}


	}

	public void printList () {
		for (int i = 0; i < sportVec.size(); i++) {
			System.out.println(i + ". " + sportVec.get(i));
		}
		
	}
	
	public static void main(String[] args) {
		MyVector vector = new MyVector();
		vector.printList();
		
	}

}
