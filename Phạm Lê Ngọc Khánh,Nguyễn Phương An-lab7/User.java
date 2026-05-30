public abstract class User {
    private String userId;
    private String email;
    private String name;

    public User( String userId , String email, String name){
        this.userId=userId;
        this.email=email;
        this.name = name;
    }

    public String getUserId(){
        return userId;
    }

    public void setUserId( String userId){
        this.userId=userId;
    }

    public String getEmail(){
        return email;
    }
    
    public void setEmail( String email){
        this.email=email;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public abstract String getUserType();

    @Override
    public String toString(){
        return " UserId: " + userId + ", email: " + email + ", name: " + name; 
    }
    
}
