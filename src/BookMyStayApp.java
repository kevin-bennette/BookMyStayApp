import java.util.HashMap;
import java.util.Map;

class Room {
    String type;
    double price;
    String amenities;

    Room(String type, double price, String amenities) {
        this.type = type;
        this.price = price;
        this.amenities = amenities;
    }

    void display() {
        System.out.println("Room Type: " + type);
        System.out.println("Price: " + price);
        System.out.println("Amenities: " + amenities);
        System.out.println();
    }
}

class Inventory {
    Map<String, Integer> rooms = new HashMap<>();

    void addRoom(String type, int count) {
        rooms.put(type, count);
    }

    int getAvailability(String type) {
        if (rooms.containsKey(type)) {
            return rooms.get(type);
        }
        return 0;
    }

    Map<String, Integer> getAllRooms() {
        return rooms;
    }
}

class SearchService {
    Inventory inventory;
    Map<String, Room> roomDetails;

    SearchService(Inventory inventory, Map<String, Room> roomDetails) {
        this.inventory = inventory;
        this.roomDetails = roomDetails;
    }

    void searchRooms() {
        for (String type : inventory.getAllRooms().keySet()) {
            int available = inventory.getAvailability(type);
            if (available > 0) {
                Room r = roomDetails.get(type);
                r.display();
                System.out.println("Available Rooms: " + available);
                System.out.println("-----------------------");
            }
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();

        inventory.addRoom("Single", 3);
        inventory.addRoom("Double", 0);
        inventory.addRoom("Suite", 2);

        Map<String, Room> roomDetails = new HashMap<>();

        roomDetails.put("Single", new Room("Single", 2000, "WiFi, TV"));
        roomDetails.put("Double", new Room("Double", 3500, "WiFi, TV, AC"));
        roomDetails.put("Suite", new Room("Suite", 5000, "WiFi, TV, AC, Mini Bar"));

        SearchService service = new SearchService(inventory, roomDetails);

        System.out.println("Available Rooms:");
        service.searchRooms();
    }
}