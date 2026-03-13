import java.util.*;

class Service {
    String name;
    double cost;

    Service(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String toString() {
        return name + " (" + cost + ")";
    }
}

class AddOnServiceManager {

    private Map<String, List<Service>> reservationServices = new HashMap<>();

    public void addService(String reservationId, Service service) {

        reservationServices.putIfAbsent(reservationId, new ArrayList<>());
        reservationServices.get(reservationId).add(service);

        System.out.println("Service added to reservation " + reservationId + ": " + service.name);
    }

    public double calculateTotalCost(String reservationId) {

        double total = 0;

        List<Service> services = reservationServices.getOrDefault(reservationId, new ArrayList<>());

        for (Service s : services) {
            total += s.cost;
        }

        return total;
    }

    public void displayServices(String reservationId) {

        List<Service> services = reservationServices.getOrDefault(reservationId, new ArrayList<>());

        System.out.println("\nServices for Reservation " + reservationId + ":");

        for (Service s : services) {
            System.out.println(s.name + " - " + s.cost);
        }

        System.out.println("Total Add-On Cost: " + calculateTotalCost(reservationId));
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        AddOnServiceManager manager = new AddOnServiceManager();

        String reservationId = "RES101";

        Service breakfast = new Service("Breakfast", 500);
        Service airportPickup = new Service("Airport Pickup", 1200);
        Service spa = new Service("Spa Access", 1500);

        manager.addService(reservationId, breakfast);
        manager.addService(reservationId, airportPickup);
        manager.addService(reservationId, spa);

        manager.displayServices(reservationId);
    }
}