package sprint2;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

//10/11/19 - Removed equals method (its in Person) - Mat
public class Nurse extends Person{

    public Nurse(String name, LocalDate birthDate, String address, char sex, int ssn, String uname, String password){
        super(name, birthDate, address, sex, ssn, uname, password);
    }
    
    public void inputVitals(int height, double weight, double temp, String bloodP, ManagementSystem manSys) throws Exception{
    	String id = appt.getID();
    	
 //////////////////////////////////////////////////
    	   	
    	AppointmentData appData = null;
		for(AppointmentData ad: manSys.appointmentData) {
			if(ad.getID().contentEquals(id)) {
				appData = ad;
				//System.out.println(appData.getID());
			}
		}
		if(appData == null) {
			appData = new AppointmentData(id);
			Connection con = null;
			try {
				con = DatabaseDriver.getConnection();
				Statement stmt = con.createStatement();
				stmt.execute("INSERT INTO AppointmentData VALUES (null, null, null, null, null, null, null, null, null, \"" + id + "\")");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			manSys.appointmentData.add(appData);
		}
		
    	if(appt.appData == null) {
    		AppointmentData ad = new AppointmentData(id);
    		appt.setAppData(ad);
    	}
   
    	appt.appData.setHeight(height);
    	appt.appData.setWeight(weight);
    	appt.appData.setTemp(temp);
    	appt.appData.setPBloodP(bloodP);
    	String sql = "UPDATE AppointmentData " +
                "SET height = " + height + ", weight = " + weight + ", " +
                "temp = " + temp + ", bloodP = \"" + bloodP + "\" WHERE AptID = \"" + id + "\"";
    	Connection con = null;
		con = DatabaseDriver.getConnection();
		Statement stmt = con.createStatement();
		stmt.execute(sql);
		con.close();
    }

    public void inputTests(String test, ManagementSystem manSys) throws Exception{
    	String id = appt.getID();
    	
    	AppointmentData appData = null;
		for(AppointmentData ad: manSys.appointmentData) {
			if(ad.getID().contentEquals(id)) {
				appData = ad;
				//System.out.println(appData.getID());
			}
		}
		if(appData == null) {
			appData = new AppointmentData(id);
			Connection con = null;
			try {
				con = DatabaseDriver.getConnection();
				Statement stmt = con.createStatement();
				stmt.execute("INSERT INTO AppointmentData VALUES (null, null, null, null, null, null, null, null, null, \"" + id + "\")");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			manSys.appointmentData.add(appData);
		}
    	if(appt.appData == null) {
    		AppointmentData ad = new AppointmentData(id);
    		appt.setAppData(ad);
    	}
    	appt.appData.setTest(test);
    	String sql = "UPDATE AppointmentData " + "SET test = '"+ test +"' WHERE AptID = \""+id+"\"";
    	Connection con = null;
    	con = DatabaseDriver.getConnection();
    	Statement stmt = con.createStatement();
    	stmt.execute(sql);
    	con.close();
    }
    @Override
    public String toString() {
    	String s = "Nurse: " + getName() + " - Appt: " + appt.toString();
    	return s;
    }
}
