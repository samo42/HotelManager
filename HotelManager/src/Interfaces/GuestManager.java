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
public interface GuestManager {
    
    public Room createGuest(Room guest);
    
    public void updateGuest(Room guest);
    
    public void deleteGuest(Room guest);
    
    public Room findGuest(Integer id);
    
    public List<Room> findAllGuests();
}
