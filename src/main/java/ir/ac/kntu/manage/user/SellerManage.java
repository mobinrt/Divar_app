package ir.ac.kntu.manage.user;

import ir.ac.kntu.Main;
import ir.ac.kntu.manage.Choice;
import ir.ac.kntu.manage.ShowMenu;
import ir.ac.kntu.util.*;
import ir.ac.kntu.util.enums.AdsCategory;
import ir.ac.kntu.util.users.Seller;
import ir.ac.kntu.util.users.User;

import java.util.Scanner;

public class SellerManage implements UserSimilar, Choice {
    /**
     * @param sc   - scan input
     * @param user - online seller
     */
    @Override
    public void menu(Scanner sc, User user) {
        Seller seller = (Seller) user;
        ShowMenu.showMenu("Profile, Available ads, Add ad, History, Massage, Feedbacks");
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
        ShowMenu.showMenu("Your information, Edit information, Wallet");
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
        ShowMenu.showMenu("Check wallet, Withdraw money");
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
        ShowMenu.showMenuEnum(AdsCategory.values());
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
}