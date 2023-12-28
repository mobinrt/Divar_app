package ir.ac.kntu.manage;

import ir.ac.kntu.Main;
import ir.ac.kntu.util.*;

import java.util.ArrayList;
import java.util.Scanner;

public class AdminManage {
    private final ArrayList<Admin> admins;
    private final ArrayList<Product> req;

    public AdminManage() {
        admins = new ArrayList<>();
        admins.add(new Admin("a", "a"));
        req = new ArrayList<>();
    }

    public Admin signInAdmin(Scanner sc) {
        sc.nextLine();
        System.out.print("Enter your username: ");
        String userName = sc.nextLine();
        System.out.print("Enter your password: ");
        String password = sc.nextLine();
        Admin admin = findUser(userName, password);
        if (admin == null) {
            System.out.println("Invalid username or password!!");
            System.out.println("==============================================================================================================");
            return signInAdmin(sc);
        }
        return admin;
    }

    /**
     * @param userName - admin name
     * @param password - admin password
     * @return admin
     */
    private Admin findUser(String userName, String password) {
        for (Admin admin : admins) {
            if (userName.equals(admin.getUserName()) && password.equals(admin.getPassword())) {
                return admin;
            }
        }
        return null;
    }

    public void addProductToReq(Product product) {
        req.add(product);
    }

    /**
     * @param sc    - scan input
     * @param admin - current admin (in this proj we have one admin.)
     */
    public void adminMenu(Scanner sc, Admin admin) { // for next project in that case we have more admin
        showAdminMenu();
        int choice = getChoice(sc, 5);
        switch (choice) {
            case 1:
                customerEdit(sc, admin);
                adminMenu(sc, admin);
                break;
            case 2:
                sellerEdit(sc, admin);
                adminMenu(sc, admin);
                break;
            case 3:
                adsEdit(sc, admin);
                adminMenu(sc, admin);
                break;
            case 4:
                reqListOption(sc, admin);
                adminMenu(sc, admin);
                break;
            default:
                Main.getRunManage().run();
                break;
        }
    }

    private void customerEdit(Scanner sc, Admin admin) {
        Main.getRunManage().getCustomerManage().showCustomersList();
        System.out.println("Select one of the customers to remove or press zero to back");
        int choice = getChoice(sc, Main.getRunManage().getCustomerManage().getCustomers().size() + 1);
        if (choice == 0) {
            adminMenu(sc, admin);
            return;
        }
        Main.getRunManage().getCustomerManage().getCustomers().remove(--choice);
        System.out.println("Successfully done.");
        System.out.println("==============================================================================================================");
    }

    private void sellerEdit(Scanner sc, Admin admin) {
        Main.getRunManage().getSellerManage().showSellersList();
        System.out.println("Select one of the sellers to remove or press zero to back");
        int choice = getChoice(sc, Main.getRunManage().getSellerManage().getSellers().size() + 1);
        if (choice == 0) {
            adminMenu(sc, admin);
            return;
        }
        Seller removeSeller = Main.getRunManage().getSellerManage().getSellers().remove(--choice);
        removeSeller.setProducts(new ArrayList<>());
        removeSellerAds(removeSeller, Main.getRunManage().getCustomerManage().getProducts());
        removeSellerAds(removeSeller, req);
        for (Customer customer : Main.getRunManage().getCustomerManage().getCustomers()) {
            removeSellerAds(removeSeller, customer.getSavedBox());
        }
        System.out.println("Successfully done.");
        System.out.println("==============================================================================================================");
    }

    private void removeSellerAds(Seller removeSeller, ArrayList<Product> ads) {
        ads.removeIf(product -> removeSeller.getUserName().matches(product.getSeller().getUserName()));
    }

    private void adsEdit(Scanner sc, Admin admin) {
        Main.getRunManage().getCustomerManage().showAdsList();
        System.out.println("Select one of the ads to remove or press zero to back");
        int choice = getChoice(sc, Main.getRunManage().getCustomerManage().getProducts().size() + 1);
        if (choice == 0) {
            adminMenu(sc, admin);
            return;
        }
        Product product = Main.getRunManage().getCustomerManage().getProducts().remove(--choice);
        Main.getRunManage().getCustomerManage().deleteProductFromSavedBox(product);
        product.getSeller().getProducts().remove(product);
        System.out.println("Successfully done.");
        System.out.println("==============================================================================================================");
    }

    private void reqListOption(Scanner sc, Admin admin) {
        showReqListOption();
        int choice = getChoice(sc, 4);
        switch (choice) {
            case 1:
                acceptReq(sc, admin);
                reqListOption(sc, admin);
                break;
            case 2:
                deniedReq(sc, admin);
                reqListOption(sc, admin);
                break;
            case 3:
                showReqList(sc, admin);
                reqListOption(sc, admin);
                break;
            default:
                adminMenu(sc, admin);
                break;
        }
    }

    private void acceptReq(Scanner sc, Admin admin) {
        showReqList(sc, admin);
        System.out.println("Which product do you want to accept?");
        int choice = getChoice(sc, req.size() + 1);
        if (choice == 0) {
            reqListOption(sc, admin);
            return;
        }
        Product product = req.get(--choice);
        product.setIsVisible(true);
        Main.getRunManage().getCustomerManage().getProducts().add(product);
        req.remove(product);
    }

    private void deniedReq(Scanner sc, Admin admin) {
        showReqList(sc, admin);
        System.out.println("Which product do you want to delete?");
        int choice = getChoice(sc, req.size() + 1);
        if (choice == 0) {
            reqListOption(sc, admin);
            return;
        }
        Product deleteProduct = req.get(--choice);
        Seller seller = req.get(choice).getSeller();
        seller.getProducts().remove(deleteProduct);
        req.remove(deleteProduct);
    }

    private void showReqList(Scanner sc, Admin admin) {
        if (req.isEmpty()) {
            System.out.println("Requests box is empty");
            reqListOption(sc, admin);
            return;
        }
        System.out.println("===============================================   Requests:  ==================================================");
        for (Product product : req) {
            System.out.println(req.indexOf(product) + 1 + ". " + product);
        }
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

    private void showAdminMenu() {
        System.out.println("==============================================================================================================");
        System.out.println("1. Customers");
        System.out.println("2. Sellers");
        System.out.println("3. All ads");
        System.out.println("4. Requests");
        System.out.println("0. Exit");
        System.out.println("==============================================================================================================");
    }

    private void showReqListOption() {
        System.out.println("==============================================================================================================");
        System.out.println("1. Accept request");
        System.out.println("2. Denied request");
        System.out.println("3. Show request list");
        System.out.println("0. Back");
        System.out.println("==============================================================================================================");

    }
}
