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
    /**
     * Creates new room.
     * 
     * @param room Room to be created 
     * @throws IllegalArgumentException when capacity is zero, description or
     *          number is empty
     * @throws NullPointerException when room, description or number is null
     * @return created room with ID
     */
    public Room createRoom(Room room) throws ServiceFailureException;
    /**
     * Updates existing room.
     * 
     * @param room Room to be created 
     * @throws IllegalArgumentException when capacity is zero, description,
     *          number is empty or room was not in database
     * @throws NullPointerException when room, id, description or number is null
     */
    public void updateRoom(Room room);
    /**
     * Updates existing room.
     * 
     * @param room Room to be deleted 
     * @throws NullPointerException when room or id is null
     */
    public void deleteRoom(Room room);
    /**
     * Updates existing room.
     * 
     * @return list of all rooms in system
     */
    public List<Room> findAllRooms();
    /**
     * Find room in system
     * 
     * @param id Id of room to be found
     * @throws NullPointerException when room or id is null
     * @return desired room
     */
    public Room findRoom(Integer id);
}
