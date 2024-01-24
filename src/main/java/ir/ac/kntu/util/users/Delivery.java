package ir.ac.kntu.util.users;

import ir.ac.kntu.util.Product;
import ir.ac.kntu.util.ShowList;
import ir.ac.kntu.util.enums.UsersRole;
import ir.ac.kntu.util.enums.VehicleType;

import java.util.ArrayList;

public class Delivery extends User implements ShowList {
    private final VehicleType vehicleType;
    private boolean isAvailable;
    private int payment;
    private ArrayList<Product> history;

    public Delivery(String userName, String password, String phoneNumber, String email, VehicleType vehicleType) {
        super(userName, password, phoneNumber, email);
        this.vehicleType = vehicleType;
        isAvailable = true;
        history = new ArrayList<>();
        setRole(UsersRole.DELIVERY);
        setPayment(0);
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

    public void showHistory() {
        if (history.isEmpty()) {
            System.out.println("History is empty");
            return;
        }
        System.out.println("===============================================   History:  ==================================================");
        showList(history);
        System.out.println("==============================================================================================================");
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

    public void setHistory(ArrayList<Product> history) {
        this.history = history;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }
}
