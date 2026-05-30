import java.time.temporal.ChronoUnit;
import java.time.LocalDate;

public class LoanRequestStrategy {
    public void exceedLimit(LocalDate borrowDate, LocalDate returnDate, LoanStatus status) throws CampusResourceException{
        if (ChronoUnit.DAYS.between(borrowDate, returnDate) > 30){
            throw new CampusResourceException("Loan period exceed 30 days!");
        }
        status = LoanStatus.OVERDUE;
    }
}
