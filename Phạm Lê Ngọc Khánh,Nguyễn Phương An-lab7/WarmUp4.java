import java.util.HashSet;

public class WarmUp4{

    public static void main(String[] args){

        HashSet<String> usedCodes = new HashSet<>(); 
        usedCodes.add("B001"); 
        usedCodes.add("B002"); 
        usedCodes.add("B001");
        System.out.println(usedCodes.size());
        System.out.println(usedCodes);
    }
}