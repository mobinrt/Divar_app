package ir.ac.kntu.manage;

import ir.ac.kntu.Main;
import ir.ac.kntu.util.enums.UsersRole;
import ir.ac.kntu.util.users.Admin;
import ir.ac.kntu.util.users.MainAdmin;
import ir.ac.kntu.util.users.User;

import java.util.Scanner;

public class MainAdminManage extends AdminManage {
    @Override
    public void adminMenu(Scanner sc, Admin admin) {
        showAdminMenu();
        int choice = getChoice(sc, 10);
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
                adminMenu(sc, admin);
            }
            default -> Main.getRunManage().run();
        }
    }

    private void salaryPay(Scanner sc, MainAdmin mainAdmin) {
        System.out.println("Witch type of user you want to pay?");
        System.out.println("1. " + UsersRole.DELIVERY);
        System.out.println("2. " + UsersRole.ADMIN);
        System.out.println("0. Back");
        int choice = getChoice(sc, 3);
        UsersRole usersRole;
        switch (choice) {
            case 1 -> {
                usersRole = UsersRole.DELIVERY;
                salaryPay(sc, mainAdmin, usersRole);
                adminMenu(sc, mainAdmin);
            }
            case 2 -> {
                usersRole = UsersRole.ADMIN;
                salaryPay(sc, mainAdmin, usersRole);
                adminMenu(sc, mainAdmin);
            }
            case 3 -> adminMenu(sc, mainAdmin);
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
            adminMenu(sc, mainAdmin);
        return findUser(choice, usersRole);
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
        System.out.println("9. Salary pay");
        System.out.println("0. Exit");
        System.out.println("==============================================================================================================");
    }
}
