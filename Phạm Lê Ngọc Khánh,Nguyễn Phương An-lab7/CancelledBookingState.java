public class CancelledBookingState implements BookingState {
    @Override
    public void approve(BookingRequest booking) {
        throw new CampusResourceException(
            "Cannot approve booking" + booking.getBookingCode() + ":already cancelled!");
    }

    @Override
    public void reject(BookingRequest booking) {
        throw new CampusResourceException(
            "Cannot reject booking" + booking.getBookingCode() + ": already cancelled!");
    }

    @Override
    public void cancel(BookingRequest booking) {
        throw new CampusResourceException(
            "Booking" + booking.getBookingCode() + ": already cancelled!");
    }

    @Override
    public void complete(BookingRequest booking) {
        throw new CampusResourceException(
            "Cannot complete booking" + booking.getBookingCode() + ": already cancelled!");
    }

    @Override
    public String getStateName() {
        return "CANCELLED";
    }
}
