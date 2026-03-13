import java.util.*;

class Reservation {
    String reservationId;
    String roomType;
    String roomId;

    Reservation(String reservationId, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.roomId = roomId;
    }
}

class CancellationService {

    private Map<String, Reservation> reservations = new HashMap<>();
    private Map<String, Integer> inventory = new HashMap<>();
    private Stack<String> rollbackStack = new Stack<>();

    public CancellationService() {
        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);
    }

    public void confirmBooking(String reservationId, String roomType, String roomId) {
        reservations.put(reservationId, new Reservation(reservationId, roomType, roomId));
        inventory.put(roomType, inventory.get(roomType) - 1);
        System.out.println("Booking confirmed: " + reservationId + " RoomID: " + roomId);
    }

    public void cancelBooking(String reservationId) {

        if (!reservations.containsKey(reservationId)) {
            System.out.println("Cancellation failed: Reservation does not exist.");
            return;
        }

        Reservation r = reservations.remove(reservationId);

        rollbackStack.push(r.roomId);

        inventory.put(r.roomType, inventory.get(r.roomType) + 1);

        System.out.println("Booking cancelled: " + reservationId);
        System.out.println("Room released: " + r.roomId);
        System.out.println("Inventory restored for " + r.roomType);
    }

    public void showRollbackHistory() {
        System.out.println("\nRollback Stack:");
        for (String id : rollbackStack) {
            System.out.println(id);
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        CancellationService service = new CancellationService();

        service.confirmBooking("RES101", "Single", "S101");
        service.confirmBooking("RES102", "Double", "D201");

        service.cancelBooking("RES101");

        service.cancelBooking("RES999");

        service.showRollbackHistory();
    }
}