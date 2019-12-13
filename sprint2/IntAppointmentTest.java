package sprint2;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class IntAppointmentTest {

	@Test
	void testAppointment() {
		LocalDate bDate1 = LocalDate.parse("1989-04-26");
		LocalDate bDate2 = LocalDate.parse("1990-03-24");
		LocalDate appdate = LocalDate.parse("2015-06-12");
		LocalTime apptime = LocalTime.of(10,43,12);
		ManagementSystem man = new ManagementSystem();
		Patient patient1 = new Patient("Barb Wilson", bDate1, "123 Main Street", 'F',"Cookies",420420243, "BarbWilson", "0243", new ManagementSystem());
		Doctor doctor1 = new Doctor("Obi Wan", bDate2, "123 Lake Street", 'M', 123422069, "Pediatrics", "ObiWan", "2069");
		Appointment appoint1 = new Appointment(patient1, doctor1, appdate, apptime, "Elbow hurts", man);
		Appointment appoint2 = new Appointment(patient1, doctor1, appdate, apptime, "Elbow hurts", man);
		assertTrue(appoint1.equals(appoint2));
	}

	@Test
	void testAppointmentFail() {
		LocalDate bDate1 = LocalDate.parse("1989-04-26");
		LocalDate bDate2 = LocalDate.parse("1990-03-24");
		LocalDate appdate = LocalDate.parse("2015-06-12");
		LocalTime apptime = LocalTime.of(10,43,12);
		ManagementSystem man = new ManagementSystem();
		ManagementSystem man2 = new ManagementSystem();
		Patient patient1 = new Patient("Barb Wilson", bDate1, "123 Main Street", 'F',"Cookies",420420243, "BarbWilson", "0243",new ManagementSystem());
		Patient patient2 = new Patient("Darth Vader", bDate2, "123 Oak Street", 'M', "Milk", 548795231, "DarthVader", "5231", new ManagementSystem());
		Doctor doctor1 = new Doctor("Obi Wan", bDate2, "123 Lake Street", 'M', 123422069, "Pediatrics", "ObiWan", "2069");
		Appointment appoint1 = new Appointment(patient1, doctor1, appdate, apptime, "Elbow hurts", man);
		Appointment appoint2 = new Appointment(patient2, doctor1, appdate, apptime, "Elbow hurts", man2);
		assertFalse(appoint1.equals(appoint2));
	}
}
