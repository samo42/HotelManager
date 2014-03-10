/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Entities.Room;
import java.util.List;

/**
 *
 * @author xpetovks
 */
public interface RoomManager {
    public Room createRoom(Room room);
    
    public void updateRoom(Room room);
    
    public void deleteRoom(Room room);
    
    public List<Room> findAllRooms();
    
    public Room findRoom(Integer id);
}
