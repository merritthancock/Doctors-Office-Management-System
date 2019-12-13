package sprint2;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;

// 9/28/19 - I extended the Person class and added the vitals from the Appointment class - Mat
//10/01/19 - Moved vitals back to appointment from Patient - Mat
//10/11/19 - I implemented the proper Doctor, Appointment, Patient many-to-many relationship - Mat
//10/11/19 - Removed equals method (its in Person) - Mat
public class Patient extends Person{
	private String allergies;
	private int height;
	private double weight;
	private ManagementSystem manSys;
	private ArrayList<Appointment> appointments = new ArrayList<>();
	
	public Patient(String name, LocalDate birthDate, String address, char sex, String allergies, int ssn, String uname, String password, ManagementSystem manSys) {
		super(name, birthDate, address, sex, ssn, uname, password);
		this.manSys = manSys;
		setAllergies(allergies);
	}
	public String getAllergies() {
		 return allergies;
	}
	void setAllergies(String allerg) {
		 this.allergies = allerg;
	}
	public int getHeight() {
		return height;
	}
	void setHeight(int height) {
		this.height = height;
	}
	public double getWeight() {
		return weight;
	}
	void setWeight(double weight) {
		this.weight = weight;
	}
	public ArrayList<Appointment> getAppointments() {
		 return appointments;
	}
	void setAppointments(ArrayList<Appointment> a) {
		 this.appointments = a;
	}
	public void generateAppointments(){
		//Variables for the upcoming loop
//		ResultSet rs, rs2;
//		LocalDate date;
//		LocalTime time;
//		String reason, id, doc;
//		Doctor doctor;
//		Appointment app;
//		//Connect to the DB and grab the patients appointments
//		Connection con = null;
//		con = DatabaseDriver.getConnection();
//		Statement stmt = con.createStatement();
//		Statement stmt2 = con.createStatement();
//		rs = stmt.executeQuery("SELECT * FROM Appointments WHERE Patient='" + this.getName() +"'");
		//Generate each appointment and add it to the patient's apps list
		for(Appointment app: manSys.calendar) {
//			date = rs.getDate("AptDate").toLocalDate();
//			time = rs.getTime("AptTime").toLocalTime();
//			reason = rs.getString("Reason");
//			id = rs.getString("AptID");
//			
//			doc = rs.getString("Doctor");
//			System.out.println(doc);
//			rs2 = stmt2.executeQuery("SELECT * FROM Doctors WHERE Name='" + doc +"'");
//			doctor = (Doctor)manSys.makePerson('d', rs2);
			
//			app = new Appointment(this, doctor, date, time, reason, manSys, id);
			if(this.getName().equals(app.getPatient().getName()))
				appointments.add(app);
			System.out.println(app.toString() + " - From Patient");
		}
		appointments.sort(appointmentDateComparator);
		//con.close();
	}
	Comparator<Appointment> appointmentDateComparator = new Comparator<Appointment>() {
        @Override
        public int compare(Appointment a1, Appointment a2) {
        	return a1.getDate().compareTo(a2.getDate());
        }
    };
    @Override
    public String toString() {
    	return this.getName();
    }

}
