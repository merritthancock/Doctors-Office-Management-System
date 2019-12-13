package sprint2;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;

import screens.LoginScreen;
import sprint2.ManagementApp;
import java.sql.*;

public class ManagementSystem {
	private Appointment currentA;
	private Person user;
	public ArrayList<Person> people;
	public ArrayList<Appointment> calendar;
	public ArrayList<AppointmentData> appointmentData;
	public ArrayList<Room> rooms;
	int count = 0;
	LocalDate lastDate;
	LoginScreen login;
	
	public ManagementSystem() {
		people = new ArrayList<Person>();
		calendar = new ArrayList<Appointment>();
		appointmentData = new ArrayList<AppointmentData>();
		rooms = new ArrayList<Room>();
		lastDate = LocalDate.now();
		try {
			retrieveDoctors();
			retrievePatients();
			retrieveNurses();
			retrieveReceptionists();
			generateAppointments();
			retrieveAppData();
			retrieveRooms();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		people.sort(personNameComparator);
	}
	
	public Appointment getCurrentAppointment() {
		return currentA;
	}
	void setCurrentAppointment(Appointment appt) {
		this.currentA = appt;
	}
	public Person getCurrentUser() {
		return user;
	}
	public void setCurrentUser(Person per) {
		this.user = per;
	}
	public LocalDate getLastDate() {
		return lastDate;
	}
	public void setLastDate(LocalDate d) {
		lastDate = d;
	}
	@SuppressWarnings("resource")
	public boolean login(String user, String password) throws Exception {
		Connection con = null;
		con = DatabaseDriver.getConnection();
		Statement stmt = con.createStatement();
		//Checks if the username and password given from login screen exist within all four tables of people in database.
		ResultSet rs = stmt.executeQuery("SELECT * FROM Nurses WHERE uname='" + user+"'" + " AND pw='"+password+"'");
		if (rs.next() != false) {
			makeUser('n', rs);
			return true;
		}
		if (rs.next() == false) {
			rs = stmt.executeQuery("SELECT * FROM Doctors WHERE uname='" + user+"'" + " AND pw='"+password+"'");
			if (rs.next() != false) {
				makeUser('d', rs);
				return true;
			} 
		}
		if (rs.next() == false) {
			rs = stmt.executeQuery("SELECT * FROM Receptionists WHERE uname='" + user+"'" + " AND pw='"+password+"'");
			if (rs.next() != false) {
				makeUser('r', rs);
				return true;
			}
		}
		if (rs.next() == false) {
			rs = stmt.executeQuery("SELECT * FROM Patients WHERE uname='" + user+"'" + " AND pw='"+password+"'");
			if (rs.next() != false) {
				makeUser('p', rs);
				return true;
			}
		}
		return false;
	}
	//Method for extracting info from the above result set and make a person with it. It then sends that person to the setCurrentUser method
	public void makeUser(char type, ResultSet rs) throws Exception {
		//Extract the common information from the result set
		String name = rs.getString("Name");
		LocalDate birthDate = rs.getDate("BirthDate").toLocalDate();
		String address = rs.getString("Address");
		char sex = rs.getString("Sex").charAt(0);	
		int ssn = rs.getInt("SSN");
		String uname = rs.getString("uname");
		String password = rs.getString("pw");
		
		//Determine the subclass and extract the other relevant information. Create the proper person subclass and set it as the current user
		switch(type) {
		case 'n':
			Nurse n = new Nurse(name, birthDate, address, sex, ssn, uname, password);
			setCurrentUser(n);
			break;
		case 'd':
			String specialty = rs.getString("Specialty");
			Doctor d = new Doctor(name, birthDate, address, sex, ssn, specialty, uname, password);
			setCurrentUser(d);
			break;
		case 'r': 
			Receptionist r = new Receptionist(name, birthDate, address, sex, ssn, this, uname, password);
			setCurrentUser(r);
			break;
		case 'p':
			String allergies = rs.getString("Allergies");
			Patient p = new Patient(name, birthDate, address, sex, allergies, ssn, uname, password, this);
			p.generateAppointments();
			setCurrentUser(p);
			break;
		}
	}
	//Method for extracting a single person from a result set and returning the person
	public Person makePerson(char type, ResultSet rs) throws Exception {
		rs.next();
		//Extract the common information from the result set
		String name = rs.getString("Name");
		LocalDate birthDate = rs.getDate("BirthDate").toLocalDate();
		String address = rs.getString("Address");
		char sex = rs.getString("Sex").charAt(0);	
		int ssn = rs.getInt("SSN");
		String uname = rs.getString("uname");
		String password = rs.getString("pw");
		
		if(type == 'n') {
			Nurse n = new Nurse(name, birthDate, address, sex, ssn, uname, password);
			return n;
		}	
		else if(type == 'd') {
			String specialty = rs.getString("Specialty");
			Doctor d = new Doctor(name, birthDate, address, sex, ssn, specialty, uname, password);
			return d;
		}	
		else if(type == 'r') {
			Receptionist r = new Receptionist(name, birthDate, address, sex, ssn, this, uname, password);
			return r;
		}	
		else {
			String allergies = rs.getString("Allergies");
			Patient p = new Patient(name, birthDate, address, sex, allergies, ssn, uname, password, this);
			return p;
		}
	}

	void generateAppointments() throws Exception {
		//Variables for the upcoming loop
		ResultSet rs;
		LocalDate date;
		LocalTime time;
		String reason, id, doc, pat;
		Doctor doctor = null;
		Patient patient = null;
		Appointment app;
		//Connect to the DB and grab the appointments
		Connection con = null;
		con = DatabaseDriver.getConnection();
		Statement stmt = con.createStatement();

		rs = stmt.executeQuery("SELECT * FROM Appointments");
		//Generate each appointment and add it to the calendar
		while(rs.next() != false) {
			date = rs.getDate("AptDate").toLocalDate();
			time = rs.getTime("AptTime").toLocalTime();
			reason = rs.getString("Reason");
			id = rs.getString("AptID");
			
			doc = rs.getString("Doctor");
			for(Person p: people) {
				if(p.getName().equals(doc)) {
					doctor = (Doctor)p;
					continue;
				}
			}
			
			pat = rs.getString("Patient");
			for(Person p: people) {
				if(p.getName().equals(pat)) {
					patient = (Patient)p;
					continue;
				}
			}
			
			app = new Appointment(patient, doctor, date, time, reason, this, id);
			calendar.add(app);
			System.out.println(app.toString());
		}
		calendar.sort(appointmentDateComparator);
		con.close();
	}
	void retrieveRooms() throws Exception{
		int roomnum;
		boolean occupancy;
		Connection con = null;
		con = DatabaseDriver.getConnection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Rooms");
		while (rs.next()) {
			roomnum = rs.getInt("RoomNumber");
			occupancy = rs.getBoolean("Occupancy");
			Room r = new Room(roomnum, occupancy);
			rooms.add(r);
		}
	}
	void retrieveDoctors() throws Exception {
		String name, address, uname, password, specialty;
		LocalDate birthDate;
		char sex;	
		int ssn;
		Doctor d;
		Connection con = null;
		con = DatabaseDriver.getConnection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Doctors");

		while(rs.next()) {
			//Extract the common information from the result set
			name = rs.getString("Name");
			birthDate = rs.getDate("BirthDate").toLocalDate();
			address = rs.getString("Address");
			sex = rs.getString("Sex").charAt(0);	
			ssn = rs.getInt("SSN");
			uname = rs.getString("uname");
			password = rs.getString("pw");
			specialty = rs.getString("Specialty");
			d = new Doctor(name, birthDate, address, sex, ssn, specialty, uname, password);
			people.add(d);
		}
	}
	void retrieveNurses() throws Exception {
		String name, address, uname, password;
		LocalDate birthDate;
		char sex;	
		int ssn;
		Nurse n;
		Connection con = null;
		con = DatabaseDriver.getConnection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Nurses");

		while(rs.next()) {
			//Extract the common information from the result set
			name = rs.getString("Name");
			birthDate = rs.getDate("BirthDate").toLocalDate();
			address = rs.getString("Address");
			sex = rs.getString("Sex").charAt(0);	
			ssn = rs.getInt("SSN");
			uname = rs.getString("uname");
			password = rs.getString("pw");
			n = new Nurse(name, birthDate, address, sex, ssn, uname, password);
			people.add(n);
		}
	}
	void retrieveReceptionists() throws Exception {
		String name, address, uname, password;
		LocalDate birthDate;
		char sex;	
		int ssn;
		Receptionist r;
		Connection con = null;
		con = DatabaseDriver.getConnection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Receptionists");

		while(rs.next()) {
			//Extract the common information from the result set
			name = rs.getString("Name");
			birthDate = rs.getDate("BirthDate").toLocalDate();
			address = rs.getString("Address");
			sex = rs.getString("Sex").charAt(0);	
			ssn = rs.getInt("SSN");
			uname = rs.getString("uname");
			password = rs.getString("pw");
			r = new Receptionist(name, birthDate, address, sex, ssn, this, uname, password);
			people.add(r);
		}
	}
	void retrievePatients() throws Exception {
		String name, address, uname, password, allergies;
		LocalDate birthDate;
		char sex;	
		int ssn;
		Patient p;
		Connection con = null;
		con = DatabaseDriver.getConnection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Patients");

		while(rs.next()) {
			//Extract the common information from the result set
			name = rs.getString("Name");
			birthDate = rs.getDate("BirthDate").toLocalDate();
			address = rs.getString("Address");
			sex = rs.getString("Sex").charAt(0);	
			ssn = rs.getInt("SSN");
			uname = rs.getString("uname");
			password = rs.getString("pw");
			allergies = rs.getString("Allergies");
			p = new Patient(name, birthDate, address, sex, allergies, ssn, uname, password, this);
			people.add(p);
		}
	}
	void retrieveAppData() throws Exception {
		AppointmentData appData = null;
		int RoomNum, height;
		String AptID;
		
		Connection con = null;
		con = DatabaseDriver.getConnection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM AppointmentData");
		while(rs.next()) {
			AptID = rs.getString("AptID");	
			appData = new AppointmentData(AptID);
			appData.setRoom(new Room(rs.getInt("RoomNum"), false));
			appData.setHeight(rs.getInt("height"));
			appData.setWeight(rs.getDouble("weight"));
			appData.setTemp(rs.getDouble("temp"));
			appData.setPBloodP(rs.getString("bloodP"));
			appData.setDiagnosis(rs.getString("diagnosis"));
			appData.setPrescription(rs.getString("prescription"));
			appData.setInstructions(rs.getString("instructions"));
			appData.setTest(rs.getString("test"));	
	
			appointmentData.add(appData);
			for(Appointment a: calendar) {
				if(a.getID().equals(AptID))
					a.setAppData(appData);
			}
		}
	}
	Comparator<Appointment> appointmentDateComparator = new Comparator<Appointment>() {
        @Override
        public int compare(Appointment a1, Appointment a2) {
        	if(a1.getDate().compareTo(a2.getDate()) == 0) {
        		return a1.getTime().compareTo(a2.getTime());
        	}
        	else
        		return a1.getDate().compareTo(a2.getDate());
        }
    };
    Comparator<Person> personNameComparator = new Comparator<Person>() {
        @Override
        public int compare(Person p1, Person p2) {
        	return p1.getName().compareTo(p2.getName());
        }
    };

	//Method to save appointments into the Appointments database table
	public void saveAppointment(Appointment a) throws Exception{
		String patName = a.getPatient().getName();
		String docName = a.getDoctor().getName();
		String apptDate = a.getDate().toString();
		String apptTime = a.getTime().toString();
		String apptReason = a.getReason();
		String apptID = a.getID();
		calendar.add(a);
		Connection con = null;
		con = DatabaseDriver.getConnection();
		Statement stmt = con.createStatement();
		stmt.execute("INSERT INTO Appointments(Patient, Doctor, AptDate, AptTime, Reason, AptID) VALUES(\""+patName+"\",\""+docName+"\",\""+apptDate+"\",\""+apptTime+"\",\""
							+apptReason+"\",\""+apptID+"\")");
		con.close();
	}
	public void addAppData(String id) {
		Connection con = null;
		try {
			con = DatabaseDriver.getConnection();
			Statement stmt = con.createStatement();
			stmt.execute("INSERT INTO AppointmentData VALUES (null, null, null, null, null, null, null, null, null, \"" + id + "\")");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		}
		
	public void createAppointment(String patientName, String docName, LocalDate apptDate, LocalTime apptTime, String apptReason, ManagementSystem manSys) throws Exception{
		Connection conn = null;
		conn = DatabaseDriver.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Patients WHERE Name='"+patientName+"'");
		Patient pat =  (Patient)makePerson('p',rs);
		rs = stmt.executeQuery("SELECT * FROM Doctors WHERE Name='"+docName+"'");
		Doctor doc = (Doctor)makePerson('d', rs);
		Appointment appt = new Appointment(pat, doc,apptDate,apptTime,apptReason, manSys);
		saveAppointment(appt);
	}

}