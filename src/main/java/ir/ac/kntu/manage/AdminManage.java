package ir.ac.kntu.manage;

import ir.ac.kntu.Main;
import ir.ac.kntu.util.*;
import ir.ac.kntu.util.enums.*;
import ir.ac.kntu.util.users.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class AdminManage {
    private final ArrayList<Product> req;
    private final ArrayList<Product> deliveryReq;

    public AdminManage() {
        req = new ArrayList<>();
        deliveryReq = new ArrayList<>();
    }

    public void addProductToReq(Product product) {
        req.add(product);
    }

    /**
     * @param sc    - scan input
     * @param admin - current admin
     */
    public void adminMenu(Scanner sc, Admin admin) {
        showAdminMenu();
        int choice = getChoice(sc, 8);
        switch (choice) {
            case 1 -> {
                adminProfile(sc, admin);
                adminMenu(sc, admin);
            }
            case 2 -> {
                generalEdit(sc, admin, UsersRole.CUSTOMER);
                adminMenu(sc, admin);
            }
            case 3 -> {
                sellerEdit(sc, admin);
                adminMenu(sc, admin);
            }
            case 4 -> {
                generalEdit(sc, admin, UsersRole.DELIVERY);
                adminMenu(sc, admin);
            }
            case 5 -> {
                adsEdit(sc, admin);
                adminMenu(sc, admin);
            }
            case 6 -> {
                reqListOption(sc, admin);
                adminMenu(sc, admin);
            }
            case 7 -> {
                deliverProduct(sc, admin);
                adminMenu(sc, admin);
            }
            default -> Main.getRunManage().run();
        }
    }


    public void adminProfile(Scanner sc, Admin admin) {
        showProfileOption();
        int choice = getChoice(sc, 4);
        switch (choice) {
            case 1 -> {
                System.out.println(admin.toString());
                adminProfile(sc, admin);
            }
            case 2 -> {
                admin.editUserInfo(sc, admin);
                adminProfile(sc, admin);
            }
            case 3 -> {
                adminWalletMenu(sc, admin);
                adminProfile(sc, admin);
            }
            default -> adminMenu(sc, admin);
        }
    }

    public void adminWalletMenu(Scanner sc, Admin admin) {
        showWalletOption();
        int choice = getChoice(sc, 3);
        switch (choice) {
            case 1 -> {
                System.out.println("Your wallet: " + admin.getWallet());
                adminWalletMenu(sc, admin);
            }
            case 2 -> {
                System.out.println("How much do you want to withdraw money? if you want to back press zero.");
                System.out.print("Enter: ");
                int withDraw = sc.nextInt();
                while (withDraw > admin.getWallet()) {
                    System.out.println("You don't have enough money!!");
                    System.out.println("How much do you want to withdraw money? if you want to back press zero.");
                    System.out.print("Enter: ");
                    withDraw = sc.nextInt();
                }
                admin.setWallet(admin.getWallet() - withDraw);
                System.out.println("Successfully done.");
                System.out.println("==============================================================================================================");
                adminWalletMenu(sc, admin);
            }
            default -> adminProfile(sc, admin);
        }
    }

    public void generalEdit(Scanner sc, Admin admin, UsersRole userRole) {
        int length = showUsersList(userRole);
        System.out.println("Select one of the customers to remove or press zero to back");
        int choice = getChoice(sc, length);
        if (choice == 0) {
            adminMenu(sc, admin);
            return;
        }
        User user = findUser(choice, userRole);
        Main.getRunManage().getUsers().remove(user);
        System.out.println("Successfully done.");
        System.out.println("==============================================================================================================");
    }

    public void sellerEdit(Scanner sc, Admin admin) {
        int length = showUsersList(UsersRole.SELLER);
        System.out.println("Select one of the sellers to remove or press zero to back");
        int choice = getChoice(sc, length);
        if (choice == 0) {
            adminMenu(sc, admin);
            return;
        }
        User removeUser = findUser(choice, UsersRole.SELLER);
        Main.getRunManage().getUsers().remove(removeUser);
        Seller removeSeller = (Seller) removeUser;
        removeSeller.setProducts(new ArrayList<>());
        removeSellerAds(removeSeller, Main.getRunManage().getCustomerManage().getProducts());
        removeSellerAds(removeSeller, req);
        for (User user : Main.getRunManage().getUsers()) {
            if (user instanceof Customer customer)
                removeSellerAds(removeSeller, customer.getSavedBox());
        }
        System.out.println("Successfully done.");
        System.out.println("==============================================================================================================");
    }

    public User findUser(int choice, UsersRole role) {
        ArrayList<User> temp = new ArrayList<>();
        for (User user : Main.getRunManage().getUsers()) {
            if (user.getRole().equals(role)) {
                temp.add(user);
            }
        }
        return temp.get(--choice);
    }

    public void deliverProduct(Scanner sc, Admin admin) {
        showReqList(sc, admin, deliveryReq);
        System.out.println("Which product do you want to deliver?");
        int choice = getChoice(sc, deliveryReq.size() + 1);
        if (choice == 0) {
            return;
        }
        Product product = deliveryReq.get(--choice);
        boolean isAvailable = Main.getRunManage().getDeliveryManage().isAvailableDelivery(product);
        if (!isAvailable) {
            System.out.println("No available delivery!");
            return;
        }
        Delivery delivery = findClosestDelivery(product);
        int distance = (int) delivery.calculateDistance(delivery, product.getSeller());
        distance += (int) delivery.calculateDistance(product.getSeller(), product.getCustomer());
        makeDeliveryUnavailable(distance, product, delivery);
        delivery.getHistory().add(product);
        delivery.setX(product.getCustomer().getX());
        delivery.setY(product.getCustomer().getY());
        delivery.setLocation();
    }

    public void makeDeliveryUnavailable(int distanceInKm, Product product, Delivery delivery) {
        product.setWaitingToSend(false);
        product.setReadyToSend(false);
        product.setSold(false);
        deliveryReq.remove(product);
        delivery.setAvailable(false);
        product.setSending(true);
        System.out.println(delivery);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                delivery.setAvailable(true);
                product.setSending(false);
                product.setSold(true);
                timer.cancel();
            }
        }, distanceInKm * 5000L);
    }

    public Delivery findClosestDelivery(Product product) {
        Delivery finalDelivery = null;
        double distance;
        double finalDistance = Double.MAX_VALUE;
        for (User user : Main.getRunManage().getUsers()) {
            if (user instanceof Delivery delivery) {
                if (!(delivery.isAvailable()))
                    continue;
                if (product.getAdsCategory().matches(AdsCategory.HOME_STUFF.toString()) &&
                        delivery.getVehicleType().equals(VehicleType.MOTOR))
                    continue;
                distance = user.calculateDistance(delivery, product.getSeller());
                if (distance < finalDistance) {
                    finalDistance = distance;
                    finalDelivery = delivery;
                }
            }
        }
        return finalDelivery;
    }

    public void removeSellerAds(Seller removeSeller, ArrayList<Product> ads) {
        ads.removeIf(product -> removeSeller.getUserName().matches(product.getSeller().getUserName()));
    }

    public void adsEdit(Scanner sc, Admin admin) {
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

    public void reqListOption(Scanner sc, Admin admin) {
        showReqListOption();
        int choice = getChoice(sc, 4);
        switch (choice) {
            case 1 -> {
                acceptReq(sc, admin);
                reqListOption(sc, admin);
            }
            case 2 -> {
                deniedReq(sc, admin);
                reqListOption(sc, admin);
            }
            case 3 -> {
                showReqList(sc, admin, getReq());
                reqListOption(sc, admin);
            }
            default -> adminMenu(sc, admin);
        }
    }

    public void acceptReq(Scanner sc, Admin admin) {
        showReqList(sc, admin, getReq());
        System.out.println("Which product do you want to accept?");
        int choice = getChoice(sc, getReq().size() + 1);
        if (choice == 0) {
            reqListOption(sc, admin);
            return;
        }
        Product product = getReq().get(--choice);
        product.setIsVisible(true);
        Main.getRunManage().getCustomerManage().getProducts().add(product);
        getReq().remove(product);
    }

    public void deniedReq(Scanner sc, Admin admin) {
        showReqList(sc, admin, getReq());
        System.out.println("Which product do you want to delete?");
        int choice = getChoice(sc, getReq().size() + 1);
        if (choice == 0) {
            reqListOption(sc, admin);
            return;
        }
        Product deleteProduct = getReq().get(--choice);
        Seller seller = getReq().get(choice).getSeller();
        seller.getProducts().remove(deleteProduct);
        getReq().remove(deleteProduct);
    }

    public void showReqList(Scanner sc, Admin admin, ArrayList<Product> req) {
        if (req.isEmpty()) {
            System.out.println("Requests box is empty");
            adminMenu(sc, admin);
            return;
        }
        System.out.println("===============================================   Requests:  ==================================================");
        for (Product product : req) {
            System.out.println(req.indexOf(product) + 1 + ". " + product);
        }
        System.out.println("0. Back");
        System.out.println("==============================================================================================================");
    }

    public int getChoice(Scanner scan, int bound) {
        System.out.print("Enter your choice: ");
        int choice = scan.nextInt();
        if (choice >= 0 && choice < bound) {
            return choice;
        } else {
            System.out.println("Invalid input!");
            return getChoice(scan, bound);
        }
    }

    public int showUsersList(UsersRole role) {
        int i = 1;
        for (User user : Main.getRunManage().getUsers()) {
            if (user.getRole().equals(role)) {
                System.out.println(i + ") " + user);
                i++;
            }
        }
        return i;
    }

    public void showProfileOption() {
        System.out.println("==============================================================================================================");
        System.out.println("1. your information");
        System.out.println("2. Edit information");
        System.out.println("3. Wallet");
        System.out.println("0. Back");
        System.out.println("==============================================================================================================");
    }

    public void showAdminMenu() {
        System.out.println("==============================================================================================================");
        System.out.println("1. Profile");
        System.out.println("2. Customers");
        System.out.println("3. Sellers");
        System.out.println("4. Deliveries");
        System.out.println("5. All ads");
        System.out.println("6. Requests");
        System.out.println("7. Deliver product");
        System.out.println("0. Exit");
        System.out.println("==============================================================================================================");
    }

    public void showReqListOption() {
        System.out.println("==============================================================================================================");
        System.out.println("1. Accept request");
        System.out.println("2. Denied request");
        System.out.println("3. Show request list");
        System.out.println("0. Back");
        System.out.println("==============================================================================================================");
    }

    public void showWalletOption() {
        System.out.println("==============================================================================================================");
        System.out.println("1. Check wallet");
        System.out.println("2. Withdraw money");
        System.out.println("0. Back");
        System.out.println("==============================================================================================================");
    }

    public ArrayList<Product> getReq() {
        return req;
    }

    public ArrayList<Product> getDeliveryReq() {
        return deliveryReq;
    }
}
