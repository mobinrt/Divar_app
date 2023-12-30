package ir.ac.kntu.util;

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
}
