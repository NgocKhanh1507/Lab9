public interface BookingService {
    public void addObserver(CampusEventObserver observer);
    public void removeObserver(CampusEventObserver observer);
    public void notifyObservers(String eventType, String message);
}
