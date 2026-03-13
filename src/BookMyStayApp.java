import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class BookingValidator {

    private Map<String, Integer> inventory = new HashMap<>();

    public BookingValidator() {
        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);
    }

    public void validateBooking(String roomType) throws InvalidBookingException {

        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        int available = inventory.get(roomType);

        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + roomType);
        }

        inventory.put(roomType, available - 1);

        System.out.println("Booking successful for room type: " + roomType);
        System.out.println("Remaining rooms: " + (available - 1));
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        BookingValidator validator = new BookingValidator();

        String[] requests = {"Single", "Suite", "Double", "Penthouse", "Suite"};

        for (String roomType : requests) {

            try {
                validator.validateBooking(roomType);
            } catch (InvalidBookingException e) {
                System.out.println("Booking failed: " + e.getMessage());
            }
        }
    }
}