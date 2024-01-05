package ir.ac.kntu.util;

import java.util.ArrayList;

public class Seller extends User {
    private ArrayList<Product> products;
    private final ArrayList<Product> history;
    private final ArrayList<String> feedback;

    /**
     * @param userName    - seller name
     * @param password    - seller password
     * @param phoneNumber - seller phone number
     * @param email       - seller email
     */
    public Seller(String userName, String password, String phoneNumber, String email) {
        super(userName, password, phoneNumber, email);
        history = new ArrayList<>();
        products = new ArrayList<>();
        feedback = new ArrayList<>();
        setRole(UsersRole.SELLER);
    }

    public void showAvailableAds() {
        if (products.isEmpty()) {
            System.out.println("Seller product box is empty");
            return;
        }
        System.out.println("===============================================   AvailableAds:  =============================================");
        for (Product product : products) {
            System.out.println(products.indexOf(product) + 1 + ") " + product + " (" + visibleProduct(product) + ")");
        }
        System.out.println("==============================================================================================================");
    }

    public void showHistory() {
        if (history.isEmpty()) {
            System.out.println("Seller history box is empty");
            return;
        }
        System.out.println("===============================================   History:  ==================================================");
        for (Product product : history) {
            System.out.println(history.indexOf(product) + 1 + ") " + product);
        }
        System.out.println("==============================================================================================================");
    }


    private String visibleProduct(Product product) {
        if (product.getIsVisible()) {
            return "Accepted";
        }
        return "Waiting for confirmation";
    }

    public Product addToRequestList(String adsCategory, String name, Seller seller, double price) {
        products.add(new Product(adsCategory, name, seller, price));
        return products.get(products.size() - 1);
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<Product> getHistory() {
        return history;
    }

    public ArrayList<String> getFeedback() {
        return feedback;
    }
}


