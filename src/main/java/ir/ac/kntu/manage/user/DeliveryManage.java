package ir.ac.kntu.manage.user;

import ir.ac.kntu.Main;
import ir.ac.kntu.manage.Choice;
import ir.ac.kntu.manage.ShowMenu;
import ir.ac.kntu.util.*;
import ir.ac.kntu.util.enums.AdsCategory;
import ir.ac.kntu.util.enums.VehicleType;
import ir.ac.kntu.util.users.Delivery;
import ir.ac.kntu.util.users.User;

import java.util.Scanner;

public class DeliveryManage implements UsersCommonMethods, Choice {
    @Override
    public void menu(Scanner sc, User user) {
        Delivery delivery = (Delivery) user;
        ShowMenu.showMenu("Profile, History");
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
        ShowMenu.showMenu("Your information, Edit information, Wallet");
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
        ShowMenu.showMenu("Check wallet, Withdraw money");
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
        return Main.getRunManage().getUsers().stream()
                .filter(user -> user instanceof Delivery)
                .map(user -> (Delivery) user)
                .filter(Delivery::isAvailable)
                .noneMatch(delivery -> product.getAdsCategory().matches(AdsCategory.HOME_STUFF.toString()) &&
                        delivery.getVehicleType().equals(VehicleType.MOTOR));
    }
}
