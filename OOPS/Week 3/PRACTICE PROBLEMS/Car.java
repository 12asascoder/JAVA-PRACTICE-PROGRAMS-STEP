public class Car {
    String brand;
    String model;
    int year;
    String color;
    boolean isRunning;

    public Car(String brand, String model, int year, String color) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.color = color;
        this.isRunning = false;
    }

    public void startEngine() {
        isRunning = true;
        System.out.println(brand + " " + model + " engine started.");
    }

    public void stopEngine() {
        isRunning = false;
        System.out.println(brand + " " + model + " engine stopped.");
    }

    public void displayInfo() {
        System.out.println("Car Info: " + brand + " " + model + " (" + year + "), Color: " + color + ", Running: " + isRunning);
    }

    public int getAge() {
        int currentYear = java.time.Year.now().getValue();
        return currentYear - year;
    }

    public static void main(String[] args) {
        Car car1 = new Car("Toyota", "Corolla", 2015, "Red");
        Car car2 = new Car("Honda", "Civic", 2018, "Blue");
        Car car3 = new Car("Ford", "Mustang", 2020, "Black");

        car1.startEngine();
        car1.displayInfo();
        System.out.println("Age: " + car1.getAge() + " years\n");

        car2.displayInfo();
        car2.startEngine();
        car2.stopEngine();
        System.out.println("Age: " + car2.getAge() + " years\n");

        car3.displayInfo();
        System.out.println("Age: " + car3.getAge() + " years\n");

        // Explanation: Each Car object has its own state (attributes/behaviour), just like real cars in real life.
    }
}