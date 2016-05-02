package vorbereitung;

import java.util.HashSet;
import java.util.Iterator;

public class MySet {

	private HashSet<String> set = new HashSet<String>();
	
	public MySet() {
		String [] sportArray = {"Basketball", "Fussball", "Hockey", "Kampfsport", "Laufen", "Schwimmen", "Volleyball", "Yoga"};

		for (int i = 0; i < sportArray.length; i++) {
			set.add(sportArray[i]);
		}
		
	}
	
	
	public void printSet () {
		Iterator<String> it = set.iterator();
		int i = 1;
		while (it.hasNext()) {
			System.out.println(i + ". " + it.next());
			it.remove();
			i++;
		}
		
		System.out.println();
		
		i = 1;
		for (String sport : set) {
			System.out.println(i + ". " + sport);
			i++;			
		}
		
		
		
	}

	public static void main(String[] args) {
		MySet set = new MySet();
		set.printSet();

	}

}
