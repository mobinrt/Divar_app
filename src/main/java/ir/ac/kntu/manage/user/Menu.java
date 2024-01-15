package ir.ac.kntu.manage.user;

import ir.ac.kntu.util.users.User;

import java.util.Scanner;

public interface Menu {
    void menu(Scanner sc, User user);

    void profile(Scanner sc, User user);

    void walletMenu(Scanner sc, User user);

    default void withdrawMoney(Scanner sc, User user) {
        System.out.println("How much do you want to withdraw money? if you want to back press zero.");
        System.out.print("Enter: ");
        int withdraw = sc.nextInt();
        while (withdraw > user.getWallet()) {
            System.out.println("You don't have enough money!!");
            System.out.println("How much do you want to withdraw money? if you want to back press zero.");
            System.out.print("Enter: ");
            withdraw = sc.nextInt();
        }
        user.setWallet(user.getWallet() - withdraw);
        System.out.println("Successfully done.");
        System.out.println("==============================================================================================================");
    }
}
