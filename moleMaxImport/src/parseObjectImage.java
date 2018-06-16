package moleMaxDefault;

//parse Objekt Bild
public class parseObjectImage {
	String imageFilename, datasetName, cameraType, patientID, patientAge, patientSex, lesionID, lesionLocationText, diagnoseTxt, diagnoseLvl, diagnoseStatus;

	public void setImageFilename(String input) {
		imageFilename=input;
	}
	public void setDatasetName(String input) {
		datasetName=input;
	}
	public void setCameraType(String input) {
		cameraType=input;
	}
	public void setPatientID(String input) {
		patientID=input;
	}
	public void setPatientAge(String input) {
		patientAge=input;
	}
	public void setPatientSex(String input) {
		patientSex=input;
	}
	public void setLesionID(String input) {
		lesionID=input;
	}
	public void setLesionLocationText(String input) {
		lesionLocationText=input;
	}
	public void setDiagnoseText(String input) {
		diagnoseTxt=input;
	}
	public void setDiagnoseLvl(String input) {
		diagnoseLvl=input;
	}
	public void setDiagnoseStatus(String input) {
		diagnoseStatus=input;
	}
	
	
	public String getImageFilename() {
		return imageFilename;
	}
	public String getDatasetName() {
		return datasetName;
	}
	public String getCameraType() {
		return cameraType;
	}
	public String getPatientID() {
		return patientID;
	}
	public String getPatientAge() {
		return patientAge;
	}
	public String getPatientSex() {
		return patientSex;
	}
	public String getLesionID() {
		return lesionID;
	}
	public String getLesionLocationText() {
		return lesionLocationText;
	}
	public String getDiagnosisTxt() {
		return diagnoseTxt;
	}
	public String getDiagnosisLvl() {
		return diagnoseLvl;
	}
	public String getDiagnosisStatus() {
		return diagnoseStatus;
	}
}
