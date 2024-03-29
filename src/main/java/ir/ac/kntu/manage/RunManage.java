package ir.ac.kntu.manage;

import ir.ac.kntu.manage.user.*;
import ir.ac.kntu.util.enums.Start;
import ir.ac.kntu.util.enums.UserSignup;
import ir.ac.kntu.util.enums.UsersRole;
import ir.ac.kntu.util.enums.VehicleType;
import ir.ac.kntu.util.users.*;

import java.util.ArrayList;
import java.util.Scanner;

public class RunManage implements Input {
    private final AdminManage adminManage = new AdminManage();
    private final SellerManage sellerManage = new SellerManage();
    private final CustomerManage customerManage = new CustomerManage();
    private final DeliveryManage deliveryManage = new DeliveryManage();
    private final MainAdminManage mainAdminManage = new MainAdminManage();
    private final ChatRoomManage chatRoomManage = new ChatRoomManage();
    private final FeedbackManage feedbackManage = new FeedbackManage();
    private ArrayList<User> users;

    public RunManage() {
        users = new ArrayList<>();
        users.add(new MainAdmin("a", "a", "a", "a"));
        users.add(new Admin("aa", "a", "a", "a"));
        users.add(new Seller("s", "s", "s", "s"));
        users.add(new Seller("ss", "s", "s", "s"));
        users.add(new Customer("c", "c", "c", "c"));
        users.add(new Delivery("d", "d", "d", "d", VehicleType.MOTOR));
        users.add(new Delivery("t", "t", "t", "t", VehicleType.TRUCK));
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        User user;
        ShowMenu.showMenuEnum(Start.values());
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
            case 3 -> {
                customerManage.showAdsList();
                guest(sc);
            }
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
        return users.stream()
                .filter(user -> userName.equals(user.getUserName()) && password.equals(user.getPassword()))
                .findFirst()
                .orElse(null);
    }

    private void handleUserMenu(Scanner sc, User user) {
        if (user instanceof MainAdmin mainAdmin) {
            mainAdminManage.menu(sc, mainAdmin);
            return;
        }
        if (user instanceof Admin currentAdmin) {
            adminManage.menu(sc, currentAdmin);
            return;
        }
        if (user instanceof Customer currentCustomer) {
            customerManage.menu(sc, currentCustomer);
            return;
        }
        if (user instanceof Seller currenrSeller) {
            sellerManage.menu(sc, currenrSeller);
            return;
        }
        if (user instanceof Delivery currentDelivery) {
            deliveryManage.menu(sc, currentDelivery);
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
        if (checkInfo(userName)) {
            System.out.println("==============================================================================================================");
            return addUser(sc);
        }
        if (checkInfo(password, phoneNumber, email)) {
            users.add(user);
            System.out.println("Successfully done.");
            return user;
        } else {
            System.out.println("==============================================================================================================");
            return addUser(sc);
        }
    }

    public boolean checkInfo(String userName) {
        boolean userNameExist = users.stream()
                .anyMatch(user -> userName.equals(user.getUserName()));

        if (userNameExist) {
            System.out.println("This user name had taken please try again.");
        }

        return userNameExist;
    }

    /**
     * @param password    - the user password
     * @param phoneNumber - the user phone number
     * @param email       - the user phone email
     * @return boolean
     */
    public static boolean checkInfo(String password, String phoneNumber, String email) {
        if (!password.matches("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}")) {
            System.out.println("The password should contain at least 8 letters, small, capital letter and number.");
            return false;
        }

        if (!phoneNumber.matches("0\\d{10}")) {
            System.out.println("Please enter phone number properly.");
            return false;
        }

        if (!email.matches(".*@.*\\.com")) {
            System.out.println("Please enter email properly.");
            return false;
        }
        return true;
    }

    private void guest(Scanner sc) {
        int back;
        do {
            back = (int) inputDouble(sc, "Enter 0 for back: ");
            if (back == 0) {
                run();
                return;
            }
            System.out.println("Invalid Input!!");
            back = (int) inputDouble(sc, "Enter 0 for back: ");
        } while (back == 0);
    }

    private User getRole(Scanner sc, String userName, String password, String phoneNumber, String email) {
        ShowMenu.showMenuEnum(UserSignup.values());
        User user = null;
        System.out.println("which one is your role?");
        int choice = getChoice(sc, 5);
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
            case 4 -> {
                user = deliverySignUp(sc, userName, password, phoneNumber, email);
                user.setRole(UsersRole.DELIVERY);
            }
            default -> run();
        }
        assert user != null;
        return user;
    }

    private User deliverySignUp(Scanner sc, String userName, String password, String phoneNumber, String email) {
        User user = null;
        deliverySignUpOption();
        int type = getChoice(sc, 3);
        if (type == 1)
            user = new Delivery(userName, password, phoneNumber, email, VehicleType.MOTOR);
        else if (type == 2)
            user = new Delivery(userName, password, phoneNumber, email, VehicleType.TRUCK);
        else
            return getRole(sc, userName, password, phoneNumber, email);
        return user;
    }

    private void deliverySignUpOption() {
        System.out.println("Witch one is your widget?");
        ShowMenu.showMenuEnum(VehicleType.values());
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

    public DeliveryManage getDeliveryManage() {
        return deliveryManage;
    }

    public MainAdminManage getMainAdminManage() {
        return mainAdminManage;
    }

    public ChatRoomManage getChatRoomManage() {
        return chatRoomManage;
    }

    public FeedbackManage getFeedbackManage() {
        return feedbackManage;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
}