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
  		statement.execute("DELETE FROM i2b2demodata.observation_fact WHERE encounter_num='1337'");
  		statement.execute("DELETE FROM i2b2demodata.visit_dimension WHERE encounter_num='1337'");
  		System.out.println("Alle Testdaten Gelöscht! ");
    //	statement.execute("INSERT INTO i2b2demodata.visit_dimension(encounter_num, patient_num) VALUES ('1337', '11')");
    //	statement.execute("INSERT INTO i2b2demodata.visit_dimension(encounter_num, patient_num) VALUES ('1337', '12')");
    //	statement.execute("INSERT INTO i2b2demodata.visit_dimension(encounter_num, patient_num) VALUES ('1337', '13')");
    //	statement.execute("INSERT INTO i2b2demodata.visit_dimension(encounter_num, patient_num) VALUES ('1337', '14')");
    //	statement.execute("INSERT INTO i2b2demodata.visit_dimension(encounter_num, patient_num) VALUES ('1337', '15')");

  }

  
  
  //Rinner Ffragen Lombok getter/stter?test1
  
  
public void insertImageIntoI2b2(parseObjectImage image, Connection conn) {	
//	+ "to_date('" + this.getBirthdate().toString() + "', 'YYYY-MM-DD')," +
	Calendar rightNow = Calendar.getInstance();
	int year = rightNow.get(1);
    try { 
    	//Insert image (imageFilename, datasetName, cameraType, patientID, patientAge, patientSex, lesionID, lesionLocationText)
        Statement statement = conn.createStatement();
        String i2b2PatientId= null;
        //Nachschauen ob MoleMax ID des Patienten in der patient_mapping Tabelle des I2b2 vorhanden ist
        ResultSet rsPatientMapping = statement.executeQuery("SELECT * FROM i2b2demodata.patient_mapping WHERE patient_ide_source='MoleMax' AND patient_ide='"+image.getPatientID()+"'");
        if(rsPatientMapping.next()) {
        	//Falls vorhanden, id holen 
        	i2b2PatientId = rsPatientMapping.getString("patient_num");
         	ResultSet rsVisitDimension = statement.executeQuery("SELECT max(encounter_num) FROM i2b2demodata.visit_dimension WHERE patient_num='" + i2b2PatientId + "'");

            	 System.out.println("Patient bereits im System, i2b2Id: " + i2b2PatientId);
            	 
            	//Insert starten:
            	 System.out.println("Inserte Observation_fact!!!");
            	 //TODO: Nachsehen wann in die visit_dimension eingetragen wird
            	int encounter_num_richtige = 1;
            	int encounter_num_temp = 1;
            	encounter_num_temp = encounter_num_richtige;
             	int encounter_number=0;
             	if(rsVisitDimension.next()) {
             		encounter_number = rsVisitDimension.getInt(1);
             	}
             	System.out.println("Halloo:  " + encounter_number);
             	if(encounter_number!=0) encounter_num_temp = encounter_number+1;
             	
            	statement.execute("INSERT INTO i2b2demodata.visit_dimension(encounter_num, patient_num) VALUES ('" + encounter_num_temp + "', '" + i2b2PatientId + "')");
            	statement.execute("INSERT INTO i2b2demodata.observation_fact(encounter_num, patient_num, concept_cd, provider_id, start_date, modifier_cd, instance_num, valtype_cd, tval_char) VALUES ('1337','"+i2b2PatientId+"', 'skin_observation', 'N/A','2016-08-25 01:23:46.0','@', "+1+", '@', 'N/A')");  
            }else{
           //Falls nicht vorhanden, neue I2b2 Id erstellen und Alter und Sex in Patient Dimension eintragen
            	rsPatientMapping=statement.executeQuery("SELECT max(patient_num) FROM i2b2demodata.patient_dimension");
            	rsPatientMapping.next();
            	int new_patient_num = rsPatientMapping.getInt(1)+1;
            	System.out.println("Patient noch nicht im System, neue Patient_num: " + new_patient_num);
            //	int patientBirthYear = year - Integer.parseInt(image.getPatientAge());
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
