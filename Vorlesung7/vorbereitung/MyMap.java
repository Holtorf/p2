package vorbereitung;

import java.util.HashMap;
import java.util.Set;

public class MyMap {

	private HashMap<Integer, String> sportMap = new HashMap<>();
	
	
	public MyMap() {
		String [] sportArray = {"Basketball", "Fussball", "Hockey", "Kampfsport", "Laufen", "Schwimmen", "Volleyball", "Yoga"};

		for (int i = 0; i < sportArray.length; i++) {
			sportMap.put(i, sportArray[i]);
		}
	}
	
	public void printMap () {

		Set<Integer> set = sportMap.keySet();
	
		for (Integer i : set) {
			System.out.println((i + 1) + ". " + sportMap.get(i));
			i++;			
		}
	}

	
	public static void main(String[] args) {
		MyMap map = new MyMap();
		map.printMap();
	}

}
