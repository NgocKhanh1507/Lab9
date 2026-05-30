import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class BookingRequest {
    private String bookingCode;
    private User requester;
    private Room room;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String purpose;
    private BookingStatus status;
    private ArrayList<BookingStatus> statusHistory = new ArrayList<>();
    private BookingState currentState;
    public BookingRequest(String bookingCode, User requester, Room room, LocalDate date, LocalTime startTime, LocalTime endTime, String purpose, BookingStatus status) throws CampusResourceException{
        this.bookingCode = bookingCode;
        this.requester = requester;
        this.room = room;

        LocalDate today = LocalDate.now();
        if (date == null || date.isBefore(today)){
            throw new CampusResourceException("Borrow date must not in the past!");
        }
        this.date = date;

        if (startTime == null || endTime == null){
            throw new CampusResourceException("Time can not be empty!");
        }
        if (!endTime.isAfter(startTime) || endTime == startTime){
            throw new CampusResourceException("End time must be later than start time.");
        }
        else{
            System.out.println("Start time: " + startTime + " - " + "End time: " + endTime);
        }
        this.startTime = startTime;
        this.endTime = endTime;

        this.purpose = purpose;
        this.status = BookingStatus.PENDING;
        this.statusHistory.add(BookingStatus.PENDING); 
        this.currentState = new PendingBookingState();

    }

    public boolean isWeekend(){
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    public static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm");

    // public static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm");

    public String getBookingCode(){
        return bookingCode;
    }

    public void setBookingCode(String bookingCode){
        this.bookingCode = bookingCode;
    }

    public LocalDate getDate(){
        return date;
    }

    public User getRequester(){
        return requester;
    }

    public Room getRoom(){
        return room;
    }

    public BookingStatus getStatus(){
        return status;
    }

    public void setStatus(BookingStatus status){
        this.status = status;
    }

    public boolean isCompleted(){
        return status == BookingStatus.COMPLETED;
    }

    public void setDate(LocalDate date){
        if (isCompleted()) {
        throw new CampusResourceException("Cannot modify a " + status + " booking!");
    }
    this.date = date;
    }

    public LocalTime getStartTime(){
        return startTime;
        // return startTime.format(fmt);
    }

    public void setStartTime(LocalTime startTime){
        if (isCompleted()) {
           throw new CampusResourceException("Cannot modify a " + status + " booking!");
    }
        this.startTime = startTime;
    }

    public LocalTime getEndTime(){
        return endTime;
        // return endTime.format(fmt);
    }

    public void setEndTime(LocalTime endTime){
        this.endTime = endTime;
    }

    public String getPurpose(){
        return purpose;
    }

    public void setPurpose(String purpose){
        this.purpose = purpose;
    }

    @Override
    public String toString(){
        return "\nBookingRequest{" +
        "code='" + bookingCode + "'" +
        ", requester=" + requester.getName() +
        ", room=" + getRoom() +
        ", date=" + date +
        ", time=" + getStartTime() + " - " + getEndTime() +
        ", purpose='" + purpose + "'" +
        ", status=" + status +
        "}";
    }

    public static final List<BookingStatus> REQUIRED_BEFORE_COMPLETED = Arrays.asList(
        BookingStatus.PENDING,
        BookingStatus.APPROVED); 

    public void completedBooked(BookingStatus next) throws CampusResourceException{
        if (next == BookingStatus.COMPLETED){
            if (!statusHistory.containsAll(REQUIRED_BEFORE_COMPLETED)){
                throw new CampusResourceException("A completed booking must already have been approved earlier!");
            }
        }
    }

    public void changeStatus(BookingStatus next) throws CampusResourceException {
    if (isCompleted()) {
        throw new CampusResourceException("Cannot modify a " + status + " booking!");
    }
    if (!status.changeTo(next)) {
        throw new CampusResourceException("Invalid status transition: " + status + " → " + next);
    }
    this.status = next;
    this.statusHistory.add(next);
    }

    public void rejectOverlappingBooking(Room room, BookingStatus next){
        if(!room.isAvailable() && next == BookingStatus.APPROVED){
                throw new CampusResourceException("Overlapping booking request is not allowed!");
        }
    }

    public void approve(){
        currentState.approve(this);
    }

    public void reject(){
        currentState.reject(this);
    }

    public void cancel(){
        currentState.cancel(this);
    }

    public void complete(){
        currentState.complete(this);
    }

    public void setState(BookingState newState){
        this.currentState = newState;
    }

    public String getStateName(){
        return currentState.getStateName();
    }
}
