import java.util.*;

class BookingSystem {

    private Queue<String> bookingQueue = new LinkedList<>();
    private Map<String, Integer> inventory = new HashMap<>();
    private Set<String> allocatedRooms = new HashSet<>();

    public BookingSystem() {
        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);
    }

    public synchronized void addRequest(String roomType) {
        bookingQueue.offer(roomType);
        System.out.println(Thread.currentThread().getName() + " requested " + roomType);
    }

    public synchronized void processRequest() {

        if (bookingQueue.isEmpty()) {
            return;
        }

        String roomType = bookingQueue.poll();

        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {

            String roomId = roomType.substring(0,1).toUpperCase() + (100 + new Random().nextInt(900));

            while (allocatedRooms.contains(roomId)) {
                roomId = roomType.substring(0,1).toUpperCase() + (100 + new Random().nextInt(900));
            }

            allocatedRooms.add(roomId);

            inventory.put(roomType, available - 1);

            System.out.println(Thread.currentThread().getName() +
                    " allocated Room " + roomId + " for " + roomType);

        } else {
            System.out.println(Thread.currentThread().getName() +
                    " booking failed for " + roomType + " (No rooms available)");
        }
    }
}

class BookingThread extends Thread {

    private BookingSystem system;
    private String roomType;

    public BookingThread(BookingSystem system, String roomType) {
        this.system = system;
        this.roomType = roomType;
    }

    public void run() {
        system.addRequest(roomType);
        system.processRequest();
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        BookingSystem system = new BookingSystem();

        Thread t1 = new BookingThread(system, "Single");
        Thread t2 = new BookingThread(system, "Single");
        Thread t3 = new BookingThread(system, "Double");
        Thread t4 = new BookingThread(system, "Suite");
        Thread t5 = new BookingThread(system, "Suite");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }
}