 package sprint2;

//added this class to modularize Appointment
// 10/11/19 - Added a constructor - Mat
public class AppointmentData {
	private Room room;
	private int height;
	private double weight;
	private double temp;
	private String bloodP;
	private String diagnosis;
	private String prescription;
	private String instructions;
	private String test;
	private String ID;
	
	public AppointmentData(String ID) {
		this.ID = ID;
	}
	
	public Room getRoom() {
		return room;
	}
	public String getID() {
		return ID;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	
	public int getHeight() {
		return height;
	}
	void setHeight(int height) {
		this.height = height;
	}
	public double getWeight() {
		return weight;
	}
	void setWeight(double weight) {
		this.weight = weight;
	}
	public double getTemp() {
		return temp;
	}
	void setTemp(double temp) {
		this.temp = temp;
	}
	public String getPBloodP() {
		return bloodP;
	}
	void setPBloodP(String bloodP) {
		this.bloodP = bloodP;
	}
	public String getDiagnosis() {
		return diagnosis;
	}
	void setDiagnosis(String diag) {
		this.diagnosis = diag;
	}
	
	public String getPrescription() {
		return prescription;
	}
	void setPrescription(String presc) {
		this.prescription = presc;
	}
	
	public String getInstructions() {
		return instructions;
	}
	void setInstructions(String instr) {
		this.instructions = instr;
	}
	public String getTest() {
		return test;
	}
	void setTest(String test) {
		this.test = test;
	}	
}
