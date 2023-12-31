package ir.ac.kntu.manage;

import ir.ac.kntu.Main;
import ir.ac.kntu.util.Delivery;

import java.util.Scanner;

public class DeliveryManage {

    public void menu(Scanner sc, Delivery delivery) {
        showDeliveryMenu();
        int choice = getChoice(sc, 2);
        if (choice == 1) {
            profile(sc, delivery);
            menu(sc, delivery);
        } else {
            Main.getRunManage().run();
        }
    }

    private void profile(Scanner sc, Delivery delivery) {
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
                WalletMenu(sc, delivery);
                profile(sc, delivery);
            }
            default -> menu(sc, delivery);
        }
    }

    private void WalletMenu(Scanner sc, Delivery delivery) {
        showWalletOption();
        int choice = getChoice(sc, 3);
        switch (choice) {
            case 1 -> {
                System.out.println("Your wallet: " + delivery.getWallet());
                WalletMenu(sc, delivery);
            }
            case 2 -> {
                System.out.println("How much do you want to withdraw money? if you want to back press zero.");
                System.out.print("Enter: ");
                int withDraw = sc.nextInt();
                while (withDraw > delivery.getWallet()) {
                    System.out.println("You don't have enough money!!");
                    System.out.println("How much do you want to withdraw money? if you want to back press zero.");
                    System.out.print("Enter: ");
                    withDraw = sc.nextInt();
                }
                delivery.setWallet(delivery.getWallet() - withDraw);
                System.out.println("Successfully done.");
                System.out.println("==============================================================================================================");
                WalletMenu(sc, delivery);
            }
            default -> profile(sc, delivery);
        }
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
        System.out.println("0. Exit");
        // delivered product
        System.out.println("==============================================================================================================");

    }
}
