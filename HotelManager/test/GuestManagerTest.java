/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Entities.Guest;
//import Entities.Guest;
import Interfaces.GuestManager;
import hotelmanager.GuestManagerImpl;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
//import date;
/**
 *
 * @author Miro
 */
public class GuestManagerTest {
    private Guest guest;
    private Guest firstNameNull;
    private Guest firstNameEmpty;
    private Guest lastNameNull;
    private Guest lastNameEmty;
//    private Guest wrongDateOrder;
    private GuestManager guestManager = new GuestManagerImpl();
    private Guest lastNameEmpty;
    private Guest nullGuest;
    private SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
    private Guest wrongOrderDate;
    
    public GuestManagerTest()
    {
    
    }
    @Before
    public void setUp() throws ParseException
    {
        guest = new Guest();
        guest = createGuest(1,"Miro","Dou",DATE_FORMAT.parse("5,2,2012"),DATE_FORMAT.parse("6,4,2012"));
        
        firstNameNull = new Guest();
        firstNameNull = createGuest(2,null,"Dou",DATE_FORMAT.parse("5,2,2012"),DATE_FORMAT.parse("6,4,2012"));
        
        firstNameEmpty = new Guest();
        firstNameEmpty = createGuest(3,"","Dou",DATE_FORMAT.parse("5,2,2012"),DATE_FORMAT.parse("6,4,2012"));
        
        lastNameNull = new Guest();
        lastNameNull = createGuest(4,"Miro",null,DATE_FORMAT.parse("5,2,2012"),DATE_FORMAT.parse("6,4,2012"));
        
        lastNameEmpty = new Guest();
        lastNameEmpty = createGuest(5,"Miro","",DATE_FORMAT.parse("5,2,2012"),DATE_FORMAT.parse("6,4,2012"));
        
        wrongOrderDate = new Guest();
        wrongOrderDate = createGuest(6,"Miro","",DATE_FORMAT.parse("6,4,2012"),DATE_FORMAT.parse("5,2,2012"));
        
        nullGuest = null;
        
                 
       
    
    }
    @Test
    public void createGuestTest()
    {
        try{
            guestManager.createGuest(firstNameNull);
            fail("Create test - null capacity no exception");
        }catch (NullPointerException ex){
        }catch (Exception ex){
                fail ("Create test - null capacity bad exception" + ex);
        }
        
        try{
            guestManager.createGuest(firstNameEmpty);
            fail("Create test - zero capacity no exception");
        }catch (IllegalArgumentException ex){
        }catch (Exception ex){
                fail ("Create test - zero capacity bad exception" + ex);
        }
        
        try{
            guestManager.createGuest(lastNameEmpty);
            fail("Create test - empty description no exception");
        }catch (IllegalArgumentException ex){
        }catch (Exception ex){
                fail ("Create test - empty description bad exception" + ex);
        }
        
        try{
            guestManager.createGuest(lastNameNull);
            fail("Create test - null description no exception");
        }catch (NullPointerException ex){
        }catch (Exception ex){
                fail ("Create test - null description bad exception" + ex);
        }
        

        
               
        try{
            guestManager.createGuest(nullGuest);
            fail("Create test - guest is null no exception");
        }catch (IllegalArgumentException ex){
        }catch (Exception ex){
                fail ("Create test - guest is null bad exception" + ex);
        }
        try{
            guestManager.createGuest(wrongOrderDate);
            fail("Create test - CheckInDate.after(CheckOutDate)");
        }catch (IllegalArgumentException ex){
        }catch (Exception ex){
                fail ("Create test - guest is null bad exception" + ex);
        }
        
        try{
            Guest returnedGuest = guestManager.createGuest(guest);
            assertDeepEquals(guest, returnedGuest);
        }catch (Exception ex){
                fail ("Create test - zero capacity bad exception" + ex);
        }
        
        
    }
    
    
    @Test
    public void updateGuestImplTest() throws ParseException{
//        Guest guestCapacityNull = createGuest();
//        guestManager.createGuest(guestCapacityNull);
//        guestCapacityNull.setCapacity(null);
        
        Guest guest1 = createGuest(1,"Miro","Dou",DATE_FORMAT.parse("5,2,2012"),DATE_FORMAT.parse("6,4,2012"));
        Guest guest2 = createGuest(1,"Miro","Dou",DATE_FORMAT.parse("5,2,2012"),DATE_FORMAT.parse("6,4,2012"));
        Guest guest3 = createGuest(1,"Miro","Dou",DATE_FORMAT.parse("5,2,2012"),DATE_FORMAT.parse("6,4,2012"));
        
        //will be replaced with manual transaction
        guestManager.createGuest(guest1); 
        guestManager.createGuest(guest2);
        guestManager.deleteGuest(guest2);
        
        try{
            guest1.setFirstName("Updated Guest");
            guestManager.updateGuest(guest);
        }catch (Exception ex){
                fail ("Update test - bad exception" + ex);
        }
        
        Guest equalGuest = guestManager.findGuest(guest1.getId());
        assertDeepEquals(guest1, equalGuest);
        
        try{
            guest2.setFirstName("Updated Guest2");
            guestManager.updateGuest(guest2);
            fail("deleted guest - no exception");
        }catch (IllegalArgumentException ex){
        }catch (Exception ex){
            fail("deleted guest - bad exception");
        }
        
        try{
            guest3.setFirstName("Updated Guest3");
            guestManager.updateGuest(guest3);
            fail("guest no ID - no exception");
        }catch (NullPointerException ex){
        }catch (Exception ex){
            fail("guest no ID - bad exception");
        }
        
        try{
            Guest guestNull = guest1;
            guestNull.setFirstName(null);
            guestNull.setLastName(null);
            guestManager.updateGuest(guestNull);
            fail("Update test - null arguments");
        }catch (NullPointerException ex){
        }catch (Exception ex){
                fail ("Create test - null capacity bad exception" + ex);
        }
        
        try{
            Guest guestEmpty = guest1;
            guestEmpty.setFirstName("");
            guestEmpty.setLastName("");
            guestManager.updateGuest(guestEmpty);
            fail("Update test - empty arguments");
        }catch (IllegalArgumentException ex){
        }catch (Exception ex){
                fail ("Update test - empty arguments bad exception" + ex);
        }
        
        
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
    
    private void assertDeepEquals(Guest guest1, Guest guest2){
        assertEquals(guest1.getFirstName(), guest2.getFirstName());
        assertEquals(guest1.getLastName(), guest2.getLastName());
        assertEquals(guest1.getCheckInDate(), guest2.getCheckInDate() );
        assertEquals(guest1.getCheckOutDate(), guest2.getCheckOutDate());
    }
}
