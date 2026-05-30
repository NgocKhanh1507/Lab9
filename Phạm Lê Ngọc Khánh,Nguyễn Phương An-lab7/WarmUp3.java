import java.util.LinkedList;
import java.util.Queue;

public class WarmUp3{

    public static void main(String [] args){
        Queue<String> projectorQueue = new LinkedList<>(); 

        projectorQueue.add("Khanh"); 
        projectorQueue.add("An"); 
        projectorQueue.add("Nhat");

        String firstInLine = projectorQueue.poll();

        System.out.println(firstInLine);
    }
}