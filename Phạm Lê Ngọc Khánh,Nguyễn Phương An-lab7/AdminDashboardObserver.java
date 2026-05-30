public class AdminDashboardObserver implements CampusEventObserver{
    private String adminName;
    public AdminDashboardObserver(String adminName){
        this.adminName = adminName;
    }

    public void update(String eventType, String message){
        System.out.println("(Dashboard add " + adminName + ")" + eventType + ": " + message);
    }
}
