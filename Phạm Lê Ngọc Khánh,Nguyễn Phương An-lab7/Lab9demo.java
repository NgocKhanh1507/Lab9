import java.time.LocalDate;
import java.time.LocalTime;
public class Lab9Demo {
    public static void main(String[] args){
        System.out.println("=== Final Demo Scenarios ===\n");

        CampusResourceSystem system = new CampusResourceSystem("IU Campus System");

        // Create student and staff users using UserFactory
        System.out.println("\n--- Scenario 1: ---");
        User student1 = UserFactory.createUser("Student", "S001", "nhat@gmail.com", "Nhat", "DS", "1");
        User student2 = UserFactory.createUser("Student", "S002", "an@gmail.com", "An", "CS", "3");
        User staff1   = UserFactory.createUser("Staff", "T001", "tien@gmail.com", "Tien", "CS", "Lecturer");
        User staff2   = UserFactory.createUser("Staff", "T002", "thuy@gmail.com", "Thuy", "IT", "Manager");

        System.out.println("Created: " + student1.getName() + " (" + student1.getUserType() + ")");
        System.out.println("Created: " + student2.getName() + " (" + student2.getUserType() + ")");
        System.out.println("Created: " + staff1.getName()   + " (" + staff1.getUserType()   + ")");
        System.out.println("Created: " + staff2.getName()   + " (" + staff2.getUserType()   + ")");

        // Create rooms and equipment through ResourceFactory
        System.out.println("\n--- Scenario 2: ---");
        Resource room1 = ResourceFactory.createResource("Room",      "R001", "Conference Room", true,  "20",  "A1");
        Resource room2 = ResourceFactory.createResource("Room",      "R002", "Lecture Hall",    true,  "100", "B2");
        Resource equip1= ResourceFactory.createResource("Equipment", "E001", "Projector",       true,  "Technology", "5");
        Resource equip2= ResourceFactory.createResource("Equipment", "E002", "Printer",         true,  "Technology", "3");

        System.out.println("Created: " + room1.getResourceName()  + " (" + room1.getResourceType()  + ")");
        System.out.println("Created: " + room2.getResourceName()  + " (" + room2.getResourceType()  + ")");
        System.out.println("Created: " + equip1.getResourceName() + " (" + equip1.getResourceType() + ")");
        System.out.println("Created: " + equip2.getResourceName() + " (" + equip2.getResourceType() + ")");

        // Add users and resources to CampusResourceSystem.
        System.out.println("\n--- Scenario 3: ---");
        try {
            system.addUser(student1);
            system.addUser(student2);
            system.addUser(staff1);
            system.addUser(staff2);
            System.out.println("All users added successfully.");
 
            system.addResource(room1);
            system.addResource(room2);
            system.addResource(equip1);
            system.addResource(equip2);
            System.out.println("All resources added successfully.");
        } catch (CampusResourceException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        // Reject duplicate user ID, resource ID, booking code, or loan code.
        System.out.println("\n--- Scenario 4: ---");
        try {
            User duplicate = UserFactory.createUser("Student", "S001", "dup@gmail.com", "Dup", "CS", "1");
            system.addUser(duplicate);
            System.out.println("ERROR: Should have thrown exception!");
        } catch (CampusResourceException e) {
            System.out.println("Caught expected (duplicate user): " + e.getMessage());
        }
 
        try {
            Resource dupRoom = ResourceFactory.createResource("Room", "R001", "Dup Room", true, "10", "C3");
            system.addResource(dupRoom);
            System.out.println("ERROR: Should have thrown exception!");
        } catch (CampusResourceException e) {
            System.out.println("Caught expected (duplicate resource): " + e.getMessage());
        }

        // Process a valid student booking using a strategy.
        System.out.println("\n--- Scenario 5: ---");
        try {
            BookingRequest b1 = new BookingRequest("B001", student1, (Room) room1,
                LocalDate.of(2026, 6, 10), // Wednesday
                LocalTime.of(9, 0), LocalTime.of(11, 0),
                "Study session", BookingStatus.PENDING);
 
            BookingApprovalStrategy studentStrategy = new StudentBookingStrategy();
            boolean canApprove = studentStrategy.canApprove(b1, system);
            System.out.println("Decision: " + studentStrategy.getDecisionMessage());
            if (canApprove) {
                b1.approve();
                system.addBooking(b1);
                system.notifyObservers("BOOKING_APPROVED", "Booking " + b1.getBookingCode() + " approved for " + student1.getName());
            }
        } catch (CampusResourceException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        // Process a valid staff booking using a strategy.
        System.out.println("\n--- Scenario 6: ---");
        try {
            BookingRequest b2 = new BookingRequest("B002", staff1, (Room) room2,
                LocalDate.of(2026, 6, 10), 
                LocalTime.of(14, 0), LocalTime.of(16, 0),
                "Meeting", BookingStatus.PENDING);

            BookingApprovalStrategy staffStrategy = new StaffBookingStrategy();
            boolean canApprove = staffStrategy.canApprove(b2, system);
            System.out.println("Decision: " + staffStrategy.getDecisionMessage());
            
            if (canApprove) {
                b2.approve();
                system.addBooking(b2);
                system.notifyObservers("BOOKING_APPROVED", "Booking " + b2.getBookingCode() + " approved for " + staff1.getName());
            }
        } catch (CampusResourceException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        // Reject an invalid or overlapping booking.
        System.out.println("\n--- Scenario 7: ---");
        try {
            BookingRequest b3 = new BookingRequest("B003", student2, (Room) room1,
                    LocalDate.of(2026, 6, 13), // Saturday
                    LocalTime.of(9, 0), LocalTime.of(11, 0),
                    "Weekend study", BookingStatus.PENDING);
 
            BookingApprovalStrategy studentStrategy = new StudentBookingStrategy();
            boolean canApprove = studentStrategy.canApprove(b3, system);
            System.out.println("Decision: " + studentStrategy.getDecisionMessage());
            if (!canApprove) {
                b3.reject();
                system.notifyObservers("BOOKING_REJECTED", "Booking " + b3.getBookingCode() + " rejected: " + studentStrategy.getDecisionMessage());
            }
        } catch (CampusResourceException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        // Notify observers after booking approval or rejection.
        System.out.println("\n--- Scenario 8: ---");
        CampusEventObserver userObserver = new CampusEventObserver() {
            @Override
            public void update(String eventType, String message) {
                System.out.println("[USER NOTIFICATION] " + eventType + ": " + message);
            }
        };
 
        CampusEventObserver logObserver = new CampusEventObserver() {
            @Override
            public void update(String eventType, String message) {
                System.out.println("[ACTIVITY LOG] " + eventType + ": " + message);
            }
        };
 
        CampusEventObserver adminObserver = new CampusEventObserver() {
            @Override
            public void update(String eventType, String message) {
                System.out.println("[ADMIN DASHBOARD] " + eventType + ": " + message);
            }
        };
 
        system.addObserver(userObserver);
        system.addObserver(logObserver);
        system.addObserver(adminObserver);
 
        System.out.println("3 observers registered. Sending notification...");
        system.notifyObservers("BOOKING_APPROVED", "Booking B004 approved for Nhat");

        // Remove one observer and show that it no longer receives updates.
        System.out.println("\n--- Scenario 9: ---");
        system.removeObserver(adminObserver);
        System.out.println("Admin observer removed. Sending notification (admin should NOT appear)...");
        system.notifyObservers("LOAN_CREATED", "Loan L001 created for An");

        // Demonstrate valid and invalid state transitions.
        System.out.println("\n--- Scenario 10: ---");
        try {
            BookingRequest b5 = new BookingRequest("B005", student1, (Room) room1,
                LocalDate.of(2026, 6, 15),
                LocalTime.of(8, 0), LocalTime.of(10, 0),
                "Seminar", BookingStatus.PENDING);
 
            // Valid: PENDING -> APPROVED
            System.out.println("State: " + b5.getStateName());
            b5.approve();
            System.out.println("After approve()  : " + b5.getStateName()); // APPROVED
 
            // Valid: APPROVED -> COMPLETED
            b5.complete();
            System.out.println("After complete() : " + b5.getStateName()); // COMPLETED
 
            // Invalid: COMPLETED -> CANCELLED
            System.out.println("Trying to cancel a COMPLETED booking...");
            b5.cancel();
            System.out.println("ERROR: Should have thrown exception!");
        } catch (CampusResourceException e) {
            System.out.println("Caught expected: " + e.getMessage());
        }
 
        try {
            BookingRequest b6 = new BookingRequest("B006", staff1, (Room) room2,
                LocalDate.of(2026, 6, 16),
                LocalTime.of(14, 0), LocalTime.of(16, 0),
                "Training", BookingStatus.PENDING);
 
            // Valid: PENDING -> REJECTED
            b6.reject();
            System.out.println("After reject()   : " + b6.getStateName()); // REJECTED
 
            // Invalid: REJECTED -> APPROVED
            System.out.println("Trying to approve a REJECTED booking...");
            b6.approve();
            System.out.println("ERROR: Should have thrown exception!");
        } catch (CampusResourceException e) {
            System.out.println("Caught expected: " + e.getMessage());
        }

        // Print final system reports.
        System.out.println("\n--- Final System Reports ---");
        system.printAllUsers();
        system.printAllResources();
        system.printAllBookings();
        system.printAllLoans();
        System.out.println("\n" + system);
 
        System.out.println("=== ALL SCENARIOS COMPLETED ===");
    }
}
