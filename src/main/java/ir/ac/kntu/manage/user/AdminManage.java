package ir.ac.kntu.manage.user;

import ir.ac.kntu.Main;
import ir.ac.kntu.manage.Input;
import ir.ac.kntu.manage.ShowMenu;
import ir.ac.kntu.util.*;
import ir.ac.kntu.util.enums.*;
import ir.ac.kntu.util.users.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class AdminManage implements UsersCommonMethods, Input {
    private ArrayList<Product> req;
    private ArrayList<Product> deliveryReq;

    public AdminManage() {
        req = new ArrayList<>();
        deliveryReq = new ArrayList<>();
    }

    /**
     * @param sc   - scan Input
     * @param user - current admin
     */
    @Override
    public void menu(Scanner sc, User user) {
        Admin admin = (Admin) user;
        ShowMenu.showMenu("Profile, Customers, Sellers, Deliveries, All ads, Requests, Deliver product");
        int choice = getChoice(sc, 8);
        switch (choice) {
            case 1 -> {
                profile(sc, admin);
                menu(sc, admin);
            }
            case 2 -> {
                generalEdit(sc, admin, UsersRole.CUSTOMER);
                menu(sc, admin);
            }
            case 3 -> {
                sellerEdit(sc, admin);
                menu(sc, admin);
            }
            case 4 -> {
                generalEdit(sc, admin, UsersRole.DELIVERY);
                menu(sc, admin);
            }
            case 5 -> {
                adsEdit(sc, admin);
                menu(sc, admin);
            }
            case 6 -> {
                reqListOption(sc, admin);
                menu(sc, admin);
            }
            case 7 -> {
                deliverProduct(sc, admin);
                menu(sc, admin);
            }
            default -> Main.getRunManage().run();
        }
    }

    public void addProductToReq(Product product) {
        req.add(product);
    }

    @Override
    public void profile(Scanner sc, User user) {
        Admin admin = (Admin) user;
        ShowMenu.showMenu("Your information, Edit information, Wallet");
        int choice = getChoice(sc, 4);
        switch (choice) {
            case 1 -> {
                System.out.println(admin.toString());
                profile(sc, admin);
            }
            case 2 -> {
                admin.editUserInfo(sc, admin);
                profile(sc, admin);
            }
            case 3 -> {
                walletMenu(sc, admin);
                profile(sc, admin);
            }
            default -> menu(sc, admin);
        }
    }

    @Override
    public void walletMenu(Scanner sc, User user) {
        Admin admin = (Admin) user;
        ShowMenu.showMenu("Check wallet, Withdraw money");
        int choice = getChoice(sc, 3);
        switch (choice) {
            case 1 -> {
                System.out.println("Your wallet: " + admin.getWallet());
                walletMenu(sc, admin);
            }
            case 2 -> {
                withdrawMoney(sc, admin);
                walletMenu(sc, admin);
            }
            default -> profile(sc, admin);
        }
    }

    public User generalEdit(Scanner sc, Admin admin, UsersRole userRole) {
        ArrayList<User> temp = Main.getRunManage().getUsers();
        int length = showUsersList(userRole);
        System.out.println("Select one of the customers to remove or press zero to back");
        int choice = getChoice(sc, length);
        if (choice == 0) {
            menu(sc, admin);
            return null;
        }
        User user = findUser(choice, userRole);
        temp.remove(user);
        Main.getRunManage().setUsers(temp);
        System.out.println("Successfully done.");
        System.out.println("==============================================================================================================");
        return user;
    }

    public void sellerEdit(Scanner sc, Admin admin) {
        Seller deletedSeller = (Seller) generalEdit(sc, admin, UsersRole.SELLER);
        deletedSeller.setProducts(null);
        removeSellerAds(deletedSeller, Main.getRunManage().getCustomerManage().getProducts());
        removeSellerAds(deletedSeller, req);
        Main.getRunManage().getUsers().stream()
                .filter(user -> user instanceof Customer)
                .map(user -> (Customer) user)
                .forEach(customer -> removeSellerAds(deletedSeller, customer.getSavedBox()));
        System.out.println("Successfully done.");
        System.out.println("==============================================================================================================");
    }

    public User findUser(int choice, UsersRole role) {
        return Main.getRunManage().getUsers().stream()
                .filter(user -> user.getRole().equals(role))
                .skip(choice - 1)
                .findFirst()
                .orElse(null);
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
        ArrayList<Product> deliveryHistory = delivery.getHistory();
        int distance1 = (int) delivery.calculateDistance(delivery, product.getSeller());
        int distance2 = (int) delivery.calculateDistance(product.getSeller(), product.getCustomer());
        distance1 += distance2;
        makeDeliveryUnavailable(distance1, product, delivery);
        deliveryPayment(delivery, product, distance1, distance2);
        deliveryHistory.add(product);
        delivery.setHistory(deliveryHistory);
        delivery.setX(product.getCustomer().getX());
        delivery.setY(product.getCustomer().getY());
        delivery.setLocation();
    }

    private void deliveryPayment(Delivery delivery, Product product, int finalDistance, int distanceForPay) {
        ExecutorService customExecutor = Executors.newCachedThreadPool();
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(finalDistance * 5000L);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }, customExecutor);

        future.thenRun(() -> {
            MainAdmin mainAdmin = Main.getRunManage().getCustomerManage().getStaticMethod().findMainAdmin();
            AdsCategory adsCategory = AdsCategory.valueOf(product.getAdsCategory());
            int charge = adsCategory.getBaseCharge() * distanceForPay;
            mainAdmin.setDeliveryMoney(mainAdmin.getDeliveryMoney() - charge);
            delivery.setWallet(charge);
        });

        customExecutor.shutdown();
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

    public void makeDeliveryUnavailable(int distanceInKm, Product product, Delivery delivery) {
        product.setWaitingToSend(false);
        product.setReadyToSend(false);
        product.setSold(false);
        deliveryReq.remove(product);
        delivery.setAvailable(false);
        product.setSending(true);
        System.out.println(delivery);

        ExecutorService makeDeliveryUnavailable = Executors.newCachedThreadPool();
        makeDeliveryUnavailable.submit(() -> {
            try {
                Thread.sleep(distanceInKm * 4000L);
                delivery.setAvailable(true);
                product.setSending(false);
                product.setSold(true);
            } catch (InterruptedException e) {
                System.out.println("Sorry! We got unwanted error. Please try again.");
            }
        });
        makeDeliveryUnavailable.shutdown();
    }

    public void removeSellerAds(Seller removeSeller, ArrayList<Product> ads) {
        ads.removeIf(product -> removeSeller.equals(product.getSeller()));
    }

    public void adsEdit(Scanner sc, Admin admin) {
        ArrayList<Product> customersList = Main.getRunManage().getCustomerManage().getProducts();
        Main.getRunManage().getCustomerManage().showAdsList();
        int choice = getChoice(sc, Main.getRunManage().getCustomerManage().getProducts().size() + 1);
        if (choice == 0) {
            menu(sc, admin);
            return;
        }
        Product product = customersList.remove(--choice);
        Main.getRunManage().getCustomerManage().setProducts(customersList);
        ArrayList<Product> sellerList = product.getSeller().getProducts();
        Main.getRunManage().getCustomerManage().getStaticMethod().deleteProductFromSavedBox(product);
        sellerList.remove(product);
        product.getSeller().setProducts(sellerList);
        System.out.println("Successfully done.");
        System.out.println("==============================================================================================================");
    }

    public void reqListOption(Scanner sc, Admin admin) {
        ShowMenu.showMenu("Accept request, Denied request, Show request list");
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
            default -> menu(sc, admin);
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
            menu(sc, admin);
            return;
        }
        System.out.println("===============================================   Requests:  ==================================================");
        IntStream.range(0, req.size())
                .mapToObj(i -> (i + 1) + ". " + req.get(i))
                .forEach(System.out::println);
        System.out.println("==============================================================================================================");
    }

    public int showUsersList(UsersRole role) {
        AtomicInteger i = new AtomicInteger(1);
        Main.getRunManage().getUsers().stream()
                .filter(user -> user.getRole().equals(role))
                .forEach(user -> System.out.println(i.getAndIncrement() + ") " + user));
        return i.get();
    }

    public ArrayList<Product> getReq() {
        return req;
    }

    public void setReq(ArrayList<Product> req) {
        this.req = req;
    }

    public ArrayList<Product> getDeliveryReq() {
        return deliveryReq;
    }

    public void setDeliveryReq(ArrayList<Product> deliveryReq) {
        this.deliveryReq = deliveryReq;
    }
}
//    public void makeDeliveryUnavailable(int distanceInKm, Product product, Delivery) {
//        product.setWaitingToSend(false);
//        product.setReadyToSend(false);
//        product.setSold(false);
//        deliveryReq.remove(product);
//        delivery.setAvailable(false);
//        product.setSending(true);
//        System.out.println(delivery);
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                delivery.setAvailable(true);
//                product.setSending(false);
//                product.setSold(true);
//                timer.cancel();
//            }
//        }, distanceInKm * 5000L);
//
//    }
