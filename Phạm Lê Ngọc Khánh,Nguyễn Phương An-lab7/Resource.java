public abstract class Resource {
    private String resourceId;
    private String resourceName;
    protected boolean isAvailable;

    public Resource ( String resourceId , String resourceName , boolean isAvailable){
        this.resourceId=resourceId;
        this.resourceName=resourceName;
        this.isAvailable=isAvailable;
    }

    public String getResourceId(){
        return resourceId;
    }

    public void setResourceId(String resourceId){
        this.resourceId=resourceId;
    }

    public String getResourceName(){
        return resourceName;
    }

    public void setResourceName(String resourceName){
        this.resourceName=resourceName;
    }

    public boolean isAvailable(){
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable){
        this.isAvailable = isAvailable;
    }

    public abstract String getResourceType();

    @Override
    public String toString(){
        return "ResourceId: "+resourceId+",ResourceName: "+resourceName+",IsAvailable: "+ isAvailable ;
    }
}
