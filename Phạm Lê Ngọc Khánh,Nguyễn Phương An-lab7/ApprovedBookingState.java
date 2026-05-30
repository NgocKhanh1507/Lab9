public class ApprovedBookingState implements BookingState {
    @Override
    public void approve(BookingRequest booking) {
        throw new CampusResourceException(
            "Booking" + booking.getBookingCode() + ": is already approved!");
    }

    @Override
    public void reject(BookingRequest booking) {
        throw new CampusResourceException(
            "Cannot reject booking" + booking.getBookingCode() + ": already approved!");
    }

    @Override
    public void cancel(BookingRequest booking) {
        System.out.println("Booking" + booking.getBookingCode() + "cancelled.");
        booking.setState(new CancelledBookingState());
    }

    @Override
    public void complete(BookingRequest booking) {
        System.out.println("Booking" + booking.getBookingCode() + "completed.");
        booking.setState(new CompletedBookingState());
    }

    @Override
    public String getStateName() {
        return "APPROVED";
    }
}
