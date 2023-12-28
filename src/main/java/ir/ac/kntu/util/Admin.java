package ir.ac.kntu.util;

public class Admin {
    private final String userName;
    private final String password;

    /**
     * @param userName - the name of user
     * @param password - password of user
     */
    public Admin(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }


    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
