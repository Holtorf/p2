package collections;

import java.util.HashSet;
import java.util.Iterator;

public class MySet {

	private HashSet<Projects> set = new HashSet<>();
	
	public MySet() {
		// TODO Auto-generated constructor stub
	}
	
	public void addProject (Projects p) {
		set.add(p);
	}
	
	public void removeProject (Projects p) {
		set.remove(p);
	}
	
	public String listAllProjects () {
		String str = "";
		
//		for (Projects pro : set) {
//			str += pro.toString() + "\n";
//		}
		
		Iterator<Projects> it = set.iterator();		
		while (it.hasNext()) {
			str += it.next().toString() + "\n";
		}
		
		return str;
	}
	
	public static void main(String[] args) {
		MySet set = new MySet();
		set.addProject(new Projects("Pro 1", "mittel"));
		set.addProject(new Projects("Pro 2", "hoch"));
		Projects p = new Projects("Pro 3", "niedrig");
		
		set.addProject(p);
		System.out.println(set.listAllProjects());

		set.removeProject(p);
		System.out.println(set.listAllProjects());
		
	}
}
