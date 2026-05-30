import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class TestProblem6 {
    public static void main(String[] args ){

        CampusResourceSystem CRS = new CampusResourceSystem("HCMIU campus");

        Scanner sc = new Scanner(System.in);
        //Create user
        System.out.println("----Creation userStudent----");
        System.out.print("Enter student Id: ");
        String studentId = sc.nextLine();
        System.out.print("Enter email:");
        String studentEmail = sc.nextLine();
        System.out.print("Enter name:");
        String studentName = sc.nextLine();
        System.out.print("Enter major:");
        String major = sc.nextLine();
        System.out.print("Enter year level:");
        int yearLevel = sc.nextInt();
        sc.nextLine();

        User student = new StudentUser(studentId, studentEmail, studentName, major, yearLevel);
        System.out.println(student);

        System.out.println("----Creation staffStudent----");
        System.out.print("Enter staff Id:");
        String staffId = sc.nextLine();
        System.out.print("Enter email:");
        String staffEmail = sc.nextLine();
        System.out.print("Enter staff name:");
        String staffName = sc.nextLine();
        System.out.print("Enter department:");
        String department = sc.nextLine();
        System.out.print("Enter year position:");
        String position  = sc.nextLine();

        User staff = new StaffUser(staffId,staffEmail, staffName, department, position);
        System.out.println(staff);

        CRS.addUser(student);
        CRS.addUser(staff);

        System.out.println("----creation resource----");
        System.out.print("Enter room resourceId:");
        String resourceId = sc.nextLine();
        System.out.print("Enter room resourceName:");
        String roomName = sc.nextLine();
        System.out.print("Enter isAvailable:");
        boolean isAvailable = sc.nextBoolean();
        sc.nextLine();
        System.out.print("Enter capacity:");
        int capacity = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter buildingName:");
        String buildindName = sc.nextLine();
        System.out.print("Enter equipment resourceName:");
        String equipmentName = sc.nextLine();
        System.out.print("Enter equipment resourceId: ");
        String equipId = sc.nextLine();
        System.out.print("Enter quantityAvailable:");
        int quantityAvailable = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter equipmentCategory:");
        String equipmentCategory = sc.nextLine();


        Resource room = new Room(resourceId, roomName, isAvailable, capacity , buildindName );
        Room room1 = new Room(resourceId, equipmentName, isAvailable, capacity , buildindName );
        Equipment equipment = new Equipment(equipId, equipmentName, isAvailable, equipmentCategory, quantityAvailable) ;
        System.out.println(room);
        System.out.println(equipment);

        CRS.addResource(room1);
        CRS.addResource(equipment);

        System.out.println("----Creation BookingRequest----");
        System.out.print("Enter booking code: ");
        String bookingCode = sc.nextLine();
        System.out.print("Enter year: ");
        int year = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter month: ");
        int month = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter day: ");
        int day = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter start hour: ");
        int startHour = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter start minute: ");
        int startMinute = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter end hour: ");
        int endHour = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter end minute: ");
        int endMinute = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter purpose: ");
        String purpose = sc.nextLine();
        System.out.print("Enter status (PENDING/APPROVED/REJECTED/CANCELLED/COMPLETED): ");
        String StatusInput = sc.nextLine();

        BookingStatus Status = BookingStatus.valueOf(StatusInput.toUpperCase());

        BookingRequest request = new BookingRequest(bookingCode, student, (Room) room, LocalDate.of(year,month,day), LocalTime.of(startHour,startMinute), LocalTime.of(endHour,endMinute), purpose , Status);
        System.out.println(request);
        CRS.addBooking(request);

        System.out.println("---Creation LoanRecord----");

        System.out.print("Enter loanCode ");
        String loanCode = sc.nextLine();
        
        System.out.println("Enter borrow date:");
        System.out.print("Year: ");
        int byear = sc.nextInt();
        sc.nextLine();
        System.out.print("Month: ");
        int bmonth = sc.nextInt();
        sc.nextLine();
        System.out.print("Day: ");
        int bday = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter due date:");
        System.out.print("Year: ");
        int dyear = sc.nextInt();
        sc.nextLine();
        System.out.print("Month: ");
        int dmonth = sc.nextInt();
        sc.nextLine();
        System.out.print("Day: ");
        int dday = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter return date:");
        System.out.print("Year: ");
        int ryear = sc.nextInt();
        sc.nextLine();
        System.out.print("Month: ");
        int rmonth = sc.nextInt();
        sc.nextLine();
        System.out.print("Day: ");
        int rday = sc.nextInt();
        sc.nextLine(); 

        System.out.print("Enter status (BORROWED/RETURNED/OVERDUE): ");
        String statusInput = sc.nextLine();

        LoanStatus status = LoanStatus.valueOf(statusInput.toUpperCase());
        LoanRecord loan = new LoanRecord(
        loanCode,
        student,
        equipment,
        LocalDate.of(byear, bmonth, bday),
        LocalDate.of(dyear, dmonth, dday),
        LocalDate.of(ryear, rmonth, rday),
        status
        );
        System.out.println(loan);
        CRS.addLoanRecord(loan);

        // TEST : duplicate ID
        try {
            User duplicateStudent = new StudentUser(studentId, "other@mail.com", "Other Name", "IT", 1);
            CRS.addUser(duplicateStudent); 
        } catch (CampusResourceException e) {
        System.out.println("Success: Duplicate ID rejected: " + e.getMessage());
        }
        //TEST : code rejection
        request.reject();
        // Test : Loan rejection
        if(loan != null){
            try {
        loan.reject();
         System.out.println("Loan rejected. Status: " + loan.getStatus());
        } catch (CampusResourceException e) {
         System.out.println("Cannot reject: " + e.getMessage());
        }
        }
        
        //overlapping booking rejection
        System.out.println("\n--- Test 2: Overlapping Booking ---");
        try {
            BookingRequest b2 = new BookingRequest("B1002", staff, room1 ,LocalDate.of(2026, 4, 25),LocalTime.of(8, 0), LocalTime.of(10, 0), "Meeting", BookingStatus.PENDING);

            b2.changeStatus(BookingStatus.APPROVED); 
            System.out.println("Should have thrown overlap exception!");
        } catch (CampusResourceException c) {
            System.out.println(c.getMessage());
        }

        
        System.out.println("\n---- Test: waitlist behavior using a queue ----");
        
        java.util.Queue<BookingRequest> waitlist = new java.util.LinkedList<>();

        try {
            
            System.out.println("add three request in list");
            
            waitlist.add(new BookingRequest("W01", student, room1, LocalDate.now().plusDays(2), LocalTime.of(8,0), LocalTime.of(10,0), " CLB 1", BookingStatus.PENDING));
            waitlist.add(new BookingRequest("W02", staff, room1, LocalDate.now().plusDays(2), LocalTime.of(8,0), LocalTime.of(10,0), "CLB 2", BookingStatus.PENDING));
            waitlist.add(new BookingRequest("W03", student, room1, LocalDate.now().plusDays(2), LocalTime.of(8,0), LocalTime.of(10,0), "CLB 3", BookingStatus.PENDING));

            System.out.println("number of queues: " + waitlist.size());

            BookingRequest nextInLine = waitlist.poll(); 
            System.out.println("process the next request: " + nextInLine.getBookingCode() + " of " + nextInLine.getRequester().getName());
            
            nextInLine.approve(); 
            System.out.println("status after processing: " + nextInLine.getStatus());
            System.out.println("number of people remaining in the quece: " + waitlist.size());

        } catch (CampusResourceException e) {
            System.out.println("ERROR QUECE: " + e.getMessage());
        }

        //Test overdue-loan restriction behavior
        System.out.println("----overdue-loan restriction behavior----");
        loan.exceedLimit();

        // --- PRINTED MANAGEMENT REPORTS ---
        
        System.out.println("----CAMPUS RESOURCE MANAGEMENT REPORT----");
        System.out.println("Campus Name: HCMIU campus"); 
        
        System.out.println("\n--- Summary ---");
        System.out.println("Total Users Registered: 2"); 
        System.out.println("Total Resources: 2 (1 Room, 1 Equipment)");
        
        System.out.println("\n--- Registered Users ---");
        System.out.println(student); 
        System.out.println(staff);  
        
        System.out.println("\n--- Resources Status ---");
        System.out.println(room);      
        System.out.println(equipment); 
        
        System.out.println("\n--- Recent Booking Request ---");
        if (request != null) {
            System.out.println(request); 
        }

    }
}    