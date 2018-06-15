package moleMaxDefault;

import java.util.ArrayList;
import moleMaxDefault.parseObjectImage;

public class moleMaxParser {
	public static String imageFilename, datasetName, cameraType, patientID, patientAge, patientSex, lesionID,
			lesionLocationText;
	private static ArrayList<parseObjectImage> allLines = new ArrayList<parseObjectImage>();
	private static int lines = 0;

	public ArrayList<parseObjectImage> getinstance() {
		return allLines;
	}

	public int getlines() {
		return lines;
	}

	public void parseLineImage(String line) {
		String wholeline[] = line.split(";");
		// überprüft die Zeilen auf Länge
		if (line.length() <= 40) {
			System.out.println("Zeile im CSV leer/nicht vollständig");
			return;
		}
		imageFilename = wholeline[0];
		// zum überspringen der ersten Zeile im CSV
		if (imageFilename.equals("IMAGE_FILENAME"))
			return;
		datasetName = wholeline[1];
		cameraType = wholeline[5];

		patientID = wholeline[9];
		patientAge = wholeline[10];
		patientSex = wholeline[11];

		lesionID = wholeline[14];  
		lesionLocationText = wholeline[15];
		parseObjectImage temp = new parseObjectImage();

		temp.setImageFilename(imageFilename);
		temp.setDatasetName(datasetName);
		temp.setCameraType(cameraType);

		temp.setPatientID(patientID);
		temp.setPatientAge(patientAge);
		temp.setPatientSex(patientSex);

		temp.setLesionID(lesionID);
		temp.setLesionLocationText(lesionLocationText);

		allLines.add(temp);
		lines++;
		// System.out.println("ImageFilename:" + imageFilename + " - DatasetName:" +
		// datasetName + " - CameraType:" + cameraType + " - patientID:" + patientID + "
		// - patientAge:" + patientAge + " - patientSex:" + patientSex + " - LesionID:"
		// + lesionID + " - LesionLocationText:" + lesionLocationText);
	}

	public void parseLineDiagnose(String line) {
		String wholeline[] = line.split(",");
		// überspringt die erste Zeile im CSV und überprüft die restlichen Zeilen auf
		// Länge
		if (line.length() <= 53) {
			System.out.println("Zeile im CSV leer/nicht vollständig");
			return;
		}
		parseObjectImage temp = new parseObjectImage();
		for(int i =0; i<allLines.size();i++ ) {
			temp = allLines.get(i);
			if(wholeline[0].equals(temp.getLesionID())) {
				temp.setDiagnoseLvl(wholeline[2]);
				temp.setDiagnoseText(wholeline[1]);
				temp.setDiagnoseStatus(wholeline[4]);
				System.out.println("LESION ID: " + temp.getLesionID() + " Gefunden, Diagnose angehängt!");
			}
			
		}
		System.out.println("Diagnose wholeline 0: " + wholeline[0]);
	//	System.out.println("Diagnose wholeline 1: " + wholeline[1]);
	//	System.out.println("Diagnose wholeline 2: " + wholeline[2]);
	//	System.out.println("Diagnose wholeline 3: " + wholeline[3]);
	//	System.out.println("Diagnose wholeline 4: " + wholeline[4]);

	}
}
