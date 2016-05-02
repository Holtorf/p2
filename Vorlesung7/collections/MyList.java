package collections;

import java.util.ArrayList;

public class MyList {

	private ArrayList<Projects> list = new ArrayList<>();
	
	public MyList() {
		
		
	}
	
	public void addProjekt (Projects p) {
		if (!list.contains(p))
			list.add(p);
	}
	
	public void removeProjekt (Projects p) {
		list.remove(p);
	}
	
	public String getAllProjectInfos (){
		String str = "";
		
		for (int i = 0; i < list.size(); i++){
			str += list.get(i) + "\n";
		}
		
		return str;
	}
	
	public static void main(String[] args) {
		MyList projektListe = new MyList();
		projektListe.addProjekt(new Projects("Pro 1", "hoch"));
		projektListe.addProjekt(new Projects("Pro 2", "niedrig"));		
		Projects p = new Projects("Pro 3", "mittel");
		projektListe.addProjekt(p);
		System.out.println(projektListe.getAllProjectInfos());
		
		projektListe.removeProjekt(p);
		System.out.println(projektListe.getAllProjectInfos());
		
	}
	
}
