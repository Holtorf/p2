package loesungen;

import java.util.Scanner;
import java.util.Stack;


public class MyStack {

	private Stack<Character> brackets;

	public MyStack() {
		brackets  = new Stack<>();
		checkBrackets("a{sa}[([ghgj)]");
	}
	
	public boolean checkBrackets(String data) {
		
		String [] brac = data.split("[^{^}^\\]^\\[^(^)]");

		for (String s : brac) {
			for (int i = 0; i < s.length(); i++) {
				switch (s.charAt(i)) {
				case '}':
					if (brackets.pop() != '{') {
						System.out.println("ERROR! Parentheses dont match");
						return false;
					}
					break;

				case ']':
					if (brackets.pop() != '[') {
						System.out.println("ERROR! Parentheses dont match");
						return false;
					}
					break;

				case ')':
					if (brackets.pop() != '(') {
						System.out.println("ERROR! Parentheses dont match");
						return false;
					}
					break;

				default:
					brackets.push(s.charAt(i));
					break;
				}
			}
		}

		System.out.println("OK Parentheses do match");
		return true;
	}
	
	public String readData () {
		String data = "";
		Scanner scan = new Scanner(System.in);
		
		while (scan.hasNext()) {
			data += scan.nextLine();
		}
		
		scan.close();
		
		return data;
	}
	
	

	public static void main(String[] args) {
		new MyStack();
	}

}
