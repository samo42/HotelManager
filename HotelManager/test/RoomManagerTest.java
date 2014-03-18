/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import Entities.Room;
import Interfaces.RoomManager;
import hotelmanager.RoomManagerImpl;
import javax.net.ssl.SSLEngineResult;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author xpetovks
 */
public class RoomManagerTest {
    
    private Room room;
    private Room roomCapacityZero;
    private Room roomNumberNull;
    private Room roomNumberEmpty;
    private Room roomDescriptionNull;
    private Room roomDescriptionEmpty;
    private Room nullRoom;
    
    private RoomManager roomManager = new RoomManagerImpl();
    
    @Before
    public void setUp() {
        room = new Room();
        room = createRoom(5, "Luxury room", 200, "001");
        
        roomCapacityZero = new Room();
        roomCapacityZero = createRoom(0, "Nobody room", 20, "003");
        
        roomNumberNull = new Room();
        roomNumberNull = createRoom(4, "Nowhere room", 600, null);
                
        roomNumberEmpty = new Room();
        roomNumberEmpty = createRoom(3, "Somewhere room", 120, "");
        
        roomDescriptionNull = new Room();
        roomDescriptionNull = createRoom(7, null, 115, "004");
        
        roomDescriptionEmpty = new Room();
        roomDescriptionEmpty = createRoom(1, "", 115, "005");
        
        nullRoom = null;
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void createZeroCapacityRoomTest() {
        roomManager.createRoom(roomCapacityZero);
        fail("Create test - zero capacity no exception");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void createDecriptionEmptyRoomTest() {
        roomManager.createRoom(roomDescriptionEmpty);
        fail("Create test - empty description no exception");
    }

    @Test(expected = NullPointerException.class)
    public void createNullDescriptionRoomTest() {    
        roomManager.createRoom(roomDescriptionNull);
        fail("Create test - null description no exception");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void createEmptyNumberRoomTest() {
        roomManager.createRoom(roomNumberEmpty);
        fail("Create test - empty number no exception");
    }
    
    @Test(expected = NullPointerException.class)
    public void createNullNumberRoomTest() {
        roomManager.createRoom(roomNumberNull);
        fail("Create test - null number no exception");
    }
        
    @Test(expected = NullPointerException.class)
    public void createNullRoomTest() {
        roomManager.createRoom(nullRoom);
        fail("Create test - room is null no exception");
    }
        
    @Test
    public void createRoomTest() {
        Room returnedRoom = roomManager.createRoom(room);
        Room foundRoom = roomManager.findRoom(returnedRoom.getId());
        assertDeepEquals(foundRoom, returnedRoom);
    }
        
    @Test
    public void updateRoomImplTest(){
        Room room1 = createRoom(1,"Small Room",100,"001");
        roomManager.createRoom(room1); 

        room1.setDescription("Updated Room");
        roomManager.updateRoom(room1);

        Room equalRoom = roomManager.findRoom(room1.getId());
        assertDeepEquals(room1, equalRoom);
        }
    
    @Test(expected = IllegalArgumentException.class)
    public void updateDeletedRoom(){
        Room room2 = createRoom(2,"Double Room", 180,"002");
        
        room2.setDescription("Updated Room2");
        roomManager.updateRoom(room2);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void updateNotInDbRoom(){
        Room room3 = createRoom(2,"Twin Room", 180, "003");
        room3.setDescription("Updated Room3");
        roomManager.updateRoom(room3);
    }
    
//    @Test
//    public void deleteTest(){
//        Room room1 = createRoom(10, "First Room", 20, "001");
//        Room room2 = createRoom(2, "Second Room", 211, "002");
//        Room notInDbRoom = createRoom(1, "Third Room", 223, "003");
//        
//        Room createdRoom = roomManager.createRoom(room1);
//        Room deletedRoom = roomManager.createRoom(room2);
//        roomManager.deleteRoom(deletedRoom);
//        
//        try{
//            roomManager.deleteRoom(createdRoom);
//        }catch (Exception ex){
//        fail("delete test - bad exception");
//        }
//        try{
//            roomManager.findRoom(createdRoom.getId());
//            fail("delete test - deleted room found");
//        }catch (NullPointerException ex){
//        }catch (Exception ex){
//                fail ("delete test - bad exception" + ex);
//        }
//        
//        try{
//            roomManager.deleteRoom(deletedRoom);
//            fail("delete test - deleted room");
//        }catch (NullPointerException ex){
//        }catch (Exception ex){
//                fail ("delete test - bad exception" + ex);
//        }
//        
//        try{
//            roomManager.deleteRoom(notInDbRoom);
//            fail("delete test - deleted non existing room");
//        }catch (NullPointerException ex){
//        }catch (Exception ex){
//                fail ("delete test - bad exception" + ex);
//        }
//        
//        try{
//            roomManager.deleteRoom(null);
//            fail("delete test - deleted non existing room");
//        }catch (NullPointerException ex){
//        }catch (Exception ex){
//                fail ("delete test - bad exception" + ex);
//        }
//        
//    }
    
    
    
    private void assertDeepEquals(Room room1, Room room2){
        assertEquals(room1.getId(), room2.getId());
        assertEquals(room1.getCapacity(), room2.getCapacity());
        assertEquals(room1.getDescription(), room2.getDescription());
        assertEquals(room1.getPriceForNight(), room2.getPriceForNight());
        assertEquals(room1.getRoomNumber(), room2.getRoomNumber());
    }
    
    private static Room createRoom(int capacity, String description, Integer price, String number){
        Room room = new Room();
        room.setCapacity(capacity);
        room.setDescription(description);
        room.setPriceForNight(price);
        room.setRoomNumber(number);
        return room;
    }
    
}