package ir.ac.kntu.util;

import java.util.ArrayList;

public class Delivery extends User {
    private final VehicleType vehicleType;
    private boolean isAvailable;
    private final ArrayList<Product> history;
    public Delivery(String userName, String password, String phoneNumber, String email, VehicleType vehicleType) {
        super(userName, password, phoneNumber, email);
        this.vehicleType = vehicleType;
        history = new ArrayList<>();
        setX(0);
        setY(0);
        setLocation();
    }

    @Override
    public String toString() {
        return "Role: " + getRole() + ", " +
                "userName: " + getUserName() + ", " +
                "Password: " + getPassword() + ", " +
                "Phone Number: " + getPhoneNumber() + ", " +
                "Email: " + getEmail() + ", " +
                "Wallet: " + getWallet() + " }";
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public ArrayList<Product> getHistory() {
        return history;
    }
}
