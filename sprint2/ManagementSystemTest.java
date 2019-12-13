package sprint2;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class ManagementSystemTest {

	@Test
	void testLoginSucceeds() throws Exception {
		//Test true for valid database credentials.
		ManagementSystem manSys = new ManagementSystem();
		String uname = "jdawg75";
		String pword = "jimsucks";
		assertTrue(manSys.login(uname, pword));
	}
	
	@Test
	void testLoginFails() throws Exception {
		//Test false for valid database credentials
		ManagementSystem manSys = new ManagementSystem();
		String uname = "imnotreal";
		String pword = "idontexist";
		assertFalse(manSys.login(uname, pword));
	}
	/* Commented out because logout is built into the log out button
	 * @Test void testLogout() throws Exception { ManagementSystem mng = new
	 * ManagementSystem(); String uname = "LukeJedi"; String pword = "0069";
	 * LocalDate bDate = LocalDate.parse("1989-04-26"); Receptionist ria = new
	 * Receptionist("Ria", bDate, "123 Chewie lane", 'F', 123547894, mng, "Ria",
	 * "7894"); ria.createPerson("Patient", "Luke Jedi", bDate, "123 Chewie lane",
	 * 'M', 123420069, uname, pword); mng.login(uname, pword); mng.logout(); Person
	 * p = mng.getCurrentUser(); assertTrue(p == null); }
	 */
	
	@Test
	void testManagementSystem() {
		fail("Not yet implemented");
	}

	@Test
	void testGetCurrentAppointment() {
		fail("Not yet implemented");
	}

	@Test
	void testSetCurrentAppointment() {
		fail("Not yet implemented");
	}

	@Test
	void testGetCurrentUser() {
		fail("Not yet implemented");
	}

	@Test
	void testSetCurrentUser() {
		fail("Not yet implemented");
	}

	@Test
	void testMakeAppointment() {
		fail("Not yet implemented");
	}

	@Test
	void testCancelAppointment() {
		fail("Not yet implemented");
	}

	@Test
	void testRescheduleAppointment() {
		fail("Not yet implemented");
	}

}