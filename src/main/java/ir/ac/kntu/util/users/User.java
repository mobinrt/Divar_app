package ir.ac.kntu.util.users;

import ir.ac.kntu.Main;
import ir.ac.kntu.manage.Input;
import ir.ac.kntu.manage.RunManage;
import ir.ac.kntu.util.ChatRoom;
import ir.ac.kntu.util.enums.UsersRole;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class User implements Input {
    private UsersRole role;
    private String userName;
    private String password;
    private String phoneNumber;
    private String email;
    private double wallet;
    private int x = -1;
    private int y = -1;
    private final Point[][] location = new Point[1][1];
    private boolean isMainAdmin;
    private ArrayList<User> users;
    private final ArrayList<ChatRoom> allChats;

    public User(String userName, String password, String phoneNumber, String email) {
        this.userName = userName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        wallet = 0;
        isMainAdmin = false;
        setRole(UsersRole.USER);
        users = new ArrayList<>();
        allChats = new ArrayList<>();
    }

    public void editUserInfo(Scanner sc, User currentUser) {
        sc.nextLine();
        System.out.print("Enter new username: ");
        String userName = sc.nextLine();
        System.out.print("Enter new password: ");
        String password = sc.nextLine();
        System.out.print("Enter your password again: ");
        String confirmPassword = sc.nextLine();
        if (!confirmPassword.matches(password)) {
            System.out.println("Enter the same password");
            System.out.println("==============================================================================================================");
            editUserInfo(sc, currentUser);
            return;
        }
        System.out.print("Enter new phone number: ");
        String phoneNumber = sc.nextLine();
        System.out.print("Enter new email: ");
        String email = sc.nextLine();
        if (!currentUser.getUserName().matches(userName)) {
            if (Main.getRunManage().checkInfo(userName)) {
                editUserInfo(sc, currentUser);
                return;
            }
        }
        if (RunManage.checkInfo(password, phoneNumber, email)) {
            currentUser.setUserName(userName);
            currentUser.setPassword(password);
            currentUser.setPhoneNumber(phoneNumber);
            currentUser.setEmail(email);
            System.out.println("Successfully done.");
            System.out.println("===========================================================================================================");
        }
    }

    public void setLocation(Scanner sc, User user) {
        int x = (int) inputDouble(sc, "Set your location relative to the X-axis: ");
        int y = (int) inputDouble(sc, "Set your location relative to the Y-axis: ");
        if (!(x >= 0 && y >= 0)) {
            System.out.println("Follow the rule!");
            setLocation(sc, user);
            return;
        }
        user.setX(x);
        user.setY(y);
        user.setLocation();
        System.out.println("Successfully done.");
        System.out.println("==============================================================================================================");
    }

    public double calculateDistance(User user1, User user2) {
        int x1 = user1.getX();
        int y1 = user1.getY();
        int x2 = user2.getX();
        int y2 = user2.getY();
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    @Override
    public String toString() {
        return "{ Role: " + getRole() + ", " +
                "userName: " + getUserName() + ", " +
                "Password: " + getPassword() + ", " +
                "Phone Number: " + getPhoneNumber() + ", " +
                "Email: " + getEmail() + ", " +
                "Wallet: " + getWallet() + ", " +
                "Location: " + "[" + getX() + ", " + getY() + "]" + " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        User user = (User) o;
        return userName.equals(user.userName);
    }

    @Override
    public int hashCode() {
        int result = 1;
        int prime = 17;
        result = result * prime + userName.hashCode();
        return result;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public double getWallet() {
        return wallet;
    }

    public void setWallet(double wallet) {
        this.wallet = wallet;
    }

    public UsersRole getRole() {
        return role;
    }

    public void setRole(UsersRole role) {
        this.role = role;
    }

    public void setLocation() {
        location[0][0] = new Point(x, y);
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public boolean isMainAdmin() {
        return isMainAdmin;
    }

    public void setMainAdmin(boolean mainAdmin) {
        isMainAdmin = mainAdmin;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<ChatRoom> getAllChats() {
        return allChats;
    }
}
