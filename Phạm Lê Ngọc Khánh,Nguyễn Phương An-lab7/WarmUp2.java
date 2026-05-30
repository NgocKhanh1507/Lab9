import java.util.HashMap;
public class WarmUp2{
    public static void main(String[] args){
        HashMap<String, String> userNames = new HashMap<>();

        userNames.put("U001", "Khanh");
        userNames.put("U002", "An");
        userNames.put("U003","Nhat");

        System.out.println(userNames.get("U001"));
    }
}