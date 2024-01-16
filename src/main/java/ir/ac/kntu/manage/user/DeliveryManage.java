package ir.ac.kntu.manage.user;

import ir.ac.kntu.Main;
import ir.ac.kntu.manage.Choice;
import ir.ac.kntu.util.*;
import ir.ac.kntu.util.enums.AdsCategory;
import ir.ac.kntu.util.enums.VehicleType;
import ir.ac.kntu.util.users.Delivery;
import ir.ac.kntu.util.users.User;

import java.util.Scanner;

public class DeliveryManage implements Menu, Choice {
    @Override
    public void menu(Scanner sc, User user) {
        Delivery delivery = (Delivery) user;
        showDeliveryMenu();
        int choice = getChoice(sc, 3);
        switch (choice) {
            case 1 -> {
                profile(sc, delivery);
                menu(sc, delivery);
            }
            case 2 -> {
                delivery.showHistory();
                menu(sc, delivery);
            }
            default -> Main.getRunManage().run();
        }
    }

    @Override
    public void profile(Scanner sc, User user) {
        Delivery delivery = (Delivery) user;
        showProfileOption();
        int choice = getChoice(sc, 4);
        switch (choice) {
            case 1 -> {
                System.out.println(delivery.toString());
                profile(sc, delivery);
            }
            case 2 -> {
                delivery.editUserInfo(sc, delivery);
                profile(sc, delivery);
            }
            case 3 -> {
                walletMenu(sc, delivery);
                profile(sc, delivery);
            }
            default -> menu(sc, delivery);
        }
    }

    @Override
    public void walletMenu(Scanner sc, User user) {
        Delivery delivery = (Delivery) user;
        showWalletOption();
        int choice = getChoice(sc, 3);
        switch (choice) {
            case 1 -> {
                System.out.println("Your wallet: " + delivery.getWallet());
                walletMenu(sc, delivery);
            }
            case 2 -> {
                withdrawMoney(sc, delivery);
                walletMenu(sc, user);
            }
            default -> profile(sc, delivery);
        }
    }

    public boolean isAvailableDelivery(Product product) {
        for (User user : Main.getRunManage().getUsers()) {
            if (user instanceof Delivery delivery) {
                if (!(delivery.isAvailable()))
                    continue;
                if (product.getAdsCategory().matches(AdsCategory.HOME_STUFF.toString()) &&
                        delivery.getVehicleType().equals(VehicleType.MOTOR))
                    continue;
            }
            return true;
        }
        return false;
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

    private void showDeliveryMenu() {
        System.out.println("==============================================================================================================");
        System.out.println("1. Profile");
        System.out.println("2. History");
        System.out.println("0. Exit");
        System.out.println("==============================================================================================================");

    }
}
