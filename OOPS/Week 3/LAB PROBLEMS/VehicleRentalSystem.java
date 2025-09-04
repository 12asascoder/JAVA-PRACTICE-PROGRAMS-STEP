public class VehicleRentalSystem {
    private String vehicleId;
    private String brand;
    private String model;
    private double rentPerDay;
    private boolean isAvailable;
    private static int totalVehicles = 0;
    private static double totalRevenue = 0;
    private static String companyName;
    private static int totalRentalDays = 0;
    private static int counter = 0;

    public VehicleRentalSystem(String brand, String model, double rentPerDay) {
        this.vehicleId = generateVehicleId();
        this.brand = brand;
        this.model = model;
        this.rentPerDay = rentPerDay;
        this.isAvailable = true;
        totalVehicles++;
    }

    private static String generateVehicleId() {
        counter++;
        return "V" + String.format("%03d", counter);
    }

    public void rentVehicle(int days) {
        if (isAvailable) {
            double rent = calculateRent(days);
            isAvailable = false;
            totalRentalDays += days;
            System.out.println(brand + " " + model + " rented for " + days + " days. Rent: " + rent);
        } else {
            System.out.println("Vehicle not available.");
        }
    }

    public void returnVehicle() {
        isAvailable = true;
        System.out.println(brand + " " + model + " returned and is now available.");
    }

    public double calculateRent(int days) {
        double rent = rentPerDay * days;
        totalRevenue += rent;
        return rent;
    }

    public void displayVehicleInfo() {
        System.out.println("VehicleID: " + vehicleId + ", Brand: " + brand + ", Model: " + model +
                ", Rent/Day: " + rentPerDay + ", Available: " + isAvailable);
    }

    public static void setCompanyName(String name) { companyName = name; }
    public static double getTotalRevenue() { return totalRevenue; }
    public static double getAverageRentPerDay() { return totalRentalDays == 0 ? 0 : totalRevenue / totalRentalDays; }
    public static void displayCompanyStats() {
        System.out.println("Company: " + companyName + ", Total Vehicles: " + totalVehicles +
                ", Revenue: " + totalRevenue + ", Avg Rent/Day: " + getAverageRentPerDay());
    }

    public static void main(String[] args) {
        setCompanyName("Super Rentals");

        VehicleRentalSystem v1 = new VehicleRentalSystem("Toyota", "Camry", 100);
        VehicleRentalSystem v2 = new VehicleRentalSystem("Honda", "Civic", 80);

        v1.rentVehicle(3);
        v2.rentVehicle(2);
        v1.returnVehicle();

        v1.displayVehicleInfo();
        v2.displayVehicleInfo();

        displayCompanyStats();
    }
}