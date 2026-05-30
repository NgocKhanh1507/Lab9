public class StaffUser extends User {
    private String department;
    private String position;

    public StaffUser( String userId , String email , String name, String department , String position){
        super(userId, email, name);
        this.department=department;
        this.position=position;
    }

    public String getDepartment(){
        return department;
    }

    public void setDepartment(String department){
        this.department=department;
    }
    public String getPosition(){
        return position;
    }

    public void setPosition( String position){
        this.position=position;
    }

    @Override
    public String getUserType(){
        return "StaffUser";
    }

    @Override
    public String toString(){
        return "\nStaffUser: " + super.toString() +", department = " +  department  + ",position = " + position ;
    }
}
