/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelmanager;

import Entities.Room;
import Interfaces.RoomManager;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author xpetovks
 */
public abstract class RoomManagerImpl implements RoomManager
{
    private Connection conn;
    public static final Logger logger = Logger.getLogger(RoomManagerImpl.class.getName());
    public RoomManagerImpl(Connection conn) {
        this.conn = conn;
    }
    //@Override
    public Room createRoom(Room room) throws ServiceFailureException {
        
        if (room == null) {
            throw new IllegalArgumentException("room is null");            
        }
        if (room.getId() != null) {
            throw new IllegalArgumentException("room id is already set");            
        }
        if (room.getCapacity() < 0) {
            throw new IllegalArgumentException("room capacity is negative number");            
        }
        if (room.getPriceForNight()< 0) {
            throw new IllegalArgumentException("room price is negative number");            
        }
        if (room.getRoomNumber() == null) {
            throw new IllegalArgumentException("room number is null");            
        }

        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO Room (capacity,description,price,number) VALUES (?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, room.getCapacity());
            st.setString(2, room.getDescription());
            st.setInt(3, room.getPriceForNight());
            st.setString(4, room.getRoomNumber());
            
       
            int addedRows = st.executeUpdate();
            if (addedRows != 1) {
                throw new ServiceFailureException("internal Error: More rooms "
                        + "inserted when trying to insert room " + room);
            }            
            
            ResultSet keyRS = st.getGeneratedKeys();
            room.setId((getKey(keyRS, room)));
            
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
        return room;
    }
        private int getKey(ResultSet keyRS, Room room) throws ServiceFailureException, SQLException {
        if (keyRS.next()) {
            if (keyRS.getMetaData().getColumnCount() != 1) {
                throw new ServiceFailureException("internal Error: Generated key"
                        + "retriving failed when trying to insert room " + room
                        + " - wrong key fields count: " + keyRS.getMetaData().getColumnCount());
            }
            int result = keyRS.getInt(1);
            if (keyRS.next()) {
                throw new ServiceFailureException("internal Error: Generated key"
                        + "retriving failed when trying to insert room " + room
                        + " - more keys found");
            }
            return result;
        } else {
            throw new ServiceFailureException("internal Error: Generated key"
                    + "retriving failed when trying to insert room " + room
                    + " - no key found");
        }
    }

    //@Override
    public Room getRoom(int id) throws ServiceFailureException {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "SELECT id,col,row,capacity,note FROM room WHERE id = ?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            
            if (rs.next()) {
                Room room = resultSetToRoom(rs);

                if (rs.next()) {
                    throw new ServiceFailureException(
                            "internal error: More entities with the same id found "
                            + "(source id: " + id + ", found " + room + " and " + resultSetToRoom(rs));                    
                }            
                
                return room;
            } else {
                return null;
            }
            
        } catch (SQLException ex) {
            throw new ServiceFailureException(
                    "Error when retrieving room with id " + id, ex);
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

    private Room resultSetToRoom(ResultSet rs) throws SQLException {
        Room room = new Room();
        room.setId(rs.getInt("id"));
        room.setCapacity(rs.getInt("capacity"));
        room.setDescription(rs.getString("descrition"));        
        room.setPriceForNight(rs.getInt("price"));
        room.setRoomNumber(rs.getString("roomnumber"));
        return room;
    }
     // capacity description price roomnumber
           
    /*
    
    
    // POZOR! Toto je jenom jednoducha ukazka, ktera neni vlaknove bezpecna a 
    // neresi transakce. Spravne reseni s transakcemi a pouzitim DataSource 
    // bude v prikladu k dalsi uloze    
    
    
    @Override
    public void createRoom(Room room) throws ServiceFailureException {
        
     
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO GRAVE (col,row,capacity,note) VALUES (?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            st.setint(1, room.getColumn());
            st.setint(2, room.getRow());
            st.setint(3, room.getCapacity());
            st.setString(4, room.getNote());
            
            int addedRows = st.executeUpdate();
            if (addedRows != 1) {
                throw new ServiceFailureException("internal Error: More rows "
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



    @Override
    public void updateRoom(Room room) throws ServiceFailureException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteRoom(Room room) throws ServiceFailureException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Room> findAllRooms() throws ServiceFailureException {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "SELECT id,col,row,capacity,note FROM room");
            ResultSet rs = st.executeQuery();
            
            List<Room> result = new ArrayList<Room>();
            while (rs.next()) {
                result.add(resultSetToRoom(rs));
            }
            return result;
            
        } catch (SQLException ex) {
            throw new ServiceFailureException(
                    "Error when retrieving all rooms", ex);
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
    
    */
    
    /*
    @Override
    public Room createRoom(Room room){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    */
    @Override
    public void updateRoom(Room room){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void deleteRoom(Room room){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public List<Room> findAllRooms(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    //@Override
    public Room findRoom(int id){
        throw new UnsupportedOperationException("Not supported yet.");
    }

   

    @Override
    public Room findRoom(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   // @Override
    /* public Room createRoom(Room room) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
}
