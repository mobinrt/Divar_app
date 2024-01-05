package ir.ac.kntu.util;

public class MainAdmin extends Admin {

    /**
     * @param userName    - admin name
     * @param password    - admin password
     * @param phoneNumber - admin phone number
     * @param email       - admin email
     */
    public MainAdmin(String userName, String password, String phoneNumber, String email) {
        super(userName, password, phoneNumber, email);
        setRole(UsersRole.MAIN_ADMIN);
        setMainAdmin(true);
    }
}
