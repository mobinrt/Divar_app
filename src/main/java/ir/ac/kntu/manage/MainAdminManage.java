package ir.ac.kntu.manage;

import ir.ac.kntu.Main;
import ir.ac.kntu.util.Admin;
import ir.ac.kntu.util.MainAdmin;
import ir.ac.kntu.util.UsersRole;

import java.util.Scanner;

public class MainAdminManage extends AdminManage {
    @Override
    public void adminMenu(Scanner sc, Admin admin) {
        showAdminMenu();
        int choice = getChoice(sc, 8);
        switch (choice) {
            case 1 -> {
                generalEdit(sc, admin, UsersRole.CUSTOMER);
                adminMenu(sc, admin);
            }
            case 2 -> {
                sellerEdit(sc, admin);
                adminMenu(sc, admin);
            }
            case 3 -> {
                generalEdit(sc, admin, UsersRole.DELIVERY);
                adminMenu(sc, admin);
            }
            case 4 -> {
                generalEdit(sc, admin, UsersRole.ADMIN);
                adminMenu(sc, admin);
            }
            case 5 -> {
                adsEdit(sc, admin);
                adminMenu(sc, admin);
            }
            case 6 -> {
                reqListOption(sc, admin);
                adminMenu(sc, admin);
            }
            case 7 ->
            default -> Main.getRunManage().run();
        }
    }
    @Override
    public void showAdminMenu() {
        System.out.println("==============================================================================================================");
        System.out.println("1. Customers");
        System.out.println("2. Sellers");
        System.out.println("3. Deliveries");
        System.out.println("4. Admins");
        System.out.println("5. All ads");
        System.out.println("6. Requests");
        System.out.println("7. Product deliver");
        System.out.println("0. Exit");
        System.out.println("==============================================================================================================");
    }

}
