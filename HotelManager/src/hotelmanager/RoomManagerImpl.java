/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package hotelmanager;

import Entities.Room;
import hotelmanager.RoomManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author xpetovks
 */
public class RoomManagerImpl implements RoomManager{
    
    public static final Logger logger = Logger.getLogger(RoomManagerImpl.class.getName());
    private Connection conn;
    private ResultSet resultSet = null;

    
    @Override
    public void createRoom(Room room) throws ServiceFailureException{
            
        if (room == null) {
            throw new IllegalArgumentException("room is null");            
        }
        if (room.getId() != null) {
            throw new IllegalArgumentException("room id is already set");            
        }
        if (room.getPriceForNight() < 0) {
            throw new IllegalArgumentException("grave column is negative number");            
        }
        if (room.getCapacity()<= 0) {
            throw new IllegalArgumentException("grave row is negative number");            
        }
        if (room.getDescription() == null) {
            throw new IllegalArgumentException("grave column is negative number");            
        }
        if (room.getRoomNumber() == null) {
            throw new IllegalArgumentException("grave column is negative number");            
        }

        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO ROOM (number,capacity,price,decription) VALUES (?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            st.setString(1, room.getRoomNumber());
            st.setInt(2, room.getCapacity());
            st.setInt(3, room.getPriceForNight());
            st.setString(4, room.getDescription());
            int addedRows = st.executeUpdate();
            if (addedRows != 1) {
                throw new ServiceFailureException("Internal Error: More rows "
                        + "inserted when trying to insert room " + room);
            }            
            
            ResultSet keyRS = st.getGeneratedKeys();
            room.setId(getKey(keyRS,room));
            
        } catch (SQLException ex) {
            throw new ServiceFailureException("Error when inserting room " + room, ex);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                    logger.log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private Long getKey(ResultSet keyRS, Room room) throws ServiceFailureException, SQLException {
        if (keyRS.next()) {
            if (keyRS.getMetaData().getColumnCount() != 1) {
                throw new ServiceFailureException("Internal Error: Generated key"
                        + "retriving failed when trying to insert room " + room
                        + " - wrong key fields count: " + keyRS.getMetaData().getColumnCount());
            }
            Long result = keyRS.getLong(1);
            if (keyRS.next()) {
                throw new ServiceFailureException("Internal Error: Generated key"
                        + "retriving failed when trying to insert room " + room
                        + " - more keys found");
            }
            return result;
        } else {
            throw new ServiceFailureException("Internal Error: Generated key"
                    + "retriving failed when trying to insert room " + room
                    + " - no key found");
        }
    }

    
    
    @Override
    public void updateRoom(Room room) throws ServiceFailureException{
        if (room == null) {
            throw new IllegalArgumentException("room is null");            
        }
        if (room.getId() == null) {
            throw new IllegalArgumentException("room id is null");            
        }
        if (room.getPriceForNight() < 0) {
            throw new IllegalArgumentException("grave column is negative number");            
        }
        if (room.getCapacity()<= 0) {
            throw new IllegalArgumentException("grave row is negative number");            
        }
        if (room.getDescription() == null) {
            throw new IllegalArgumentException("grave column is negative number");            
        }
        if (room.getRoomNumber() == null) {
            throw new IllegalArgumentException("grave column is negative number");            
        }

        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE ROOM SET number=?,capacity=?,price=?,description=? WHERE id=?",
                    Statement.RETURN_GENERATED_KEYS);
            st.setString(1, room.getRoomNumber());
            st.setInt(2, room.getCapacity());
            st.setInt(3, room.getPriceForNight());
            st.setString(4, room.getDescription());
            st.setLong(5, room.getId());
            int updatedRows = st.executeUpdate();
            if (updatedRows != 1) {
                throw new ServiceFailureException("Internal Error: More rows "
                        + "updated when trying to update room " + room);
            }            
            
        } catch (SQLException ex) {
            throw new ServiceFailureException("Error when inserting grave " + room, ex);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                    logger.log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    @Override
    public void deleteRoom(Room room) throws ServiceFailureException{
        if (room == null) {
            throw new IllegalArgumentException("room is null");            
        }
        if (room.getId() == null) {
            throw new IllegalArgumentException("room id is null");            
        }
        if (room.getPriceForNight() < 0) {
            throw new IllegalArgumentException("grave column is negative number");            
        }
        if (room.getCapacity()<= 0) {
            throw new IllegalArgumentException("grave row is negative number");            
        }
        if (room.getDescription() == null) {
            throw new IllegalArgumentException("grave column is negative number");            
        }
        if (room.getRoomNumber() == null) {
            throw new IllegalArgumentException("grave column is negative number");            
        }

        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "DELETE FROM ROOM WHERE id=?",
                    Statement.RETURN_GENERATED_KEYS);
            st.setLong(1, room.getId());
            int deletedRows = st.executeUpdate();
            if (deletedRows == 0) {
                throw new ServiceFailureException("Internal Error: Room you "
                        + "delete was not in db " + room);
            }            
            if (deletedRows > 1) {
                throw new ServiceFailureException("Internal Error: More rows "
                        + "deleted when trying to delete room " + room);
            }            
            
        } catch (SQLException ex) {
            throw new ServiceFailureException("Error when deleting grave " + room, ex);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                    logger.log(Level.SEVERE, null, ex);
                }
            }
        }
    }    

    @Override
    public List<Room> findAllRooms() throws ServiceFailureException{
        resultSet = null;
        List<Room> roomsList = new ArrayList<Room>();
        
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "SELECT number,capacity,price,decription FROM ROOM");
            resultSet = st.executeQuery();
            if (resultSet == null) {
                return null;
            }            
            while(resultSet.next()){
            roomsList.add(resultSetConvertor(resultSet));
        }

        } catch (SQLException ex) {
            throw new ServiceFailureException("Error when executing findall ", ex);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                    logger.log(Level.SEVERE, null, ex);
                }
            }
        }
        
        return roomsList;
    
    }
    
    @Override
    public Room findRoom(Long id) throws ServiceFailureException{
        resultSet = null;
        Room room = new Room();
        if (id== null) {
            throw new IllegalArgumentException("room id is null");            
        }
        
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "SELECT number,capacity,price,decription FROM ROOM WHERE id=?");
            st.setLong(1, id);
            resultSet = st.executeQuery();
            if (resultSet == null) {
                throw new ServiceFailureException("Internal Error: No result "
                        + "for your id " + id);
            }
            room = resultSetConvertor(resultSet);
        } catch (SQLException ex) {
            throw new ServiceFailureException("Error when deleting grave " + id, ex);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                    logger.log(Level.SEVERE, null, ex);
                }
            }
        }
        
        return room;
    }

    private Room resultSetConvertor(ResultSet resultSet)throws SQLException {
        Room room = new Room();
        room.setId(resultSet.getLong("id"));
        room.setCapacity(resultSet.getInt("capacity"));
        room.setDescription(resultSet.getString("capacity"));
        room.setPriceForNight(resultSet.getInt("price"));
        room.setRoomNumber(resultSet.getString("number"));
        return room;
    }

}
