package Models;

public class User {
    private int userID;
    private String username;
    private String email;
    private String passoword;
    private String firstName;
    private String lastName;
    private String role;

    public String getRole() {
        return role;
    }

    public User(int userID, String username, String email, String passoword, String firstName, String lastName,
            String role) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.passoword = passoword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public User(String username, String email, String passoword, String firstName, String lastName) {
        this.username = username;
        this.email = email;
        this.passoword = passoword;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassoword() {
        return passoword;
    }

    public void setPassoword(String passoword) {
        this.passoword = passoword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
