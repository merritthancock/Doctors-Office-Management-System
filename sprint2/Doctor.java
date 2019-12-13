package sprint2;

import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

//	9/23/19 - I refactored the class to extend the person class and added instruct(), prescribe() and diagnose() - Mat
//	10/11/19 - I implemented the proper Doctor, Appointment, Patient many-to-many relationship. Also updated appData update methods - Mat
//  10/11/19 - Removed equals method (its in Person) - Mat
public class Doctor extends Person{
	private String specialty;
	ManagementSystem manSys;
	private ArrayList<Appointment> appointments = new ArrayList<>();
	
	public Doctor(String name, LocalDate birthDate, String address, char sex, int ssn, String specialty, String uname, String password) {
		super(name, birthDate, address, sex, ssn, uname, password);
		setSpecialty(specialty);
	}
	
	public String getSpeciality() {
		return specialty;
	}
	void setSpecialty(String s) {
		this.specialty = s;
	}
	
	public ArrayList<Appointment> getAppointments() {
		 return appointments;
	}

	void setAppointments(ArrayList<Appointment> appts) {
		 this.appointments = appts;
	}
	
	//The following three methods now tell the argument appointment to tell its appData object to update the variable - Mat
	public void instruct(Appointment appt, String instructions) throws Exception {
		String id = appt.getID();
		
    	if(appt.appData == null) {
    		AppointmentData ad = new AppointmentData(id);
    		appt.setAppData(ad);
    	}
    	
    	appt.appData.setInstructions(instructions);
    	
    	String sql = "UPDATE AppointmentData " +
                "SET instructions = \"" + instructions + "\" WHERE AptID = \"" + id + "\"";
		
    	Connection con = null;
		con = DatabaseDriver.getConnection();
		Statement stmt = con.createStatement();
		stmt.execute(sql);
		con.close();
	}
	
	public void prescribe(Appointment appt, String prescription) throws Exception {
		String id = appt.getID();
		
    	if(appt.appData == null) {
    		AppointmentData ad = new AppointmentData(id);
    		appt.setAppData(ad);
    	}
    	
    	appt.appData.setPrescription(prescription);
    	
    	String sql = "UPDATE AppointmentData " +
                "SET prescription = \"" + prescription + "\" WHERE AptID = \"" + id + "\"";
		
    	Connection con = null;
		con = DatabaseDriver.getConnection();
		Statement stmt = con.createStatement();
		stmt.execute(sql);
		con.close();
	}
	public void diagnose(Appointment appt, String diagnosis) throws Exception {
		String id = appt.getID();
		
    	if(appt.appData == null) {
    		AppointmentData ad = new AppointmentData(id);
    		appt.setAppData(ad);
    	}
    	
    	appt.appData.setDiagnosis(diagnosis);
    	
    	String sql = "UPDATE AppointmentData " +
                "SET diagnosis = \"" + diagnosis + "\" WHERE AptID = \"" + id + "\"";
		
    	Connection con = null;
		con = DatabaseDriver.getConnection();
		Statement stmt = con.createStatement();
		stmt.execute(sql);
		con.close();
	}

	@Override
    public String toString() {
    	return this.getName();
    }

}