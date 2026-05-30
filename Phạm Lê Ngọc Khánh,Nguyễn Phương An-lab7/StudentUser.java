public class StudentUser extends User{
    private String major;
    private int yearLevel;

    public StudentUser(String userId , String email , String name, String major , int yearLevel){
        super(userId, email, name);
        this.major=major;
        this.yearLevel=yearLevel;
    }

    public String getMajor(){
        return major;
    }

    public void setMajor(String major){
        this.major=major;
    }

    public int getYearLevel(){
        return yearLevel;
    }

    public void getYearLevel(int yearLevel){
        this.yearLevel=yearLevel;
    }

    @Override
    public String getUserType(){
        return "StudentUser";
    }

    @Override
    public String toString(){
        return "\nStudentUser : " + super.toString() + ",Major:" + major + ",yearLevel:" + yearLevel ;
    }
}
