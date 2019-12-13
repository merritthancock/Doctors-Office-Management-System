package sprint2;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class PatientTest {

	@Test
	void testPatientPass() {
		LocalDate bDate = LocalDate.parse("1989-04-26");
		Patient patient1 = new Patient("Luke Jedi", bDate, "123 Chewie lane", 'M', "Nuts", 123420069, "LukeJedi", "0069", new ManagementSystem());
		Patient patient2 = new Patient("Obi Wan", bDate, "123 Main Street", 'M', "Bananas", 123420069, "ObiWan", "0069", new ManagementSystem());
		assertTrue(patient1.equals(patient2));
	}
	
	@Test
	void testPatientFail() {
		LocalDate bDate = LocalDate.parse("1989-04-26");
		Patient patient1 = new Patient("Luke Jedi", bDate, "123 Chewie lane", 'M', "Nuts", 123420069, "LukeJedi", "0069", new ManagementSystem());
		Patient patient2 = new Patient("Obi Wan", bDate, "123 Main Street", 'M', "Bananas", 123422069, "ObiWan", "2069", new ManagementSystem());
		assertFalse(patient1.equals(patient2));
	}

}
