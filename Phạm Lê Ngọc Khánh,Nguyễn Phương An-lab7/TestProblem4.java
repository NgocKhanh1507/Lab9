import java.time.LocalTime;
import java.time.LocalDate;
public class TestProblem4 {
    public static void main(String[] args){
        CampusResourceSystem system = new CampusResourceSystem("My Campus System");
        User requester1 = new StudentUser("A021", "haduc234@gmail.com", "Duc", "CS", 3);
        User requester2 = new StaffUser("B357", "mpthuy@gmail.com", "Thuy", "IT", "Lecturer");
            
            Room room1 = new Room("IU2089", "Biology", true, 4, "A2");
            Room room2 = new Room("IU2345", "Technology", false, 1, "A1");
            
            BookingRequest b1 = new BookingRequest("B3567", requester1, room1, LocalDate.of(2026, 5, 20), LocalTime.of(8, 30), LocalTime.of(14,25), "Reference", BookingStatus.PENDING);
            BookingRequest b2 = new BookingRequest("B4321", requester2, room2, LocalDate.of(2026, 5, 1), LocalTime.of(9, 45), LocalTime.of(20, 15), "Research", BookingStatus.APPROVED);
            
            User borrower = new StudentUser("B5689", "nmtrang@gmail.com", "Trang", "DS", 2);
    
            Equipment e1 = new Equipment("IU3456", "test-tube", true, "School's property", 3 );
            Equipment e2 = new Equipment("IU2345", "Printer", false, "School's property", 2);
            
            LoanRecord l = new LoanRecord("N4578", borrower, e1, LocalDate.of(2026, 4, 8), LocalDate.of(2026, 4,19), LocalDate.of(2026, 4, 20), LoanStatus.BORROWED);
        try{
            system.addUser(requester1);
            system.addUser(requester2);
            system.addUser(borrower);
            
            system.addResource(room1);
            system.addResource(room2);
            system.addResource(e1);
            system.addResource(e2);
            
            system.addBooking(b1);
            system.addBooking(b2);
            
            system.addLoanRecord(l);
            
        
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        system.printAllUsers();
            system.printAllResources();
            system.printAllBookings();
            system.printAllLoans();
            System.out.println(system);
    }
}