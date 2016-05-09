package loesungen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class MyMap {

	
	private HashMap<String, Integer> words;
	
	public MyMap() {
		words = new HashMap<>();
		readWords();
	}
	
	private void countWords (String word) {
		if (words.containsKey(word)) {
			words.put(word, words.get(word) + 1);
		}
		else {
			words.put(word, 1);			
		}
	}

	
	public void readWords () {
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Please a text:");
		String word = "";

		while (scan.hasNext()) {
			word = scan.next();
			char lastChar = word.charAt(word.length() - 1);
			while (!((lastChar <= 'Z' && lastChar >= 'A') || (lastChar <= 'z' && lastChar >= 'a'))) {
				word = word.substring(0, word.length() - 1) ;
				lastChar = word.charAt(word.length() - 1);
			}
			if (word.equals("q")){
				break;
			}
			countWords(word);
		}

		ArrayList<String> numbers = new ArrayList<>();
		numbers.addAll(words.keySet());
		
		for (String key: words.keySet()) {

			for (int i = 0; i < numbers.size(); i++) {
				if (words.get(key) >= words.get(numbers.get(i))) {
					numbers.remove(key);
					numbers.add(i, key);
					break;
				}
			}
			
		}
		
		for (String s : numbers) {
			System.out.println(s + ": \t" + words.get(s));

		}
		
		scan.close();
	}
	
	
	
	public static void main(String[] args) {
		new MyMap();
	}
}
