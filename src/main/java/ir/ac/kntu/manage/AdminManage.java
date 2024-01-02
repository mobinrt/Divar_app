package ir.ac.kntu.manage;

import ir.ac.kntu.Main;
import ir.ac.kntu.util.*;

import java.util.ArrayList;
import java.util.Scanner;

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
                generalEdit(sc, admin, UsersRole.CUSTOMER);
                adminMenu(sc, admin);
            }
            case 2 -> {
                sellerEdit(sc, admin);
                adminMenu(sc, admin);
            }
            case 3 -> {
                generalEdit(sc, admin, UsersRole.DELIVERY);
                adminMenu(sc, admin);
            }
            case 4 -> {
                adsEdit(sc, admin);
                adminMenu(sc, admin);
            }
            case 5 -> {
                reqListOption(sc, admin);
                adminMenu(sc, admin);
            }
            case 6 -> {
                deliverProduct(sc, admin);
                adminMenu(sc, admin);
            }
            default -> Main.getRunManage().run();
        }
    }

    public void generalEdit(Scanner sc, Admin admin, UsersRole usersRole) {
        int length = Main.getRunManage().showUsersList(usersRole);
        System.out.println("Select one of the customers to remove or press zero to back");
        int choice = getChoice(sc, length);
        if (choice == 0) {
            adminMenu(sc, admin);
            return;
        }
        Main.getRunManage().getUsers().remove(--choice);
        System.out.println("Successfully done.");
        System.out.println("==============================================================================================================");
    }

    public void sellerEdit(Scanner sc, Admin admin) {
        int length = Main.getRunManage().showUsersList(UsersRole.SELLER);
        System.out.println("Select one of the sellers to remove or press zero to back");
        int choice = getChoice(sc, length);
        if (choice == 0) {
            adminMenu(sc, admin);
            return;
        }
        User removeUser = Main.getRunManage().getUsers().remove(--choice);
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

    private void deliverProduct(Scanner sc, Admin admin) {
        showReqList(sc, admin, deliveryReq);
        System.out.println("Which product do you want to deliver?");
        int choice = getChoice(sc, deliveryReq.size() + 1);
        if (choice == 0) {
            adminMenu(sc, admin);
            return;
        }
        Product product = req.get(--choice);
        boolean isAvailable = Main.getRunManage().getDeliveryManage().isAvailableDelivery(product);
        if (!isAvailable) {
            System.out.println("No available delivery!");
            return;
        }
        Delivery delivery = findClosestDelivery(product);
        int distance = (int) admin.calculateDistance(delivery, product.getSeller());
        Main.getRunManage().getDeliveryManage().unavailability(delivery, distance, product);
    }

    private Delivery findClosestDelivery(Product product) {
        Delivery finalDelivery = null;
        double distance;
        double finalDistance = Integer.MAX_VALUE;
        for (User user : Main.getRunManage().getUsers()) {
            if (!(user instanceof Delivery delivery))
                continue;
            if (!(delivery.isAvailable()))
                continue;
            if (product.getAdsCategory().matches(AdsCategory.HOME_STUFF.toString())) {
                if (delivery.getVehicleType().equals(VehicleType.MOTOR))
                    continue;
            }
            distance = user.calculateDistance(delivery, product.getSeller());
            if (distance < finalDistance) {
                finalDistance = distance;
                finalDelivery = delivery;
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
                showReqList(sc, admin, req);
                reqListOption(sc, admin);
            }
            default -> adminMenu(sc, admin);
        }
    }

    public void acceptReq(Scanner sc, Admin admin) {
        showReqList(sc, admin, req);
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

    public void deniedReq(Scanner sc, Admin admin) {
        showReqList(sc, admin, req);
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

    public void showAdminMenu() {
        System.out.println("==============================================================================================================");
        System.out.println("1. Customers");
        System.out.println("2. Sellers");
        System.out.println("3. Deliveries");
        System.out.println("4. All ads");
        System.out.println("5. Requests");
        System.out.println("6. Product deliver");
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

    public ArrayList<Product> getDeliveryReq() {
        return deliveryReq;
    }
}
