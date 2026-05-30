public class RejectedBookingState implements BookingState {
    @Override
    public void approve(BookingRequest booking) {
        throw new CampusResourceException(
            "Cannot approve booking" + booking.getBookingCode() + ": already rejected!");
    }

    @Override
    public void reject(BookingRequest booking) {
        throw new CampusResourceException(
            "Booking" + booking.getBookingCode() + ": is already rejected!");
    }

    @Override
    public void cancel(BookingRequest booking) {
        throw new CampusResourceException(
            "Cannot cancel booking" + booking.getBookingCode() + ": already rejected!");
    }

    @Override
    public void complete(BookingRequest booking) {
        throw new CampusResourceException(
            "Cannot complete booking" + booking.getBookingCode() + ": already rejected!");
    }

    @Override
    public String getStateName() {
        return "REJECTED";
    }
}