package ir.ac.kntu.manage.user;

import ir.ac.kntu.Main;
import ir.ac.kntu.util.*;
import ir.ac.kntu.util.enums.AdsCategory;
import ir.ac.kntu.util.users.Seller;
import ir.ac.kntu.util.users.User;

import java.util.Scanner;

public class SellerManage implements Menu {
    /**
     * @param sc   - scan input
     * @param user - online seller
     */
    @Override
    public void menu(Scanner sc, User user) {
        Seller seller = (Seller) user;
        showSellerMenu();
        int choice = getChoice(sc, 7);
        switch (choice) {
            case 1 -> {
                profile(sc, seller);
                menu(sc, seller);
            }
            case 2 -> {
                seller.showAvailableAds();
                menu(sc, seller);
            }
            case 3 -> {
                addAd(sc, seller);
                menu(sc, seller);
            }
            case 4 -> {
                seller.showHistory();
                menu(sc, seller);
            }
            case 5 -> {
                Main.getRunManage().getChatRoomManage().chatBox(sc, seller);
                menu(sc, seller);
            }
            case 6 -> {
                showFeedbacks(seller);
                menu(sc, seller);
            }
            default -> Main.getRunManage().run();
        }
    }

    public void showFeedbacks(Seller seller) {
        if (seller.getFeedback().isEmpty()) {
            System.out.println("There is no comment.");
            return;
        }
        for (Feedback feedback : seller.getFeedback()) {
            System.out.println(feedback);
        }
    }

    /**
     * @param sc            - scan input
     * @param currentSeller - online seller
     */
    public void addAd(Scanner sc, Seller currentSeller) {
        if (currentSeller.getX() < 0 || currentSeller.getY() < 0) {
            System.out.println("you don't set your location.");
            currentSeller.setLocation(sc, currentSeller);
        }
        String adsCategory = showAdsCategory(sc, currentSeller);
        sc.nextLine();
        System.out.print("Enter product's name: ");
        String name = sc.next();
        System.out.print("Enter product's price: ");
        double price = sc.nextDouble();
        Product product = currentSeller.addToRequestList(adsCategory, name, currentSeller, price);
        System.out.println("Successfully done.");
        System.out.println("Waiting for admin accept...");
        System.out.println("==============================================================================================================");
        Main.getRunManage().getAdminManage().addProductToReq(product);
        Main.getRunManage().getMainAdminManage().addProductToReq(product);
    }

    @Override
    public void profile(Scanner sc, User user) {
        Seller seller = (Seller) user;
        showProfileOption();
        int choice = getChoice(sc, 4);
        switch (choice) {
            case 1 -> {
                System.out.println(seller.toString());
                profile(sc, seller);
            }
            case 2 -> {
                seller.editUserInfo(sc, seller);
                profile(sc, seller);
            }
            case 3 -> {
                walletMenu(sc, seller);
                profile(sc, seller);
            }
            default -> menu(sc, seller);
        }
    }

    @Override
    public void walletMenu(Scanner sc, User user) {
        Seller seller = (Seller) user;
        showWalletOption();
        int choice = getChoice(sc, 3);
        switch (choice) {
            case 1 -> {
                System.out.println("Your wallet: " + seller.getWallet());
                walletMenu(sc, seller);
            }
            case 2 -> {
                withdrawMoney(sc, seller);
                walletMenu(sc, seller);
            }
            default -> profile(sc, seller);
        }
    }

    private String showAdsCategory(Scanner sc, Seller seller) {
        String adsCategory = null;
        showAdsCategory();
        int choice = getChoice(sc, 6);
        switch (choice) {
            case 1 -> adsCategory = AdsCategory.PHONE.name();
            case 2 -> adsCategory = AdsCategory.HOME_STUFF.name();
            case 3 -> adsCategory = AdsCategory.STATIONARY.name();
            case 4 -> adsCategory = AdsCategory.CLOTHE.name();
            case 5 -> adsCategory = AdsCategory.CAR.name();
            default -> menu(sc, seller);
        }
        return adsCategory;
    }

    private void showAdsCategory() {
        System.out.println("1. " + AdsCategory.PHONE.name());
        System.out.println("2. " + AdsCategory.HOME_STUFF.name());
        System.out.println("3. " + AdsCategory.STATIONARY.name());
        System.out.println("4. " + AdsCategory.CLOTHE.name());
        System.out.println("5. " + AdsCategory.CAR.name());
        System.out.println("0. Back");
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

    private void showSellerMenu() {
        System.out.println("==============================================================================================================");
        System.out.println("1. Profile");
        System.out.println("2. Available ads");
        System.out.println("3. Add ad");
        System.out.println("4. History");
        System.out.println("5. Massage");
        System.out.println("6. Feedbacks");
        System.out.println("0. Exit");
        System.out.println("==============================================================================================================");
    }

    private void showProfileOption() {
        System.out.println("==============================================================================================================");
        System.out.println("1. your information");
        System.out.println("2. Edit information");
        System.out.println("3. Wallet");
        System.out.println("0. Back");
        System.out.println("==============================================================================================================");
    }

    private void showWalletOption() {
        System.out.println("==============================================================================================================");
        System.out.println("1. Check wallet");
        System.out.println("2. Withdraw money");
        System.out.println("0. Back");
        System.out.println("==============================================================================================================");
    }
}