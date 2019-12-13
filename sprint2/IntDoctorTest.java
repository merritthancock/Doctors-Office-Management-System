package sprint2;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

class IntDoctorTest {

	@Test
	void testDoctor() {
		LocalDate bDate = LocalDate.parse("1989-04-26");
		Doctor doctor1 = new Doctor("Luke Jedi", bDate, "123 Chewie lane", 'M', 123420069, "Cardiology", "LukeJedi", "0069");
		Doctor doctor2 = new Doctor("Obi Wan", bDate, "123 Main Street", 'M', 123422069, "Pediatrics", "ObiWan", "2069");
		assertFalse(doctor1.equals(doctor2));
	}

	@Test
	void testGetSpecialityPass() {
		LocalDate bDate = LocalDate.parse("1989-04-26");
		Doctor doctor1 = new Doctor("Luke Jedi", bDate, "123 Chewie lane", 'M', 123420069, "Cardiology", "LukeJedi", "0069");
		String specialty1 = "Cardiology";
		String actual = doctor1.getSpeciality();
		assertTrue(specialty1 == actual);
	}
	
	@Test
	void testGetSpecialityFail() {
		LocalDate bDate = LocalDate.parse("1989-04-26");
		Doctor doctor1 = new Doctor("Obi Wan", bDate, "123 Main Street", 'M', 123422069, "Pediatrics", "ObiWan", "2069");
		String specialty1 = "Cardiology";
		String actual = doctor1.getSpeciality();
		assertFalse(specialty1 == actual);
	}

	@Test
	void testSetSpecialtyPass() {
		LocalDate bDate = LocalDate.parse("1989-04-26");
		Doctor doctor1 = new Doctor("Obi Wan", bDate, "123 Main Street", 'M', 123422069, "Pediatrics", "ObiWan", "2069");
		doctor1.setSpecialty("Cardiology");
		String actual = "Cardiology";
		String test = doctor1.getSpeciality();
		assertTrue(test== actual);
	}

	@Test
	void testSetSpecialtyFail() {
		LocalDate bDate = LocalDate.parse("1989-04-26");
		Doctor doctor1 = new Doctor("Obi Wan", bDate, "123 Main Street", 'M', 123422069, "Pediatrics","ObiWan", "2069");
		doctor1.setSpecialty("Cardiology");
		String actual = "Pediatrics";
		String test = doctor1.getSpeciality();
		assertFalse(test== actual);
	}
	
	@Test
	void testInstructPass() throws Exception {
		LocalDate bDate1 = LocalDate.parse("1989-04-26");
		LocalDate bDate2 = LocalDate.parse("1990-03-24");
		LocalDate appdate = LocalDate.parse("2015-06-12");
		LocalTime apptime = LocalTime.of(10,43,12);
		String instruction = "Use ice";
		ManagementSystem man = new ManagementSystem();
		Patient patient1 = new Patient("Barb Wilson", bDate1, "123 Main Street", 'F',"Cookies",420420243, "BarbWilson", "0243", new ManagementSystem());
		Doctor doctor1 = new Doctor("Obi Wan", bDate2, "123 Lake Street", 'M', 123422069, "Pediatrics", "ObiWan", "2069");
		Appointment appoint1 = new Appointment(patient1, doctor1, appdate, apptime, "Elbow hurts", man);
		doctor1.instruct(appoint1, instruction);
		String instr = appoint1.appData.getInstructions();
		assertTrue(instr == instruction);
	}
	
	@Test
	void testInstructFail() throws Exception {
		LocalDate bDate1 = LocalDate.parse("1989-04-26");
		LocalDate bDate2 = LocalDate.parse("1990-03-24");
		LocalDate appdate = LocalDate.parse("2015-06-12");
		LocalTime apptime = LocalTime.of(10,43,12);
		String instruction = "Use ice";
		String instruction2 = "Use fire";
		ManagementSystem man = new ManagementSystem();
		Patient patient1 = new Patient("Barb Wilson", bDate1, "123 Main Street", 'F',"Cookies",420420243, "BarbWilson", "0243", new ManagementSystem());
		Doctor doctor1 = new Doctor("Obi Wan", bDate2, "123 Lake Street", 'M', 123422069, "Pediatrics", "ObiWan", "2069");
		Appointment appoint1 = new Appointment(patient1, doctor1, appdate, apptime, "Elbow hurts",man);
		doctor1.instruct(appoint1, instruction);
		doctor1.instruct(appoint1, instruction2);
		String instr = appoint1.appData.getInstructions();
		assertFalse(instr == instruction);
	}

