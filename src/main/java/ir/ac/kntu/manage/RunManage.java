package ir.ac.kntu.manage;

import ir.ac.kntu.util.Admin;
import ir.ac.kntu.util.Customer;
import ir.ac.kntu.util.Seller;

import java.util.Scanner;

public class RunManage {
    private final AdminManage adminManage = new AdminManage();
    private final SellerManage sellerManage = new SellerManage();
    private final CustomerManage customerManage = new CustomerManage();

    public void run() {
        Scanner sc = new Scanner(System.in);
        Admin currentAdmin;
        showFirstMenu();
        int type = getChoice(sc, 5);
        switch (type) {
            case 1:
                currentAdmin = adminManage.signInAdmin(sc);
                adminManage.adminMenu(sc, currentAdmin);
                break;
            case 2:
                handleSeller(sc);
                break;
            case 3:
                handleCustomer(sc);
                break;
            case 4:
                guest(sc);
                break;
            default:
                System.exit(0);
                break;
        }
        sc.close();
    }

    private void guest(Scanner sc) {
        customerManage.showAdsList();
        System.out.print("Enter zero if you want to back: ");
        int back;
        do {
            back = sc.nextInt();
            if (back == 0) {
                run();
                return;
            }
            System.out.println("Invalid input!!");
            back = sc.nextInt();
        } while (back == 0);
    }

    /**
     * @param sc     - scan input
     * @param seller - identify current seller
     */
    private void handleSeller(Scanner sc, Seller seller) {
        sellerManage.sellerMenu(sc, seller);
    }

    private void handleSeller(Scanner sc) {
        Seller currentSeller;
        showSignMenu();
        int choice = getChoice(sc, 3);
        if (choice == 1) {
            currentSeller = sellerManage.signInSeller(sc);
            handleSeller(sc, currentSeller);
        }
        if (choice == 2) {
            currentSeller = sellerManage.addSeller(sc);
            handleSeller(sc, currentSeller);
        }
        if (choice == 0) {
            run();
        }
    }

    private void handleCustomer(Scanner sc) {
        Customer currentCustomer;
        showSignMenu();
        int choice = getChoice(sc, 3);
        if (choice == 1) {
            currentCustomer = customerManage.signInCustomer(sc);
            handleCustomer(sc, currentCustomer);
        }
        if (choice == 2) {
            currentCustomer = customerManage.addCustomer(sc);
            handleCustomer(sc, currentCustomer);
        }
        if (choice == 0) {
            run();
        }
    }

    private void handleCustomer(Scanner sc, Customer customer) {
        customerManage.customerMenu(sc, customer);
    }

    /**
     * @param scan  - scan input
     * @param bound - limit the top
     * @return int
     */
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

    private void showSignMenu() {
        System.out.println("==============================================================================================================");
        System.out.println("1. Sign in");
        System.out.println("2. Sign up");
        System.out.println("0. Back");
        System.out.println("==============================================================================================================");
    }

    private void showFirstMenu() {
        System.out.println("==============================================================================================================");
        System.out.println("1. Admin");
        System.out.println("2. Seller");
        System.out.println("3. Customer");
        System.out.println("4. Guest");
        System.out.println("0. Exit");
        System.out.println("==============================================================================================================");
    }

    public AdminManage getAdminManage() {
        return adminManage;
    }

    public CustomerManage getCustomerManage() {
        return customerManage;
    }

    public SellerManage getSellerManage() {
        return sellerManage;
    }
}