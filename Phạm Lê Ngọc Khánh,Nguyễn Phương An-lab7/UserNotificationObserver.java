public class UserNotificationObserver implements CampusEventObserver{
    private String userName;
    public UserNotificationObserver(String userName){
        this.userName = userName;
    }

    @Override
    public void update(String eventType, String message){
          System.out.println("(Notification to " + userName + ")" + eventType + ": " + message);
    }
}