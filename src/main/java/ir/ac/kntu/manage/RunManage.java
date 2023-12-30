package ir.ac.kntu.manage;

import ir.ac.kntu.util.*;

import java.util.ArrayList;
import java.util.Scanner;

public class RunManage {
    private final AdminManage adminManage = new AdminManage();
    private final SellerManage sellerManage = new SellerManage();
    private final CustomerManage customerManage = new CustomerManage();
    private ArrayList<User> users;

    public RunManage() {
        users = new ArrayList<>();
        users.add(new Admin("a", "a", "a", "a"));
        users.add(new Seller("s", "s", "s", "s"));
        users.add(new Customer("c", "c", "c", "c"));
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        User user;
        showSignMenu();
        int choice = getChoice(sc, 4);
        switch (choice) {
            case 1 -> {
                user = signIn(sc);
                handleUserMenu(sc, user);
            }
                case 2 -> {
                user = addUser(sc);
                user = getRole(sc, user.getUserName(), user.getPassword(),
                        user.getPhoneNumber(), user.getEmail());
                handleUserMenu(sc, user);
            }
            case 3 -> guest(sc);
            default -> System.exit(0);
        }
        sc.close();
    }

    public User signIn(Scanner sc) {
        sc.nextLine();
        System.out.print("Enter your username: ");
        String userName = sc.nextLine();
        System.out.print("Enter your password: ");
        String password = sc.nextLine();
        User currentUser = findUser(userName, password);
        if (currentUser == null) {
            System.out.println("Invalid username or password!!");
            System.out.println("==============================================================================================================");
            return signIn(sc);
        }
        return currentUser;
    }

    public User findUser(String userName, String password) {
        for (User user : users) {
            if (userName.equals(user.getUserName()) && password.equals(user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    private void handleUserMenu(Scanner sc, User user) {
        if (user instanceof Admin currentAdmin) {
            adminManage.adminMenu(sc, currentAdmin);
            return;
        }
        if (user instanceof Customer currentCustomer) {
            customerManage.customerMenu(sc, currentCustomer);
            return;
        }
        if (user instanceof Seller currenrSeller) {
            sellerManage.sellerMenu(sc, currenrSeller);
        }
    }

    public User addUser(Scanner sc) {
        sc.nextLine();
        System.out.print("Enter your username: ");
        String userName = sc.nextLine();
        System.out.print("Enter customer password: ");
        String password = sc.nextLine();
        System.out.print("Enter your password again: ");
        String confirmPassword = sc.nextLine();
        if (!confirmPassword.matches(password)) {
            System.out.println("Enter  the same password");
            System.out.println("==============================================================================================================");
            return addUser(sc);
        }
        System.out.print("Enter your phone number: ");
        String phoneNumber = sc.nextLine();
        System.out.print("Enter your email: ");
        String email = sc.nextLine();
        User user = new User(userName, password, phoneNumber, email);
        if (!checkInfo(userName)) {
            System.out.println("==============================================================================================================");
            return addUser(sc);
        }
        if (user.checkInfo(password, phoneNumber, email)) {
            users.add(user);
            System.out.println("Successfully done.");
            return user;
        } else {
            System.out.println("==============================================================================================================");
            return addUser(sc);
        }
    }

    public boolean checkInfo(String userName) {
        for (User user : users) {
            if (userName.equals(user.getUserName())) {
                System.out.println("This user name had taken please try again.");
                return false;
            }
        }
        return true;
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

    private User getRole(Scanner sc, String userName, String password, String phoneNumber, String email) {
        showRoleMenu();
        User user = null;
        System.out.println("which one is your role?");
        int choice = getChoice(sc, 4);
        switch (choice) {
            case 1 -> {
                user = new Admin(userName, password, phoneNumber, email);
                user.setRole(UsersRole.ADMIN);
            }
            case 2 -> {
                user = new Seller(userName, password, phoneNumber, email);
                user.setRole(UsersRole.SELLER);
            }
            case 3 -> {
                user = new Customer(userName, password, phoneNumber, email);
                user.setRole(UsersRole.CUSTOMER);
            }
            default -> run();
        }
        return user;
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
        System.out.println("3. Guest");
        System.out.println("0. Exit");
        System.out.println("==============================================================================================================");
    }

    private void showRoleMenu() {
        System.out.println("==============================================================================================================");
        System.out.println("1. Admin");
        System.out.println("2. Seller");
        System.out.println("3. Customer");
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

    public ArrayList<User> getUsers() {
        return users;
    }
}