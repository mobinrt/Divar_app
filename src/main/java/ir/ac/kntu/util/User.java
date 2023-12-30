package ir.ac.kntu.util;

import ir.ac.kntu.Main;

import java.util.Scanner;

public class User {
    private UsersRole role;
    private String userName;
    private String password;
    private String phoneNumber;
    private String email;
    private int wallet;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        wallet = 0;
    }

    public User(String userName, String password, String phoneNumber, String email) {
        this(userName, password);
        this.phoneNumber = phoneNumber;
        this.email = email;
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
            System.out.println("Enter  the same password");
            System.out.println("==============================================================================================================");
            editUserInfo(sc, currentUser);
            return;
        }
        System.out.print("Enter new phone number: ");
        String phoneNumber = sc.nextLine();
        System.out.print("Enter new email: ");
        String email = sc.nextLine();

        if (!currentUser.getUserName().matches(userName)) {
            if (!Main.getRunManage().checkInfo(userName)) {
                editUserInfo(sc, currentUser);
                return;
            }
        }
        if (checkInfo(password, phoneNumber, email)) {
            currentUser.setUserName(userName);
            currentUser.setPassword(password);
            currentUser.setPhoneNumber(phoneNumber);
            currentUser.setEmail(email);
            System.out.println("Successfully done.");
            System.out.println("===========================================================================================================");
        }
    }

    /**
     * @param password    - the user password
     * @param phoneNumber - the user phone number
     * @param email       - the user phone email
     * @return boolean
     */
    public boolean checkInfo(String password, String phoneNumber, String email) {
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

    @Override
    public String toString() {
        return "Role: " + getRole() + ", " +
                "userName: " + getUserName() + ", " +
                "Password: " + getPassword() + ", " +
                "Phone Number: " + getPhoneNumber() + ", " +
                "Email: " + getEmail() + ", " +
                "Wallet: " + getWallet() + " }";
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

    public int getWallet() {
        return wallet;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    public UsersRole getRole() {
        return role;
    }

    public void setRole(UsersRole role) {
        this.role = role;
    }
}
