import java.util.Map;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.time.LocalTime;

public class CampusResourceSystem implements BookingService, CampusEventObserver{
    private String systemName;
    ArrayList<User> users;
    ArrayList<Resource> resources;
    HashMap<String, BookingRequest> bookings;
    HashMap<String, LoanRecord> loans;
    HashSet<String> usedBookingCodes;
    HashSet<String> usedLoanCodes;
    PriorityQueue<BookingRequest> waitingBookings = new PriorityQueue<>();
    private ArrayList<CampusEventObserver> campusEventObservers;
    
    public CampusResourceSystem(String systemName){
        this.systemName = systemName;
        this.users = new ArrayList<>();
        this.resources = new ArrayList<>();
        this.bookings = new HashMap<>();
        this.loans = new HashMap<>();
        this.waitingBookings = new PriorityQueue<>();
        this.usedBookingCodes = new HashSet<>();
        this.usedLoanCodes = new HashSet<>();
        this.campusEventObservers = new ArrayList<CampusEventObserver>();
    }

    @Override
    public void addObserver(CampusEventObserver ob){
        campusEventObservers.add(ob);
    }
    @Override
    public void removeObserver(CampusEventObserver ob){
        campusEventObservers.remove(ob);
    }
    @Override
    public void notifyObservers(String eventType, String message){
        for(CampusEventObserver cEO : campusEventObservers){
            cEO.update(eventType, message);
        }
    }

    private List<BookingService> observers = new ArrayList<>();
    @Override
    public void update(String eventType, String message){
        
    }

    public void addUser(User u){
        if (u == null){
            throw new CampusResourceException("Cannot null user!");
        }
        for (User user : users){
            if (user.getUserId().equals(u.getUserId())){
                throw new CampusResourceException("Duplicate user IDs are not allowed");
            }
        }
        users.add(u);
    }

    public void addResource(Resource r){
        if (r == null){
            throw new CampusResourceException("Cannot null resource!");
        }
        for (Resource resource : resources){
            if(resource.getResourceId().equals(r.getResourceId())){
                throw new CampusResourceException("Duplicate resources IDs are not allowed");
            }
        }
        resources.add(r);
    }

    public void addBooking(BookingRequest b){
        if (b == null){
            throw new CampusResourceException("Booking request is null!");
        }
        String code = b.getBookingCode();
        if (usedBookingCodes.contains(code)){
            throw new CampusResourceException("Duplicate booking codes are not allowed");
        }
        usedBookingCodes.add(code);
        bookings.put(code, b);
        System.out.println("Booking " + code + " added successfully");
    }

    public void addLoanRecord(LoanRecord l){
        if (l == null){
            throw new CampusResourceException("Loan record is null!");
        }
        String code = l.getLoanCode();
        if (usedLoanCodes.contains(code)){
            throw new CampusResourceException("Duplicate loan codes are not allowed");
        }
        usedLoanCodes.add(code);
        loans.put(code, l);
        System.out.println("Loan " + code + " added successfully");
    }

    public User findUserById(String id){
        for (User user : users){
            if (user.getUserId().equals(id)){
                return user;
            }
        }
        return null;
    }

    public Resource findResourceById(String id){
        for (Resource resource : resources){
            if (resource.getResourceId().equals(id)){
                return resource;
            }
        }
        return null;
    }

    public BookingRequest findBookingByCode(String code){
        return bookings.get(code);
    }

    public LoanRecord findLoanByCode(String code){
        return loans.get(code);
    }

    public int countStudents(){
        int count = 0;
        for (User user : users){
            if (user instanceof StudentUser){
                count++;
            }
        }
        return count;
    }

    public int countStaff(){
        int count = 0;
        for (User user : users){
            if (user instanceof StaffUser){
                count++;
            }
        }
        return count;
    }

    public int countRooms(){
        int count = 0;
        for (Resource resource : resources){
            if (resource instanceof Room){
                count++;
            }
        }
        return count;
    }

    public int countEquipment(){
        int count = 0;
        for (Resource resource : resources){
            if (resource instanceof Equipment){
                count++;
            }
        }
        return count;
    }

    public void printAllUsers(){
        for (User user : users){
            System.out.println("\n=== USERS ===");
            System.out.println(user);
        }
    }

    public void printAllResources(){
        for (Resource resource : resources){
            System.out.println("\n=== RESOUCES ===");
            System.out.println(resource);
        }
    }

    public void printAllBookings(){
        for (BookingRequest book : bookings.values()){
            System.out.println("\n=== BOOKINGS ===");
            System.out.println(book);
        }
    }

    public void printAllLoans(){
        for (LoanRecord loan : loans.values()){
            System.out.println("\n=== LOANS ===");
            System.out.println(loan);
        }
    }

    @Override
    public String toString(){
        return "====" + systemName + "===\n" +
        "Users: " +users.size() +
        "(Student: " + countStudents() + 
        ", Staff: " + countStaff() + ")\n" +
        "Resources: " + resources.size() + 
        "(Rooms: " + countRooms() + 
        ", Equipment: " + countEquipment() + ")\n" +
        "Bookings: " + bookings.size() + "\n" +
        "Loans: " + loans.size();
    }

