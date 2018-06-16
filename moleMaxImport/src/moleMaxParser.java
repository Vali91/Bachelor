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
		// �berpr�ft die Zeilen auf L�nge
		if (line.length() <= 40) {
			System.out.println("Zeile im Image.CSV leer/nicht vollst�ndig");
			return;
		}
		String wholeline[] = line.split(";");
		imageFilename = wholeline[0];
		// zum �berspringen der ersten Zeile im CSV
		if (imageFilename.equals("IMAGE_FILENAME"))return;
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
		System.out.println(lines + " Lines Parsed.");
	}

	public void parseLineDiagnose(String line) {
		// �berspringt die erste Zeile im CSV und �berpr�ft die restlichen Zeilen auf L�nge/Vollst�ndigkeit
		if (line.length() <= 40) {
			System.out.println("Zeile im Diagnose.CSV leer/nicht vollst�ndig");
			return;
		}
		String wholeline[] = line.split(",");
		if(wholeline[0].equals("lesion_id")) return;
		parseObjectImage temp = new parseObjectImage();
		for(int i =0; i<allLines.size();i++ ) {
			temp = allLines.get(i);
			if(wholeline[0].equals(temp.getLesionID())) {
				temp.setDiagnoseLvl(wholeline[2]);
				temp.setDiagnoseText(wholeline[1]);
				temp.setDiagnoseStatus(wholeline[4]);
				System.out.println("LESION ID: " + temp.getLesionID() + " Gefunden & Diagnose angeh�ngt!");
			}	
		}
	}
}
