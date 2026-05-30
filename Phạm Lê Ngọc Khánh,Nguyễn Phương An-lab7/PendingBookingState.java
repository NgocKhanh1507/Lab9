public class PendingBookingState implements BookingState {
    @Override
    public void approve(BookingRequest booking){
        System.out.println("Booking" + booking.getBookingCode() + "approved.");
        booking.setState(new ApprovedBookingState());
    }

    @Override
    public void reject(BookingRequest booking) {
        System.out.println("Booking" + booking.getBookingCode() + "rejected.");
        booking.setState(new RejectedBookingState());
    }

    @Override
    public void cancel(BookingRequest booking) {
        System.out.println("Booking" + booking.getBookingCode() + "cancelled.");
        booking.setState(new CancelledBookingState());
    }

    @Override
    public void complete(BookingRequest booking) {
        throw new CampusResourceException(
            "Cannot complete booking" + booking.getBookingCode() + ": must be approve first!");
    }

    @Override
    public String getStateName() {
        return "PENDING";
    }
}
