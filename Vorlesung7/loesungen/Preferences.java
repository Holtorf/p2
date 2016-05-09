package loesungen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class Preferences {

	private HashMap<String, String> prefs;
	public Preferences() {
		prefs = new HashMap<>();
		
	}
	



	private String get(String key) {
		return prefs.get(key);
	}




	private void read(File file) {

		prefs.clear();

		try {
			Scanner scan = new Scanner(file);
			String key = "";
			String value = "";
			while (scan.hasNextLine()) {
				key = scan.nextLine();
				
				if(scan.hasNextLine()) {
					value = scan.nextLine();
					prefs.put(key, value);
				}
			}
			scan.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}




	private void write(File file) {
		try {
			PrintWriter out = new PrintWriter(file);
			for (String key : prefs.keySet()) {
				out.println(key);
				out.println(prefs.get(key));
			}
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}




	private void set(String key, String value) {
		prefs.put(key, value);
	}

	
	
	public static void main(String[] args) {
		Preferences prefs = new Preferences();
		prefs.set("width", "480");
		prefs.set("height", "320");
		prefs.set("speed", "0.5");
		
		prefs.write(new File("preferences.txt"));
		prefs.read(new File("preferences.txt"));

		String speed = prefs.get("speed");
		System.out.println(speed);

	}


}
