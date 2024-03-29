package ir.ac.kntu.util.users;

import ir.ac.kntu.util.Product;
import ir.ac.kntu.util.ShowList;
import ir.ac.kntu.util.enums.UsersRole;

import java.util.ArrayList;

public class Customer extends User implements ShowList {
    private ArrayList<Product> savedBox;
    private ArrayList<Product> history;

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
        showList(savedBox);
        System.out.println("==============================================================================================================");
    }

    public void showHistory() {
        if (history.isEmpty()) {
            System.out.println("History is empty");
            return;
        }
        System.out.println("===============================================   History:  ==================================================");
        history.stream()
                .map(product -> history.indexOf(product) + 1 + ") " + product + " {" + product.productStatus(product) + "}")
                .forEach(System.out::println);
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

    public void setHistory(ArrayList<Product> history) {
        this.history = history;
    }

    public void setSavedBox(ArrayList<Product> savedBox) {
        this.savedBox = savedBox;
    }
}

