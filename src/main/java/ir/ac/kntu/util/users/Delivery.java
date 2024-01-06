package ir.ac.kntu.util.users;

import ir.ac.kntu.util.Product;
import ir.ac.kntu.util.enums.UsersRole;
import ir.ac.kntu.util.enums.VehicleType;

import java.util.ArrayList;

public class Delivery extends User {
    private final VehicleType vehicleType;
    private boolean isAvailable;
    private final ArrayList<Product> history;

    public Delivery(String userName, String password, String phoneNumber, String email, VehicleType vehicleType) {
        super(userName, password, phoneNumber, email);
        this.vehicleType = vehicleType;
        isAvailable = true;
        history = new ArrayList<>();
        setRole(UsersRole.DELIVERY);
        setX(0);
        setY(0);
        setLocation();
    }

    @Override
    public String toString() {
        return "{ Role: " + getRole() + ", " +
                "userName: " + getUserName() + ", " +
                "Password: " + getPassword() + ", " +
                "Phone Number: " + getPhoneNumber() + ", " +
                "Email: " + getEmail() + ", " +
                "Wallet: " + getWallet() + ", " +
                "Location: " + location() + " }" +
                "(" + available() + ")";
    }

    private String available() {
        if (isAvailable)
            return "free at the moment.";
        return "product to deliver.";
    }

    private String location() {
        if (isAvailable)
            return "[" + getX() + ", " + getY() + "]";
        return "unAvailable";
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
