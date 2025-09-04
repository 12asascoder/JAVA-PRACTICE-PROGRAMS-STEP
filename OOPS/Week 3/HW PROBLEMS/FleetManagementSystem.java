import java.util.*;

class VehicleF {
    String vehicleId, brand, model, fuelType, currentStatus;
    int year;
    double mileage;
    static int totalVehicles = 0;
    static double fleetValue = 0;
    static String companyName = "Unset Transport";
    static double totalFuelConsumption = 0;
    private static int vid=0;

    public VehicleF(String brand, String model, int year, double mileage, String fuelType) {
        this.vehicleId = "VH" + String.format("%03d", ++vid);
        this.brand = brand; this.model = model; this.year = year;
        this.mileage = mileage; this.fuelType = fuelType; this.currentStatus = "Available";
        totalVehicles++;
    }

    void assignDriver(Driver d) { d.assignedVehicle = this; this.currentStatus = "Assigned"; d.totalTrips = 0; }
    void scheduleMaintenance() { this.currentStatus = "Maintenance"; }
    double calculateRunningCost(double fuelPricePerLitre, double distanceKm, double avgKmPerL) {
        double fuelUsed = distanceKm / avgKmPerL;
        totalFuelConsumption += fuelUsed;
        return fuelUsed * fuelPricePerLitre;
    }
    void updateMileage(double addKm) { this.mileage += addKm; }
    boolean checkServiceDue(double serviceIntervalKm) { return mileage % serviceIntervalKm >= (serviceIntervalKm - 500); }
}

class CarF extends VehicleF { int seats; public CarF(String brand,String model,int year,double mileage,String fuel,int seats){ super(brand,model,year,mileage,fuel); this.seats=seats; } }
class BusF extends VehicleF { int seatingCapacity; public BusF(String brand,String model,int year,double mileage,String fuel,int cap){ super(brand,model,year,mileage,fuel); this.seatingCapacity=cap; } }
class TruckF extends VehicleF { double loadCapacity; public TruckF(String brand,String model,int year,double mileage,String fuel,double load){ super(brand,model,year,mileage,fuel); this.loadCapacity=load; } }

class Driver {
    String driverId, driverName, licenseType;
    VehicleF assignedVehicle;
    int totalTrips;
    private static int did=0;
    public Driver(String name, String licenseType){
        this.driverId="DR"+String.format("%03d",++did);
        this.driverName=name; this.licenseType=licenseType;
    }
}

public class FleetManagementSystem {
    static double getFleetUtilization(VehicleF[] fleet) {
        int assigned=0, total=0;
        for (VehicleF v: fleet) if (v!=null){ total++; if ("Assigned".equals(v.currentStatus)) assigned++; }
        return total==0?0:(assigned*100.0/total);
    }
    static double calculateTotalMaintenanceCost(VehicleF[] fleet) {
        // mock: maintenance cost based on type/year
        double cost=0;
        for (VehicleF v: fleet) if (v!=null) cost += 0.02 * (2025 - v.year) * 10000;
        return cost;
    }
    static VehicleF[] getVehiclesByType(VehicleF[] fleet, String type) {
        int c=0; for (VehicleF v: fleet) if (v!=null && v.getClass().getSimpleName().startsWith(type)) c++;
        VehicleF[] out=new VehicleF[c]; int i=0;
        for (VehicleF v: fleet) if (v!=null && v.getClass().getSimpleName().startsWith(type)) out[i++]=v;
        return out;
    }

    public static void main(String[] args) {
        VehicleF.companyName = "Nova Logistics";

        VehicleF v1 = new CarF("Toyota","Etios",2019,45000,"Petrol",5);
        VehicleF v2 = new BusF("Tata","CityRide",2018,120000,"Diesel",40);
        VehicleF v3 = new TruckF("Ashok Leyland","HaulPro",2020,90000,"Diesel",12.0);
        VehicleF[] fleet = {v1,v2,v3};

        Driver d1 = new Driver("Arvind","LMV");
        Driver d2 = new Driver("Rakesh","HMV");

        v1.assignDriver(d1);
        v3.assignDriver(d2);

        double cost1 = v1.calculateRunningCost(105, 150, 15);
        v1.updateMileage(150);
        double cost3 = v3.calculateRunningCost(98, 400, 5);
        v3.updateMileage(400);

        System.out.println("Trip costs: Car ₹"+String.format("%.2f",cost1)+", Truck ₹"+String.format("%.2f",cost3));
        System.out.println("Fleet Utilization: " + String.format("%.1f", getFleetUtilization(fleet)) + "%");
        System.out.println("Total fuel used (approx): " + String.format("%.2f", VehicleF.totalFuelConsumption) + " L");
        System.out.println("Maintenance Estimate: ₹" + String.format("%.2f", calculateTotalMaintenanceCost(fleet)));
        System.out.println("Trucks in fleet: " + getVehiclesByType(fleet,"Truck").length);
    }
}
