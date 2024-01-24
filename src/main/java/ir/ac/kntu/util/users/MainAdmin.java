package ir.ac.kntu.util.users;

import ir.ac.kntu.util.enums.UsersRole;

public class MainAdmin extends Admin {
    private int deliveryMoney;

    /**
     * @param userName    - admin name
     * @param password    - admin password
     * @param phoneNumber - admin phone number
     * @param email       - admin email
     */
    public MainAdmin(String userName, String password, String phoneNumber, String email) {
        super(userName, password, phoneNumber, email);
        setRole(UsersRole.MAIN_ADMIN);
        setMainAdmin(true);
    }

    public int getDeliveryMoney() {
        return deliveryMoney;
    }

    public void setDeliveryMoney(int deliveryMoney) {
        this.deliveryMoney = deliveryMoney;
    }
}
