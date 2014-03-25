/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import Entities.Guest;
import java.util.List;

/**
 *
 * @author xpetovks
 */
public interface GuestManager {
    /**
     * 
     * 
     * @param guest
     * @return guest with id 
     * 
     */
    
    public Guest createGuest(Guest guest);
    
    public void updateGuest(Guest guest);
    
    public void deleteGuest(Guest guest);
    
    public Guest findGuest(Integer id);
    
    public List<Guest> findAllGuests();
}
