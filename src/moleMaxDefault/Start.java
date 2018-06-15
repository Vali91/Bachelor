package moleMaxDefault;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;
import java.sql.Connection;
import java.sql.SQLException;
import moleMaxDefault.parseObjectImage;

public class Start {

	public static void main(String[] args) throws SQLException {
		boolean diagnoseFile = false;
		String inputFileImage = "C:/Users/Vali/Dropbox/Vali/Bachelor Arbeit/Dateien/Beispiel Daten/LesionImages.csv";
		String inputFileDiagnose ="C:/Users/Vali/Dropbox/Vali/Bachelor Arbeit/Dateien/Beispiel Daten/LesionResults.csv";
		moleMaxParser mmParser = new moleMaxParser();
		ArrayList<parseObjectImage> allparseObjectImages = new ArrayList<parseObjectImage>();
		
		try (Stream<String> stream = Files.lines(Paths.get(inputFileImage), Charset.forName("ISO-8859-1"))) {
		      stream.forEach(line -> mmParser.parseLineImage(line));
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		if(!inputFileDiagnose.isEmpty()) diagnoseFile=true;
		if(diagnoseFile) {
			try (Stream<String> stream1 = Files.lines(Paths.get(inputFileDiagnose), Charset.forName("ISO-8859-1"))) {
				stream1.forEach(line -> mmParser.parseLineDiagnose(line));
		    	} catch (IOException e) {
		    		e.printStackTrace();
		    }
		}
		allparseObjectImages = mmParser.getinstance();
		System.out.println("Parsing Done");
		System.out.println("--------------------------------------------------------");
		System.out.println("Start I2b2 Import");
		I2b2Import i2b2import = new I2b2Import();
	    Connection conn = i2b2import.connect("localhost", 5439, "i2b2", "postgres", "postgres");
	    
		for(int i=0; i < mmParser.getlines();i++) {
			System.out.println("insert Image for Patient with MoleMaxID: " + allparseObjectImages.get(i).patientID);
			i2b2import.insertImageIntoI2b2(allparseObjectImages.get(i), conn);
		}
		//Testdaten loeschen
		i2b2import.deleteTestData(conn);
		System.out.println("Alles erledigt, connection wird geschlossen");
	    conn.close();
	}
  //  DbInterface db = i2b2import;
}
