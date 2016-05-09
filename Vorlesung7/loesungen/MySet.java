package loesungen;

import java.util.HashSet;
import java.util.Scanner;

public class MySet {

	private HashSet<Integer> numbers;

	public MySet() {
		numbers = new HashSet<>();
//		readData();
	}
	
	public boolean checkData (Integer number) {
		if (number == -1) {
			return true;
		}

		if (!numbers.contains(number)) {
			System.out.println(number);
		}

		numbers.add(number);

		return false;
		
	}
	
	public void readData (){
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Please enter a new Number:");
		Integer number = scan.nextInt();
		
		System.out.println("\nDie Eingabe wurde beendet. Folgende Zahlen sind eingegeben worden:");
		while (checkData(number)) {
			number = scan.nextInt();
		}
		
		for (Integer zahl : numbers) {
			System.out.print(zahl + "\t");
		}
		
		scan.close();
	}
	
	public HashSet<Integer> getNumbers() {
		return numbers;
	}
	
	public static void main(String[] args) {
		new MySet();
	}

}
