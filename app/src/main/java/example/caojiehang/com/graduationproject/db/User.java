package example.caojiehang.com.graduationproject.db;

public class User {
    private int id;
    private String userName;
    private String userPwd;
    private String userEmail;

    public User(String userName,String userPwd,String userEmail){
        super();
        this.userName = userName;
        this.userPwd = userPwd;
        this.userEmail = userEmail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountname() {
        return userName;
    }

    public void setAccountname(String accountname) {
        this.userName = accountname;
    }

    public String getAccountpassword() {
        return userPwd;
    }

    public void setAccountpassword(String accountpassword) {
        this.userPwd = accountpassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
