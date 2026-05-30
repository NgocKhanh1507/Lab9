public class CompletedBookingState implements BookingState{
    @Override
    public void approve(BookingRequest booking){
        throw new CampusResourceException(
            "Cannot modify booking" + booking.getBookingCode() + " already completed!");
    }

    @Override
    public void reject(BookingRequest booking) {
        throw new CampusResourceException(
            "Cannot modify booking" + booking.getBookingCode() + " already completed!");
    }

    @Override
    public void cancel(BookingRequest booking) {
        throw new CampusResourceException(
            "Cannot cancel booking" + booking.getBookingCode() + " already completed!");
    }

    @Override
    public void complete(BookingRequest booking) {
        throw new CampusResourceException(
            "Booking" + booking.getBookingCode() + " already completed!");
    }

    @Override
    public String getStateName() {
        return "COMPLETED";
    }
}
