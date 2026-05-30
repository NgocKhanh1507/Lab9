
public class TestPart2 {
    public static void main (String[] args){
        try{
            User su1 = UserFactory.createUser("Student","A25025", "A2025@gmail.com", "An", "DS", "1");
            User su2 = UserFactory.createUser("Student", "K25025", "K2025@gmail.com","Kiet", "DS", "1");
             
            System.out.println(su1.toString());
            System.out.println(su2.toString());
           
        } catch(CampusResourceException e){
            System.out.println(e.getMessage());
        }

        try{
            User s1 =  UserFactory.createUser("Staff", "TTIU0505","TTIU0505@gmail.com" , "Thien", "CSE", "lecturer");
            User s2 =  UserFactory.createUser("Staff","sosIU0505","sosIU0505@gmail.com" , "Son", "CSE", "lecturer");
            
            System.out.println(s1.toString());
            System.out.println(s2.toString());
        }catch(CampusResourceException e){
            System.out.println(e.getMessage());
        }

        try{
            Resource r1 = ResourceFactory.createResource("Room","R001", "lab", true, "100", "A1");
            Resource r2 = ResourceFactory.createResource("Room","R002", "OOP", true, "100", "A2");

            System.out.println(r1.toString());
            System.out.println(r2.toString());
        }catch(CampusResourceException e){
            System.out.println(e.getMessage());
        }

        try{
            Resource e1 = ResourceFactory.createResource("Equipment","E001","Projector",true,"Technology","10");
            Resource e2 = ResourceFactory.createResource("Equipment","E002","Computer",true, "Technology","50");

            System.out.println(e2.toString());
            System.out.println(e1.toString());
        }catch(CampusResourceException e){
            System.out.println(e.getMessage());
        }
    
    }
}
