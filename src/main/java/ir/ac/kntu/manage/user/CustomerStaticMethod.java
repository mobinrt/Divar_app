package ir.ac.kntu.manage.user;

import ir.ac.kntu.Main;
import ir.ac.kntu.manage.ShowMenu;
import ir.ac.kntu.util.Product;
import ir.ac.kntu.util.enums.AdsCategory;
import ir.ac.kntu.util.users.Customer;
import ir.ac.kntu.util.users.MainAdmin;
import ir.ac.kntu.util.users.User;

import java.util.ArrayList;
import java.util.Scanner;

public class CustomerStaticMethod {
    public void addToSavedBox(Customer customer, Product product) {
        customer.addToSavedBox(product);
    }

    public void setLocation(Scanner sc, Customer customer) {
        if (customer.getX() < 0 || customer.getY() < 0) {
            System.out.println("you don't set your location.");
            customer.setLocation(sc, customer);
        }
    }

    public void chargeWallet(Scanner sc, Customer customer) {
        System.out.println("How much do you want to charge your wallet?");
        System.out.print("Enter: ");
        double charge = sc.nextDouble();
        charge += customer.getWallet();
        customer.setWallet(charge);
        System.out.println("Successfully done.");
    }

    public void deleteProductFromSavedBox(Product deleteProduct) {
        Main.getRunManage().getUsers().stream()
                .filter(user -> user instanceof Customer)
                .map(user -> (Customer) user)
                .forEach(customer -> {
                    ArrayList<Product> temp = new ArrayList<>(customer.getSavedBox());
                    temp.removeIf(product -> product.equals(deleteProduct));
                    customer.setSavedBox(temp);
                });
    }

    public MainAdmin findMainAdmin() {
        return Main.getRunManage().getUsers().stream()
                .filter(User::isMainAdmin)
                .map(user -> (MainAdmin) user)
                .findFirst()
                .orElse(null);
    }

    public void inputPriceFilter(Scanner sc, int[] filter) {
        System.out.print("Enter min price range: ");
        int temp = sc.nextInt();
        filter[0] = temp;
        System.out.print("Enter max price range: ");
        temp = sc.nextInt();
        filter[1] = temp;
    }

    public void makeSureToDeliver(int charge, AdsCategory adsCategory) {
        System.out.println("It costs " + (charge * adsCategory.getBaseCharge()) + ". Are you sure?");
        System.out.println("1. Yes");
        System.out.println("2. No");
    }

    public void priceFilterOption() {
        System.out.println("Do you want to filter product with price?");
        ShowMenu.showMenu("Yes, No");
    }

    public void wishToDeliver() {
        System.out.println("Do you want to deliver product?");
        System.out.println("1. Yes");
        System.out.println("2. No");
    }
}
