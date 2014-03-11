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
    
    @Test
    public void createRoomTest(){
        
        try{
            roomManager.createRoom(roomCapacityZero);
            fail("Create test - zero capacity no exception");
        }catch (IllegalArgumentException ex){
        }catch (Exception ex){
                fail ("Create test - zero capacity bad exception" + ex);
        }
        
        try{
            roomManager.createRoom(roomDescriptionEmpty);
            fail("Create test - empty description no exception");
        }catch (IllegalArgumentException ex){
        }catch (Exception ex){
                fail ("Create test - empty description bad exception" + ex);
        }
        
        try{
            roomManager.createRoom(roomDescriptionNull);
            fail("Create test - null description no exception");
        }catch (NullPointerException ex){
        }catch (Exception ex){
                fail ("Create test - null description bad exception" + ex);
        }
        
        try{
            roomManager.createRoom(roomNumberEmpty);
            fail("Create test - empty number no exception");
        }catch (IllegalArgumentException ex){
        }catch (Exception ex){
                fail ("Create test - empty number bad exception" + ex);
        }
        
        try{
            roomManager.createRoom(roomNumberNull);
            fail("Create test - null number no exception");
        }catch (NullPointerException ex){
        }catch (Exception ex){
                fail ("Create test - null number bad exception" + ex);
        }
        
        try{
            roomManager.createRoom(nullRoom);
            fail("Create test - room is null no exception");
        }catch (IllegalArgumentException ex){
        }catch (Exception ex){
                fail ("Create test - room is null bad exception" + ex);
        }
        
        try{
            Room returnedRoom = roomManager.createRoom(room);
            Room foundRoom = roomManager.findRoom(returnedRoom.getId());
            assertDeepEquals(foundRoom, returnedRoom);
        }catch (Exception ex){
                fail ("Create test - zero capacity bad exception" + ex);
        }
        
    }
    
    @Test
    public void updateRoomImplTest(){
        Room room1 = createRoom(1,"Small Room",100,"001");
        Room room2 = createRoom(2,"Double Room", 180,"002");
        Room room3 = createRoom(2,"Twin Room", 180, "003");
        
        //will be replaced with manual transaction
        roomManager.createRoom(room1); 
        roomManager.createRoom(room2);
        roomManager.deleteRoom(room2);
        
        try{
            room1.setDescription("Updated Room");
            roomManager.updateRoom(room);
        }catch (Exception ex){
                fail ("Update test - bad exception" + ex);
        }
        
        Room equalRoom = roomManager.findRoom(room1.getId());
        assertDeepEquals(room1, equalRoom);
        
        try{
            room2.setDescription("Updated Room2");
            roomManager.updateRoom(room2);
            fail("deleted room - no exception");
        }catch (IllegalArgumentException ex){
        }catch (Exception ex){
            fail("deleted room - bad exception");
        }
        
        try{
            room3.setDescription("Updated Room3");
            roomManager.updateRoom(room3);
            fail("room no ID - no exception");
        }catch (NullPointerException ex){
        }catch (Exception ex){
            fail("room no ID - bad exception");
        }
        
        try{
            Room roomNull = room1;
            roomNull.setDescription(null);
            roomNull.setRoomNumber(null);
            roomManager.updateRoom(roomNull);
            fail("Update test - null arguments");
        }catch (NullPointerException ex){
        }catch (Exception ex){
                fail ("Create test - null capacity bad exception" + ex);
        }
        
        try{
            Room roomEmpty = room1;
            roomEmpty.setDescription("");
            roomEmpty.setRoomNumber("");
            roomManager.updateRoom(roomEmpty);
            fail("Update test - empty arguments");
        }catch (IllegalArgumentException ex){
        }catch (Exception ex){
                fail ("Update test - empty arguments bad exception" + ex);
        }
        
        try{
            Room roomZeroCapacity = room1;
            roomZeroCapacity.setCapacity(0);
            roomManager.updateRoom(roomZeroCapacity);
            fail("Update test - zero capacity");
        }catch (IllegalArgumentException ex){
        }catch (Exception ex){
                fail ("Create test - zero capacity bad exception" + ex);
        }
        
    }
    
    
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
    
    @Test
    public void deleteTest(){
        Room room1 = createRoom(10, "First Room", 20, "001");
        Room room2 = createRoom(2, "Second Room", 211, "002");
        Room notInDbRoom = createRoom(1, "Third Room", 223, "003");
        
        //implement later
        Room createdRoom = roomManager.createRoom(room1);
        Room deletedRoom = roomManager.createRoom(room2);
        roomManager.deleteRoom(deletedRoom);
        
        try{
            roomManager.deleteRoom(createdRoom);
        }catch (Exception ex){
        fail("delete test - bad exception");
        }
        try{
            roomManager.findRoom(createdRoom.getId());
            fail("delete test - deleted room found");
        }catch (NullPointerException ex){
        }catch (Exception ex){
                fail ("delete test - bad exception" + ex);
        }
        
        try{
            roomManager.deleteRoom(deletedRoom);
            fail("delete test - deleted room");
        }catch (NullPointerException ex){
        }catch (Exception ex){
                fail ("delete test - bad exception" + ex);
        }
        
        try{
            roomManager.deleteRoom(notInDbRoom);
            fail("delete test - deleted non existing room");
        }catch (NullPointerException ex){
        }catch (Exception ex){
                fail ("delete test - bad exception" + ex);
        }
        
    }
    
}