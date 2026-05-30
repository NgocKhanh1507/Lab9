import java.time.LocalTime;
import java.time.LocalDate;

public class TestProblem2 {
    public static void main(String[]args){
        User requester1 = new StudentUser("N0202", "nvnhat1234@gmail.com", "Nhat", "DS", 1);
        User requester2 = new StudentUser("A2707", "npan2707@gmail.com", "An", "DS", 1);
        Room room1 = new Room("IU2134", "Business", true, 2, "A1");
        Room room2 = new Room("IU25045", "Technology", true, 1, "A2");

        BookingRequest b1 = new BookingRequest("B1234", requester1, room1, LocalDate.of(2026, 4, 16), LocalTime.of(7, 30), LocalTime.of(14,25), "Reference", BookingStatus.PENDING);
        BookingRequest b2 = new BookingRequest("B5678", requester2, room2, LocalDate.of(2026, 4, 15), LocalTime.of(7, 20), LocalTime.of(20, 15), "Research", BookingStatus.APPROVED);

        User borrower1 = new StaffUser("T2403", "tttien@gmail.com", "Tien", "CS", "Lecturer");
        User borrower2 = new StudentUser("A2707", "npan2707@gmail.com", "An", "DS", 1);
        Equipment e1 = new Equipment("IU1212", "test-tube", true, "School's property", 3 );
        Equipment e2 = new Equipment("IU1006", "Printer", true, "School's property", 2);

        LoanRecord l1 = new LoanRecord("N1104", borrower1, e1, LocalDate.of(2026, 4, 12), LocalDate.of(2026, 4,19), LocalDate.of(2026, 4, 20), LoanStatus.BORROWED);
        LoanRecord l2 = new LoanRecord("L1000", borrower2, e2, LocalDate.of(2026, 3, 30), LocalDate.of(2026, 4, 5), LocalDate.of(2026, 4, 4), LoanStatus.RETURNED);

        System.out.println(b1);
        System.out.println(b2);
        System.out.println(l1);
        System.out.println(l2);

        //Invalid case:
        System.out.println("-- Test invalid cases --");
        try{
            BookingRequest errorb1 = new BookingRequest("B1234", requester1, room1, LocalDate.of(2026, 4, 25), LocalTime.of(7, 30), LocalTime.of(14,25), "Reference", BookingStatus.PENDING);
        }
        catch (CampusResourceException c){
            System.out.println(c.getMessage());
        }

        try{
            BookingRequest errorb2 = new BookingRequest("B1234", requester1, room1, LocalDate.of(2026, 4, 16), LocalTime.of(7, 30), null, "Reference", BookingStatus.PENDING);
        }
        catch (CampusResourceException c){
            System.out.println(c.getMessage());
        }
        
    }   
}
