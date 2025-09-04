class Vehicle {
    protected String make;
    protected String model;
    protected int year;
    protected double fuelLevel;

    public Vehicle(String make, String model, int year, double fuelLevel) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.fuelLevel = fuelLevel;
    }

    public void startVehicle() {
        System.out.println(make + " " + model + " started.");
    }

    public void stopVehicle() {
        System.out.println(make + " " + model + " stopped.");
    }

    public void refuel(double amount) {
        fuelLevel += amount;
        System.out.println(make + " " + model + " refueled. Current fuel: " + fuelLevel);
    }

    public void displayVehicleInfo() {
        System.out.println(year + " " + make + " " + model + ", Fuel Level: " + fuelLevel);
    }

    public static void main(String[] args) {
        Vehicle car = new Vehicle("Toyota", "Corolla", 2018, 50);
        Vehicle truck = new Vehicle("Ford", "F-150", 2020, 70);
        Vehicle motorcycle = new Vehicle("Harley", "Sportster", 2022, 20);

        Vehicle[] vehicles = {car, truck, motorcycle};

        for (Vehicle v : vehicles) {
            v.startVehicle();
            v.displayVehicleInfo();
            v.refuel(10);
            v.stopVehicle();
            System.out.println();
        }

        // Reusability: The Vehicle class is reused for different types of vehicles.
        // Extensibility: We can extend Vehicle to create Car, Truck, Motorcycle subclasses.
        // Benefits: Avoids code duplication, promotes modular and maintainable code.
    }
}