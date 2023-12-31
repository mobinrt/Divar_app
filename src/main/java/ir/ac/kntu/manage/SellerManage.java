package ir.ac.kntu.manage;

import ir.ac.kntu.Main;
import ir.ac.kntu.util.*;

import java.util.Scanner;

public class SellerManage {
    /**
     * @param sc     - scan input
     * @param seller - online seller
     */
    public void sellerMenu(Scanner sc, Seller seller) {
        showSellerMenu();
        int choice = getChoice(sc, 5);
        switch (choice) {
            case 1 -> {
                sellerProfile(sc, seller);
                sellerMenu(sc, seller);
            }
            case 2 -> {
                seller.showAvailableAds();
                sellerMenu(sc, seller);
            }
            case 3 -> {
                addAd(sc, seller);
                sellerMenu(sc, seller);
            }
            case 4 -> {
                seller.showHistory();
                sellerMenu(sc, seller);
            }
            default -> Main.getRunManage().run();
        }
    }

    /**
     * @param sc            - scan input
     * @param currentSeller - online seller
     */
    public void addAd(Scanner sc, Seller currentSeller) {
        String adsCategory = showAdsCategory(sc, currentSeller);
        System.out.print("Enter product's name: ");
        String name = sc.next();
        System.out.print("Enter product's price: ");
        int price = sc.nextInt();
        Product product = currentSeller.addToRequestList(adsCategory, name, currentSeller, price);
        System.out.println("Successfully done.");
        System.out.println("Waiting for admin accept...");
        System.out.println("==============================================================================================================");
        Main.getRunManage().getAdminManage().addProductToReq(product);
        sellerMenu(sc, currentSeller);
    }

    private void sellerProfile(Scanner sc, Seller seller) {
        showProfileOption();
        int choice = getChoice(sc, 4);
        switch (choice) {
            case 1 -> {
                System.out.println(seller.toString());
                sellerProfile(sc, seller);
            }
            case 2 -> {
                seller.editUserInfo(sc, seller);
                sellerProfile(sc, seller);
            }
            case 3 -> {
                sellerWalletMenu(sc, seller);
                sellerProfile(sc, seller);
            }
            default -> sellerMenu(sc, seller);
        }
    }

    private void sellerWalletMenu(Scanner sc, Seller seller) {
        showWalletOption();
        int choice = getChoice(sc, 3);
        switch (choice) {
            case 1 -> {
                System.out.println("Your wallet: " + seller.getWallet());
                sellerWalletMenu(sc, seller);
            }
            case 2 -> {
                System.out.println("How much do you want to withdraw money? if you want to back press zero.");
                System.out.print("Enter: ");
                int withDraw = sc.nextInt();
                while (withDraw > seller.getWallet()) {
                    System.out.println("You don't have enough money!!");
                    System.out.println("How much do you want to withdraw money? if you want to back press zero.");
                    System.out.print("Enter: ");
                    withDraw = sc.nextInt();
                }
                seller.setWallet(seller.getWallet() - withDraw);
                System.out.println("Successfully done.");
                System.out.println("==============================================================================================================");
                sellerWalletMenu(sc, seller);
            }
            default -> sellerProfile(sc, seller);
        }
    }

    private String showAdsCategory(Scanner sc, Seller seller) {
        String adsCategory = null;
        System.out.println("1. " + AdsCategory.PHONE.name());
        System.out.println("2. " + AdsCategory.HOME_STUFF.name());
        System.out.println("3. " + AdsCategory.STATIONARY.name());
        System.out.println("4. " + AdsCategory.CLOTHE.name());
        System.out.println("5. " + AdsCategory.CAR.name());
        System.out.println("0. Back");
        int choice = getChoice(sc, 6);
        switch (choice) {
            case 1 -> adsCategory = AdsCategory.PHONE.name();
            case 2 -> adsCategory = AdsCategory.HOME_STUFF.name();
            case 3 -> adsCategory = AdsCategory.STATIONARY.name();
            case 4 -> adsCategory = AdsCategory.CLOTHE.name();
            case 5 -> adsCategory = AdsCategory.CAR.name();
            default -> sellerMenu(sc, seller);
        }
        return adsCategory;
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
        System.out.println("0. Exit");
        System.out.println("==============================================================================================================");
    }

    private void showProfileOption() {
        System.out.println("==============================================================================================================");
        System.out.println("1. Seller information");
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


//    private int checkId(Scanner sc) { in case need in next project
//        int id = sc.nextInt();
//        if (Main.runManage.customerManage.getProducts().isEmpty()) {
//            return id;
//        }
//        for (Product product : Main.runManage.customerManage.getProducts()) {
//            if (id == product.getId()) {
//                System.out.println("This id has been taken.");
//                return checkId(sc);
//            }
//        }
//        return id;
//    }
