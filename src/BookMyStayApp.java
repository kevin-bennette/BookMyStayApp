import java.util.*;

public class BookMyStayApp {

    private Queue<String> bookingQueue = new LinkedList<>();
    private Map<String, Integer> inventory = new HashMap<>();
    private Map<String, Set<String>> allocatedRooms = new HashMap<>();
    private Set<String> usedRoomIds = new HashSet<>();

    public BookMyStayApp() {
        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);

        allocatedRooms.put("Single", new HashSet<>());
        allocatedRooms.put("Double", new HashSet<>());
        allocatedRooms.put("Suite", new HashSet<>());
    }

    public void addBookingRequest(String roomType) {
        bookingQueue.offer(roomType);
        System.out.println("Booking request added for: " + roomType);
    }

    private String generateRoomId(String roomType) {
        String roomId;
        do {
            roomId = roomType.substring(0,1).toUpperCase() + (100 + new Random().nextInt(900));
        } while (usedRoomIds.contains(roomId));

        usedRoomIds.add(roomId);
        return roomId;
    }

    public void processBookings() {

        while (!bookingQueue.isEmpty()) {

            String roomType = bookingQueue.poll();
            System.out.println("\nProcessing booking for: " + roomType);

            int available = inventory.getOrDefault(roomType, 0);

            if (available > 0) {

                String roomId = generateRoomId(roomType);

                allocatedRooms.get(roomType).add(roomId);

                inventory.put(roomType, available - 1);

                System.out.println("Reservation Confirmed!");
                System.out.println("Assigned Room ID: " + roomId);
                System.out.println("Remaining " + roomType + " rooms: " + (available - 1));

            } else {
                System.out.println("No rooms available for " + roomType);
            }
        }
    }

    public void displayAllocations() {

        System.out.println("\nRoom Allocations:");

        for (String type : allocatedRooms.keySet()) {
            System.out.println(type + " -> " + allocatedRooms.get(type));
        }
    }

    public static void main(String[] args) {

        BookMyStayApp service = new BookMyStayApp();

        service.addBookingRequest("Single");
        service.addBookingRequest("Double");
        service.addBookingRequest("Single");
        service.addBookingRequest("Suite");
        service.addBookingRequest("Double");

        service.processBookings();

        service.displayAllocations();
    }
}