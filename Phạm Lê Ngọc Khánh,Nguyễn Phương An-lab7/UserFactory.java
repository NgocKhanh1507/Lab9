public class UserFactory{
    public static User createUser(String userType, String userId, String email, 
        String name, String info1, String info2) {
    switch(userType){
        case "Student":
    int year = 0;
    try {
        year = Integer.parseInt(info2); 
    } catch (NumberFormatException e) {
        throw new CampusResourceException("Student year must be a number, got: " + info2);
    }
    return new StudentUser(userId, email, name, info1, year);

        case "Staff":
    return new StaffUser(userId, email, name, info1, info2);

    default:
        throw new CampusResourceException("Unknown user type");
    }
    }  
    
}