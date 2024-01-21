package ir.ac.kntu.manage.user;

import ir.ac.kntu.Main;
import ir.ac.kntu.manage.Choice;
import ir.ac.kntu.manage.ShowMenu;
import ir.ac.kntu.util.*;
import ir.ac.kntu.util.enums.AdsCategory;
import ir.ac.kntu.util.enums.SavedBoxOptions;
import ir.ac.kntu.util.users.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class CustomerManage implements UserSimilar, Choice {
    private final ArrayList<Product> products;

    public CustomerManage() {
        products = new ArrayList<>();
    }

    @Override
    public void menu(Scanner sc, User user) {
        Customer customer = (Customer) user;
        ShowMenu.showMenu("Profile, Sales ads, Saved box, History, Chats, Search");
        int choice = getChoice(sc, 7);
        switch (choice) {
            case 1 -> {
                profile(sc, customer);
                menu(sc, customer);
            }
            case 2 -> {
                sortByPrice(sc, customer);
                Product product = handleAdSelection(sc, customer);
                handleUserAction(sc, customer, product);
                menu(sc, customer);
            }
            case 3 -> {
                savedBoxOption(sc, customer);
                menu(sc, customer);
            }
            case 4 -> {
                customer.showHistory();
                menu(sc, customer);
            }
            case 5 -> {
                Main.getRunManage().getChatRoomManage().chatBox(sc, customer);
                menu(sc, customer);
            }
            case 6 -> {
                searchProductBySeller(sc, customer);
                menu(sc, customer);
            }
            default -> Main.getRunManage().run();
        }
    }

    private void searchProductBySeller(Scanner sc, Customer customer) {
        sc.nextLine();
        System.out.print("Enter seller name: ");
        String name = sc.nextLine();
        int i = 1;
        for (Product product : products) {
            if (product.getSeller().toString().contains(name)) {
                System.out.println(i + ") " + product);
                i++;
            }
        }
        System.out.print("Enter 0 for Back ");
        int choice = getChoice(sc, i + 1);
        if (choice == 0)
            return;
        Product product = findProduct(choice, "", name);
        handleUserAction(sc, customer, product);
    }

    private void savedBoxOption(Scanner sc, Customer customer) {
        customer.showSavedBox();
        if (customer.getSavedBox().isEmpty())
            return;
        int choice = getChoice(sc, customer.getSavedBox().size() + 1);
        if (choice == 0) {
            menu(sc, customer);
            return;
        }
        Product product = customer.getSavedBox().get(--choice);
        ShowMenu.showMenuEnum(SavedBoxOptions.values());
        int type = getChoice(sc, 5);
        switch (type) {
            case 1 -> {
                buyAd(sc, customer, product);
                deliverProduct(sc, customer, product);
                menu(sc, customer);
            }
            case 2 -> {
                customer.getSavedBox().remove(choice);
                menu(sc, customer);
            }
            case 3 -> {
                Main.getRunManage().getChatRoomManage().checkExistenceChat(sc, customer, product.getSeller());
                menu(sc, customer);
            }
            case 5 -> {
                Main.getRunManage().getFeedbackManage().handleFeedback(sc, customer, product);
                menu(sc, customer);
            }
            default -> menu(sc, customer);
        }
    }

    private void sortByPrice(Scanner sc, Customer customer) {
        ShowMenu.showMenu("Ascending sort by product price, Descending Acceding sort by product price");
        int choice = getChoice(sc, 3);
        switch (choice) {
            case 1 -> products.sort(Comparator.comparing(Product::getPrice));
            case 2 -> products.sort(Comparator.comparing(Product::getPrice).reversed());
            default -> menu(sc, customer);
        }
    }

    private Product handleAdSelection(Scanner sc, Customer customer) {
        int choice;
        Product product;
        String category = equipAdsCategory(sc, customer);
        if (category.isEmpty()) {
            showAdsList();
            choice = getChoice(sc, products.size() + 1);
            if (choice == 0) {
                menu(sc, customer);
                return null;
            }
            return products.get(--choice);
        } else {
            int length = showAdsListByCategory(sc, category, customer);
            choice = getChoice(sc, length + 1);
            if (choice == 0) {
                menu(sc, customer);
                return null;
            }
            product = findProduct(choice, category, "");
        }
        if (product == null) {
            menu(sc, customer);
            return null;
        }
        return product;
    }

    private void handleUserAction(Scanner sc, Customer customer, Product product) {
        ShowMenu.showMenuEnum(SavedBoxOptions.values());
        int type = getChoice(sc, 5);
        switch (type) {
            case 1 -> addToSavedBox(customer, product);
            case 2 -> {
                buyAd(sc, customer, product);
                deliverProduct(sc, customer, product);
            }
            case 3 -> {
                Seller receiver = product.getSeller();
                Main.getRunManage().getChatRoomManage().checkExistenceChat(sc, customer, receiver);
            }
            case 4 -> Main.getRunManage().getFeedbackManage().handleFeedback(sc, customer, product);
            default -> menu(sc, customer);
        }
        menu(sc, customer);
    }

    private Product findProduct(int choice, String category, String name) {
        ArrayList<Product> temp = new ArrayList<>();
        for (Product product : products) {
            if (category.matches(product.getAdsCategory()) && name.isEmpty()) {
                temp.add(product);
            }
            if (category.isEmpty() && product.getSeller().toString().contains(name)) {
                temp.add(product);
            }
        }
        return temp.get(--choice);
    }

    private void addToSavedBox(Customer customer, Product product) {
        customer.addToSavedBox(product);
        System.out.println("Successfully done.");
        System.out.println("===========================================================================================================");
    }

    private void buyAd(Scanner sc, Customer customer, Product product) {
        outOfBudget(sc, customer, product.getPrice());
        customer.setWallet(customer.getWallet() - product.getPrice());
        product.getSeller().setWallet((product.getPrice() * 9) / 10);
        MainAdmin mainAdmin = findMainAdmin();
        assert mainAdmin != null;
        mainAdmin.setWallet(product.getPrice() / 10);
        customer.getHistory().add(product);
        product.getSeller().getProducts().remove(product);
        product.getSeller().getHistory().add(product);
        customer.getSavedBox().remove(product);
        deleteProductFromSavedBox(product);
        product.setCustomer(customer);
        products.remove(product);
        System.out.println("Successfully done.");
        System.out.println("===========================================================================================================");
    }

    private void outOfBudget(Scanner sc, Customer customer, double need) {
        while (need > customer.getWallet()) {
            System.out.println("Charge your account. you do not have enough money.");
            chargeWallet(sc, customer);
        }
    }

    private void deliverProduct(Scanner sc, Customer customer, Product product) {
        if (product.getAdsCategory().matches(AdsCategory.CAR.toString()))
            return;
        wishToDeliver();
        int choice = getChoice(sc, 3);
        if (choice == 2) {
            product.setSold(true);
            return;
        }
        setLocation(sc, customer);
        AdsCategory adsCategory = AdsCategory.valueOf(product.getAdsCategory());
        deliverPay(sc, customer, product, adsCategory);
    }

    private void setLocation(Scanner sc, Customer customer) {
        if (customer.getX() < 0 || customer.getY() < 0) {
            System.out.println("you don't set your location.");
            customer.setLocation(sc, customer);
        }
    }

    private void deliverPay(Scanner sc, Customer customer, Product product, AdsCategory adsCategory) {
        int charge = (int) customer.calculateDistance(customer, product.getSeller());
        makeSureToDeliver(charge, adsCategory);
        int temp = getChoice(sc, 2);
        if (temp == 2) {
            product.setSold(true);
            return;
        }
        outOfBudget(sc, customer, (charge * adsCategory.getBaseCharge()));
        MainAdmin mainAdmin = findMainAdmin();
        assert mainAdmin != null;
        mainAdmin.setWallet(charge * adsCategory.getBaseCharge());
        customer.setWallet(customer.getWallet() - (charge * adsCategory.getBaseCharge()));
        Main.getRunManage().getAdminManage().getDeliveryReq().add(product);
        Main.getRunManage().getMainAdminManage().getDeliveryReq().add(product);
        System.out.println("==============================================================================================================");
        boolean isAvailableDelivery = Main.getRunManage().getDeliveryManage().isAvailableDelivery(product);
        if (isAvailableDelivery) {
            product.setReadyToSend(true);
            return;
        }
        product.setWaitingToSend(true);
    }

    private void makeSureToDeliver(int charge, AdsCategory adsCategory) {
        System.out.println("It costs " + (charge * adsCategory.getBaseCharge()) + ". Are you sure?");
        System.out.println("1. Yes");
        System.out.println("2. No");
    }

    private MainAdmin findMainAdmin() {
        for (User user : Main.getRunManage().getUsers()) {
            if (user.isMainAdmin()) {
                return (MainAdmin) user;
            }
        }
        return null;
    }

    public void deleteProductFromSavedBox(Product deleteProduct) {
        for (User user : Main.getRunManage().getUsers()) {
            if (user instanceof Customer customer)
                customer.getSavedBox().removeIf(product -> product.equals(deleteProduct));
        }
    }

    @Override
    public void profile(Scanner sc, User user) {
        Customer customer = (Customer) user;
        ShowMenu.showMenu("Your information, Edit information, Wallet");
        int choice = getChoice(sc, 4);
        switch (choice) {
            case 1 -> {
                System.out.println(customer.toString());
                profile(sc, customer);
            }
            case 2 -> {
                customer.editUserInfo(sc, customer);
                profile(sc, customer);
            }
            case 3 -> {
                walletMenu(sc, customer);
                profile(sc, customer);
            }
            default -> menu(sc, customer);
        }
    }

    @Override
    public void walletMenu(Scanner sc, User user) {
        Customer customer = (Customer) user;
        ShowMenu.showMenu("Check wallet, Charge wallet");
        int choice = getChoice(sc, 3);
        switch (choice) {
            case 1 -> {
                System.out.println("wallet: " + customer.getWallet());
                walletMenu(sc, customer);
            }
            case 2 -> {
                chargeWallet(sc, customer);
                walletMenu(sc, customer);
            }
            default -> profile(sc, customer);
        }
    }

    private void chargeWallet(Scanner sc, Customer customer) {
        System.out.println("How much do you want to charge your wallet?");
        System.out.print("Enter: ");
        double charge = sc.nextDouble();
        charge += customer.getWallet();
        customer.setWallet(charge);
        System.out.println("Successfully done.");
    }

    private int[] handlePriceFilter(Scanner sc, Customer customer) {
        priceFilterOption();
        int[] filter = new int[2];
        filter[0] = -1;
        filter[1] = Integer.MAX_VALUE;
        int choice = getChoice(sc, 3);
        switch (choice) {
            case 1 -> {
                inputPriceFilter(sc, filter);
                return filter;
            }
            case 2 -> {
                return filter;
            }
            default -> menu(sc, customer);
        }
        return filter;
    }

    private void inputPriceFilter(Scanner sc, int[] filter) {
        System.out.print("Enter min price range: ");
        int temp = sc.nextInt();
        filter[0] = temp;
        System.out.print("Enter max price range: ");
        temp = sc.nextInt();
        filter[1] = temp;
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
        System.out.println("press zero if zero for back");
    }

    private int showAdsListByCategory(Scanner sc, String adsCategory, Customer customer) {
        if (products.isEmpty()) {
            System.out.println("Product box is empty");
            menu(sc, customer);
            return 0;
        }
        int[] filter = handlePriceFilter(sc, customer);
        int i = 1;
        System.out.println("===============================================   Ads list:  =================================================");
        for (Product product : products) {
            if (adsCategory.matches(product.getAdsCategory())) {
                if (product.getPrice() >= filter[0] && product.getPrice() <= filter[1]) {
                    System.out.println(i + ") " + product);
                    ++i;
                }
            }
        }
        System.out.println("0) Back");
        System.out.println("==============================================================================================================");
        return i;
    }

    private String equipAdsCategory(Scanner sc, Customer customer) {
        String adsCategory = "";
        ShowMenu.showMenu("Phone, Home stuff, Stationary, Clothe, Car, All category");
        int choice = getChoice(sc, 7);
        switch (choice) {
            case 1 -> adsCategory = AdsCategory.PHONE.name();
            case 2 -> adsCategory = AdsCategory.HOME_STUFF.name();
            case 3 -> adsCategory = AdsCategory.STATIONARY.name();
            case 4 -> adsCategory = AdsCategory.CLOTHE.name();
            case 5 -> adsCategory = AdsCategory.CAR.name();
            case 6 -> {
            }
            default -> menu(sc, customer);
        }
        return adsCategory;
    }

    private void priceFilterOption() {
        System.out.println("Do you want to filter product with price?");
        ShowMenu.showMenu("Yes, No");
    }

    private void wishToDeliver() {
        System.out.println("Do you want to deliver product?");
        System.out.println("1. Yes");
        System.out.println("2. No");
    }

    public ArrayList<Product> getProducts() {
        return products;
    }
}
