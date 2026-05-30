public class Equipment extends Resource {
    private String equipmentCategory;
    private int quantityAvailable;

    public Equipment(String resourceId , String resourceName , boolean isAvailable, String equipmentCategory, int quantityAvailable){
        super(resourceId, resourceName, isAvailable);
        this.equipmentCategory=equipmentCategory;
        this.quantityAvailable=quantityAvailable;
    }

    public String getEquipmentCategory(){
        return equipmentCategory;
    }

    public void setEquipmentCategory(String equipmentCategory){
        this.equipmentCategory=equipmentCategory;
    }

    public int getQuantityAvailable(){
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable){
        this.quantityAvailable=quantityAvailable;
    }

    @Override
    public String getResourceType(){
        return " Equipment";
    }

    @Override
    public boolean isAvailable(){
        return isAvailable;
    }

    @Override
    public String toString(){
        return "\nEquipment:" + super.toString() + ", equipmentCategory: " + equipmentCategory +", quantityAvailable:"+ quantityAvailable ;
    }
}
