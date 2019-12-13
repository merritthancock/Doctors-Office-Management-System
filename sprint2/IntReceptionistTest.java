package sprint2;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class IntReceptionistTest {
	@Test
	void testMakeAppointment() {
		//declarations
		LocalDate bDate = LocalDate.parse("1993-05-21");
		LocalDate date = LocalDate.parse("2019-11-21");
		LocalTime time = LocalTime.parse("10:15:00");
		String reason = "always hungry";

		//object creation
		ManagementSystem man = new ManagementSystem();
		Receptionist r = new Receptionist("Randy", bDate, "937 North St", 'M', 234820948, man, "Randy", "0948");
		Patient p = new Patient("Paul", bDate, "123 Pine Dr.", 'M', "everything", 749630987, "Paul", "0987", new ManagementSystem());
		Doctor d = new Doctor("Paula", bDate, "126 Pine Dr.", 'F', 387207538, "healing Paul", "Paula", "7538");
		
		//create calendar
		//ArrayList<Appointment> calendar	= new ArrayList<Appointment>();
		
		//create the correct appointment
		Appointment expectedAppointment = new Appointment(p, d, date, time, reason, man);
		
		//call the method
		r.makeAppointment(p, d, date, time, reason);
		//check if the first element is the appointment
		assertEquals(expectedAppointment, man.calendar.get(man.calendar.size() - 1));
	}
	
	@Test
	void testCancelAppointment() throws Exception {
		//declarations
		LocalDate bDate = LocalDate.parse("1996-05-21");
		LocalDate date = LocalDate.parse("2019-12-21");
		LocalTime time = LocalTime.parse("10:30:00");
		String reason = "just because";

		//object creation
		ManagementSystem man = new ManagementSystem();
		Receptionist r = new Receptionist("Phil", bDate, "937 South St", 'M', 938627856, man, "Phil", "7856");
		Patient p = new Patient("Chad", bDate, "123 Pine Dr.", 'M', "everything", 192784675, "Chad", "4675", new ManagementSystem());
		Doctor d = new Doctor("Paula", bDate, "126 Pine Dr.", 'F', 387765538, "healing Chad", "Paula", "5538");

		//create calendar
		//ArrayList<Appointment> calendar	= new ArrayList<Appointment>();

		//create the correct appointment
		Appointment cancelledAppointment = new Appointment(p, d, date, time, reason, man);

		//add the appointment
		r.makeAppointment(p, d, date, time, reason);
		//remove the appointment
		r.cancelAppointment(cancelledAppointment);
		//check if the appointment was removed
		assertFalse(cancelledAppointment.equals(man.calendar.get(man.calendar.size() - 1)));
	}
	
	@Test
	void testRescheduleAppointment() throws Exception {
		//declarations
		LocalDate bDate = LocalDate.parse("1993-05-21");
		LocalDate date = LocalDate.parse("2019-11-21");
		LocalTime time = LocalTime.parse("10:15:00");
		String reason = "too funny";

		//object creation
		ManagementSystem man = new ManagementSystem();
		Receptionist r = new Receptionist("Randy", bDate, "937 North St", 'M', 234820948, man, "Randy", "0948");
		Patient p = new Patient("Paul", bDate, "123 Pine Dr.", 'M', "everything", 749630987, "Paul", "0987", new ManagementSystem());
		Doctor d = new Doctor("Paula", bDate, "126 Pine Dr.", 'F', 387207538, "healing Paul", "Paula", "7538");
		
		//create calendar
		//ArrayList<Appointment> calendar	= new ArrayList<Appointment>();
		
		//create the correct appointment
		//Appointment originalAppointment = new Appointment(p, d, date, time, reason, man);
		
		//add the appointment
		r.makeAppointment(p, d, date, time, reason);
		//get the appointment we just added
		Appointment originalAppointment = man.calendar.get(0);
		//create new date and time
		LocalDate newDate = LocalDate.parse("2020-01-10");
		LocalTime newTime = LocalTime.parse("09:30:00");		
		
		//call the method
		r.rescheduleAppointment(originalAppointment, newDate, newTime);
		//check if the first element is the new appointment
		assertAll("Should reflect a date and time change",
				() -> assertEquals(LocalDate.parse("2020-01-10"), man.calendar.get(0).getDate()),
				() -> assertEquals(LocalTime.parse("09:30:00"), man.calendar.get(0).getTime())
		);		
	}
	@Test
	void testcheckout() throws Exception {
		LocalDate bDate = LocalDate.parse("1993-05-21");
		LocalDate date = LocalDate.parse("2019-11-21");
		LocalTime time = LocalTime.parse("10:15:00");
		String reason = "too funny";

		//object creation
		ManagementSystem man = new ManagementSystem();
		Receptionist r = new Receptionist("Bob Bobsaw", bDate, "937 North St", 'M', 234820948, man, "BobBobsaw", "0948");
		Patient p = new Patient("Paul", bDate, "123 Pine Dr.", 'M', "everything", 749630987, "Paul", "0987", new ManagementSystem());
		Doctor d = new Doctor("Paula", bDate, "126 Pine Dr.", 'F', 387207538, "healing Paul", "Paula", "7538");
		r.makeAppointment(p, d, date, time, reason);

		Appointment a1 = man.calendar.get(0);
		r.checkOut();
		assertEquals(false,a1.appData.getRoom().isOccupied());
	}

	
	@Test
	void testCheckIn() throws Exception {
		LocalTime time = LocalTime.parse("09:30:00");
		LocalDate date = LocalDate.parse("2020-01-05");
		LocalDate bDate = LocalDate.parse("1982-10-08");
		String reason = "my cat died";
		
		ManagementSystem man = new ManagementSystem();
		Room room = new Room(203, false);
		man.rooms.add(room);
		
		Receptionist r = new Receptionist("Randy", bDate, "937 North St", 'M', 234820948, man, "Randy", "0948");
		Patient p = new Patient("Phil", bDate, "4629 South St", 'M', "bees?", 948038759, "Phil", "8759", new ManagementSystem());
		Doctor d = new Doctor("Sandra DoLittle", bDate, "Pawesome Dr", 'F', 139859477, "veternarian", "SandraDoLittle", "9477");

		r.makeAppointment(p, d, date, time, reason);
		Appointment originalAppointment = man.calendar.get(0);
		
		r.checkIn(room.getRoomNumber(), man);
		
		assertAll("Should make room occupied and change room of appt",
				() -> assertEquals(room, originalAppointment.appData.getRoom()),
				() -> assertEquals(true, originalAppointment.appData.getRoom().isOccupied())
		);
	}
	
	/*
	 * @Test void testCheckOut() { LocalTime time = LocalTime.parse("11:30:00");
	 * LocalDate date = LocalDate.parse("2020-01-05"); LocalDate bDate =
	 * LocalDate.parse("1957-04-10"); String reason = "fell";
	 * 
	 * ManagementSystem man = new ManagementSystem(); Room room = new Room(104,
	 * false); man.rooms.add(room);
	 * 
	 * Receptionist r = new Receptionist("Randy", bDate, "937 North St", 'M',
	 * 234820948, man); Patient p = new Patient("Debrah", bDate, "78 North St", 'F',
	 * "mean people", 148038759); Doctor d = new Doctor("Joe", bDate, "Swag Street",
	 * 'M', 139859477, "being cool");
	 * 
	 * r.makeAppointment(p, d, date, time, reason); Appointment appt =
	 * man.calendar.get(0);
	 * 
	 * //System.out.println(appt.appData.getRoom().isOccupied());
	 * 
	 * r.checkOut(appt);
	 * 
	 * assertEquals(false, appt.appData.getRoom().isOccupied()); }
	 */
}
