package ir.ac.kntu.manage;

import ir.ac.kntu.Main;
import ir.ac.kntu.util.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class MainAdminManage extends AdminManage {
    @Override
    public void adminMenu(Scanner sc, Admin admin) {
        showAdminMenu();
        int choice = getChoice(sc, 9);
        MainAdmin mainAdmin = (MainAdmin) admin;
        switch (choice) {
            case 1 -> {
                adminProfile(sc, mainAdmin);
                adminMenu(sc, admin);
            }
            case 2 -> {
                generalEdit(sc, mainAdmin, UsersRole.CUSTOMER);
                adminMenu(sc, admin);
            }
            case 3 -> {
                sellerEdit(sc, mainAdmin);
                adminMenu(sc, admin);
            }
            case 4 -> {
                generalEdit(sc, mainAdmin, UsersRole.DELIVERY);
                adminMenu(sc, admin);
            }
            case 5 -> {
                generalEdit(sc, mainAdmin, UsersRole.ADMIN);
                adminMenu(sc, admin);
            }
            case 6 -> {
                adsEdit(sc, mainAdmin);
                adminMenu(sc, admin);
            }
            case 7 -> {
                reqListOption(sc, mainAdmin);
                adminMenu(sc, admin);
            }
            case 8 -> {
                deliverProduct(sc, mainAdmin);
                adminMenu(sc, admin);
            }
            case 9 -> {
                salaryPay(sc, mainAdmin);
                adminMenu(sc,admin);
            }
            default -> Main.getRunManage().run();
        }
    }

    private void salaryPay(Scanner sc, MainAdmin mainAdmin) {

    }

    @Override
    public void showAdminMenu() {
        System.out.println("==============================================================================================================");
        System.out.println("1. Profile");
        System.out.println("2. Customers");
        System.out.println("3. Sellers");
        System.out.println("4. Deliveries");
        System.out.println("5. Admins");
        System.out.println("6. All ads");
        System.out.println("7. Requests");
        System.out.println("8. Product deliver");
        System.out.println("0. Exit");
        System.out.println("==============================================================================================================");
    }
}
