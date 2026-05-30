import java.util.ArrayList;

public class WarmUp1{
    public static void main (String[] args){
        ArrayList<String> campusRooms = new ArrayList<>();
        campusRooms.add("service");
        campusRooms.add("LAB");
        campusRooms.add("Sport");
        campusRooms.add("Department");

        for( String room : campusRooms){
            System.out.println(room);
        }

    }
}