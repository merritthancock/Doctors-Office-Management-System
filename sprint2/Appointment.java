package sprint2;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

// 9/28/19 - I removed the vitals from the class when I refactored the Patient class - Mat
// 10/01/19 - Moved vitals back to appointment from Patient - Mat
// 10/11/19 - Gave an AppointmentData object to Appointment - Mat
public class Appointment {

	private Patient pat;
	private Doctor doc;
	private LocalDate date;
	private LocalTime time;
	private String reason;
	LocalTime checkIn;
	LocalTime checkOut;
	ManagementSystem manSys;
	AppointmentData appData;
	String ID;
	// vvv  We may want to split these up into another class 
	//I moved them to the AppointmentData class -E
	// ^^^
	

	public Appointment(Patient pat, Doctor doc, LocalDate date, LocalTime time, String reason, ManagementSystem man) {
		setPatient(pat);
		setDoctor(doc);
		setDate(date);
		setTime(time);
		setReason(reason);
		this.manSys = man;
		ID = generateID();
		setAppData(new AppointmentData(ID));
		manSys.appointmentData.add(this.appData);
	}
	
	public Appointment(Patient pat, Doctor doc, LocalDate date, LocalTime time, String reason, ManagementSystem man, String ID) {
		setPatient(pat);
		setDoctor(doc);
		setDate(date);
		setTime(time);
		setReason(reason);
		this.manSys = man;
		this.ID = ID;
	}

	public Patient getPatient() {

		return pat;
	}
	void setPatient(Patient pat){
		this.pat = pat;
	}
	
	public Doctor getDoctor() {
		return doc;
	}
	void setDoctor(Doctor doc) {
		this.doc = doc;
	}
	
	public LocalDate getDate() {
		return date;
	}

	void setDate(LocalDate doc) {
		this.date = doc;
	}
	
	public LocalTime getTime() {
		return time;
	}
	void setTime(LocalTime t) {
		this.time = t;
	}
	public String getReason() {
		return reason;
	}
	void setReason(String r) {
		this.reason = r;
	}
	void checkIn() {
		checkIn = LocalTime.now();
	}
	void checkOut() {
		checkOut = LocalTime.now();
	}
	public AppointmentData getAppData() {
		return appData;
	}
	void setAppData(AppointmentData a){
		this.appData = a;
	}
	public String getID() {
		return ID;
	}
	public boolean equals(Object o){
		if (o instanceof Appointment){
			Appointment a = (Appointment)o;
			return this.getID().equals(a.getID());
		}
		return false;
	}	

	String generateID() {
		String ID = "";

		LocalDate doc = manSys.lastDate;
		String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH-mm-ss"));
		ID = doc.toString() + "-" +  time;
		//Leaving in old method in case simplified method causes issues.
		/*if(doc.equals(LocalDate.now())) {
			ID = doc.toString() + generateCount();
			manSys.count++;
		}
		else {
			doc = LocalDate.now();
			ID = doc.toString() + "-00";
			manSys.lastDate = doc;
			manSys.count = 1;
		}*/
		return ID;
	}
	
	String generateCount() {
		String count;
		
		if(manSys.count < 10) 
			count = "-0" + manSys.count;
		else
			count = "-" + manSys.count;
		
		return count;
	}
	@Override
	public String toString() {
		String result = "";
		if(this.date.compareTo(LocalDate.now()) < 0) {
			result += "(Past Date)";
		}
		result += ID + " - " + pat.getName() + " - " + doc.getName() + " - " + date + " - " + reason;	

		return result;
	}
}