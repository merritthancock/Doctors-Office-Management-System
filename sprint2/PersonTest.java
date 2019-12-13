package sprint2;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class PersonTest {

	@Test
	void testPersonPass() {
		LocalDate bDate = LocalDate.parse("1989-04-26");
		Person person1 = new Patient("Luke Jedi", bDate, "123 Chewie lane", 'M', "Muffins", 123420069, "LukeJedi", "0069", new ManagementSystem());
		Person person2 = new Patient("Obi Wan", bDate, "123 Main Street", 'M', "Oreos", 123420069, "ObiWan", "0069", new ManagementSystem());
		assertTrue(person1.equals(person2));
	}
	
	@Test
	void testPersonFail() {
		LocalDate bDate = LocalDate.parse("1989-04-26");
		Person person1 = new Patient("Luke Jedi", bDate, "123 Chewie lane", 'M', "Nuts", 123420069, "LukeJedi", "0069", new ManagementSystem());
		Person person2 = new Patient("Obi Wan", bDate, "123 Main Street", 'M', "Bananas", 123422069, "ObiWan", "2069", new ManagementSystem());
		assertFalse(person1.equals(person2));
	}
	
}
