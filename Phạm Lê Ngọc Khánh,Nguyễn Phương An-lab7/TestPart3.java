import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class TestPart3 {
    static CampusResourceSystem system = new CampusResourceSystem(null);
    public static void main(String[] args){
        User student = UserFactory.createUser("Student","N0202", "nvnhat@gmail.com", "Nhat", "DS", "1");
        User staff   = UserFactory.createUser("Staff","T2403", "tttien@gmail.com", "Tien", "CS", "Lecturer");
        Room room   = new Room("IU2134", "Conference Room", true, 20, "A1");

        LocalDate tomorrow    = LocalDate.now().plusDays(1);
        LocalDate weekend  = LocalDate.now().plusDays(1).with(DayOfWeek.SATURDAY);

        // Valid: Student booking trong giờ học 
        System.out.println("\n--- Valid Student Booking ---");
        BookingRequest req1 = new BookingRequest("B1001", student, room,
                LocalDate.of(2026, 6, 1),
                LocalTime.of(8, 0), LocalTime.of(10, 0),
                "Study session", BookingStatus.PENDING);
        system.approveBooking(req1, student);

        // Invalid: Student booking ngoài giờ học
        System.out.println("\n--- Rejected Student Booking (outside hours) ---");
        BookingRequest req2 = new BookingRequest("BK002", student, room,
            tomorrow, LocalTime.of(23, 0), LocalTime.of(23, 30), "Study", BookingStatus.PENDING);
        system.approveBooking(req2, student);

        // Valid: Staff booking 
        System.out.println("\n--- Valid Staff Booking ---");
        BookingRequest req3 = new BookingRequest("BK003", student, room,
            tomorrow, LocalTime.of(14, 0), LocalTime.of(16, 0), "Research", BookingStatus.PENDING);
        system.approveBooking(req3, staff);

        // Invalid: Booking trùng lịch 
        System.out.println("\n--- Rejected Booking ---");
        BookingRequest req4 = new BookingRequest("BK004", student, room,
            tomorrow, LocalTime.of(10, 0), LocalTime.of(12, 0), "Study Session", BookingStatus.PENDING); // trùng với BK001
        system.approveBooking(req4, student);

        // Dynamic: đổi strategy lúc runtime 
        System.out.println("\n--- Dynamic Strategy Switch (Weekend) ---");
        BookingRequest req5 = new BookingRequest("BK005", student, room,
            weekend, LocalTime.of(10, 0), LocalTime.of(12, 0), "Lab",  BookingStatus.PENDING);
        System.out.println("  Booking as STUDENT on weekend:");
        system.approveBooking(req5, student);
        System.out.println("  Booking as STAFF on weekend:");
        system.approveBooking(req5, staff);

    }
}
