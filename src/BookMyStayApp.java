import java.io.*;
import java.util.*;

class SystemState implements Serializable {
    Map<String, Integer> inventory;
    List<String> bookingHistory;

    SystemState(Map<String, Integer> inventory, List<String> bookingHistory) {
        this.inventory = inventory;
        this.bookingHistory = bookingHistory;
    }
}

class PersistenceService {

    public void saveState(SystemState state, String fileName) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
            out.writeObject(state);
            out.close();
            System.out.println("System state saved successfully.");
        } catch (Exception e) {
            System.out.println("Error saving system state.");
        }
    }

    public SystemState loadState(String fileName) {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
            SystemState state = (SystemState) in.readObject();
            in.close();
            System.out.println("System state restored successfully.");
            return state;
        } catch (Exception e) {
            System.out.println("No previous state found. Starting fresh.");
            return null;
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        String fileName = "system_state.dat";

        PersistenceService service = new PersistenceService();

        SystemState restoredState = service.loadState(fileName);

        Map<String, Integer> inventory;
        List<String> bookingHistory;

        if (restoredState != null) {
            inventory = restoredState.inventory;
            bookingHistory = restoredState.bookingHistory;
        } else {
            inventory = new HashMap<>();
            bookingHistory = new ArrayList<>();

            inventory.put("Single", 2);
            inventory.put("Double", 2);
            inventory.put("Suite", 1);
        }

        bookingHistory.add("RES101 - Single");
        bookingHistory.add("RES102 - Double");

        System.out.println("\nCurrent Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " : " + inventory.get(type));
        }

        System.out.println("\nBooking History:");
        for (String b : bookingHistory) {
            System.out.println(b);
        }

        SystemState newState = new SystemState(inventory, bookingHistory);

        service.saveState(newState, fileName);
    }
}