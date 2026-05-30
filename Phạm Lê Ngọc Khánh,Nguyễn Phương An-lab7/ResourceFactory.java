public class ResourceFactory {
    public static Resource createResource(String resourceType, String resourceId,
String resourceName, boolean isAvailable, String info1,
String info2){
    switch(resourceType){
        case "Room": 
            int capacity = Integer.parseInt(info1);
            return new Room(resourceId, resourceName, isAvailable, capacity, info2);

        case "Equipment":
            int quantity = Integer.parseInt(info2);
            return new Equipment(resourceId, resourceName, isAvailable, info1, quantity);

        default:
            throw new CampusResourceException("Unknown resource type");
    }

}
}
