package sprint2;

//contains room number, occupancy value, and isOccupied()
public class Room {
	private int roomNumber;
	private boolean occupancy;
	
	public Room (int roomNum) {
		this.roomNumber = roomNum;
	}
	public Room(int roomNum, boolean occup){
		this.roomNumber = roomNum;
		this.occupancy = occup;
	}
	
	boolean isOccupied() {
		if(getOccupancy() == true)
			return true;
		else
			return false;			
	}
	
	public int getRoomNumber() {
		return roomNumber;
	}
	
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}
	
	public boolean getOccupancy() {
		return occupancy;
	}
	
	public void setOccupancy(boolean occupancy) {
		this.occupancy = occupancy;
	}
	public boolean equals(Object o) {
		if (o instanceof Room) {
			Room r = (Room) o;
			return this.getRoomNumber() == r.getRoomNumber();
		}
		return false;
	}
}
