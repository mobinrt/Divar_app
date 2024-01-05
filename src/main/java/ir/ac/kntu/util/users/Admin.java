package ir.ac.kntu.util.users;

import ir.ac.kntu.util.enums.UsersRole;

public class Admin extends User {
    /**
     * @param userName    - admin name
     * @param password    - admin password
     * @param phoneNumber - admin phone number
     * @param email       - admin email
     */
    public Admin(String userName, String password, String phoneNumber, String email) {
        super(userName, password, phoneNumber, email);
        setRole(UsersRole.ADMIN);
    }

    @Override
    public String toString() {
        return "{ Role: " + getRole() + ", " +
                "userName: " + getUserName() + ", " +
                "Password: " + getPassword() + ", " +
                "Phone Number: " + getPhoneNumber() + ", " +
                "Email: " + getEmail() + ", " +
                "Wallet: " + getWallet() + " }";
    }
}
