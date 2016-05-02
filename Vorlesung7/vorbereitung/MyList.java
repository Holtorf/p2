package vorbereitung;

import java.util.ArrayList;
import java.util.Collections;

public class MyList {

	private ArrayList<String> sportList = new ArrayList<String>();

	public MyList() {
//		String [] sportArray = {"Basketball", "Fussball", "Hockey", "Kampfsport", "Laufen", "Schwimmen", "Volleyball", "Yoga"};
		String [] sportArray = {"Fussball", "Hockey", "Schwimmen", "Basketball", "Volleyball", "Yoga", "Kampfsport", "Laufen"};

		for (int i = 0; i < sportArray.length; i++) {
			sportList.add(sportArray[i]);
		}
	}

	public void sortieren () {
		Collections.sort(sportList);
	}

	
	public void printList () {
		for (int i = 0; i < sportList.size(); i++) {
			System.out.print(i + ". " + sportList.get(i) + "\t");
		}				
	}
	
	
	public static void main(String[] args) {
		MyList list = new MyList();
		list.printList();
		
		list.sortieren();
		System.out.println();
		
		list.printList();
	}
}
