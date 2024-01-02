package ir.ac.kntu.manage;

import ir.ac.kntu.Main;
import ir.ac.kntu.util.Admin;
import ir.ac.kntu.util.MainAdmin;
import ir.ac.kntu.util.UsersRole;

import java.util.Scanner;

public class MainAdminManage extends AdminManage {
    @Override
    public void adminMenu(Scanner sc, Admin Admin) {
        MainAdmin mainAdmin = (MainAdmin) Admin;
        showAdminMenu();
        int choice = getChoice(sc, 9);
        switch (choice) {
            case 1 -> {
                adminProfile(sc, mainAdmin);
                adminMenu(sc, mainAdmin);
            }
            case 2 -> {
                generalEdit(sc, mainAdmin, UsersRole.CUSTOMER);
                adminMenu(sc, mainAdmin);
            }
            case 3 -> {
                sellerEdit(sc, mainAdmin);
                adminMenu(sc, mainAdmin);
            }
            case 4 -> {
                generalEdit(sc, mainAdmin, UsersRole.DELIVERY);
                adminMenu(sc, mainAdmin);
            }
            case 5 -> {
                generalEdit(sc, mainAdmin, UsersRole.ADMIN);
                adminMenu(sc, mainAdmin);
            }
            case 6 -> {
                adsEdit(sc, mainAdmin);
                adminMenu(sc, mainAdmin);
            }
            case 7 -> {
                reqListOption(sc, mainAdmin);
                adminMenu(sc, mainAdmin);
            }
            case 8 -> {

            }
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
