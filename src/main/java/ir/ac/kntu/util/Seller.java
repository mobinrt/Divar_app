package ir.ac.kntu.util;

import java.util.ArrayList;

public class Seller {
    private String userName;
    private String password;
    private String phoneNumber;
    private String email;
    private int wallet;
    private ArrayList<Product> products;
    private final ArrayList<Product> history;

    /**
     * @param userName    - seller name
     * @param password    - seller password
     * @param phoneNumber - seller phone number
     * @param email       - seller email
     */
    public Seller(String userName, String password, String phoneNumber, String email) {
        this(userName, password);
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Seller(String userName, String password) {
        this.userName = userName;
        this.password = password;
        products = new ArrayList<>();
        history = new ArrayList<>();
        wallet = 0;
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

    public Product addToRequestList(String adsCategory, String name, Seller seller, int price) {
        products.add(new Product(adsCategory, name, seller, price));
        return products.get(products.size() - 1);
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

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList<Product> getHistory() {
        return history;
    }

    @Override
    public String toString() {
        return "Seller) " + "{ " +
                "UserName: " + userName + ", " +
                "Password: " + password + ", " +
                "Phone Number: " + phoneNumber + ", " +
                "Email: " + email + ", " +
                "Wallet: " + wallet + " }";
    }
}


