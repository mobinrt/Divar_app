package ir.ac.kntu.manage;

import ir.ac.kntu.Main;
import ir.ac.kntu.util.AdsCategory;
import ir.ac.kntu.util.Customer;
import ir.ac.kntu.util.Product;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class CustomerManage {
    private final ArrayList<Customer> customers;
    private final ArrayList<Product> products;

    public CustomerManage() {
        customers = new ArrayList<>();
        customers.add(new Customer("c", "c", "c", "c"));
        customers.add(new Customer("cc", "c", "c", "c"));
        products = new ArrayList<>();
    }

    public void showCustomersList() {
        if (customers.isEmpty()) {
            System.out.println("Customer box is empty");
            return;
        }
        System.out.println("===============================================   Customers:  ================================================");
        for (Customer customer : customers) {
            System.out.println(customers.indexOf(customer) + 1 + ") " + customer);
        }
        System.out.println("==============================================================================================================");
    }

    public Customer signInCustomer(Scanner sc) {
        sc.nextLine();
        System.out.print("Enter your username: ");
        String userName = sc.nextLine();
        System.out.print("Enter your password: ");
        String password = sc.nextLine();
        Customer currentCustomer = findUser(userName, password);
        if (currentCustomer == null) {
            System.out.println("Invalid username or password!!");
            System.out.println("==============================================================================================================");
            return signInCustomer(sc);
        }
        return currentCustomer;
    }

    /**
     * @param userName - The name of the user
     * @param password - The password of the user
     * @return customer
     */
    private Customer findUser(String userName, String password) {
        for (Customer customer : customers) {
            if (userName.equals(customer.getUserName()) && password.equals(customer.getPassword())) {
                return customer;
            }
        }
        return null;
    }

    public void customerMenu(Scanner sc, Customer customer) {
        showCustomerMenu();
        int choice = getChoice(sc, 5);
        switch (choice) {
            case 1 -> {
                customerProfile(sc, customer);
                customerMenu(sc, customer);
            }
            case 2 -> {
                sortByPrice(sc, customer);
                showAds(sc, customer);
                customerMenu(sc, customer);
            }
            case 3 -> {
                savedBoxOption(sc, customer);
                customerMenu(sc, customer);
            }
            case 4 -> {
                customer.showHistory();
                customerMenu(sc, customer);
            }
            default -> Main.getRunManage().run();
        }
    }

    private void savedBoxOption(Scanner sc, Customer customer) {
        if (customer.getSavedBox().isEmpty()) {
            System.out.println("Saved box is empty");
            customerMenu(sc, customer);
            return;
        }
        customer.showSavedBox();
        int choice = getChoice(sc, customer.getSavedBox().size() + 1);
        if (choice == 0) {
            customerMenu(sc, customer);
            return;
        }
        Product product = customer.getSavedBox().get(--choice);
        System.out.println("==============================================================================================================");
        System.out.println("1. Buy");
        System.out.println("2. Delete");
        System.out.println("0. Back");
        System.out.println("==============================================================================================================");
        int type = getChoice(sc, 3);
        switch (type) {
            case 1 -> {
                buyAd(sc, customer, product);
                customerMenu(sc, customer);
            }
            case 2 -> {
                customer.getSavedBox().remove(choice);
                customerMenu(sc, customer);
            }
            default -> customerMenu(sc, customer);
        }
    }

    private void sortByPrice(Scanner sc, Customer customer) {
        System.out.println("==============================================================================================================");
        System.out.println("1. Ascending sort by product price");
        System.out.println("2. Descending Acceding sort by product price");
        System.out.println("0. Back");
        System.out.println("==============================================================================================================");
        int choice = getChoice(sc, 3);
        switch (choice) {
            case 1 -> products.sort(Comparator.comparing(Product::getPrice));
            case 2 -> products.sort(Comparator.comparing(Product::getPrice).reversed());
            default -> customerMenu(sc, customer);
        }
    }

    private void showAds(Scanner sc, Customer customer) {
        int choice;
        Product product;
        String category = showAdsCategory(sc, customer);
        if (category.matches("")) {
            showAdsList();
            System.out.println("Select one of the ads to remove or press zero to back: ");
            choice = getChoice(sc, products.size() + 1);
            if (choice == 0) {
                customerMenu(sc, customer);
                return;
            }
            product = products.get(--choice);
        } else {
            int length = showAdsListByCategory(category, sc, customer);
            choice = getChoice(sc, length + 1);
            if (choice == 0) {
                customerMenu(sc, customer);
                return;
            }
            product = findProduct(choice, category);
        }
        showAdListOption();
        int type = getChoice(sc, 3);
        switch (type) {
            case 1 -> {
                addToSavedBox(customer, product);
                customerMenu(sc, customer);
            }
            case 2 -> {
                buyAd(sc, customer, product);
                customerMenu(sc, customer);
            }
            default -> showAds(sc, customer);
        }
    }

    private Product findProduct(int choice, String category) {
        List<Product> temp = new ArrayList<>();
        for (Product product : products) {
            if (category.matches(product.getAdsCategory())) {
                temp.add(product);
            }
        }
        return temp.get(--choice);
    }

    private void addToSavedBox(Customer customer, Product product) {
        customer.addToSavedBox(product);
        System.out.println("Successfully done.");
    }

    private void buyAd(Scanner sc, Customer customer, Product product) {
        if (product.getPrice() > customer.getWallet()) {
            System.out.println("Charge your account. you do not have enough money to buy this product.");
            customerMenu(sc, customer);
            return;
        }
        customer.setWallet(customer.getWallet() - product.getPrice());
        product.getSeller().setWallet(product.getPrice());
        customer.getHistory().add(product);
        product.getSeller().getProducts().remove(product);
        product.getSeller().getHistory().add(product);
        customer.getSavedBox().remove(product);
        deleteProductFromSavedBox(product);
        products.remove(product);
        System.out.println("Successfully done.");
        System.out.println("===========================================================================================================");
    }

    public void deleteProductFromSavedBox(Product deleteProduct) {
        for (Customer customer : customers) {
            customer.getSavedBox().removeIf(product -> product.equals(deleteProduct));
        }
    }


    private void customerProfile(Scanner sc, Customer customer) {
        showProfileOption();
        int choice = getChoice(sc, 4);
        switch (choice) {
            case 1 -> {
                System.out.println(customer.toString());
                customerProfile(sc, customer);
            }
            case 2 -> {
                customer.editUserInfo(sc, customer);
                customerProfile(sc, customer);
            }
            case 3 -> {
                customerWalletMenu(sc, customer);
                customerProfile(sc, customer);
            }
            default -> customerMenu(sc, customer);
        }
    }

    private void customerWalletMenu(Scanner sc, Customer customer) {
        walletOption();
        int choice = getChoice(sc, 3);
        switch (choice) {
            case 1 -> {
                System.out.println("wallet: " + customer.getWallet());
                customerWalletMenu(sc, customer);
            }
            case 2 -> {
                System.out.println("How much do you want to charge your wallet?");
                System.out.print("Enter: ");
                int charge = sc.nextInt();
                charge += customer.getWallet();
                customer.setWallet(charge);
                System.out.println("Successfully done.");
                customerWalletMenu(sc, customer);
            }
            default -> customerProfile(sc, customer);
        }
    }

    public void showAdsList() {
        if (products.isEmpty()) {
            System.out.println("Product box is empty");
            return;
        }
        System.out.println("===============================================   Ads list:  =================================================");
        for (Product product : products) {
            System.out.println(products.indexOf(product) + 1 + ") " + product);
        }
        System.out.println("==============================================================================================================");
    }

    private int showAdsListByCategory(String adsCategory, Scanner sc, Customer customer) {
        if (products.isEmpty()) {
            System.out.println("Product box is empty");
            customerMenu(sc, customer);
            return 0;
        }
        int i = 1;
        System.out.println("===============================================   Ads list:  =================================================");
        for (Product product : products) {
            if (adsCategory.matches("")) {
                System.out.println((products.indexOf(product) + 1) + ") " + product);
                continue;
            }
            if (adsCategory.matches(product.getAdsCategory())) {
                System.out.println(i + ") " + product);
                ++i;
            }
        }
        System.out.println("0) Back");
        System.out.println("==============================================================================================================");
        return i;
    }

    private String showAdsCategory(Scanner sc, Customer customer) {
        String adsCategory = "";
        System.out.println("1. " + AdsCategory.PHONE.name());
        System.out.println("2. " + AdsCategory.HOME_STUFF.name());
        System.out.println("3. " + AdsCategory.STATIONARY.name());
        System.out.println("4. " + AdsCategory.CLOTHE.name());
        System.out.println("5. " + AdsCategory.CAR.name());
        System.out.println("6. All category");
        System.out.println("0. Back");
        int choice = getChoice(sc, 7);
        switch (choice) {
            case 1 -> adsCategory = AdsCategory.PHONE.name();
            case 2 -> adsCategory = AdsCategory.HOME_STUFF.name();
            case 3 -> adsCategory = AdsCategory.STATIONARY.name();
            case 4 -> adsCategory = AdsCategory.CLOTHE.name();
            case 5 -> adsCategory = AdsCategory.CAR.name();
            case 6 -> {
            }
            default -> customerMenu(sc, customer);
        }
        return adsCategory;
    }

    private void showCustomerMenu() {
        System.out.println("==============================================================================================================");
        System.out.println("1. Profile");
        System.out.println("2. Sales ads");
        System.out.println("3. Saved box");
        System.out.println("4. History");
        System.out.println("0. Exit");
        System.out.println("==============================================================================================================");
    }

    private void showProfileOption() {
        System.out.println("==============================================================================================================");
        System.out.println("1. Customer information");
        System.out.println("2. Edit information");
        System.out.println("3. Wallet");
        System.out.println("0. Back");
        System.out.println("==============================================================================================================");
    }

    private void showAdListOption() {
        System.out.println("==============================================================================================================");
        System.out.println("1. Add to save box");
        System.out.println("2. Buy");
        System.out.println("0. Back");
        System.out.println("==============================================================================================================");
    }

    private void walletOption() {
        System.out.println("==============================================================================================================");
        System.out.println("1. Check wallet");
        System.out.println("2. Charge wallet");
        System.out.println("0. Back");
        System.out.println("==============================================================================================================");
    }

    private int getChoice(Scanner scan, int bound) {
        System.out.print("Enter your choice: ");
        int choice = scan.nextInt();
        if (choice >= 0 && choice < bound) {
            return choice;
        } else {
            System.out.println("Invalid input!");
            return getChoice(scan, bound);
        }
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }
}