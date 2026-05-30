public class StaffBookingStrategy implements BookingApprovalStrategy{
    private String decisionMessage; 
 
    @Override
    public boolean canApprove(BookingRequest request, CampusResourceSystem
system){

    if(system.hasOverlappingBooking(request)){
        decisionMessage = "The room is already booked.";
        return false;
    }

    if(request.isWeekend()){
        decisionMessage = "Weekend booking approved for staff member.";
        return true;
    }

    decisionMessage = "Booking approved for staff";
    return true;
}

@Override
public String getDecisionMessage(){
    return decisionMessage;
}


}
   
