package moleMaxDefault;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

public class I2b2Import {
	
  public Connection connect(String host, Integer port, String db, String user, String password) {
    //jdbc:postgresql://host:port/database
    String url = "jdbc:postgresql://"+host+":"+port+"/"+db;

    Connection conn = null;
    try {
      conn = DriverManager.getConnection(url, user, password);
      System.out.println("Connected to the PostgreSQL server successfully.");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return conn;
  }
  
  public void deleteTestData(Connection conn) throws SQLException {
	  	Statement statement = conn.createStatement();
	  	statement.execute("DELETE FROM i2b2demodata.patient_mapping WHERE patient_ide_source='MoleMax'");
  		statement.execute("DELETE FROM i2b2demodata.patient_dimension WHERE patient_num>='11'");
  		System.out.println("Alle Testdaten Gelöscht! ");

  }

public void insertImageIntoI2b2(parseObjectImage image, Connection conn) {	
//	+ "to_date('" + this.getBirthdate().toString() + "', 'YYYY-MM-DD')," +
	Calendar rightNow = Calendar.getInstance();
	int year = rightNow.get(1);
    try { 
    	//Insert image (imageFilename, datasetName, cameraType, patientID, patientAge, patientSex, lesionID, lesionLocationText)
        Statement statement = conn.createStatement();
        String i2b2PatientId= null;
        //Nachschauen ob MoleMax ID des Patienten in der patient_mapping Tabelle des I2b2 vorhanden ist
        ResultSet rs = statement.executeQuery("SELECT * FROM i2b2demodata.patient_mapping WHERE patient_ide_source='MoleMax' AND patient_ide='"+image.getPatientID()+"'");
        if(rs.next()) {
        	//Falls vorhanden, id holen 
        	i2b2PatientId = rs.getString("patient_num");
            	 System.out.println("Patient bereits im System, i2b2Id: " + i2b2PatientId);
            	 
            	//Insert starten:
            	//statement.execute("INSERT INTO i2b2demodata.observation_fact(encounter_num, patient_num, concept_cd, provider_id, start_date, modifier_cd, instance_num, valtype_cd, tval_char) VALUES ('"+  +"', '"+i2b2PatientId+"'"     
            }else{
           //Falls nicht vorhanden, neue I2b2 Id erstellen und Alter und Sex in Patient Dimension eintragen
            	rs=statement.executeQuery("SELECT max(patient_num) FROM i2b2demodata.patient_dimension");
            	rs.next();
            	int new_patient_num = rs.getInt(1)+1;
            	System.out.println("Patient noch nicht im System, neue Patient_num: " + new_patient_num);
            	int patientBirthYear = year - Integer.parseInt(image.getPatientAge());
            	System.out.println("Patienten Alter: " + image.getPatientAge());
    			System.out.println("Patienten Geburtsdatum???:: " + patientBirthYear );
            	statement.execute("INSERT INTO i2b2demodata.patient_mapping(patient_ide, patient_ide_source, patient_num, project_id) VALUES ('"+image.getPatientID()+"' , 'MoleMax', '"+new_patient_num+"', 'MoleMax')");
            	statement.execute("INSERT INTO i2b2demodata.patient_dimension(patient_num, birth_date, age_in_years_num, sex_cd) VALUES ('"+ new_patient_num + "','2016-08-25 01:23:46.0','" +Integer.parseInt(image.getPatientAge()) +"','"+image.getPatientSex()+"')");
            	
            	//Insert starten:
            	//statement.execute("INSERT INTO i2b2demodata.observation_fact(encounter_num, patient_num, concept_cd, provider_id, start_date, modifier_cd, instance_num, valtype_cd, tval_char) VALUES ('"+  +"', '"+i2b2PatientId+"'"     
            	//statement.execute("INSERT INTO i2b2demodata.observation_fact(encounter_num, patient_num, concept_cd, provider_id) VALUES ('"+ 1 +"', '"+new_patient_num+"','skin_observation', '"+  image.getDatasetName()+"')");    

        	
        }    	
     // statement.execute("INSERT INTO i2b2demodata.patient_dimension(patient_num, age_in_years_num, sex_cd) VALUES ('"+ count +"', '"+Integer.parseInt(insert.getPatientAge()) +"', '"+insert.getPatientSex()+"')");
     // 	System.out.println("Image " + insert.getImageFilename() + " inserted");
    	} catch (SQLException e) {
    	  e.printStackTrace();
    	}
	}
}
