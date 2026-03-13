import java.util.*;

class Reservation {
    String guestName;
    String roomType;

    Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

class BookingRequestQueue {
    Queue<Reservation> queue = new LinkedList<>();

    void addRequest(Reservation r) {
        queue.add(r);
    }

    void processRequests() {
        System.out.println("Booking Request Queue");
        while (!queue.isEmpty()) {
            Reservation r = queue.poll();
            System.out.println("Processing booking for Guest: " + r.guestName + ", Room Type: " + r.roomType);
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        bookingQueue.addRequest(new Reservation("Abhi", "Single"));
        bookingQueue.addRequest(new Reservation("Subha", "Double"));
        bookingQueue.addRequest(new Reservation("Vanmathi", "Suite"));

        bookingQueue.processRequests();
    }
}