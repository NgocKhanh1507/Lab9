import java.util.PriorityQueue;

public  class Room extends Resource {
    private int capacity;
    private String buildingName;
    private PriorityQueue<BookingRequest> waitingBookings = new PriorityQueue<>();

    public Room(String resourceId , String resourceName , boolean isAvailable , int capacity , String buildingName){
        super(resourceId, resourceName, isAvailable);
        this.capacity=capacity;
        this.buildingName=buildingName;
        this.waitingBookings = new PriorityQueue<>();
    }

    public PriorityQueue<BookingRequest> getWaitingBookings(){
        return waitingBookings;
    }

    public void setWaitingBookings(){
        this.waitingBookings = new PriorityQueue<>();
    }

    public int getCapacity(){
        return capacity;
    }

    public void setCapacity(int capacity){
        this.capacity=capacity;
    }

    public String getBuildingName(){
        return buildingName;
    }

    public void setBuildingName( String buildingName){
        this.buildingName=buildingName;
    }

    @Override 
    public boolean isAvailable(){
        return isAvailable;
    }

    public void isOccupied(){
        this.isAvailable = false;
    }

    public void isFree(){
        this.isAvailable = true;
    }

    @Override
    public String getResourceType(){
        return "Room";
    }

    @Override
    public String toString(){
        return "Room:" + super.toString() + ",Capacity:" + capacity + ",Building Name:"+ buildingName  ;
    }

    public void addRequests(BookingRequest request){
        waitingBookings.offer(request);
        System.out.println(request.getRequester().getName() + "added to waiting list.");
    }

    public BookingRequest nextInQueue(){
        if (waitingBookings.isEmpty()){
            return null;
        }
        return waitingBookings.poll();
    }

    public void printWaitingList(){
        if (waitingBookings.isEmpty()){
            System.out.println("No requests waiting.");
            return;
        }

        for (BookingRequest b : waitingBookings){
            System.out.println(b.getBookingCode());
        }
    }

    PriorityQueue<BookingRequest> user = new PriorityQueue<>( (a,b) 
    -> {  int pr = (a.getRequester() instanceof StaffUser) ? 1 : 2;
          int npr = (b.getRequester() instanceof StudentUser) ? 1 : 2;
          return Integer.compare(pr, npr);
    });   
}