    public Resource searchResourcesByType(){
        for (Resource r : resources){
            if (r instanceof Room){
                System.out.println(r.getResourceId() + "is " + r.getResourceType());
            }
            if (r instanceof Equipment){
                System.out.println(r.getResourceId() + "is " + r.getResourceType());
            }
        }
        return null;
    }

    public User searchByNameKeyword(String name){
        for (User keyname : users){
            if (keyname.getName().equalsIgnoreCase(name)){
                System.out.println(keyname);
            }
        }
        return null;
    }

    public void bookingCount(){
        HashMap<String, Integer> count = new HashMap<>();
        for (BookingRequest booking : bookings.values()){
            if (booking.getRoom().getResourceId() != null && booking.getStatus() == BookingStatus.APPROVED){
                String roomId = booking.getRoom().getResourceId();
                count.put(roomId, count.getOrDefault(roomId, 0) + 1);
            }
        }
        for (String roomId : count.keySet()) {
            System.out.println("Room " + roomId + ": " + count.get(roomId) + " approved booking(s)");
        }
    }
    
    public User mostRecordUser(){
        HashMap<String, Integer> loanCount = new HashMap<>();
        for (LoanRecord record : loans.values()){
            String user = record.getBorrower().getUserId(); 
            if (loanCount.containsKey(user)){
                loanCount.put(user, loanCount.get(user) + 1);
            } else {
                loanCount.put(user, 1);
            }
        }
        System.out.println("Loan count map: " + loanCount);

        String topUser = null;
        int maxLoanRecords = 0;
        for (String userId : loanCount.keySet()) {
            if (loanCount.get(userId) > maxLoanRecords) {
                maxLoanRecords = loanCount.get(userId);
                topUser = userId;
            }
        }
        System.out.println("Top user ID: " + topUser);
        if (topUser == null) {return null;}
        User result = findUserById(topUser);
        System.out.println("Found user: " + result);
        return result;
   }
    

    public void cancelBooking(String bookingCode) throws CampusResourceException {
        BookingRequest booking = findBookingByCode(bookingCode);
        booking.cancel();

        Room room = booking.getRoom();
        BookingRequest next = room.nextInQueue();
            if (next != null) {
            next.approve();
            System.out.println(next.getRequester().getName() + " approved from waitlist");
            }
    }

    public void waitingForAvailableRoom(BookingRequest newBooking) throws CampusResourceException{
        LocalTime startTime = newBooking.getStartTime();
        LocalTime endTime = newBooking.getEndTime();
        Room room = newBooking.getRoom();
        boolean hasConflict = false;

        for (BookingRequest booking : bookings.values()){
            boolean sameRoom = booking.getRoom().getResourceId().equals(room.getResourceId());
            boolean isApproved = booking.getStatus() == BookingStatus.APPROVED;
            boolean timeOverlap = booking.getStartTime().compareTo(endTime)  < 0 && booking.getEndTime().compareTo(startTime) > 0;

            if (sameRoom && isApproved && timeOverlap){
                hasConflict = true;
                break;
            }
        }

        if (hasConflict){
            waitingBookings.offer(newBooking);
            System.out.println(newBooking.getBookingCode() + " added to waiting list.");
        } else {
            newBooking.approve();
            addBooking(newBooking);
            System.out.println(newBooking.getBookingCode() + " approved directly.");
        }
    }

    public void printAllApprovedBookings(){
        for ( BookingRequest bk : bookings.values()){
            if(bk.getStatus() == BookingStatus.APPROVED){
                System.out.println(bk.toString());
            }
        }
    }

    public void printAllPendingBookings(){
        for ( BookingRequest bk : bookings.values()){
            if(bk.getStatus() == BookingStatus.PENDING){
                System.out.println(bk.toString());
            }
        }
    }

    public void printAllActiveLoan(){
        for ( LoanRecord s : loans.values()){
            if( s.getStatus() == LoanStatus.BORROWED || s.getStatus() == LoanStatus.OVERDUE );
            System.out.println(s.toString());
        }
    }

    public boolean hasOverdueLoans(){
        ArrayList<String> overdueLoan = new ArrayList<>();
        for (Map.Entry<String,LoanRecord> entry : loans.entrySet()){
            if (entry.getValue().getStatus() == LoanStatus.OVERDUE){
                overdueLoan.add(entry.getKey());
                return true;
            }
        }
        return false;
    }

    private Map<String, List<BookingRequest>> approvedBookings = new HashMap<>();

    public boolean hasOverlappingBooking(BookingRequest request) {
        List<BookingRequest> roomBookings = approvedBookings
                .getOrDefault(request.getBookingCode(), new ArrayList<>());

        for (BookingRequest existing : roomBookings) {
            // Cùng ngày mới cần check giờ
            if (!existing.getDate().equals(request.getDate())) continue;

            // Overlap khi: start mới < end cũ  VÀ  end mới > start cũ
            boolean overlap = request.getStartTime().isBefore(existing.getEndTime())
                           && request.getEndTime().isAfter(existing.getStartTime());

            if (overlap) return true;
        }
        return false;
    }

}