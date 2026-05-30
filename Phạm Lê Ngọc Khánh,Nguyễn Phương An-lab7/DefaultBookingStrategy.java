public class DefaultBookingStrategy implements BookingApprovalStrategy{
    private static final int DEFAULT_START = 8, DEFAULT_END = 20;
    private String decisionMessage;

    @Override
    public boolean canApprove(BookingRequest request, CampusResourceSystem system){
        int hour = request.getStartTime().getHour();
        if(hour < DEFAULT_START|| hour >= DEFAULT_END){
            decisionMessage = "Default booking can only booked between 8:00 and 20:00.";
            return false;
        }

        if(system.hasOverlappingBooking(request)){
            decisionMessage = "The room is already booked.";
            return false;
        }

        if(request.isWeekend()){
            decisionMessage = "Weekend bookings is not allowed";
            return false;
        }

        decisionMessage = "Booking approved under default policy";
        return true;
    }

   @Override
   public String getDecisionMessage(){
    return decisionMessage;
   }
}
