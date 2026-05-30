import java.time.LocalDate;
import java.time.LocalTime;

public class TestProblem3 {
    public static void main(String[]args){

        User student1 = new StudentUser("N0202", "nvnhat@gmail.com", "Nhat", "DS", 1);
        User staff1   = new StaffUser("T2403", "tttien@gmail.com", "Tien", "CS", "Lecturer");
        Room room1    = new Room("IU2134", "Conference Room", true, 20, "A1");
        Equipment e1  = new Equipment("IU1212", "Projector", true, "School's property", 2);
        Equipment e2  = new Equipment("IU1006", "Printer",   true, "School's property", 0); 

        System.out.println("PROBLEM 3 TEST\n");

        //Test valid booking qpproval:
        System.out.println("--- Test 1: Valid Booking Approval ---");
        try {
            BookingRequest b1 = new BookingRequest("B1001", student1, room1,
                LocalDate.of(2026, 4, 25),
                LocalTime.of(8, 0), LocalTime.of(10, 0),
                "Study session", BookingStatus.PENDING);

            b1.changeStatus(BookingStatus.APPROVED);
            System.out.println("Booking approved: " + b1.getStatus());}
        catch (CampusResourceException c){
            System.out.println(c.getMessage());
        }
            
        // Test invalid overlapping booking:
        System.out.println("\n--- Test 2: Overlapping Booking ---");
        try {
            BookingRequest b2 = new BookingRequest("B1002", staff1, room1,
                LocalDate.of(2026, 4, 25),
                LocalTime.of(8, 0), LocalTime.of(10, 0), 
                "Meeting", BookingStatus.PENDING);

            b2.changeStatus(BookingStatus.APPROVED); 
            System.out.println("Should have thrown overlap exception!");
        } catch (CampusResourceException c) {
            System.out.println(c.getMessage());
        }

        // Test valid borrow process:
        System.out.println("\n--- Test 3: Cannot Modify Cancelled Booking ---");
        try {
            BookingRequest b3 = new BookingRequest("B1003", student1, room1,
                LocalDate.of(2026, 4, 26),
                LocalTime.of(9, 0), LocalTime.of(11, 0),
                "Group project", BookingStatus.PENDING);

            b3.changeStatus(BookingStatus.CANCELLED);
            System.out.println("Booking cancelled: " + b3.getStatus());

            b3.changeStatus(BookingStatus.APPROVED); 
            System.out.println("Should have thrown exception!");
        } catch (CampusResourceException c) {
            System.out.println(c.getMessage());
        }

        // Test rejected borrow attempt
        System.out.println("\n--- Test 4: Completed Without Approved First ---");
        try {
            BookingRequest b4 = new BookingRequest("B1004", student1, room1,
                LocalDate.of(2026, 4, 27),
                LocalTime.of(13, 0), LocalTime.of(15, 0),
                "Seminar", BookingStatus.PENDING);

            b4.completedBooked(BookingStatus.COMPLETED); 
            System.out.println("Should have thrown exception!");
        } catch (CampusResourceException c) {
            System.out.println(c.getMessage());
        }

        // Test one caught exception:
        System.out.println("\n--- Test 5: Valid Borrow Process ---");
        try {
            LoanRecord l1 = new LoanRecord("L1001", student1, e1,
                LocalDate.of(2026, 4, 20),
                LocalDate.of(2026, 4, 27),
                null, LoanStatus.REQUESTED);

            l1.changeStatus(LoanStatus.BORROWED);
            e1.setQuantityAvailable(e1.getQuantityAvailable() - 1); 
            System.out.println("Loan status: " + l1.getStatus());
            System.out.println("Projector remaining: " + e1.getQuantityAvailable()); 
        } catch (CampusResourceException c) {
            System.out.println(c.getMessage());
        }

        // Test unavailable quantity:
        System.out.println("\n--- Test 6: Borrow Rejected — No Quantity ---");
        try {
            if (e2.getQuantityAvailable() <= 0) {
                throw new CampusResourceException("No units available for borrowing!");
            }
            LoanRecord l2 = new LoanRecord("L1002", staff1, e2,
                LocalDate.of(2026, 4, 20),
                LocalDate.of(2026, 4, 27),
                null, LoanStatus.REQUESTED);
            System.out.println("Should have thrown exception!");
        } catch (CampusResourceException c) {
            System.out.println(c.getMessage());
        }

        // Return can not be changed
        System.out.println("\n--- Test 7: Cannot Modify Returned Loan ---");
        try {
            LoanRecord l3 = new LoanRecord("L1003", staff1, e1,
                LocalDate.of(2026, 4, 20),
                LocalDate.of(2026, 4, 27),
                null, LoanStatus.BORROWED);

            l3.changeStatus(LoanStatus.RETURNED);
            e1.setQuantityAvailable(e1.getQuantityAvailable() + 1); 
            System.out.println("Loan returned. Projector remaining: " + e1.getQuantityAvailable()); 

            l3.changeStatus(LoanStatus.BORROWED); 
            System.out.println("Should have thrown exception!");
        } catch (CampusResourceException c) {
            System.out.println(c.getMessage());
        }

        // Invalid returned day
        System.out.println("\n--- Test 8: Invalid Return Date ---");
        try {
            LoanRecord l4 = new LoanRecord("L1004", student1, e1,
                LocalDate.of(2026, 4, 20),
                LocalDate.of(2026, 4, 27),
                LocalDate.of(2026, 4, 15), 
                LoanStatus.BORROWED);
            System.out.println("Should have thrown exception!");
        } catch (CampusResourceException c) {
            System.out.println(c.getMessage());
        }

    }
}
