public class ActivityLogObserver implements CampusEventObserver{
    @Override
    public void update(String eventType, String message){
        System.out.println(eventType);

    }
}
