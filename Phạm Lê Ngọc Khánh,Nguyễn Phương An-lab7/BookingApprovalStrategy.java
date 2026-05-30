public interface BookingApprovalStrategy{
    boolean canApprove(BookingRequest request, CampusResourceSystem
system);
    String getDecisionMessage();
}