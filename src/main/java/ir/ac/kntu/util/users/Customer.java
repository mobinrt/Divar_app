package ir.ac.kntu.util.users;

import ir.ac.kntu.util.Product;
import ir.ac.kntu.util.ShowHistory;
import ir.ac.kntu.util.enums.UsersRole;

import java.util.ArrayList;

public class Customer extends User implements ShowHistory {
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
        if (getSavedBox().isEmpty()) {
            System.out.println("Saved box is empty");
            return;
        }
            System.out.println("===============================================   Saved box:  ================================================");
        for (Product product : savedBox) {
            System.out.println(savedBox.indexOf(product) + 1 + ") " + product);
        }
        System.out.println("0) Back");
        System.out.println("==============================================================================================================");
    }

    @Override
    public void showHistory() {
        if (history.isEmpty()) {
            System.out.println("History box is empty");
            return;
        }
        System.out.println("===============================================   History:  ==================================================");
        for (Product product : history) {
            System.out.println(history.indexOf(product) + 1 + ") " + product + product.productStatus(product));
        }
        System.out.println("==============================================================================================================");
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

