public interface BookingState {
    void approve(BookingRequest booking);
    void reject(BookingRequest booking);
    void cancel(BookingRequest booking);
    void complete(BookingRequest booking);
    String getStateName();
}