	@Test
	void testPrescribePass() throws Exception {
		LocalDate bDate1 = LocalDate.parse("1989-04-26");
		LocalDate bDate2 = LocalDate.parse("1990-03-24");
		LocalDate appdate = LocalDate.parse("2015-06-12");
		LocalTime apptime = LocalTime.of(10,43,12);
		String prescribe = "Advil";
		ManagementSystem man = new ManagementSystem();
		Patient patient1 = new Patient("Barb Wilson", bDate1, "123 Main Street", 'F',"Cookies",420420243, "BarbWilson", "0243", new ManagementSystem());
		Doctor doctor1 = new Doctor("Obi Wan", bDate2, "123 Lake Street", 'M', 123422069, "Pediatrics", "ObiWan", "2069");
		Appointment appoint1 = new Appointment(patient1, doctor1, appdate, apptime, "Elbow hurts",man);
		doctor1.prescribe(appoint1, prescribe);
		String actualPrescribe = appoint1.appData.getPrescription();
		assertTrue(actualPrescribe == prescribe);
	}
	
	@Test
	void testPrescribeFail() throws Exception {
		LocalDate bDate1 = LocalDate.parse("1989-04-26");
		LocalDate bDate2 = LocalDate.parse("1990-03-24");
		LocalDate appdate = LocalDate.parse("2015-06-12");
		LocalTime apptime = LocalTime.of(10,43,12);
		String prescribe = "Advil";
		String prescribe2 = "Tylenol";
		ManagementSystem man = new ManagementSystem();
		Patient patient1 = new Patient("Barb Wilson", bDate1, "123 Main Street", 'F',"Cookies",420420243, "BarbWilson", "0243", new ManagementSystem());
		Doctor doctor1 = new Doctor("Obi Wan", bDate2, "123 Lake Street", 'M', 123422069, "Pediatrics", "ObiWan", "2069");
		Appointment appoint1 = new Appointment(patient1, doctor1, appdate, apptime, "Elbow hurts",man);
		doctor1.prescribe(appoint1, prescribe);
		doctor1.prescribe(appoint1, prescribe2);
		String actualPrescribe = appoint1.appData.getPrescription();
		assertFalse(actualPrescribe == prescribe);
	}
	
	@Test
	void testDiagnosePass() throws Exception {
		LocalDate bDate1 = LocalDate.parse("1989-04-26");
		LocalDate bDate2 = LocalDate.parse("1990-03-24");
		LocalDate appdate = LocalDate.parse("2015-06-12");
		String diagnose = "Broken elbow";
		LocalTime apptime = LocalTime.of(10,43,12);
		ManagementSystem man = new ManagementSystem();
		Patient patient1 = new Patient("Barb Wilson", bDate1, "123 Main Street", 'F',"Cookies",420420243, "BarbWilson", "0243", new ManagementSystem());
		Doctor doctor1 = new Doctor("Obi Wan", bDate2, "123 Lake Street", 'M', 123422069, "Pediatrics", "ObiWan", "2069");
		Appointment appoint1 = new Appointment(patient1, doctor1, appdate, apptime, "Elbow hurts",man);
		doctor1.diagnose(appoint1, diagnose);
		String actualDiagnosis = appoint1.appData.getDiagnosis();
		assertTrue(actualDiagnosis == diagnose);
	}
	
	@Test
	void testDiagnoseFail() throws Exception {
		LocalDate bDate1 = LocalDate.parse("1989-04-26");
		LocalDate bDate2 = LocalDate.parse("1990-03-24");
		LocalDate appdate = LocalDate.parse("2015-06-12");
		String diagnose = "Broken elbow";
		String diagnose2 = "Broken pinky";
		LocalTime apptime = LocalTime.of(10,43,12);
		ManagementSystem man = new ManagementSystem();
		Patient patient1 = new Patient("Barb Wilson", bDate1, "123 Main Street", 'F',"Cookies",420420243, "BarbWilson", "20243", new ManagementSystem());
		Doctor doctor1 = new Doctor("Obi Wan", bDate2, "123 Lake Street", 'M', 123422069, "Pediatrics", "ObiWan", "2069");
		Appointment appoint1 = new Appointment(patient1, doctor1, appdate, apptime, "Elbow hurts",man);
		doctor1.diagnose(appoint1, diagnose);
		doctor1.diagnose(appoint1, diagnose2);
		String actualDiagnos = appoint1.appData.getDiagnosis();
		assertFalse(actualDiagnos == diagnose);
	}

}
