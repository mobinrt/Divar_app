package ir.ac.kntu.manage.user;

import ir.ac.kntu.Main;
import ir.ac.kntu.manage.Input;
import ir.ac.kntu.manage.ShowMenu;
import ir.ac.kntu.util.*;
import ir.ac.kntu.util.enums.AdsCategory;
import ir.ac.kntu.util.enums.BuyOptions;
import ir.ac.kntu.util.enums.SavedBoxOptions;
import ir.ac.kntu.util.users.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class CustomerManage implements UsersCommonMethods, Input {
    private ArrayList<Product> products;
    private final CustomerStaticMethod staticMethod;

    public CustomerManage() {
        products = new ArrayList<>();
        staticMethod = new CustomerStaticMethod();
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
        int i = foundProductBySeller(name);
        int choice = getChoice(sc, i + 1);
        if (choice == 0)
            return;
        Product product = findProduct(choice, "", name);
        handleUserAction(sc, customer, product);
    }

    private int foundProductBySeller(String name) {
        AtomicInteger i = new AtomicInteger(1);
        products.stream()
                .filter(product -> product.getSeller().toString().contains(name))
                .peek(product -> System.out.println(i.getAndIncrement() + ") " + product))
                .count();
        System.out.print("(Enter 0 for Back)");
        return i.get();
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
                soldAdAction(sc, customer, product);
                deliverProduct(sc, customer, product);
                menu(sc, customer);
            }
            case 2 -> {
                ArrayList<Product> temp = customer.getSavedBox();
                temp.remove(product);
                customer.setSavedBox(temp);
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
        ShowMenu.showMenuEnum(BuyOptions.values());
        int type = getChoice(sc, 5);
        switch (type) {
            case 1 -> staticMethod.addToSavedBox(customer, product);
            case 2 -> {
                soldAdAction(sc, customer, product);
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
        return products.stream()
                .filter(product -> (category.isEmpty() && product.getSeller().toString().contains(name))
                        || (category.matches(product.getAdsCategory()) && name.isEmpty()))
                .skip(choice - 1)
                .findFirst()
                .orElse(null);
    }

    private void soldAdAction(Scanner sc, Customer customer, Product product) {
        ArrayList<Product> customerHistory = customer.getHistory();
        ArrayList<Product> customerSavedBox = customer.getSavedBox();
        ArrayList<Product> sellerHistory = product.getSeller().getHistory();
        ArrayList<Product> sellerProducts = product.getSeller().getProducts();

        productPayment(sc, customer, product);
        customerHistory.add(product);
        customer.setHistory(customerHistory);
        sellerProducts.remove(product);
        product.getSeller().setProducts(sellerProducts);
        sellerHistory.add(product);
        product.getSeller().setHistory(sellerHistory);
        customerSavedBox.remove(product);
        customer.setSavedBox(customerSavedBox);
        staticMethod.deleteProductFromSavedBox(product);
        product.setCustomer(customer);
        products.remove(product);
        System.out.println("Successfully done.");
        System.out.println("===========================================================================================================");
    }

    private void productPayment(Scanner sc, Customer customer, Product product) {
        outOfBudget(sc, customer, product.getPrice());
        customer.setWallet(customer.getWallet() - product.getPrice());
        product.getSeller().setWallet((product.getPrice() * 9) / 10);
        MainAdmin mainAdmin = staticMethod.findMainAdmin();
        assert mainAdmin != null;
        mainAdmin.setWallet(product.getPrice() / 10);
    }

    private void outOfBudget(Scanner sc, Customer customer, double need) {
        while (need > customer.getWallet()) {
            System.out.println("Charge your account. you do not have enough money.");
            staticMethod.chargeWallet(sc, customer);
        }
    }

    private void deliverProduct(Scanner sc, Customer customer, Product product) {
        if (product.getAdsCategory().matches(AdsCategory.CAR.toString()))
            return;
        staticMethod.wishToDeliver();
        int choice = getChoice(sc, 3);
        if (choice == 2) {
            product.setSold(true);
            return;
        }
        staticMethod.setLocation(sc, customer);
        AdsCategory adsCategory = AdsCategory.valueOf(product.getAdsCategory());
        deliverPay(sc, customer, product, adsCategory);
    }


    private void deliverPay(Scanner sc, Customer customer, Product product, AdsCategory adsCategory) {
        ArrayList<Product> temp = Main.getRunManage().getMainAdminManage().getDeliveryReq();
        int charge = (int) customer.calculateDistance(customer, product.getSeller()) * adsCategory.getBaseCharge();
        staticMethod.makeSureToDeliver(charge, adsCategory);
        int i = getChoice(sc, 2);
        if (i == 2) {
            product.setSold(true);
            return;
        }
        outOfBudget(sc, customer, charge);
        MainAdmin mainAdmin = staticMethod.findMainAdmin();
        assert mainAdmin != null;
        mainAdmin.setDeliveryMoney(charge);
        customer.setWallet(customer.getWallet() - charge);
        temp.add(product);
        Main.getRunManage().getAdminManage().setDeliveryReq(temp);
        Main.getRunManage().getMainAdminManage().setDeliveryReq(temp);
        System.out.println("==============================================================================================================");
        checkActiveDelivery(product);
    }

    private void checkActiveDelivery(Product product) {
        boolean isAvailableDelivery = Main.getRunManage().getDeliveryManage().isAvailableDelivery(product);
        if (isAvailableDelivery) {
            product.setReadyToSend(true);
            return;
        }
        product.setWaitingToSend(true);
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
                staticMethod.chargeWallet(sc, customer);
                walletMenu(sc, customer);
            }
            default -> profile(sc, customer);
        }
    }

    private int[] handlePriceFilter(Scanner sc, Customer customer) {
        staticMethod.priceFilterOption();
        int[] filter = new int[2];
        filter[0] = -1;
        filter[1] = Integer.MAX_VALUE;
        int choice = getChoice(sc, 3);
        switch (choice) {
            case 1 -> {
                staticMethod.inputPriceFilter(sc, filter);
                return filter;
            }
            case 2 -> {
                return filter;
            }
            default -> menu(sc, customer);
        }
        return filter;
    }

    public void showAdsList() {
        if (products.isEmpty()) {
            System.out.println("Product box is empty");
            return;
        }
        System.out.println("===============================================   Ads list:  =================================================");
        IntStream.range(0, products.size())
                .mapToObj(i -> (i + 1) + ") " + products.get(i))
                .forEach(System.out::println);
        System.out.println("==============================================================================================================");
        System.out.println("press zero for back");
    }

    private int showAdsListByCategory(Scanner sc, String adsCategory, Customer customer) {
        if (products.isEmpty()) {
            System.out.println("Product box is empty");
            menu(sc, customer);
            return 0;
        }
        int[] filter = handlePriceFilter(sc, customer);
        AtomicInteger i = new AtomicInteger(1);
        System.out.println("===============================================   Ads list:  =================================================");
        products.stream()
                .filter(product -> adsCategory.matches(product.getAdsCategory()))
                .filter(product -> product.getPrice() >= filter[0] && product.getPrice() <= filter[1])
                .forEach(product -> System.out.println(i.getAndIncrement() + ") " + product));
        System.out.println("0) Back");
        System.out.println("==============================================================================================================");
        return i.get();
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

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public CustomerStaticMethod getStaticMethod() {
        return staticMethod;
    }
}
