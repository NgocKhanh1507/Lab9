import java.time.LocalTime;
public class StudentBookingStrategy implements BookingApprovalStrategy{
    private final int START_HOUR = 7, END_HOUR = 18;
    private String decisionMessage;

    @Override
    public boolean canApprove(BookingRequest request, CampusResourceSystem system){
        int hour = request.getStartTime().getHour();
        if (hour <= START_HOUR || hour >= END_HOUR){
            decisionMessage = "Students can only book a room between 8:00 and 18:00.";
            return false;
        }

        if(system.hasOverdueLoans(request.getRequester().getUserId())){
            decisionMessage = "Student has overdue loan! Please return them before making a new booking.";
            return false;
        }
        
        if(system.hasOverlappingBooking(request)){
            decisionMessage = "The room is already booked.";
            return false;
        }

        if(request.isWeekend()){
            decisionMessage = "Weekend bookings by students require manual approval.";
            return false;
        }

        decisionMessage = "Booking approved for student.";
        return true;
    }

    @Override
    public String getDecisionMessage(){
        return decisionMessage;
    }
}
