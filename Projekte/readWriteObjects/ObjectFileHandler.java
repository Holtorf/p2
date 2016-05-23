package readWriteObjects;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Date;

public class ObjectFileHandler {

	private String filename;
	
	public ObjectFileHandler(String filename) {
		this.filename = filename;
	}
	
	public boolean writeObjekt(Object objekt) {

		OutputStream fos = null;

		try {
			// hier legt ihr einen Daten-Stram zum schreiben an, ähnlich wie ein
			// FileWriter
			fos = new FileOutputStream(filename);

			// um objekte schreiben zu können, braucht ihr einen
			// ObjectOutputStream, damit werden alle Infos des bestehenden
			// Objektes gesichtert (Attribute)
			ObjectOutputStream o = new ObjectOutputStream(fos);

			
			o.writeObject("Today"); 	//Man kann String-Objekte schreiben
			o.writeObject(new Date());	//Oder schon existierende Java Objekte wie Date

			o.writeObject(objekt);		//oder selbstgeschriebene Objekte
			o.writeObject(new MeinObjekt(16, "Ein weiteres Objekt"));
		
		
		} catch (IOException e) {
			System.err.println(e);
		} finally {
			try {
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return false;
	}


	public Object lesen() {
		Object obj = null;
		InputStream fis = null;

		try {
			// hier legt ihr einen Daten-Stram zum Lesen an, ähnlich wie ein
			// FileReader
			fis = new FileInputStream(filename);

			// um objekte lesen zu können, braucht ihr einen ObjectInputStream
			ObjectInputStream o = new ObjectInputStream(fis);

			//mit readObject wird der nächste Datensatz gelesen und in ein Object gepackt
			String string = (String) o.readObject();
			Date date = (Date) o.readObject();

			System.out.println(string);
			System.out.println(date);

			//um sicher zu gehen dass man keine Cast fehler macht, sollte man die Klassen der
			//Objekte überprüfen
			obj = o.readObject();
			if (obj.getClass() == MeinObjekt.class) {
				MeinObjekt obj1 = (MeinObjekt) obj;
				System.out.println(obj1.toString());
			}

			obj = o.readObject();

			if (obj.getClass() == MeinObjekt.class) {
				MeinObjekt obj2 = (MeinObjekt) obj;
				System.out.println(obj2.toString());
			}
		} catch (IOException e) {
			System.err.println(e);
		} catch (ClassNotFoundException e) {
			System.err.println(e);
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return obj;
	}


	
}
