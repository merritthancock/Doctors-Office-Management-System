package sprint2;
import java.time.LocalDate;

//	9/30/19 - Added getters for variables -Merritt
// 10/11/19 - Gave the Person class an Appointment object for App and AppData ops - Mat
//11/20/19 - Made password a String on account of an integer not being able to start with a 0.
public abstract class Person {
    private String name;
    private LocalDate birthDate;
    private String address;
    private char sex;
    private int ssn;
    private String uname;
    private String password;
    public Appointment appt;

    public Person(String name, LocalDate birthDate, String address, char sex, int ssn, String uname, String password){
        this.name = name;
        this.birthDate = birthDate;
        this.address = address;
        this.sex = sex;
        this.ssn = ssn;
        this.uname = uname;
        this.password = password;
    }

    public String getName(){
        return name;
    }

    void setName(String name){
        this.name = name;
    }

    public String getAddress(){
        return address;
    }

    void setAddress(String addr) {
        address = addr;
    }

    public LocalDate getBirthDate(){
        return birthDate;
    }

    void setBirthDate(LocalDate birth){
        this.birthDate = birth;
    }
    
    public char getSex(){
        return sex;
    }

    void setSex(char sex){
        this.sex = sex;
    }

    public int getSSN(){
        return ssn;
    }
    
    void setSSN(int ssn){
        this.ssn = ssn;
    }
    Appointment getAppt(){
        return appt;
    }
    
    void setUname(String uname) {
    	this.uname = uname;
    }
    
    String getUname() {
    	return uname;
    }
    
    void setPassword(String password) {
    	this.password = password;
    }
    
    String getPassword() {
    	return password;
    }
    
    public void setAppt(Appointment a){

        this.appt = a;
    }
    
    public boolean equals(Object o){
        if (o instanceof Person){
            Person p = (Person)o;
            return this.getSSN() == p.getSSN();

        }
        return false;
    }
}