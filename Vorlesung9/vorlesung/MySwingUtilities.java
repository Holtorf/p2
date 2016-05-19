package vorlesung;

import javax.swing.SwingUtilities;

import loesungen.MyColorWithFileIO;

public class MySwingUtilities {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new MyColorWithFileIO();
				
			}
		});
		System.out.println("bin da");

	}

}
