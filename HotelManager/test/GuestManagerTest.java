
import Entities.Guest;
import Interfaces.GuestManager;
import hotelmanager.GuestManagerImpl;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import org.junit.Before;
import org.junit.Test;
import java.text.SimpleDateFormat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Miro
 */
public class GuestManagerTest {
    private Guest guest;
    private SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
    private  GuestManagerImpl manager;
 
    @Before
    public void setUp() throws SQLException {
        manager = new GuestManagerImpl();
    }

    @Test
    public void createGuest() throws ParseException {
        guest = new Guest(); //newGuest1 ("Miro","Dou",DATE_FORMAT.parse("5,2,2012"),DATE_FORMAT.parse("6,4,2012");
        guest = createGuest(4 ,"Miro","Dou",DATE_FORMAT.parse("5,2,2012"),DATE_FORMAT.parse("6,4,2012"));
        
       // guest = createGuest(1,"Miro","Dou",DATE_FORMAT.parse("5,2,2012"),DATE_FORMAT.parse("6,4,2012"));
        int id = guest.getId();
        assertNotNull(id);
        Guest result = manager.findGuest(id);
        assertEquals(guest, result);
        assertNotSame(guest, result);
        assertDeepEquals(guest, result);
        
    }
        @Test
    public void findGuestTest() throws ParseException {
        
        assertNull(manager.findGuest(11));
        
        Guest grave = createGuest(11 ,"Miro","Dou",DATE_FORMAT.parse("5,2,2012"),DATE_FORMAT.parse("6,4,2012"));
        manager.createGuest(grave);
        int graveId = grave.getId();

        Guest result = manager.findGuest(graveId);
        assertEquals(grave, result);
        assertDeepEquals(grave, result);
    }
      @Test
    public void deleteGuestTest() throws ParseException {

        Guest guest1 = createGuest(1 ,"Miro","Dou",DATE_FORMAT.parse("5,2,2012"),DATE_FORMAT.parse("6,4,2012"));
        Guest guest2 = createGuest(5 ,"Fero","Novak",DATE_FORMAT.parse("7,2,2012"),DATE_FORMAT.parse("9,4,2012"));

        assertNotNull(manager.findGuest(guest1.getId()));
        assertNotNull(manager.findGuest(guest2.getId()));

        manager.deleteGuest(guest1);
        
        assertNull(manager.findGuest(guest1.getId()));
        assertNotNull(manager.findGuest(guest2.getId()));        
    }
       
       private Guest createGuest(int id, String firstName, String lastName, Date chcekInDate, Date chcekOutDate)
    {
        Guest guest = new Guest();
        guest.setId(id);
        guest.setFirstName(firstName);
        guest.setLastName(lastName);
        guest.setCheckInDate(chcekInDate);
        guest.setCheckOutDate(chcekOutDate);
        return guest;
        
    }
    
       
       
    private void assertDeepEquals(Guest expected, Guest actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getCheckInDate(), actual.getCheckInDate());
        assertEquals(expected.getCheckOutDate(), actual.getCheckOutDate());
    }
}
