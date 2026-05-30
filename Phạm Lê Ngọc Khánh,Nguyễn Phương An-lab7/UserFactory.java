public class UserFactory{
    public static User createUser(String userId, String email, 
        String name, String info1, String info2) {
    // switch(userType){
    //     case "Student":
    //         int year = Integer.parseInt(info2);
    //         return new StudentUser(userId, email, name, info1, year);
    //     case "Staff":
    //         return new StaffUser(userId, email, name, info1, info2);

    //     default:
    //         throw new CampusResourceException("Unknown user type");
    // }
        if(Integer.parseInt(info2) == 1 || Integer.parseInt(info2) == 2 ||
            Integer.parseInt(info2) == 3 || Integer.parseInt(info2) == 4){
            return new StudentUser(userId, email, name, info1 , Integer.parseInt(info2));
        }   
        else{
            return new StaffUser(userId, email, name, info1, info2);
        }
    }  
    
}