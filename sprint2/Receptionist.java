package sprint2;
import java.time.LocalDate;
import java.sql.*;
import java.time.LocalTime;

//Receptionist can check a patient in/out, schedule appointments (uses AppointmentScheduler)
// 10/11/19 - Removed equals method (its in Person) - Mat
// 10/13/19 - Restored lost changes from last night: Refactored to fit current model. Recep can directly
//			  manipulate the manSys calendar 
//11/22/19 - Added a simple utility method for creating new users. It may change in the future
public class Receptionist extends Person{
	ManagementSystem manSys;
		
	public Receptionist(String name, LocalDate birthDate, String address, char sex, int ssn, ManagementSystem man, String uname, String password) {
		super(name, birthDate, address, sex, ssn, uname, password);
		this.manSys = man;
	}	
	

	public void makeAppointment(Patient pat, Doctor doc, LocalDate date, LocalTime time, String reason) {
		Appointment appt = new Appointment(pat, doc, date, time, reason, manSys);
		manSys.calendar.add(appt);
		pat.getAppointments().add(appt);
		doc.getAppointments().add(appt);
	}
	
	//Need comparator?
	public void cancelAppointment(Appointment appt) throws Exception {
		String id = appt.getID();
		manSys.calendar.remove(appt);
		appt.getPatient().getAppointments().remove(appt);
		appt.getDoctor().getAppointments().remove(appt);

		Connection con = null;
		con = DatabaseDriver.getConnection();
		Statement stmt = con.createStatement();
		stmt.execute("DELETE FROM AppointmentData WHERE AptID = '" + id + "'");
		stmt.execute("DELETE FROM Appointments WHERE AptID='"+ id+ "'");
		con.close();
	}
	
	public void rescheduleAppointment(Appointment appt, LocalDate d, LocalTime t) throws Exception {
		String id = appt.getID();
		appt.setDate(d);
		appt.setTime(t);

		Connection con = null;
		con = DatabaseDriver.getConnection();
		Statement stmt = con.createStatement();
		stmt.execute("UPDATE Appointments SET AptDate = '" + d.toString() + "', AptTime = '" + t.toString() + "' WHERE AptID = '" + id + "'");
		con.close();

	}
	
	//Need check in/out times for Appointment?
	public void checkIn(int roomNum, ManagementSystem manSys) throws Exception {
		String id = appt.getID();
    	Room r1 = new Room(roomNum);
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
    	if(manSys.rooms.contains(r1)) {
    		r1.setOccupancy(true);
    		appt.appData.setRoom(r1);
    		String sql = "UPDATE AppointmentData " + "SET RoomNum = '"+ roomNum +"' WHERE AptID = \""+id+"\"";
    		String sql1 = "UPDATE Rooms SET Occupancy = '"+ 1 + "' WHERE RoomNumber = '"+roomNum+"'"; 
    		Connection con = null;
    		con = DatabaseDriver.getConnection();
    		Statement stmt = con.createStatement();
    		stmt.execute(sql);
    		stmt.execute(sql1);
    		con.close();
    	}
    	else {
    		Connection con = null;
    		con = DatabaseDriver.getConnection();
    		Statement stmt = con.createStatement();
    		String sql = "UPDATE AppointmentData " + "SET RoomNum = '"+ roomNum +"' WHERE AptID = \""+id+"\"";
    		stmt.execute("INSERT INTO Rooms(RoomNumber, Occupancy) VALUES('"+roomNum + "',true)");
    		stmt.execute(sql);
    		con.close(); 		
    	}
	}

	public void checkOut() throws Exception {
		
		String id = appt.getID();
		Room r1 = appt.appData.getRoom();
		int roomNum = r1.getRoomNumber();
		r1.setOccupancy(false);
		//Updates the Rooms database table, sets the Room's occupancy to false
		String sql = "UPDATE AppointmentData " + "SET Roomnum = "+ null +" WHERE AptID = \""+id+"\"";
		String sql1 = "UPDATE Rooms SET Occupancy = '"+ 0 + "' WHERE RoomNumber = '"+roomNum+"'"; 
		Connection con = null;
		con = DatabaseDriver.getConnection();
		Statement stmt = con.createStatement();
		stmt.execute(sql);
		stmt.execute(sql1);
		con.close();	
	}
}