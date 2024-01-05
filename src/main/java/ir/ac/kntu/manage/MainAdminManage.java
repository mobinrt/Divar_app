package ir.ac.kntu.manage;

import ir.ac.kntu.Main;
import ir.ac.kntu.util.Admin;
import ir.ac.kntu.util.MainAdmin;
import ir.ac.kntu.util.Product;
import ir.ac.kntu.util.UsersRole;

import java.util.ArrayList;
import java.util.Scanner;

public class MainAdminManage extends AdminManage {
    public MainAdminManage() {
//        getReq() = new ArrayList<>();

    }

    @Override
    public void adminMenu(Scanner sc, Admin admin) {
        getReq().addAll(super.getReq());
        getDeliveryReq().addAll(super.getDeliveryReq());
        showAdminMenu();
        int choice = getChoice(sc, 9);
        switch (choice) {
            case 1 -> adminProfile(sc, admin);
            case 2 -> generalEdit(sc, admin, UsersRole.CUSTOMER);
            case 3 -> sellerEdit(sc, admin);
            case 4 -> generalEdit(sc, admin, UsersRole.DELIVERY);
            case 5 -> generalEdit(sc, admin, UsersRole.ADMIN);
            case 6 -> adsEdit(sc, admin);
            case 7 -> reqListOption(sc, admin);
            case 8 -> deliverProduct(sc, admin);
            default -> Main.getRunManage().run();
        }
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
