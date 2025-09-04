import java.util.*;

class Room {
    String roomNumber;
    String roomType;
    double pricePerNight;
    boolean isAvailable;
    int maxOccupancy;

    public Room(String roomNumber, String roomType, double pricePerNight, int maxOccupancy) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.maxOccupancy = maxOccupancy;
        this.isAvailable = true;
    }
}

class Guest {
    String guestId;
    String guestName;
    String phoneNumber;
    String email;
    String[] bookingHistory;
    int historyCount = 0;
    private static int gidCounter = 0;

    public Guest(String guestName, String phoneNumber, String email) {
        this.guestId = "G" + String.format("%03d", ++gidCounter);
        this.guestName = guestName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.bookingHistory = new String[20];
    }

    void addToHistory(String bookingId) {
        if (historyCount < bookingHistory.length) bookingHistory[historyCount++] = bookingId;
    }
}

class Booking {
    String bookingId;
    Guest guest;
    Room room;
    String checkInDate;
    String checkOutDate;
    double totalAmount;

    static int totalBookings = 0;
    static double hotelRevenue = 0;
    static String hotelName = "Unset Hotel";
    private static int bidCounter = 0;

    public Booking(Guest guest, Room room, String in, String out, int nights) {
        this.bookingId = "B" + String.format("%04d", ++bidCounter);
        this.guest = guest;
        this.room = room;
        this.checkInDate = in;
        this.checkOutDate = out;
        this.totalAmount = room.pricePerNight * nights;
        totalBookings++;
        hotelRevenue += totalAmount;
    }

    static void setHotelName(String name) { hotelName = name; }
    static double getTotalRevenue() { return hotelRevenue; }

    static double getOccupancyRate(Room[] rooms) {
        int occupied = 0;
        int total = 0;
        for (Room r : rooms) if (r != null) { total++; if (!r.isAvailable) occupied++; }
        return total == 0 ? 0 : (occupied * 100.0 / total);
    }

    static String getMostPopularRoomType(Room[] rooms, Booking[] bookings) {
        // Count booked room types from bookings
        Map<String,Integer> counts = new HashMap<>();
        for (Booking b : bookings) if (b != null) counts.put(b.room.roomType, counts.getOrDefault(b.room.roomType,0)+1);
        String best = "N/A";
        int max = -1;
        for (Map.Entry<String,Integer> e : counts.entrySet()) if (e.getValue() > max) { max = e.getValue(); best = e.getKey(); }
        return best;
    }
}

public class HotelReservationSystem {
    static Booking makeReservation(Guest guest, Room room, String in, String out, int nights, Booking[] bookings, int idx) {
        if (!room.isAvailable) {
            System.out.println("Room " + room.roomNumber + " not available.");
            return null;
        }
        room.isAvailable = false;
        Booking b = new Booking(guest, room, in, out, nights);
        bookings[idx] = b;
        guest.addToHistory(b.bookingId);
        System.out.println("Booked " + room.roomNumber + " for " + guest.guestName + " @ ₹" + b.totalAmount);
        return b;
    }

    static void cancelReservation(Booking b) {
        if (b == null) return;
        if (!b.room.isAvailable) {
            b.room.isAvailable = true;
            Booking.hotelRevenue -= b.totalAmount;
            Booking.totalBookings--;
            System.out.println("Cancelled booking " + b.bookingId);
        }
    }

    static boolean checkAvailability(Room room) { return room.isAvailable; }

    static double calculateBill(Booking b, int nightsStayed) {
        return b.room.pricePerNight * nightsStayed;
    }

    public static void main(String[] args) {
        Booking.setHotelName("Azure Bay Hotel");

        Room[] rooms = {
            new Room("101","Deluxe", 3500, 2),
            new Room("102","Deluxe", 3500, 2),
            new Room("201","Suite", 7000, 4),
            new Room("301","Standard", 2200, 2)
        };

        Guest g1 = new Guest("Riya", "9876543210", "riya@mail.com");
        Guest g2 = new Guest("Kabir", "9123456780", "kabir@mail.com");

        Booking[] bookings = new Booking[10];
        Booking b1 = makeReservation(g1, rooms[0], "2025-09-05", "2025-09-07", 2, bookings, 0);
        Booking b2 = makeReservation(g2, rooms[2], "2025-09-05", "2025-09-06", 1, bookings, 1);

        System.out.println("Occupancy Rate: " + String.format("%.1f", Booking.getOccupancyRate(rooms)) + "%");
        System.out.println("Total Revenue: ₹" + String.format("%.2f", Booking.getTotalRevenue()));
        System.out.println("Popular Room Type: " + Booking.getMostPopularRoomType(rooms, bookings));

        // Checkout g1 with bill calculation
        double bill = calculateBill(b1, 2);
        System.out.println("Bill for " + g1.guestName + ": ₹" + bill);

        // Cancel second booking (demonstration)
        cancelReservation(b2);
        System.out.println("Revenue after cancellation: ₹" + String.format("%.2f", Booking.getTotalRevenue()));
    }
}
