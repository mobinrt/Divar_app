package ir.ac.kntu.manage.user;

import ir.ac.kntu.Main;
import ir.ac.kntu.manage.Choice;
import ir.ac.kntu.manage.ShowMenu;
import ir.ac.kntu.util.Product;
import ir.ac.kntu.util.enums.UsersRole;
import ir.ac.kntu.util.users.*;

import java.util.Scanner;

public class MainAdminManage extends AdminManage implements UsersCommonMethods, Choice {
    @Override
    public void menu(Scanner sc, User user) {
        MainAdmin mainAdmin = (MainAdmin) user;
        ShowMenu.showMenu("Profile, Customers, Sellers, Deliveries, Admins, All ads, Requests, Deliver product, Salary pay");
        int choice = getChoice(sc, 10);
        switch (choice) {
            case 1 -> {
                profile(sc, mainAdmin);
                menu(sc, mainAdmin);
            }
            case 2 -> {
                generalEdit(sc, mainAdmin, UsersRole.CUSTOMER);
                menu(sc, mainAdmin);
            }
            case 3 -> {
                sellerEdit(sc, mainAdmin);
                menu(sc, mainAdmin);
            }
            case 4 -> {
                generalEdit(sc, mainAdmin, UsersRole.DELIVERY);
                menu(sc, mainAdmin);
            }
            case 5 -> {
                generalEdit(sc, mainAdmin, UsersRole.ADMIN);
                menu(sc, mainAdmin);
            }
            case 6 -> {
                adsEdit(sc, mainAdmin);
                menu(sc, mainAdmin);
            }
            case 7 -> {
                reqListOption(sc, mainAdmin);
                menu(sc, mainAdmin);
            }
            case 8 -> {
                deliverProduct(sc, mainAdmin);
                menu(sc, mainAdmin);
            }
            case 9 -> {
                salaryPay(sc, mainAdmin);
                menu(sc, mainAdmin);
            }
            default -> Main.getRunManage().run();
        }
    }

    private void salaryPayText() {
        System.out.println("Witch type of user you want to pay?");
        System.out.println("1. " + UsersRole.DELIVERY);
        System.out.println("2. " + UsersRole.ADMIN);
        System.out.println("0. Back");
    }

    private void salaryPay(Scanner sc, MainAdmin mainAdmin) {
        salaryPayText();
        int choice = getChoice(sc, 3);
        UsersRole usersRole;
        switch (choice) {
            case 1 -> {
                usersRole = UsersRole.DELIVERY;
                salaryPay(sc, mainAdmin, usersRole);
                menu(sc, mainAdmin);
            }
            case 2 -> {
                usersRole = UsersRole.ADMIN;
                salaryPay(sc, mainAdmin, usersRole);
                menu(sc, mainAdmin);
            }
            case 3 -> menu(sc, mainAdmin);
        }
    }

    private void salaryPay(Scanner sc, MainAdmin mainAdmin, UsersRole usersRole) {
        User user = chooseUser(sc, mainAdmin, usersRole);
        int salary;
        System.out.print("Enter the payment amount: ");
        salary = sc.nextInt();
        while (mainAdmin.getWallet() < salary) {
            System.out.println("You don't have enough money to pay!");
            System.out.print("Enter the payment amount: ");
            salary = sc.nextInt();
        }
        mainAdmin.setWallet(mainAdmin.getWallet() - salary);
        user.setWallet(salary);
        System.out.println("Successfully done.");
        System.out.println("==============================================================================================================");
    }

    private User chooseUser(Scanner sc, MainAdmin mainAdmin, UsersRole usersRole) {
        int length = showUsersList(usersRole);
        System.out.println("Select one of the user to pay or press zero to back");
        int choice = getChoice(sc, length);
        if (choice == 0)
            menu(sc, mainAdmin);
        return findUser(choice, usersRole);
    }

    @Override
    public void makeDeliveryUnavailable(int distanceInKm, Product product, Delivery delivery) {
        super.makeDeliveryUnavailable(distanceInKm, product, delivery);
        Main.getRunManage().getAdminManage().getDeliveryReq().remove(product);
    }

    @Override
    public void acceptReq(Scanner sc, Admin admin) {
        showReqList(sc, admin, getReq());
        System.out.println("Which product do you want to accept?");
        int choice = getChoice(sc, getReq().size() + 1);
        if (choice == 0) {
            reqListOption(sc, admin);
            return;
        }
        Product product = getReq().get(--choice);
        product.setIsVisible(true);
        Main.getRunManage().getCustomerManage().getProducts().add(product);
        getReq().remove(product);
        Main.getRunManage().getAdminManage().getReq().remove(product);
    }

    @Override
    public void deniedReq(Scanner sc, Admin admin) {
        showReqList(sc, admin, getReq());
        System.out.println("Which product do you want to delete?");
        int choice = getChoice(sc, getReq().size() + 1);
        if (choice == 0) {
            reqListOption(sc, admin);
            return;
        }
        Product deleteProduct = getReq().get(--choice);
        Seller seller = getReq().get(choice).getSeller();
        seller.getProducts().remove(deleteProduct);
        getReq().remove(deleteProduct);
        Main.getRunManage().getAdminManage().getReq().remove(deleteProduct);
    }
}