/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelmanager;

import Entities.Guest;
import Interfaces.RoomManager;
import java.util.List;

/**
 *
 * @author xpetovks
 */
public class RoomManagerImpl implements RoomManager{
    
    @Override
    public Guest createRoom(Guest room){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void updateRoom(Guest room){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void deleteRoom(Guest room){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public List<Guest> findAllRooms(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public Guest findRoom(Integer id){
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
