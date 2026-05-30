import java.time.LocalDate;
import java.time.LocalTime;

public class TestPart4 {
    static CampusResourceSystem system = new CampusResourceSystem(null);
    public static void main (String[]args){
         UserNotificationObserver userObs  = new UserNotificationObserver("Alice");
        AdminDashboardObserver   adminObs = new AdminDashboardObserver("Admin John");
        ActivityLogObserver      logObs   = new ActivityLogObserver();

        system.addObserver(userObs);
        system.addObserver(adminObs);
        system.addObserver(logObs);
        System.out.println("3 observers added.");

        User student = UserFactory.createUser("Student","S020", "Alice", "alice@uni.edu", "CS", "2022");
        User staff   = UserFactory.createUser("Staff", "T020", "Dr. Park", "park@uni.edu", "Sci", "Lecturer");

        Room room1 = new Room("IU2089", "Biology", true, 4, "A2");
        Room room2 = new Room("IU2345", "Technology", false, 1, "A1");
        // Approve booking 
        System.out.println("\n--- Approve Booking ---");
        BookingRequest req6 = new BookingRequest("BK006", student, room1,
            LocalDate.now(), LocalTime.of(9, 0), LocalTime.of(10, 0), "Study", BookingStatus.PENDING);
        system.approveBooking(req6, student);

         // ── Reject booking 
        System.out.println("\n--- Reject Booking → All 3 Observers Notified ---");
        BookingRequest req7 = new BookingRequest("BK007", student, room2,
            LocalDate.now(), LocalTime.of(23, 0), LocalTime.of(23, 30), "Lab", BookingStatus.PENDING);
        system.approveBooking(req7, student);

        // ── Remove 1 observer ────────────────────
        System.out.println("\n--- Removing AdminDashboardObserver ---");
        system.removeObserver(adminObs);
        System.out.println("AdminDashboardObserver removed.");

        // ── Trigger event → chỉ còn 2 observers ─
        System.out.println("\n--- Staff Booking → Only 2 Observers Notified ---");
        BookingRequest req8 = new BookingRequest("BK008", staff, room1,
            LocalDate.now(), LocalTime.of(14, 0), LocalTime.of(15, 0), "Research", BookingStatus.PENDING);
        system.approveBooking(req8, staff);

        // ── In log ra để verify ──────────────────
        System.out.println("\n--- Activity Log ---");
        logObs.getLogs().forEach(log -> System.out.println("  " + log));
    }
    
}
