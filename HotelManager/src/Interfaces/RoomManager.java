/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Entities.Guest;
import java.util.List;

/**
 *
 * @author xpetovks
 */
public interface RoomManager {
        public Guest createRoom(Guest room);
    
    public void updateRoom(Guest room);
    
    public void deleteRoom(Guest room);
    
    public List<Guest> findAllRooms();
    
    public Guest findRoom(Integer id);
}
