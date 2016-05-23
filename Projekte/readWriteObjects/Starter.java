package readWriteObjects;

public class Starter {

	public static void main(String[] args) {
		MeinObjekt objekt = new MeinObjekt(10, "Ich bin irgendein Objekt");

		ObjectFileHandler ofh = new ObjectFileHandler("MeinObjekt.txt");
		ofh.writeObjekt(objekt);

		ofh.lesen();

	}

}
