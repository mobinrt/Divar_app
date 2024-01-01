package ir.ac.kntu.util;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Customer extends User {
    private final ArrayList<Product> savedBox;
    private final ArrayList<Product> history;

    /**
     * @param userName    - customer name
     * @param password    - customer password
     * @param phoneNumber - customer phone number
     * @param email       - customer email
     */
    public Customer(String userName, String password, String phoneNumber, String email) {
        super(userName, password, phoneNumber, email);
        history = new ArrayList<>();
        savedBox = new ArrayList<>();
        setRole(UsersRole.CUSTOMER);
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
            System.out.println(history.indexOf(product) + 1 + ") " + product + productStatus(product));
        }
        System.out.println("==============================================================================================================");
    }

    private String productStatus(Product product) {
        if (product.isReadyToSell())
            return "Ready to sell";
        if (product.isSending())
            return "Sending product";
        if (product.isSold())
            return "sold";
        if (product.isWaitingToSend())
            return "Waiting to send";
        return "";
    }

    public void addToSavedBox(Product product) {
        savedBox.add(product);
    }

    public ArrayList<Product> getSavedBox() {
        return savedBox;
    }

    public ArrayList<Product> getHistory() {
        return history;
    }
}

