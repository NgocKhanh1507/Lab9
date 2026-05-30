import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class TestProblem5 {
    public static void main (String[] args){
        User student = new StudentUser("N0202", "nhat@gmail.com", "Nhat", "DS", 1);
        User staff   = new StaffUser("T2403", "tien@gmail.com", "Tien", "CS", "Lecturer");
        Room room    = new Room("IU2134", "Conference Room", true, 20, "A1");
 
        System.out.println("========== STATE PATTERN TEST ==========\n");
 
        // Test 1: PENDING -> APPROVED (valid)
        System.out.println("--- Test 1: PENDING -> APPROVED (valid) ---");
        try {
            BookingRequest b1 = new BookingRequest("B1001", student, room,
                    LocalDate.of(2026, 6, 1),
                    LocalTime.of(8, 0), LocalTime.of(10, 0), "Study session", BookingStatus.PENDING);
            System.out.println("Initial state : " + b1.getStateName());
            b1.approve();
            System.out.println("After approve : " + b1.getStateName());
        } catch (CampusResourceException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
 
        // Test 2: APPROVED -> COMPLETED (valid)
        System.out.println("\n--- Test 2: APPROVED -> COMPLETED (valid) ---");
        try {
            BookingRequest b2 = new BookingRequest("B1002", staff, room,
                    LocalDate.of(2026, 6, 2),
                    LocalTime.of(9, 0), LocalTime.of(11, 0), "Meeting", BookingStatus.PENDING);
            b2.approve();
            System.out.println("After approve  : " + b2.getStateName());
            b2.complete();
            System.out.println("After complete : " + b2.getStateName());
        } catch (CampusResourceException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
 
        // Test 3: COMPLETED -> CANCELLED (invalid)
        System.out.println("\n--- Test 3: COMPLETED -> CANCELLED (invalid) ---");
        try {
            BookingRequest b3 = new BookingRequest("B1003", student, room,
                    LocalDate.of(2026, 6, 3),
                    LocalTime.of(10, 0), LocalTime.of(12, 0), "Seminar", BookingStatus.PENDING);
            b3.approve();
            b3.complete();
            System.out.println("State before cancel: " + b3.getStateName());
            b3.cancel(); // should throw
            System.out.println("ERROR: Should have thrown exception!");
        } catch (CampusResourceException e) {
            System.out.println("Caught expected: " + e.getMessage());
        }

        // Test 4: PENDING -> REJECTED (valid)
        System.out.println("\n--- Test 4: PENDING -> REJECTED (valid) ---");
        try {
            BookingRequest b4 = new BookingRequest("B1004", student, room,
                    LocalDate.of(2026, 6, 4),
                    LocalTime.of(13, 0), LocalTime.of(15, 0), "Workshop", BookingStatus.PENDING);
            System.out.println("Initial state : " + b4.getStateName());
            b4.reject();
            System.out.println("After reject  : " + b4.getStateName());
        } catch (CampusResourceException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
 
        // Test 5: REJECTED -> APPROVED (invalid)
        System.out.println("\n--- Test 5: REJECTED -> APPROVED (invalid) ---");
        try {
            BookingRequest b5 = new BookingRequest("B1005", staff, room,
                    LocalDate.of(2026, 6, 5),
                    LocalTime.of(14, 0), LocalTime.of(16, 0), "Training", BookingStatus.PENDING);
            b5.reject();
            System.out.println("State before approve: " + b5.getStateName());
            b5.approve(); // should throw
            System.out.println("ERROR: Should have thrown exception!");
        } catch (CampusResourceException e) {
            System.out.println("Caught expected: " + e.getMessage());
        }
 
        // Test PENDING -> COMPLETE (invalid)
        System.out.println("\n--- Test 6: PENDING -> COMPLETE directly (invalid) ---");
        try {
            BookingRequest b6 = new BookingRequest("B1006", student, room,
                    LocalDate.of(2026, 6, 6),
                    LocalTime.of(7, 0), LocalTime.of(9, 0), "Club meeting", BookingStatus.PENDING);
            System.out.println("Initial state  : " + b6.getStateName());
            b6.complete(); // should throw
            System.out.println("ERROR: Should have thrown exception!");
        } catch (CampusResourceException e) {
            System.out.println("Caught expected: " + e.getMessage());
        }
 
        System.out.println("\n========== ALL TESTS DONE ==========");
    }
}
