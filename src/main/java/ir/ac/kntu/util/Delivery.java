package ir.ac.kntu.util;

import java.awt.*;

public class Delivery extends User implements Location {
    private VehicleType vehicleType;
    private boolean isAvailable;

    public Delivery(String userName, String password, String phoneNumber, String email, VehicleType vehicleType) {
        super(userName, password, phoneNumber, email);
        this.vehicleType = vehicleType;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
