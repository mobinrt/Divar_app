package ir.ac.kntu.util;

public class Delivery implements Location {
    private String name;
    private VehicleType vehicleType;
    private double wallet;
    private boolean isAvailable;

    public Delivery() {
        wallet = 0;
        isAvailable = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public double getSalary() {
        return wallet;
    }

    public void setSalary(double salary) {
        this.wallet = salary;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
