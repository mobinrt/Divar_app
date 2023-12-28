package ir.ac.kntu.util;

import java.util.ArrayList;

public class Customer {
    private String userName;
    private String password;
    private String phoneNumber;
    private String email;
    private int wallet;
    private final ArrayList<Product> savedBox;
    private final ArrayList<Product> history;

    /**
     * @param userName    - customer name
     * @param password    - customer password
     * @param phoneNumber - customer phone number
     * @param email       - customer email
     */
    public Customer(String userName, String password, String phoneNumber, String email) {
        this(userName, password);
        this.phoneNumber = phoneNumber;
        this.email = email;
        wallet = 0;
    }

    public Customer(String userName, String password) {
        this.userName = userName;
        this.password = password;
        savedBox = new ArrayList<>();
        history = new ArrayList<>();
    }

    public void showSavedBox() {
        System.out.println("===============================================   Saved box:  ================================================");
        for (Product product : savedBox) {
            System.out.println(savedBox.indexOf(product) + 1 + ") " + product);
        }
        System.out.println("0) Back");
        System.out.println("==============================================================================================================");
    }

    public void showHistory() {
        if (history.isEmpty()) {
            System.out.println("History box is empty");
            return;
        }
        System.out.println("===============================================   History:  ==================================================");
        for (Product product : history) {
            System.out.println(history.indexOf(product) + 1 + ") " + product);
        }
        System.out.println("==============================================================================================================");
    }

    public void addToSavedBox(Product product) {
        savedBox.add(product);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getWallet() {
        return wallet;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    @Override
    public String toString() {
        return "Customer) " + "{ " +
                "userName: " + userName + ", " +
                "Password: " + password + ", " +
                "Phone Number: " + phoneNumber + ", " +
                "Email: " + email + ", " +
                "Wallet: " + wallet + " }";
    }

    public ArrayList<Product> getSavedBox() {
        return savedBox;
    }

    public ArrayList<Product> getHistory() {
        return history;
    }
}

