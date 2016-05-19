package staticBsp;

import java.util.ArrayList;
import java.util.Scanner;

public class KundenBsp {

	private ArrayList<Kunde> kunden = new ArrayList<>();
	
	public void eingabe () {
		Scanner scan = new Scanner(System.in);
		boolean mehrKunden = true;
		String weiter = "";
		String vorname = "";
		String nachname = "";
		
		while (mehrKunden) {
			System.out.println("Wollen Sie (noch) einen Kunden eingeben? (J/N)");

			weiter = scan.nextLine();
			if (weiter.equals("J")) {
				System.out.println("Vorname:");
				vorname = scan.nextLine();
			
				System.out.println("Nachname:");
				nachname = scan.nextLine();
				kunden.add(new Kunde(vorname, nachname));
			}
			else if (weiter.equals("N")) {
				mehrKunden = false;
				for (Kunde k : kunden)
					System.out.println(k.toString());				
			}			
		}
		scan.close();
	}
	
	public static void main(String[] args) {
		KundenBsp bsp = new KundenBsp();
		bsp.eingabe();
	}
}
