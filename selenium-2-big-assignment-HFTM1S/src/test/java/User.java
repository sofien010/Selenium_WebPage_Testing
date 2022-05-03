public class User {

    private int id;

    private String userName;

    private String password;

    public String getuserName() {
        return userName;
    }

    public void setuserName(String userName) {
        this.userName = userName;
    }

    public String getpassword() {
        return password;
    }

    public void setpassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", userName=" + userName + ", password=" + password + "]";
    }

    public User() {
        System.out.println("User is created");
    }

    public void updateUserData() {

        ReadConfigFile rd = new ReadConfigFile();

        this.setuserName(rd.loadProperties().getProperty("email"));
        this.setpassword(rd.loadProperties().getProperty("password"));

    }

}