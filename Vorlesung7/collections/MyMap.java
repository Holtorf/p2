package collections;

import java.util.HashMap;
import java.util.Set;

public class MyMap {

	private HashMap<Buch, Integer> map = new HashMap<>();
	
	public MyMap() {
		// TODO Auto-generated constructor stub
	}
	
	public void addBooks (Buch buch, int anzahl){
		map.put(buch, anzahl);
	}
	
	public void remove (Buch buch) {
		map.remove(buch);
	}
	
	public String listAllBooks (){
		String str = "";
		
		Set<Buch> set = map.keySet();
		
		for (Buch buch : set) {
			str += buch.getName() + " : " + map.get(buch) + "\n";
		}
		
		return str;
	}
	
	
	public static void main(String[] args) {
		MyMap map = new MyMap();
		map.addBooks(new Buch("Tolles Buch", "123-123-13"), 4);
		map.addBooks(new Buch("java ist eine Insel", "7893-5343-423"), 10);
		Buch buch = new Buch("bla", "987-123-645");
		map.addBooks(buch, 3);
		
		System.out.println(map.listAllBooks());
		map.remove(buch);
		System.out.println(map.listAllBooks());
	}
}
