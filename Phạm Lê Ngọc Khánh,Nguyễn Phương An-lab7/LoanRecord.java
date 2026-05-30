import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class LoanRecord {
    private String loanCode;
    private User borrower;
    private Equipment equipment;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private LoanStatus status;
    public LoanRecord(String loanCode, User borrower, Equipment equipment, LocalDate borrowDate, LocalDate dueDate, LocalDate returnDate, LoanStatus status) throws CampusResourceException{
        this.loanCode = loanCode;
        this.borrower = borrower;
        this.equipment = equipment;

        if (borrowDate == null || dueDate == null){
            throw new CampusResourceException("Date can not be empty");
        }
        if (returnDate != null && returnDate.isBefore(borrowDate)){
            throw new CampusResourceException("Return date cannot be earlier than borrow date!");
        }
        if(returnDate != null){
            long days = ChronoUnit.DAYS.between(borrowDate, returnDate);
        }
    
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.status = LoanStatus.REQUESTED;
    }

    public String getLoanCode(){
        return loanCode;
    }

    public User getBorrower(){
        return borrower;
    }

    public Equipment getEquipment(){
        return equipment;
    }

    public LoanStatus getStatus(){
        return status;
    }

    public static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd - MM - yyyy");

    public String getFormattedBorrowDate() {
        return borrowDate.format(fmt); 
    }

    public String getFormattedDueDate() {
        return dueDate.format(fmt); 
    }

    public String getFormattedReturnDate() {
        if (returnDate == null) return "Not returned yet";
        return returnDate.format(fmt);
    }

    public boolean isReturned(){
        return status == LoanStatus.RETURNED;
    }

    public void setDueDate(LocalDate dueDate) throws CampusResourceException {
    if (isReturned()) {
        throw new CampusResourceException("Cannot modify a returned loan record!");
    }
    this.dueDate = dueDate;
    }

    public void setReturnDate(LocalDate returnDate) throws CampusResourceException {
    if (isReturned()) {
        throw new CampusResourceException("Cannot modify a returned loan record!");
    }
    this.returnDate = returnDate;
    }

    public void changeStatus(LoanStatus next) throws CampusResourceException {
    if (isReturned()) {
        throw new CampusResourceException("Cannot modify a returned loan record!");
    }
    this.status = next;
    }

    public void unavailableQuantity(Equipment e) throws CampusResourceException{
        if(e.getQuantityAvailable() <= 0){
            throw new CampusResourceException("Quantity is unavailable!");
        }
        e.setQuantityAvailable(e.getQuantityAvailable() - 1);
        this.status = LoanStatus.REJECTED;

        if(isReturned()){
            e.setQuantityAvailable(e.getQuantityAvailable() + 1);
        }
    }

    public void reject() throws CampusResourceException {
        if (status != LoanStatus.REQUESTED) {
            throw new CampusResourceException("Loan rejection");
        }
        this.status = LoanStatus.REJECTED;
    }


    public void exceedLimit() throws CampusResourceException{
        if (ChronoUnit.DAYS.between(borrowDate, returnDate) > 30){
            throw new CampusResourceException("Loan period exceed 30 days!");
        }
        this.status = LoanStatus.OVERDUE;

        if (this.status == LoanStatus.OVERDUE){
            throw new CampusResourceException("Cannot create a new loan request!");
        }
        LoanStatus next = LoanStatus.REJECTED;
    }

    @Override
    public String toString(){
        return "LoanRecord{" +
        "id='" + loanCode + "'" +
        ", borrower=" + borrower.getName() +
        ", equipment=" + getEquipment() +
        ", borrowDate=" + borrowDate +
        ", dueDate=" + dueDate +
        ", returnDate=" + (returnDate != null ? returnDate : "Not returned yet") +
        ", status=" + status +
        "}";
    }

    Queue<LoanRecord> waitingRecords = new LinkedList<>();
    public void addLoanRecord(LoanRecord record){
        waitingRecords.offer(record);
        System.out.println(record.getBorrower().getUserId() + "added to waiting list.");
    }

    public LoanRecord nextInQueue(){
        if (waitingRecords.isEmpty()){
           return null;
        }
        return waitingRecords.poll();
    }

    public void printRecordQueue(){
        if (waitingRecords.isEmpty()){
            System.out.println("No loan record is waiting.");
            return;
        }
        for (LoanRecord r : waitingRecords){
            System.out.println(r.getLoanCode());
        }
    }
}
