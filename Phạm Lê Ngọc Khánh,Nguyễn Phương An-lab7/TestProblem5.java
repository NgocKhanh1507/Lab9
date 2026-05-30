import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class TestProblem5 {
    public static void main (String[] args){
        CampusResourceSystem system = new CampusResourceSystem("IU campus");
        User u1 = new StudentUser("A25025", "A2025@gmail.com", "An", "DS", 1);
        User u2 = new StudentUser("K25025", "K2025@gmail.com","Kiet", "DS", 1);

        User s1 = new StaffUser("TTIU0505","TTIU0505@gmail.com" , "Thien", "CSE", "lecturer");
        User s2 = new StaffUser("sosIU0505","sosIU0505@gmail.com" , "Son", "CSE", "lecturer");

        Room room1 = new Room("IU2089", "Biology", true, 4, "A2");
        Room room2 = new Room("IU2345", "Technology", false, 1, "A1");

        Equipment e1 = new Equipment("IU3456", "test-tube", true, "School's property", 3 );
        Equipment e2 = new Equipment("IU2346", "Printer", false, "School's property", 2);

        User requester1 = new StudentUser("A021", "haduc234@gmail.com", "Duc", "CS", 3);
        User requester2 = new StaffUser("B357", "mpthuy@gmail.com","Thuy" ,"IT", "Lecturer");
            
        BookingRequest b1 = new BookingRequest("B3567", requester1, room1, LocalDate.of(2026, 5, 20), LocalTime.of(8, 30), LocalTime.of(14,25), "Reference", BookingStatus.PENDING);
        BookingRequest b2 = new BookingRequest("B4321", requester2, room2, LocalDate.of(2026, 5, 1), LocalTime.of(9, 45), LocalTime.of(20, 15), "Research", BookingStatus.APPROVED);

        User borrower = new StudentUser("B5689", "nmtrang@gmail.com", "Trang", "DS", 2);
        User borrower1 = new StudentUser("A2707", "npan2707@gmail.com", "An", "DS", 1);
        
        LoanRecord l1 = new LoanRecord("N1104", borrower, e1, LocalDate.of(2026, 4, 12), LocalDate.of(2026, 4,19), LocalDate.of(2026, 4, 20), LoanStatus.BORROWED);
        LoanRecord l2 = new LoanRecord("L1000", borrower1, e2, LocalDate.of(2026, 3, 30), LocalDate.of(2026, 4, 5), LocalDate.of(2026, 4, 4), LoanStatus.RETURNED);
        LoanRecord l = new LoanRecord("N4578", borrower, e1, LocalDate.of(2026, 4, 8), LocalDate.of(2026, 4,19), LocalDate.of(2026, 4, 20), LoanStatus.BORROWED);
            

        system.addUser(u1);
        system.addUser(u2);
        system.addUser(s1);
        system.addUser(s2);
        system.addUser(requester1);
        system.addUser(requester2);
        system.addUser(borrower);
        system.addUser(borrower1);

        system.addResource(room1);
        system.addResource(room2);
        system.addResource(e1);
        system.addResource(e2);

        system.addBooking(b1);
        system.addBooking(b2);

        system.addLoanRecord(l);
        system.addLoanRecord(l1);
        system.addLoanRecord(l2);

        System.out.println("----keyword-based user search----");
        Scanner sc = new Scanner(System.in);
        String name;
    
        do{
            System.out.println("Enter name: ");
            name = sc.nextLine();

            if (system.searchByNameKeyword(name) != null){
            System.out.println(system.searchByNameKeyword(name));
            } else {
                System.out.println("Not found");
            }
        } while (!name.isEmpty());

        System.out.println("----resource-type search----");
        System.out.println(system.searchResourcesByType());

        System.out.println("----approved booking report----");
        b2.changeStatus(BookingStatus.APPROVED);
        system.printAllApprovedBookings();

        System.out.println("----active loan report----");
        system.printAllActiveLoan();

        System.out.println("----Room usage summary----");
        system.bookingCount();

        System.out.println("----Top borrower user----");
        User topUser = system.mostRecordUser();
        if (topUser != null) {
            System.out.println("Top borrower: " + topUser.getName());
        }
}
}
