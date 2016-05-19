package vorlesung;

public class MyThread extends Thread{

	private String text;

	public MyThread(String text) {
		this.text = text;
	}
	
	@Override
	public void run() {
		super.run();
		System.out.println(text);
	}

	public static void main(String[] args) {
		MyThread t1 = new MyThread("ich lebe");
		t1.start();
	}
}
