package sprint2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RoomTest {

	@Test
	void testRoom() {
		Room room1 = new Room(12, true);
		Room room2 = new Room(14, true);
		assertFalse(room1.equals(room2));
	}

	@Test
	void testIsOccupiedSuccess() {
		Room room1 = new Room(12, true);
		boolean occ = room1.isOccupied();
		boolean sol = true;
		assertTrue(occ == sol);
	}
	
	@Test
	void testIsOccupiedFail() {
		Room room1 = new Room(12, false);
		boolean occ = room1.isOccupied();
		boolean sol = true;
		assertFalse(occ == sol);
	}

	@Test
	void testGetRoomNumberSuccess() {
		Room room1 = new Room(12, true);
		int num = room1.getRoomNumber();
		int sol = 12;
		assertTrue(num == sol);
	}
	
	@Test
	void testGetRoomNumberFail() {
		Room room1 = new Room(12, true);
		int num = room1.getRoomNumber();
		int sol = 14;
		assertFalse(num == sol);
	}

}
