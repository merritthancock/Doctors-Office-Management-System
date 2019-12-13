package sprint2;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class IntNurseTest {

	@Test
	void testEqualsSucceeds() {
		LocalDate bDate = LocalDate.parse("1989-04-26");
		Nurse nurse1 = new Nurse("Billy Jean", bDate, "123 Oxtail Lane", 'M', 123420069, "BillyJean", "0069");
		bDate = LocalDate.parse("1994-05-06");
		Nurse nurse2 = new Nurse("John Harbaugh",bDate, "456 Grove Street", 'M',123420069, "JohnHarbaugh", "0069");
		assertTrue(nurse1.equals(nurse2));
		
	}


	@Test
	void testEqualsFails(){
		LocalDate bDate = LocalDate.parse("1989-04-26");
		Nurse nurse1 = new Nurse("Nancy Drew", bDate, "123 Oxtail Lane", 'F', 123420069,"NancyDrew", "0069");
		bDate = LocalDate.parse("1994-05-06");
		Nurse nurse2 = new Nurse("Jim Harbaugh",bDate, "456 Grove Street", 'M',193857594, "JimHarbaugh", "7594");
		assertFalse(nurse1.equals(nurse2));
	}


	@Test
	void testInputTestsSucceeds() throws Exception {
		LocalDate bDate = LocalDate.parse("1989-04-20");
		Nurse nurse1 = new Nurse("Chad TRAUUUNT", bDate , "167 Grove Street", 'M' , 584739403, "ChadTRAUUUNT", "9403");
		Patient patient1 = new Patient("Bob Ross", bDate ,"169 Grove Street", 'M' ,"Sadness" , 394854595, "BobRoss", "4595", new ManagementSystem());
		Doctor doctor1 = new Doctor("Doctor D", bDate , "171 Grove Street", 'M' , 584739403, "Googling symptoms", "DoctorD", "9403");
		ManagementSystem man = new ManagementSystem();
		Appointment appointment1 = new Appointment(patient1, doctor1, LocalDate.now(), LocalTime.now(),"My elbow feel funny.",man);
		nurse1.setAppt(appointment1);
		String test1 = "Test or whatever";
	//	nurse1.inputTests(test1);
		assertEquals(test1,nurse1.getAppt().getAppData().getTest());	
	}

}
